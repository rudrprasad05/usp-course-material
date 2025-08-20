
from a1.client.client_tcp_socket import tcp_socket
from a1.models.message import Message, MessageType
import base64
import json
import os
from pathlib import Path
from socket import *
import threading
import uuid
from a1.models import User
from a1.context.global_state import global_app_state
from a1.models.chat import Chat, ChatType, send_chat
from a1.models.user import DUMMY_USER
from a1.utils.constants import  MAX_STUDENTS, SERVER_NAME, SERVER_PORT
from a1.utils.libs import receive_message, decode_message

'''
Logic to handle custom message format. 
Takes a received message and performs a function
Contains smaller helper threads for additonal data deliveries or connections
'''

def handle_message(msg: Message) -> str:
    current_user = global_app_state.user
    print("[DEBUG] Recieved msg", msg)
    if msg is None:
        print("Empty message received")
        ack = Message(
            from_user=current_user,
            to_user=DUMMY_USER,
            content="",
            type=MessageType.OK
        )
        return None
    match msg.type:
        case MessageType.JOIN_SESSION.value:
            '''
            sent by: client
            received by: server
            purpose: ask server to join session and allocate port
            '''
            if(len(global_app_state.attendance_list) > MAX_STUDENTS):
                return 
            
            global_app_state.add_to_attendance(msg.from_user)
            port = 0

            if len(global_app_state.connections) > 0:
                port = max(global_app_state.connections.keys()) + 1
            else:
                port = SERVER_PORT + 1

            ack = Message(
                from_user=current_user,
                to_user=msg.from_user,
                content=str(port),
                type=MessageType.JOIN_SESSION_ACK
            )

            global_app_state.connections[port] = msg.from_user
            return ack.to_json()
        
        case MessageType.JOIN_SESSION_ACK.value:
            '''
            sent by: server
            received by: client
            purpose: ack message for JOIN_SESSION. contains p2p port
            '''
            global_app_state.teacher = msg.from_user
            global_app_state.port = int(msg.content)
            print("my port assifgned is ", msg.content)

            ack = Message(
                from_user=current_user,
                to_user=msg.from_user,
                content="",
                type=MessageType.OK
            )

            return ack.to_json()
        
        case MessageType.END_SESSION.value:
            '''
            sent by: server
            received by: all connected nodes
            purpose: end session, broadcast to everyone
            '''
            global_app_state.is_session_running = False

            ack = Message(
                from_user=current_user,
                to_user=msg.from_user,
                content="",
                type=MessageType.END_SESSION_ACK
            )

            return ack.to_json()
        
        case MessageType.END_SESSION_ACK.value:
            '''
            sent by: client
            received by: server
            purpose: ack end of session. exit gracefullt
            '''
            global_app_state.is_session_running = False

            ack = Message(
                from_user=current_user,
                to_user=msg.from_user,
                content="",
                type=MessageType.OK
            )

            return ack.to_json()

        case MessageType.UPDATE_ATTENDANCE.value:
            '''
            sent by: client
            received by: server
            purpose: ask for updated attendance from server
            '''
            attendance_string = json.dumps([
                {
                    **user.to_dict(),
                    "active": is_active
                }
                for user, is_active in global_app_state.attendance_list.items()
            ])
            ack = Message(
                from_user=current_user,
                to_user=msg.from_user,
                content=attendance_string,
                type=MessageType.UPDATE_ATTENDANCE_ACK
            )
            threading.Thread(target=_update_attendance, daemon=True).start()
            
            return ack.to_json()
        
        case MessageType.UPDATE_ATTENDANCE_ACK.value:
            '''
            sent by: server
            received by: client
            purpose: ack message with dictionary object containing all users + active state
            '''
            global_app_state.attendance_list = {
                User.from_dict(obj): obj["active"]
                for obj in json.loads(msg.content)
            }
            
            ack = Message(
                from_user=current_user,
                to_user=DUMMY_USER,
                content="",
                type=MessageType.OK
            )
        
            return ack.to_json()
        
        case MessageType.GET_CONNS.value:
            '''
            sent by: client
            received by: server
            purpose: ask server to send list of all connected user + port number
            '''
            from a1.client.client import broadcast_conns

            conns_string = json.dumps([
            {
                **user.to_dict(),
                "port": port
            }
            for port, user in global_app_state.connections.items()
            ])

            ack = Message(
                from_user=current_user,
                to_user=msg.from_user,
                content=conns_string,
                type=MessageType.GET_CONNS_ACK
            )
            threading.Thread(target=broadcast_conns, daemon=True).start()

            return ack.to_json()
        
        case MessageType.GET_CONNS_ACK.value:
            '''
            sent by: server
            received by: client
            purpose: ack message
            '''
            global_app_state.connections = {
                int(obj["port"]): User.from_dict(obj)
                for obj in json.loads(msg.content)
            }
            
            ack = Message(
                from_user=current_user,
                to_user=DUMMY_USER,
                content="",
                type=MessageType.OK
            )
        
            return ack.to_json()
        
        case MessageType.GET_FILE.value:
            '''
            sent by: client
            received by: server
            purpose: ask for file to be downloaded
            '''
            filename = msg.content

            try:
                with open(filename, "rb") as f:
                    file_data = f.read()

                file_abs_name = Path(filename).name
                
                # Base64 encode file data to safely send inside JSON
                encoded_data = base64.b64encode(file_data).decode()

                ack = Message(
                    from_user=current_user,
                    to_user=msg.from_user,
                    content=file_abs_name + "|" + encoded_data,
                    type=MessageType.GET_FILE_ACK
                )
                return ack.to_json()
            
            except Exception as e:
                print(f"Error reading file: {e}")

                ack = Message(
                    from_user=current_user,
                    to_user=msg.from_user,
                    content="",
                    type=MessageType.OK
                )
                return ack.to_json()
          
          
        case MessageType.GET_FILE_ACK.value:
            '''
            sent by: server
            received by: client
            purpose: respond with file in base64
            '''
            encoded_file = msg.content
            if not encoded_file:
                print("No file received.")
                return None

            try:
                file_name, encoded_data = encoded_file.split('|', 1)
                file_data = base64.b64decode(encoded_data)

                downloads_dir = os.path.join(os.path.expanduser("~"), "Downloads")
                os.makedirs(downloads_dir, exist_ok=True)

                # Optional: you can pass the filename inside the message if you want to get original extension
                if file_name:
                    name, ext = os.path.splitext(file_name)
                else:
                    ext = ".bin"  # default if you don't know

                unique_filename = str(uuid.uuid4()) + ext
                file_path = os.path.join(downloads_dir, unique_filename)

                with open(file_path, "wb") as f:
                    f.write(file_data)

            except Exception as e:
                print(f"Error saving file: {e}")

            return None

        
        case MessageType.GET_TIME.value:
            '''
            sent by: client
            received by: server
            purpose: ask for session time left
            '''
            time_string = str(global_app_state.time_left)
            ack = Message(
                from_user=current_user,
                to_user=msg.from_user,
                content=time_string,
                type=MessageType.GET_TIME_ACK
            )
            
            return ack.to_json()
        
        
        case MessageType.GET_TIME_ACK.value:
            '''
            sent by: server
            received by: client
            purpose: session time left
            '''
            global_app_state.time_left = int(msg.content)
            ack = Message(
                from_user=current_user,
                to_user=msg.from_user,
                content="",
                type=MessageType.OK
            )
            
            return ack.to_json()
       
        case MessageType.SEND_CHAT.value:
            '''
            sent by: anyone
            received by: anyone
            purpose: send a chat globally
            '''
            new_chat = Chat.from_dict(json.loads(msg.content))

            send_chat(new_chat)
            chat_str = json.dumps([chat.to_dict() for chat in global_app_state.chat])

            ack = Message(
                from_user=current_user,
                to_user=msg.from_user,
                content=chat_str,
                type=MessageType.SEND_CHAT_ACK
            )
        
            return ack.to_json()
        
        case MessageType.SEND_CHAT_ACK.value:
            '''
            sent by: anyone
            received by: server
            purpose: receive chat globally
            '''
            global_app_state.chat = {Chat.from_dict(obj) for obj in json.loads(msg.content)}
            global_app_state.chat_widgets = {}

            ack = Message(
                from_user=current_user,
                to_user=DUMMY_USER,
                content="ok",
                type=MessageType.OK
            )
        
            return ack.to_json()
        

        case MessageType.P2P_CHAT.value:
            '''
            sent by: node
            received by: node
            purpose: send a chat to a node
            '''
            new_chat = Chat.from_dict(json.loads(msg.content))
            
            global_app_state.add_to_p2p(user=new_chat.from_user, chat=new_chat)
            # chat_str = json.dumps([chat.to_dict() for chat in global_app_state.chat])

            ack = Message(
                from_user=current_user,
                to_user=new_chat.from_user,
                content="ok",
                type=MessageType.OK
            )
        
            return ack.to_json()

        
        case MessageType.LEAVE_SESSION.value:
            '''
            sent by: node
            received by: server
            purpose: leave session. mark attendance
            '''
            global_app_state.attendance_list[msg.from_user] = False
            for conn_id, user in list(global_app_state.connections.items()):
                if user.id == msg.from_user.id:
                    del global_app_state.connections[conn_id]
                    break

            send_chat(Chat(chat_type=ChatType.LEAVE_SESSION, text="left session", from_user=msg.from_user, to_user=current_user))
            
            ack = Message(
                from_user=current_user,
                to_user=msg.from_user,
                content="ACK",
                type=MessageType.LEAVE_SESSION_ACK
            )
            threading.Thread(target=_update_attendance, daemon=True).start()
            threading.Thread(target=_broadcast_conns, daemon=True).start()


            return ack.to_json()

        case MessageType.LEAVE_SESSION_ACK.value:
            '''
            sent by: server
            received by: node
            purpose: ack session left
            '''
            global_app_state.is_session_running = False
            ack = Message(
                from_user=current_user,
                to_user=msg.from_user,
                content="ACK",
                type=MessageType.OK
            )

            return ack.to_json()

        case MessageType.OK.value:
            return None

