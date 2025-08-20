import threading
import tkinter as tk
from tkinter import ttk
from a1.client.client import join_session
from a1.client.session_frame import SessionWindow
from a1.context.global_state import global_app_state

class HomeFrame(ttk.Frame):
    def __init__(self, container, show_login=None):
        super().__init__(container)

        self._is_header_updated = False
        self.show_login = show_login
        self.container = container

        # === Header: Blue Background, Full Width ===
        header_wrapper = tk.Frame(self, bg="#1E3A8A")  # Blue background
        header_wrapper.grid(column=0, row=0, sticky="ew")
        header_wrapper.columnconfigure(0, weight=1)
        header_wrapper.columnconfigure(1, weight=0)

        # Hello {name} label in big bold white text
        self.header_label = tk.Label(
            header_wrapper,
            text="Hello ...",
            font=("Helvetica", 20, "bold"),
            fg="white",
            bg="#1E3A8A"
        )
        self.header_label.grid(column=0, row=0, sticky="w", padx=10, pady=10)

        # Logout button aligned right
        self.logout_btn = ttk.Button(header_wrapper, text="Logout", command=self.handle_logout)
        self.logout_btn.grid(column=1, row=0, sticky="e", padx=10, pady=10)

        # === Actions Section ===
        self.actions = ActionsFrame(self, self.show_session)
        self.actions.grid(row=1, column=0, sticky="ew")

        self.grid(sticky="nsew")
        self.columnconfigure(0, weight=1)
        self.rowconfigure(1, weight=1)

        self.update_header()
        self.update_tick()

    def handle_logout(self):
        global_app_state.logout()
        self.show_login()

    def show_session(self):
        global_app_state.is_session_running = True
        self.session_window = SessionWindow(self.container, show_login=self.show_login)

    def update_tick(self):
        if not global_app_state.is_session_running:
            if hasattr(self, 'session_window'):
                print("window should be closed")
                self.session_window.destroy()
            else:
                self.after(1000, self.update_tick)
        else:
            self.after(1000, self.update_tick)

    def update_header(self):
        if self._is_header_updated:
            return
        if global_app_state.user:
            self.header_label.config(text=f"Hello {global_app_state.user.name}")
            self._is_header_updated = True
        else:
            self.after(100, self.update_header)

class ActionsFrame(ttk.Frame):
    def __init__(self, container, on_change):
        super().__init__(container)

        self.bg_color = "#eff6ff"  # blue-50
        self.primary_color = "#3b82f6"  # blue-500
        self.primary_hover = "#2563eb"  # blue-600
        self.error_color = "#ef4444"  # red-500

        self.configure(style="Login.TFrame")
        self.columnconfigure(0, weight=1)
        self.rowconfigure(0, weight=1)

        # apply modern style
        style = ttk.Style()
        style.theme_use("clam")
        style.configure("Login.TFrame", background=self.bg_color)
        style.configure("Header.TLabel", font=("Segoe UI", 24, "bold"), background=self.bg_color, foreground=self.primary_color)
        style.configure("TLabel", font=("Segoe UI", 12), background=self.bg_color)
        style.configure("TEntry", font=("Segoe UI", 12))
        style.configure("TButton", font=("Segoe UI", 12), background=self.primary_color, foreground="white")
        style.map("TButton", background=[("active", self.primary_hover)])

        self.change_self = on_change

        # full-height wrapper
        wrapper = ttk.Frame(self, style="Login.TFrame")
        wrapper.grid(column=0, row=0, sticky="nsew")
        wrapper.columnconfigure(0, weight=1)
        wrapper.rowconfigure(0, weight=1)

        # inner content frame for centering content vertically
        content_frame = ttk.Frame(wrapper, style="Login.TFrame")
        content_frame.grid(column=0, row=0, sticky="nsew", pady=150)  # padded center
        content_frame.columnconfigure(0, weight=1)

        # menu label
        title_label = ttk.Label(content_frame, text="Menu", style="Header.TLabel")
        title_label.grid(column=0, row=0, pady=(5, 15), sticky="n")

        # start session button
        self.start_session_btn = ttk.Button(content_frame, text="Start Session", command=self.start_session)
        self.start_session_btn.grid(column=0, row=1, padx=5, pady=5, sticky="n")

        # Error label
        self.error_label = ttk.Label(content_frame, text="", foreground=self.error_color, style="TLabel")
        self.error_label.grid(column=0, row=2, pady=(5, 0), sticky="n")

        self.update_header()
        self.grid(sticky="nsew")
        self.columnconfigure(0, weight=1)
        self.rowconfigure(0, weight=1)


    # error notification update handler
    def update_header(self):
        if global_app_state.error:
            self.error_label.config(text=f"{global_app_state.error.error_message}")
            global_app_state.error = None
        else:
            self.error_label.config(text="")

        self.after(5000, self.update_header)

    # funtion to start session
    def start_session(self):
        if global_app_state.user:
            thread = threading.Thread(target=self.run_join_session, daemon=True)
            thread.start()
        else:
            print("No user is logged in. Cannot start session.")

    # wrapper funciton to ensure start_session runs as a thread and doesnt block UI
    def run_join_session(self):
        try:
            join_session()
            self.after(0, self.change_self)
        except Exception as e:
            print(f"[Error] starting session: {e}")
