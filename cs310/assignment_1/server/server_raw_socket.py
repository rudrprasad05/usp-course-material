import ctypes
import ctypes.util
import os
import struct
from a1.common.handle_message import handle_message
from a1.context.global_state import global_app_state
from a1.utils.constants import SERVER_PORT
from a1.utils.libs import decode_message

def raw_socket_server(port: int = SERVER_PORT) -> str:
    print("[DEBUG] Starting TCP Socket Server port: ", port)

    AF_INET = 2
    SOCK_STREAM = 1
    IPPROTO_TCP = 6
    SOL_SOCKET = 1
    SO_REUSEADDR = 2

    libc = ctypes.CDLL(ctypes.util.find_library('c'), use_errno=True)

    class sockaddr_in(ctypes.Structure):
        _fields_ = [
            ("sin_family", ctypes.c_ushort),
            ("sin_port", ctypes.c_uint16),
            ("sin_addr", ctypes.c_uint32),
            ("sin_zero", ctypes.c_char * 8)
        ]

    def htons(x):
        return ((x & 0xff) << 8) | ((x & 0xff00) >> 8)

    server_socket = libc.socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)
    if server_socket < 0:
        print("[SERVER] Socket creation failed.")
        return

    opt = ctypes.c_int(1)
    if libc.setsockopt(server_socket, SOL_SOCKET, SO_REUSEADDR, ctypes.byref(opt), ctypes.sizeof(opt)) < 0:
        print("[SERVER] Failed to set socket options.")
        libc.close(server_socket)
        return

    addr = sockaddr_in()
    addr.sin_family = AF_INET
    addr.sin_port = htons(port)
    addr.sin_addr = 0  # INADDR_ANY
    addr.sin_zero = b'\x00' * 8

    if libc.bind(server_socket, ctypes.byref(addr), ctypes.sizeof(addr)) < 0:
        print("[SERVER] Bind failed.")
        libc.close(server_socket)
        return

    if libc.listen(server_socket, 5) < 0:
        print("[SERVER] Listen failed.")
        libc.close(server_socket)
        return

    print(f"[SERVER] Listening on port {port}...")

    try:
        while global_app_state.is_session_running:
            client_socket = libc.accept(server_socket, None, None)
            if client_socket < 0:
                errno = ctypes.get_errno()
                print(f"[SERVER] Accept failed: errno={errno} ({os.strerror(errno)})")
                continue

            print("[SERVER] Client connected.")

            while global_app_state.is_session_running:
                # read the 4-byte length prefix
                length_prefix = ctypes.create_string_buffer(4)
                bytes_received = libc.recv(client_socket, length_prefix, 4, 0)
                
                if bytes_received == 0:
                    print("[SERVER] Client disconnected gracefully.")
                    break
                elif bytes_received != 4:
                    errno = ctypes.get_errno()
                    print(f"[SERVER] Failed to read length prefix: errno={errno} ({os.strerror(errno)})")
                    break

                # decode the message length (big-endian)
                msg_length = struct.unpack('!I', length_prefix.raw)[0]

                # read the full message
                chunks = []
                remaining = msg_length
                while remaining > 0:
                    chunk_size = min(remaining, 1024)
                    chunk_buf = ctypes.create_string_buffer(chunk_size)
                    n = libc.recv(client_socket, chunk_buf, chunk_size, 0)
                    
                    if n <= 0:
                        errno = ctypes.get_errno()
                        print(f"[SERVER] recv failed: errno={errno} ({os.strerror(errno)})")
                        break
                    
                    chunks.append(chunk_buf.raw[:n])
                    remaining -= n

                if remaining > 0:
                    print("[SERVER] Incomplete message received.")
                    break

                # process the message 
                full_msg = b''.join(chunks).decode('utf-8').strip('\r\n')
                print("[DEBUG SERVER] Received full message:", full_msg)

                try:
                    decoded = decode_message(full_msg)
                    ack = handle_message(decoded)
                    
                    if ack:
                        # send ACK with length prefix
                        ack_bytes = ack.encode('utf-8')
                        ack_length = struct.pack('!I', len(ack_bytes))
                        libc.send(client_socket, ack_length, 4, 0)
                        libc.send(client_socket, ack_bytes, len(ack_bytes), 0)
                        print("[DEBUG SERVER] Sent ACK:", ack)

                except Exception as e:
                    print(f"[SERVER] Error processing message: {e}")
                    break

            libc.close(client_socket)
            print("[SERVER] Client socket closed.")

    except KeyboardInterrupt:
        print("[SERVER] Interrupted by user.")
    finally:
        libc.close(server_socket)
        print("[SERVER] Closed server socket.")