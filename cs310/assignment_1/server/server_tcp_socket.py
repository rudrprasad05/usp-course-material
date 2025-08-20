from socket import *
from a1.common.handle_message import handle_message
from a1.utils import SERVER_NAME, SERVER_PORT
from a1.context.global_state import global_app_state
from a1.utils.libs import decode_message, receive_message

temp_chats = {}

def tcp_socket_server(port: int = SERVER_PORT):
    server_socket = socket(AF_INET,SOCK_STREAM)
    server_socket.bind((SERVER_NAME ,port))
    server_socket.listen(10)
    print(f"[DEBUG] Starting TCP Socket Server port: {port}")

    try:
        while True:
            # server_socket.settimeout(1.0)
            connection_socket, addr = server_socket.accept()

            if not global_app_state.is_session_running:
                print("Session ended, port closed")
                connection_socket.close()
                break
            
            print(f"[INFO] Connection from {addr} established")
            
            sentence = receive_message(connection_socket) # Receive the data (in byte form)
            decoded = decode_message(sentence)
            ack = handle_message(decoded)
            
            if ack is not None:
                connection_socket.sendall(ack.encode())
            connection_socket.close()

        
    except Exception as e:
        print(f"Error: {e}")
