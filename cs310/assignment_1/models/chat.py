
import datetime
from enum import Enum
import json
import threading
import uuid
from a1.models.user import User
from a1.utils.constants import CARRIAGE_RETURN

'''
used for global and p2p chat. 
'''
class ChatType(str, Enum):
    JOIN_SESSION = "JOIN_SESSION"
    LEAVE_SESSION = "LEAVE_SESSION"
    GLOBAL_CHAT = "GLOBAL_CHAT"
    PRIVATE_CHAT = "PRIVATE_CHAT"
    FILE = "FILE"


class Chat(): 
    def __init__(self, chat_type: ChatType, text: str, from_user: User, to_user: User, timestamp=None, chat_uuid=None):
        self.chat_type = chat_type
        self.text = text
        self.from_user = from_user
        self.to_user = to_user
        self.timestamp = datetime.datetime.now() if timestamp is None else timestamp
        self.uuid = uuid.uuid4() if chat_uuid is None else uuid.UUID(chat_uuid)

    def __repr__(self):
        return (f"Chat(type={self.chat_type}, text={self.text}, "
            f"from={self.from_user.name}, to={self.to_user.name}, "
            f"timestamp={self.timestamp})")
    
    # used for hashing and comparisions in sets
    def __hash__(self):
        return hash(self.uuid)
    
    def __str__(self):
        return f'''
            Chat 
                Type: {self.chat_type}
                Text: {self.text}
                From: {self.from_user}
                To: {self.to_user}
        '''

    # used for hashing and comparisions in sets
    def __eq__(self, other):
        return self.uuid == other.uuid
    
    # convert obj to json for sending over network. add \r\n
    def to_json(self):
        return json.dumps(self.to_dict(), indent=4) + CARRIAGE_RETURN
    
    # convert obj to dict
    def to_dict(self):
        return {
            "chat_type": self.chat_type.value,
            "text": self.text,
            "from_user": self.from_user.to_dict(), 
            "to_user": self.to_user.to_dict(),
            "timestamp": self.timestamp.isoformat(),
            "uuid": str(self.uuid)
        }
    
    # convert dict to obj
    @staticmethod
    def from_dict(data):
        return Chat(
            chat_type=ChatType(data["chat_type"]),
            text=data["text"],
            from_user=User.from_dict(data["from_user"]), 
            to_user=User.from_dict(data["to_user"]),
            timestamp=datetime.datetime.fromisoformat(data["timestamp"]) if data["timestamp"] else None,
            chat_uuid=data["uuid"]
        )
    

def send_chat(chat: Chat):
    from a1.server.server import broadcast_chat
    from a1.context.global_state import global_app_state

    global_app_state.add_to_chat(chat)
    global_app_state.chat_widgets[chat.timestamp] = None
    threading.Thread(target=broadcast_chat, daemon=True).start()

