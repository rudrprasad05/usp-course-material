import sqlite3
from pathlib import Path
from a1.models.student import Student
from a1.models.teacher import Teacher
from a1.models.user import User, UserType
from a1.utils import DB

def read_users():
    # Ensure the file exists (you can skip this if you are confident)
    file = Path(DB)
    if not file.exists():
        
        return
    
    # Connect to the database
    conn = sqlite3.connect(DB)
    cursor = conn.cursor()

    # Read all users from the table
    cursor.execute("SELECT id, name, password, sessionId, role FROM users")
    
    # Fetch all rows
    rows = cursor.fetchall()

    # Close the connection
    conn.close()

def get_user_by_id(user_id: str, type: UserType) -> User:
    conn = sqlite3.connect(DB)
    cursor = conn.cursor()

    if type == UserType.STUDENT:
        cursor.execute("SELECT id, name, password, sessionId, role FROM student WHERE id=?", (user_id,))
        result = cursor.fetchone()
    elif type == UserType.TEACHER:
        cursor.execute("SELECT id, name, password, sessionId, subject, role FROM teacher WHERE id=?", (user_id,))
        result = cursor.fetchone()

    conn.close()

    if result:
        # Convert the tuple into a User object
        user = None
        if type == UserType.STUDENT:
            user = Student(result[0], result[1], result[2])
        elif type == UserType.TEACHER:
            user = Teacher(result[0], result[1], result[2], result[4])

        return user
    else:
        return None