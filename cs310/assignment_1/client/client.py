import json
from socket import *    
import threading
from a1.common.handle_message import handle_message
from a1.client.client_tcp_socket import tcp_socket
from a1.client.client_raw_socket import raw_socket
from a1.models.chat import Chat, ChatType
from a1.models.user import DUMMY_USER
from a1.models.message import Message, MessageType
from a1.models.error import Error 
from a1.utils.constants import SERVER_PORT
from a1.context.global_state import global_app_state

# sends a request to the server to send file in base64 encoding
def handle_file_download(name):
    message = Message(
        from_user=global_app_state.user, 
        to_user=global_app_state.teacher, 
        content=name, 
        type=MessageType.GET_FILE
    ).to_json() 

    try:
        if global_app_state.machine == "mac":
            decoded = tcp_socket(msg=message)
        else:
            decoded = raw_socket(port=SERVER_PORT, msg=message)
            
        handle_message(decoded)
        
    except Exception as e: 
        print(f"[Error]: {e}")

# broadcast connections to all peers. happens everytime a new connection is registered by central server (tutor)
def broadcast_conns():
    # string of users and port assigned
    conns_string = json.dumps([
        {
            **user.to_dict(),
            "port": port
        }
        for port, user in global_app_state.connections.items()
        ])
    
    for port, user in global_app_state.connections.items():
        ack = Message(
            from_user=global_app_state.user,
            to_user=user,
            content=conns_string,
            type=MessageType.GET_CONNS_ACK
        ).to_json()

        if port == SERVER_PORT:
            continue

        if global_app_state.machine == "mac":
            thread = threading.Thread(target=tcp_socket, args=(port,ack), daemon=True)
            thread.start()   
        else:
            thread = threading.Thread(target=raw_socket, args=(port,ack), daemon=True)
            thread.start()   
   
        print(f"broadcast chat sent to ${user.name} on port {port}")

# broadcast the end of session to all connected peers. Called by central server which acts like a node in this case
def broadcast_session_end():

    for port, user in global_app_state.connections.items():
        if user.id == global_app_state.user.id:
            continue

        ack = Message(
            from_user=global_app_state.user,
            to_user=user,
            content="end session",
            type=MessageType.END_SESSION
        ).to_json()

        try:
            if global_app_state.machine == "mac":
                thread = threading.Thread(target=tcp_socket, args=(port,ack), daemon=True)
                thread.start()   
            else:
                thread = threading.Thread(target=raw_socket, args=(port,ack), daemon=True)
                thread.start()   
        except Exception as e: 
            print(f"[Error]: {e}")

# used by nodes to send chats to other nodes. One way communication. Node -> Node
def send_p2p_chat(chat: Chat, user, port):
    try:
        message = Message(
            from_user=global_app_state.user, 
            to_user=user, 
            content=chat.to_json(), 
            type=MessageType.P2P_CHAT
        ).to_json() 

        if global_app_state.machine == "mac":
            decoded = tcp_socket(port=port, msg=message)
            
        else:
            decoded = raw_socket(port=port, msg=message)
        
        handle_message(decoded)

    except Exception as e: 
        print(f"[Error]: {e}")


# used by students to get the remaining session time when initially joining into a session
def get_time():
    try:
        message = Message(
            from_user=global_app_state.user, 
            to_user=DUMMY_USER, 
            content="get time", 
            type=MessageType.GET_TIME
        ).to_json() 

        if global_app_state.machine == "mac":
            decoded = tcp_socket(msg=message)
            
        else:
            decoded = raw_socket(port=SERVER_PORT, msg=message)
        
        handle_message(decoded)

    except Exception as e: 
        print(f"[Error]: {e}")
        global_app_state.error = Error(500, str(e))

# called when student joins the session. Loads previous global chats into memory
def get_chats():
    handle_chat_send(Chat(chat_type=ChatType.JOIN_SESSION, text="joined session", from_user=global_app_state.user, to_user=DUMMY_USER))

