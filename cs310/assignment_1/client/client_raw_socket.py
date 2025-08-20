import ctypes
import ctypes.util
import struct
from a1.utils.libs import decode_message

# create raw socket for linux environment
def raw_socket(port: int = 12345, msg: str = "Default Message") -> str:
    AF_INET = 2
    SOCK_STREAM = 1
    IPPROTO_TCP = 6
    INVALID_SOCKET = -1
    PORT = port

    # load c library for socket functions
    libc = ctypes.CDLL(ctypes.util.find_library('c'), use_errno=True)

    class sockaddr_in(ctypes.Structure):
        _fields_ = [("sin_family", ctypes.c_ushort),
                    ("sin_port", ctypes.c_uint16),
                    ("sin_addr", ctypes.c_uint32),
                    ("sin_zero", ctypes.c_char * 8)]

    def htons(x):
        return ((x & 0xff) << 8) | ((x & 0xff00) >> 8)

    def inet_addr(ip):
        parts = list(map(int, ip.split('.')))
        return (parts[0] | (parts[1] << 8) | (parts[2] << 16) | (parts[3] << 24))

    # create socket
    sock = libc.socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)
    if sock < 0:
        print("[CLIENT] Socket creation failed.")
        exit(1)

    # create server address structure
    addr = sockaddr_in()
    addr.sin_family = AF_INET
    addr.sin_port = htons(PORT)
    addr.sin_addr = inet_addr("127.0.0.1")
    addr.sin_zero = b'\x00' * 8

    # connect to server
    if libc.connect(sock, ctypes.byref(addr), ctypes.sizeof(addr)) < 0:
        print("[CLIENT] Connection failed.")
        libc.close(sock)
        exit(1)

    print("[CLIENT] Connected to server.")

    # communication loop
    buf = ctypes.create_string_buffer(1024)
    try:
        # ecode message before sending
        msg_to_send = msg.strip()
        msg_bytes = msg_to_send.encode('utf-8')
        
        # Prepend 4-byte length (network byte order)
        length_prefix = struct.pack('!I', len(msg_bytes))
        full_message = length_prefix + msg_bytes

        # send message
        print("[DEBUG CLIENT] as message was sent", full_message)
        bytes_sent = libc.send(sock, full_message, len(full_message), 0)
        if bytes_sent < 0:
            print("[CLIENT] Send failed")
            return None
        
        # receive message
        length_prefix_buf = ctypes.create_string_buffer(4)
        bytes_received = libc.recv(sock, length_prefix_buf, 4, 0)
        if bytes_received != 4:
            print("[CLIENT] Failed to read length prefix")
            return None

        # Decode the message length
        msg_length = struct.unpack('!I', length_prefix_buf.raw)[0]

        # read the exact number of bytes expected
        chunks = []
        remaining = msg_length
        while remaining > 0:
            chunk_buf = ctypes.create_string_buffer(min(remaining, 4096))
            n = libc.recv(sock, chunk_buf, min(remaining, 4096), 0)
            if n <= 0:
                print("[CLIENT] Incomplete message")
                return None
            chunks.append(chunk_buf.raw[:n])
            remaining -= n

        full_reply = b''.join(chunks).decode('utf-8').strip('\r\n')
        print("[DEBUG CLIENT] Received:", full_reply)

        return decode_message(full_reply)
    finally:
        libc.close(sock)
        print("[CLIENT] Closed.")

