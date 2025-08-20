from a1.models import User
from a1.models.user import UserType

'''
represent the tutor. inherits from user
'''
class Teacher(User):
    def __init__(self, id: str, name: str, password: str, subject: str):
        super().__init__ (id, name, password)
        self.role = UserType.TEACHER
        self.subject = subject

    def __repr__(self):
        return f"Teacher(name={self.name}, id={self.id})"

    