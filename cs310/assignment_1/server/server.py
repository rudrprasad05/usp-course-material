import json
from socket import *
import threading
from a1.client.client_tcp_socket import tcp_socket as tcp_socket_client
from a1.client.client_raw_socket import raw_socket as raw_socket_client
from a1.context.global_state import global_app_state
from a1.models.chat import *
from a1.models.message import Message, MessageType
from a1.models.user import DUMMY_USER
from a1.server.server_raw_socket import raw_socket_server
from a1.server.server_tcp_socket import tcp_socket_server
from a1.utils.constants import SERVER_PORT

def start_session():
    if global_app_state.user:
     
        global_app_state.add_to_attendance(global_app_state.user)
        global_app_state.connections[SERVER_PORT] = global_app_state.user
        
        # start the thread
        if global_app_state.machine == "mac":
            thread = threading.Thread(target=tcp_socket_server, args=(SERVER_PORT,), daemon=True)
            thread.start()   
        else:
            thread = threading.Thread(target=raw_socket_server, args=(SERVER_PORT,), daemon=True)
            thread.start()       
        
        global_app_state.is_session_running = True

        send_chat(Chat(chat_type=ChatType.GLOBAL_CHAT, text="started session", from_user=global_app_state.user, to_user=DUMMY_USER))
    else:
        pass


def p2p_server():
    port = global_app_state.port

    if global_app_state.machine == "mac":
        thread = threading.Thread(target=tcp_socket_server, args=(port,), daemon=True)
        thread.start()   
    else:
        thread = threading.Thread(target=raw_socket_server, args=(port,), daemon=True)
        thread.start() 


def broadcast_chat():

    chat_string = json.dumps([chat.to_dict() for chat in global_app_state.chat])

    for port, user in global_app_state.connections.items():
        if user.id == global_app_state.user.id:
            continue
        ack = Message(
            from_user=global_app_state.user,
            to_user=user,
            content=chat_string,
            type=MessageType.SEND_CHAT_ACK
        ).to_json()

        print(f"broadcast chat sent to {user.name} on port {port}")

        if global_app_state.machine == "mac":
            thread = threading.Thread(target=tcp_socket_client, args=(port,ack), daemon=True)
            thread.start()   
        else:
            thread = threading.Thread(target=raw_socket_client, args=(port,ack), daemon=True)
            thread.start()     

