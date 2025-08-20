import os
import signal
import sys
import threading
import tkinter as tk
from tkinter import ttk
from a1.client.client import broadcast_session_end
import argparse
from a1.common.login_frame import LoginFrame
from a1.context.global_state import global_app_state
from a1.server.home_frame import HomeFrame

parser = argparse.ArgumentParser()
parser.add_argument('--type', default='mac', help='Specify the environment type')
args = parser.parse_args()
machine = args.type

print(f"[DEBUG] Running in {args.type} mode")

class App(tk.Tk):
    def __init__(self):
        super().__init__()

        CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
        BASE_DIR = os.path.abspath(os.path.join(CURRENT_DIR, '..', '..'))
        UPLOAD_ICON_PATH = os.path.join(BASE_DIR, 'a1', 'public', 'logo-t.png')
        UPLOAD_ICON_PATH_ICO = os.path.join(BASE_DIR, 'a1', 'public', 'logo-t.ico')

        self.configure(bg="#eff6ff")

        try:
            if machine == "mac" or machine == "linux":
                self.iconphoto(True, tk.PhotoImage(file=UPLOAD_ICON_PATH))
            else:
                self.iconbitmap(UPLOAD_ICON_PATH_ICO)
        except Exception as e:
            print(f"[WARN] Failed to load app icon: {e}")

        self.title("Teacher Portal")
        self.center_window(600, 450)
        self.resizable(False, False)

        # Configure rows/cols for centering
        self.columnconfigure(0, weight=1)
        self.rowconfigure(0, weight=1)

        # Create a container and center it
        self.container = ttk.Frame(self, )
        self.container.grid(column=0, row=0, sticky="nsew")
        self.container.columnconfigure(0, weight=1)
        self.container.rowconfigure(0, weight=1)

        self.login_frame = LoginFrame(self.container, self.show_home, name="Teacher")

        global_app_state.machine = machine


    def center_window(self, width, height):
        screen_width = self.winfo_screenwidth()
        screen_height = self.winfo_screenheight()
        x = (screen_width // 2) - (width // 2)
        y = (screen_height // 2) - (height // 2)
        self.geometry(f"{width}x{height}+{x}+{y}")

    def show_home(self):
        self.login_frame.destroy()
        self.home_frame = HomeFrame(self.container, self.show_login)

    def show_login(self):
        self.home_frame.destroy()
        self.login_frame = LoginFrame(self.container, self.show_home, name='Teacher')


def signal_handler(sig, frame):
    print("Ctrl + C detected. Exiting gracefully...")
    thread = threading.Thread(target=broadcast_session_end, daemon=True)
    thread.start()
    thread.join()
    sys.exit(0)

signal.signal(signal.SIGINT, signal_handler)    

if __name__ == "__main__":
    print("[INFO] Starting server app")
    app = App()
    app.mainloop()
