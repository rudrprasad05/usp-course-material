import csv
import datetime
import os
from tkinter import filedialog
import shutil

# used by server to save attendace in file
def save_attendance(users: set, session_id: str):
    tmp_dir = os.path.join(os.getcwd(), "tmp")
    os.makedirs(tmp_dir, exist_ok=True)
    
    # Create a filename with session_id and timestamp
    filename = f"attendance_{session_id}.csv"
    file_path = os.path.join(tmp_dir, filename)
    
    with open(file_path, "w", newline='') as csvfile:
        fieldnames = ['User ID', 'Name', 'Present']
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
        
        writer.writeheader()
        for user, is_present in users.items():  # assuming users is dict[User, bool]
            writer.writerow({
                'User ID': user.id,
                'Name': user.name,
                'Present': 'Yes'
            })

    return file_path

# used by nodes to download file and save it to downloads
def download_attendance_file(tmp_file_path):
    today = datetime.date.today().strftime("%Y-%m-%d")
    default_filename = f"attendance_{today}.csv"
    # open a Save As dialog
    save_path = filedialog.asksaveasfilename(
        title="Save Attendance File",
        initialdir=os.path.expanduser("~/Downloads"), 
        initialfile=default_filename,
        defaultextension=".csv",
        filetypes=[("CSV files", "*.csv"), ("All files", "*.*")]
    )

    if save_path:
        shutil.copy(tmp_file_path, save_path)