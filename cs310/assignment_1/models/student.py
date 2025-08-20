import json
from a1.models import User
from a1.models.user import UserType

'''
represent the students. inherits from user
'''
class Student(User):
    def __init__(self, id: str, name: str, password: str):
        super().__init__(id, name, password)
        self.role = UserType.STUDENT

    def __repr__(self):
        return f"Student(name={self.name}, id={self.id})"

   
    @staticmethod
    def from_dict(data):
        return Student(data["id"], data["name"], data["password"])