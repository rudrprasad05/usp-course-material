from enum import Enum
import json
from socket import *
import uuid
from a1.models import User
from a1.models.user import DUMMY_USER
from a1.utils.constants import CARRIAGE_RETURN

temp_chats = {}


'''
enum to define all possible types of messages received or transmitted. 
'''
class MessageType(str, Enum):
    JOIN_SESSION = "JOIN_SESSION"
    JOIN_SESSION_ACK = "JOIN_SESSION_ACK"
    UPDATE_ATTENDANCE = "UPDATE_ATTENDANCE"
    UPDATE_ATTENDANCE_ACK = "UPDATE_ATTENDANCE_ACK"
    SEND_CHAT = "SEND_CHAT"
    SEND_CHAT_ACK = "SEND_CHAT_ACK"
    P2P_CHAT = "P2P_CHAT"
    P2P_CHAT_ACK = "P2P_CHAT_ACK"
    GET_TIME = "GET_TIME"
    GET_TIME_ACK = "GET_TIME_ACK"
    P2P_PORT = "P2P_PORT"
    LEAVE_SESSION = "LEAVE_SESSION"
    LEAVE_SESSION_ACK = "LEAVE_SESSION"
    GET_FILE = "GET_FILE"
    GET_FILE_ACK = "GET_FILE_ACK"
    END_SESSION = "END_SESSION"
    END_SESSION_ACK = "END_SESSION_ACK"
    GET_CONNS = "GET_CONNS"
    GET_CONNS_ACK = "GET_CONNS_ACK"

    OK = "OK"

'''
custom message class used to transport definded and strcutured messages between noded. 
replace the current TCP or HTTP commands due to using raw sockets. 
'''

class Message():
    def __init__(self, from_user: User, to_user: User, content: str, type: MessageType, chat_uuid=None):
        self.from_user = from_user
        self.to_user = to_user
        self.content = content
        self.type = type
        self.uuid = uuid.uuid4() if chat_uuid is None else uuid.UUID(chat_uuid)


    def __repr__(self):
        return f'''
            Message 
                Type: {self.type}
                From: {self.from_user}
                To: {self.to_user}
                Content: {self.content}
                UUID: {self.uuid}
        '''
    def __str__(self):
        return f'''
            Message 
                Type: {self.type}
                From: {self.from_user}
                To: {self.to_user}
                Content: {self.content}
                UUID: {self.uuid}
        '''

    def to_dict(self):
        return {
            "content": self.content, 
            "from_user": self.from_user.to_dict() if self.from_user else None, 
            "to_user": self.to_user.to_dict() if self.from_user else None, 
            "type": str(self.type.value),
            "uuid": str(self.uuid)
        }
    
    def to_json(self):
        json_str = json.dumps(self.to_dict(), indent=4) + CARRIAGE_RETURN
        return json_str
    
    def __eq__(self, other):
        return self.uuid == other.uuid
    
    def __hash__(self):
        return hash(self.uuid)
    
    @staticmethod
    def from_dict(data):
        return Message(
            from_user=User.from_dict(data["from_user"]) if data["from_user"] else None,
            to_user=User.from_dict(data["to_user"]) if data["to_user"] else None,
            content=data["content"],
            type=MessageType(data["type"]),
            chat_uuid=data["uuid"]
        )
    
DUMMY_MESSAGE = Message(
    from_user=DUMMY_USER, 
    to_user=DUMMY_USER, 
    content="this isnt supposed to be sent tho", 
    type=MessageType.OK
).to_json() 