# called when students joins session. Gets list of all other students + teacher in the session
def get_attendance():

    try:
        decoded = ""

        message = Message(
            from_user=global_app_state.user, 
            to_user=DUMMY_USER, 
            content="get attendance", 
            type=MessageType.UPDATE_ATTENDANCE
        ).to_json()  

        if global_app_state.machine == "mac":
            decoded = tcp_socket(msg=message)
            
        else:
            decoded = raw_socket(port=SERVER_PORT, msg=message)
        
        handle_message(decoded)

    except Exception as e: 
        global_app_state.error = Error(500, str(e))
        print(f"[Error]: {e}")

# called when students joins session. Gets list of all nodes in the session, with port used. 
def get_conns():
    try:
        decoded = ""
        # send register message
        message = Message(
            from_user=global_app_state.user, 
            to_user=DUMMY_USER, 
            content="get conns", 
            type=MessageType.GET_CONNS
        ).to_json() 

        if global_app_state.machine == "mac":
            print("machine state mac")
            decoded = tcp_socket(msg=message)
            
        else:
            print("machine state linoox")
            decoded = raw_socket(port=SERVER_PORT, msg=message)
            
        handle_message(decoded)

    except Exception as e: 
        global_app_state.error = Error(500, str(e))
        print(f"[Error]: {e}")

# used by all nodes. sends a chat message into global chat. seperate from p2p chat system
def handle_chat_send(chat: str):

    try:
        message = Message(
            from_user=global_app_state.user, 
            to_user=global_app_state.teacher, 
            content=chat.to_json(), 
            type=MessageType.SEND_CHAT
        ).to_json() 

        if global_app_state.machine == "mac":
            decoded = tcp_socket(port=SERVER_PORT, msg=message)
            
        else:
            decoded = raw_socket(port=SERVER_PORT, msg=message)
        
        handle_message(decoded)

    except Exception as e: 
        print(f"[Error]: {e}")
        global_app_state.error = Error(500, str(e))

# called when join session button is pressed. 
def join_session():
    try:
        decoded = ""
        # send register message
        message = Message(
            from_user=global_app_state.user, 
            to_user=DUMMY_USER, 
            content="register", 
            type=MessageType.JOIN_SESSION
        ).to_json() 

        if global_app_state.machine == "mac":
            print("machine state mac")
            decoded = tcp_socket(msg=message)
            
        else:
            print("machine state linoox")
            decoded = raw_socket(port=SERVER_PORT, msg=message)
        
        handle_message(decoded)

    except Exception as e: 
        print(f"[Error]: {e}")

    # run threads to fetch all neccesary data
    from a1.server.server import p2p_server

    # start p2p server
    thread1 = threading.Thread(target=p2p_server, daemon=True)
    thread1.start()

    # get updated list of attendance
    thread2 = threading.Thread(target=get_attendance, daemon=True)
    thread2.start()

    # get global chats
    thread3 = threading.Thread(target=get_chats, daemon=True)
    thread3.start()

    # get connections for nodes. 
    thread4 = threading.Thread(target=get_conns, daemon=True)
    thread4.start()

# called when leave button is pressed. 
def leave_session():
    try:
        decoded = ""

        # send custom leave message
        message = Message(
            from_user=global_app_state.user, 
            to_user=global_app_state.teacher, 
            content="leave", 
            type=MessageType.LEAVE_SESSION
        ).to_json() 

        if global_app_state.machine == "mac":
            print("machine state mac")
            decoded = tcp_socket(port=SERVER_PORT, msg=message)
            
        else:
            print("machine state linoox")
            decoded = raw_socket(port=SERVER_PORT, msg=message)
        
        handle_message(decoded)

    except Exception as e: 
        print(f"[Error]: {e}")
        global_app_state.error = Error(500, str(e))
