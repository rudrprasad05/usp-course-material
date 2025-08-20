from pathlib import Path
import sqlite3
from a1.models import Student, Teacher

def main():
    db_file = "a1/db/students.db"

    file = Path(db_file)

    if file.exists():
        
        return
    file.touch(exist_ok=True)

    conn = sqlite3.connect(db_file)
    cursor = conn.cursor()

    # Create table
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS student (
        id TEXT PRIMARY KEY,
        name TEXT NOT NULL,
        password TEXT NOT NULL,
        sessionId TEXT,
        role TEXT NOT NULL
    );
    ''')

    cursor.execute('''
    CREATE TABLE IF NOT EXISTS teacher (
        id TEXT PRIMARY KEY,
        name TEXT NOT NULL,
        password TEXT NOT NULL,
        sessionId TEXT,
        role TEXT NOT NULL,
        subject TEXT NOT NULL
    );
    ''')

    # Generate student data
    students = [
        Student("s1", "Alice", "1"),
        Student("s2", "Bob", "2"),
        Student("s11219302", "Charlie", "xyz"),
        Student("s11219303", "David", "456"),
        Student("s11219304", "Eva", "def"),
        Student("s11219305", "Frank", "789"),
        Student("s11219306", "Grace", "ghi"),
        Student("s11219307", "Helen", "321"),
        Student("s11219308", "Ivy", "jkl"),
        Student("s11219309", "Jack", "654"),
    ]

    teachers = [
        Teacher("t1", "Jane", "1", "cs111"),
        Teacher("s11219301", "John", "123", "cs211"),
        Teacher("s11219302", "Jim", "xyz", "cs311"),
    ]   

    # Insert students into DB
    for student in students:
        cursor.execute('''
        INSERT OR REPLACE INTO student (id, name, password, sessionId, role)
        VALUES (?, ?, ?, ?, ?)
        ''', (student.id, student.name, student.password, student.sessionId, student.role.value))

    for teacher in teachers:
        cursor.execute('''
        INSERT OR REPLACE INTO teacher (id, name, password, sessionId, role, subject)
        VALUES (?, ?, ?, ?, ?, ?)
        ''', (teacher.id, teacher.name, teacher.password, teacher.sessionId, teacher.role.value, teacher.subject))

    conn.commit()
    conn.close()
    

if __name__ == "__main__":
    main()