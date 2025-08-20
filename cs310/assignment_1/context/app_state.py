import threading
from typing import List

from a1.models.chat import Chat
from a1.models.user import User

'''
global context in singleton form
contains globally accessible data. used for session management
'''
class AppState:
    def __init__(self):
        self.user = None
        self.session_id = None
        self.attendance_list: dict[User, bool] = {}
        self.error = None
        self.time_left = 0
        self.teacher = None
        self.is_session_running = False;
        self.attendance_list_path = None;

        self.connections: dict[int, User] = {}
        self.port = 0

        self.p2p_chats: dict[User, List[Chat]] = {}
        self.p2p_chat_widgets = {}

        self.machine: str = "mac"

        self.chat = set()
        self.chat_widgets = {}

    def login(self, user_data):
        from a1.models.user import User  # Lazy import to avoid circular dependency
        if isinstance(user_data, User):
            self.user = user_data

    def logout(self):
        self.user = None

    def add_to_chat(self, chat: Chat):
        if chat.uuid not in {c.uuid for c in self.chat}:
            self.chat.add(chat)


    def add_to_attendance(self, user):
        from a1.models.user import User
        if isinstance(user, User):
            self.attendance_list[user] = True
    
    def add_to_p2p(self, user, chat):
        from a1.models.user import User
        

        if user not in self.p2p_chats:
            self.p2p_chats[user] = []
        self.p2p_chats[user].append(chat)
        

    def remove_from_attendance(self, user):
        from a1.models.user import User
        
        if isinstance(user, User):
            self.attendance_list[user] = False
            
            
                    
    def get_attenace(self):
       return self.attendance_list

