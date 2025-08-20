import os
import shutil
import uuid
from tkinter import filedialog

def handle_file_upload() -> str:
    # open file picker
    file_path = filedialog.askopenfilename(title="Select a file to upload")

    if file_path:
        # create a UUID filename (keep the extension!)
        file_ext = os.path.splitext(file_path)[1]  # e.g., '.png', '.txt'
        unique_filename = f"{uuid.uuid4()}{file_ext}"

        # destination path in your tmp/ folder
        tmp_dir = os.path.join(os.getcwd(), "tmp")
        os.makedirs(tmp_dir, exist_ok=True)  # make sure tmp exists

        dest_path = os.path.join(tmp_dir, unique_filename)

        # copy the file
        shutil.copy(file_path, dest_path)

        # return file
        return dest_path

    return None
