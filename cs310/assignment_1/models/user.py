from enum import Enum
import json

from a1.utils.constants import CARRIAGE_RETURN

'''
super user class to init common variables and methods. inherited by student + teacher
'''
class UserType(str, Enum):
    USER = "USER"
    TEACHER = "TEACHER"
    STUDENT = "STUDENT"

class User(): 
    def __init__(self, id: str, name: str, password: str):
        self.id = id
        self.name = name
        self.password = password
        self.sessionId = None
        self.role = UserType.USER

    def __repr__(self):
        return f"User(name={self.name}, id={self.id})"

    def __str__(self):
        return f'''
            Student 
                Name: {self.name}
                ID: {self.id}
                Role: {self.role}
        '''
    
    def __hash__(self):
        return hash(self.id)
    
    def __eq__(self, other):
        return self.id == other.id
    
    def to_dict(self):
        return {
            "id": self.id,
            "name": self.name,
            "password": self.password,
            "sessionId": self.sessionId,
            "role": self.role
        }
    
    def to_json(self):
        return json.dumps(self.to_dict(), indent=4) + CARRIAGE_RETURN
    
    @staticmethod
    def from_dict(data):
        role = UserType(data["role"])
        return User(data["id"], data["name"], data["password"])
    

DUMMY_USER = User(id="0", name="Dummy", password="password")
SERVER_USER = User(id="100", name="Server", password="server")
