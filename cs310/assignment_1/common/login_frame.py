import tkinter as tk
from tkinter import ttk
from tkinter.messagebox import showerror
from a1.db.db_utils import get_user_by_id
from a1.models.user import UserType
from a1.context.global_state import global_app_state


'''
common login window
used by server & client
handle login by taking in ID + password
'''
class LoginFrame(ttk.Frame):
    def __init__(self, container, on_success, name):

        # init the login page. user enters ID + password to login
        super().__init__(container)
        self.container = container
        self.on_success = on_success
        self.name = name

        self.bg_color = "#eff6ff"  
        self.primary_color = "#3b82f6" 
        self.primary_hover = "#2563eb" 
        self.error_color = "#ef4444" 

        self.configure(style="Login.TFrame")
        self.columnconfigure(0, weight=1)
        self.rowconfigure(0, weight=1)

        # apply style
        style = ttk.Style()
        style.theme_use("clam")
        style.configure("Login.TFrame", background=self.bg_color)
        style.configure("Header.TLabel", font=("Segoe UI", 24, "bold"), background=self.bg_color, foreground=self.primary_color)
        style.configure("SubHeader.TLabel", font=("Segoe UI", 16), background=self.bg_color)
        style.configure("TLabel", font=("Segoe UI", 12), background=self.bg_color)
        style.configure("TEntry", font=("Segoe UI", 12))
        style.configure("TButton", font=("Segoe UI", 12), background=self.primary_color, foreground="white")
        style.map("TButton", background=[("active", self.primary_hover)])

        wrapper = ttk.Frame(self, padding=40, style="Login.TFrame")
        wrapper.grid(column=0, row=0)
        self.rowconfigure(0, weight=1)
        self.columnconfigure(0, weight=1)

        ttk.Label(wrapper, text=f"{name} Portal", style="Header.TLabel").grid(
            column=0, row=0, columnspan=2, pady=(0, 10)
        )
        ttk.Label(wrapper, text="Login to Continue", style="SubHeader.TLabel").grid(
            column=0, row=1, columnspan=2, pady=(0, 20)
        )

        # input fields
        self.username_entry(self, wrapper)
        self.password_entry(self, wrapper)

        # display error message
        self.error_text = ttk.Label(wrapper, text="", foreground=self.error_color, background=self.bg_color)
        self.error_text.grid(column=0, row=4, columnspan=2, pady=10)

        # login button
        self.login_button = ttk.Button(wrapper, text="Login", command=self.check_login)
        self.login_button.grid(column=0, row=5, columnspan=2, pady=15, sticky="ew")

        wrapper.columnconfigure(0, weight=1)
        wrapper.columnconfigure(1, weight=1)

        self.grid(column=0, row=0, sticky="nsew")

    @staticmethod
    def password_entry(self, wrapper):
        password_frame = ttk.Frame(wrapper, style="Login.TFrame")
        password_frame.grid(column=0, row=3, columnspan=2, sticky='ew', padx=5, pady=5)

        ttk.Label(password_frame, text="Password").grid(column=0, row=0, sticky='e', padx=5)

        self.password = tk.StringVar()
        self.password_entry = ttk.Entry(password_frame, textvariable=self.password, show='*')
        self.password_entry.grid(column=1, row=0, sticky="ew", padx=5)

        password_frame.columnconfigure(1, weight=1)

    @staticmethod
    def username_entry(self, wrapper):
        username_frame = ttk.Frame(wrapper, style="Login.TFrame")
        username_frame.grid(column=0, row=2, columnspan=2, sticky='ew', padx=5, pady=5)

        ttk.Label(username_frame, text="Username").grid(column=0, row=0, sticky='e', padx=5)

        self.username = tk.StringVar()
        self.username_entry = ttk.Entry(username_frame, textvariable=self.username)
        self.username_entry.grid(column=1, row=0, sticky="ew", padx=5)

        username_frame.columnconfigure(1, weight=1)

    def check_login(self):
        username = self.username.get()
        password = self.password.get()

        if not username or not password:
            showerror("Login Failed", "Username and Password cannot be empty")
            return
        
        user_type = UserType.STUDENT if str(self.name).lower() == "student" else UserType.TEACHER
        user = get_user_by_id(username, type=user_type)

        if user is None:
            self.error_text.config(text="Incorrect Username or Password")
            return
        
        elif user.password != password:
            self.error_text.config(text="Incorrect Username or Password")
            return
      
        
        global_app_state.login(user)
        self.on_success()