# broadcast connections using broadcast server to all nodes
def _broadcast_conns():
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

        try:
            clientSocket = socket(AF_INET,SOCK_STREAM)
            clientSocket.connect((SERVER_NAME,port))
            clientSocket.sendall(ack.encode())  # Send the data (in byte form)

            sentence = receive_message(clientSocket)
            
            decoded = decode_message(sentence)
            ack = handle_message(decoded)

            clientSocket.close()  

        except Exception as e: 
            print(f"Error: {e}")
            clientSocket.close()

   
        print(f"broadcast chat sent to ${user.name} on port {port}")

# broadcast attendance using broadcast server to all nodes
def _update_attendance():
    attendance_string = json.dumps([
        {
            **user.to_dict(),
            "active": is_active
        }
        for user, is_active in global_app_state.attendance_list.items()
    ])

    for port, user in global_app_state.connections.items():
        if user.id == global_app_state.user.id:
            continue

        ack = Message(
            from_user=global_app_state.user,
            to_user=user,
            content=attendance_string,
            type=MessageType.UPDATE_ATTENDANCE_ACK
        ).to_json()

        try:
            clientSocket = socket(AF_INET,SOCK_STREAM)
            clientSocket.connect((SERVER_NAME,port))
            clientSocket.sendall(ack.encode())  # Send the data (in byte form)

            sentence = receive_message(clientSocket)
            
            decoded = decode_message(sentence)
            ack = handle_message(decoded)

            clientSocket.close() 
        
        except Exception as e: 
            print(f"Error: {e}")
            clientSocket.close()
