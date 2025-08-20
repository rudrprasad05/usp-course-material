import os
from socket import *
import threading
import tkinter as tk
from tkinter import PhotoImage, ttk
from a1.common import TimerFrame, ChatWindow
from a1.client.client import handle_file_download, leave_session
from a1.context.global_state import global_app_state
from a1.models.chat import Chat, ChatType, send_chat
from a1.models.user import DUMMY_USER
from a1.utils.constants import *

class ScrollableFrame(ttk.Frame):
    def __init__(self, container, *args, **kwargs):
        super().__init__(container, *args, **kwargs)

        # themes
        self.bg_color = "#eff6ff"        # blue-50
        self.primary_color = "#3b82f6"   # blue-500

        # style setup
        style = ttk.Style()
        style.configure("Login.TFrame", background=self.bg_color)
        style.configure("Timer.TLabel", font=("Segoe UI", 14, "bold"), background=self.bg_color, foreground=self.primary_color)
        style.configure("Card.TFrame", background=self.bg_color)

        self.container = container
        self.configure(style="Login.TFrame")

        # main wrapper
        self.main_frame = ttk.Frame(self, style="Login.TFrame")
        self.main_frame.grid(row=0, column=0, sticky="nsew")
        self.rowconfigure(0, weight=1)
        self.columnconfigure(0, weight=1)

        # divide screen 50-50
        self.main_frame.columnconfigure(0, weight=1)
        self.main_frame.columnconfigure(1, weight=1)
        self.main_frame.rowconfigure(0, weight=1)

        self.show_user_list()
        self.show_chat()
       
    def show_chat(self):
        # chat
        self.chat_frame = ttk.Frame(self.main_frame, padding=10, style="Login.TFrame")
        self.chat_frame.grid(row=0, column=1, sticky="nsew")

        # label for chat
        ttk.Label(self.chat_frame, text="Chat", font=("Helvetica", 12, "bold")).grid(row=0, column=0, columnspan=2, sticky="w", pady=(0, 5))

        # chat canvas
        self.chat_canvas = tk.Canvas(self.chat_frame, bg=self.bg_color, bd=0, highlightthickness=0)
        self.chat_scrollbar = ttk.Scrollbar(self.chat_frame, orient="vertical", command=self.chat_canvas.yview)
        
        self.chat_scrollable_frame = ttk.Frame(self.chat_canvas, style="Login.TFrame")
        
        # chat autoscroll
        self.chat_scrollable_frame.bind(
            "<Configure>",
            lambda e: self.chat_canvas.configure(scrollregion=self.chat_canvas.bbox("all"))
        )

        # window scrollable content
        self.chat_canvas.create_window((0, 0), window=self.chat_scrollable_frame, anchor="nw")
        self.chat_canvas.configure(yscrollcommand=self.chat_scrollbar.set)

        # layout the chat canvas and scrollbar
        self.chat_canvas.grid(row=1, column=0, sticky="nsew")
        self.chat_scrollbar.grid(row=1, column=1, sticky="ns")

        # message input field
        self.text_message_frame()

        self.chat_frame.columnconfigure(0, weight=1)
        self.chat_frame.rowconfigure(1, weight=1)

    def text_message_frame(self):
        # frame to contain the message input and buttons
        text_frame = ttk.Frame(self.chat_frame, padding=(0, 10, 0, 0), style="Login.TFrame")
        text_frame.grid(row=2, column=0, columnspan=2, sticky="sew", padx=0, pady=5)

        # configure columns for dynamic resizing
        text_frame.columnconfigure(0, weight=1)
        text_frame.columnconfigure(1, weight=0)
        text_frame.columnconfigure(2, weight=0)

        # chat entry field
        self.chat_entry_var = tk.StringVar()
        self.chat_entry = ttk.Entry(text_frame, textvariable=self.chat_entry_var)
        self.chat_entry.grid(column=0, row=0, sticky="ew", padx=(0, 5), pady=5)

        # path for icons
        CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
        BASE_DIR = os.path.abspath(os.path.join(CURRENT_DIR, '..', '..'))
        SEND_TEXT_ICON_PATH = os.path.join(BASE_DIR, 'a1', 'public', 'send-horizontal.png')

        self.send_icon = PhotoImage(file=SEND_TEXT_ICON_PATH)
       
        # submit button with send icon and consistent style
        self.submit_btn = tk.Button(
            text_frame,
            image=self.send_icon,
            command=self.send_message,
            padx=5,
            pady=5,
            width=20,
            height=20,
            relief="flat",
            bd=0,
            bg=self.bg_color
        )
        self.submit_btn.grid(column=2, row=0, pady=2, padx=2)

  
    def send_message(self):
        txt = self.chat_entry_var.get()
        chat_msg = Chat(chat_type=ChatType.GLOBAL_CHAT, text=txt, from_user=global_app_state.user, to_user=DUMMY_USER)
        
        # clear the input field
        self.chat_entry_var.set("")

        # update chat
        send_chat(chat_msg)
            
    def show_user_list(self):
        # user list
        self.user_frame = ttk.Frame(self.main_frame, style="Login.TFrame", padding=10)
        self.user_frame.grid(row=0, column=0, sticky="nsew", padx=(5, 0), pady=5)

        # label for Users
        ttk.Label(
            self.user_frame,
            text="Users",
            font=("Helvetica", 12, "bold")
        ).grid(row=0, column=0, columnspan=2, sticky="w", pady=(0, 5))

        user_canvas = tk.Canvas(self.user_frame, bg=self.bg_color, highlightthickness=0)
        user_scrollbar = ttk.Scrollbar(self.user_frame, orient="vertical", command=user_canvas.yview)

        # inner frame
        self.user_scrollable_frame = ttk.Frame(user_canvas, style="Login.TFrame")
        self.user_scrollable_frame.bind(
            "<Configure>",
            lambda e: user_canvas.configure(scrollregion=user_canvas.bbox("all"))
        )

        user_canvas.create_window((0, 0), window=self.user_scrollable_frame, anchor="nw")
        user_canvas.configure(yscrollcommand=user_scrollbar.set)

        # Grid layout
        user_canvas.grid(row=1, column=0, sticky="nsew")
        user_scrollbar.grid(row=1, column=1, sticky="ns")

        self.user_frame.columnconfigure(0, weight=1)
        self.user_frame.rowconfigure(1, weight=1)


class SessionWindow(tk.Toplevel):
    def __init__(self, container, show_login=None):
        super().__init__(container)

        self.container = container
        self.bg_color = "#eff6ff"

        self.users = {}
        self.user_cards = {}
        self.chats: set[Chat] = set()

        # window setup
        self.title(f"Session Window ({global_app_state.user.name})")
        self.center_window(600, 450)
        self.resizable(True, True)
        self.columnconfigure(0, weight=1)
        self.rowconfigure(0, weight=1)

        self.show_login = show_login

        # themes
        style = ttk.Style()
        style.theme_use("clam")
        style.configure("Session.TFrame", background=self.bg_color)
        style.configure("Card.TFrame", background=self.bg_color)
        style.configure("CardText.TLabel", background=self.bg_color)

        # define wrapper
        self.wrapper = ttk.Frame(self,  style="Session.TFrame")
        self.wrapper.grid(column=0, row=0, sticky="nsew")
        
        self.wrapper.columnconfigure(0, weight=1)
        self.wrapper.columnconfigure(1, weight=0) 
        self.wrapper.rowconfigure(1, weight=1)

        # generate header
        self.show_header()

        self.scroll = ScrollableFrame(self.wrapper) 
        self.scroll.grid(row=1, column=0, columnspan=2, sticky="nsew", padx=5, pady=5)
        
        # window close handler
        self.update()
        self.protocol("WM_DELETE_WINDOW", self.handle_session_end)

    def show_header(self):
        style = ttk.Style()
        style.configure("Header.TLabel", font=("Segoe UI", 18, "bold"), background="#eff6ff", foreground="#3b82f6")
        style.configure("Session.TFrame", background="#eff6ff")
        style.configure("TButton", font=("Segoe UI", 12), background="#3b82f6", foreground="white")
        style.map("TButton", background=[("active", "#2563eb")])
        style.configure("Info.TLabel", font=("Segoe UI", 12), background="#eff6ff")

        # header label
        self.header_label = ttk.Label(self.wrapper, text="Session", style="Header.TLabel")
        self.header_label.grid(column=0, row=0, sticky="w", padx=20, pady=5)

        # attendance info frame
        self.attendance_view_wrapper = ttk.Frame(self.wrapper, style="Session.TFrame")
        self.attendance_view_wrapper.grid(column=1, row=0, padx=10, pady=10, sticky="nsew")

        self.attendance_view_wrapper.columnconfigure(0, weight=1)
        self.attendance_view_wrapper.columnconfigure(1, weight=1)
        self.attendance_view_wrapper.columnconfigure(2, weight=1)

        # timer
        self.timer = TimerFrame(self.attendance_view_wrapper, is_server=True)
        self.timer.grid(column=0, row=0, sticky="w", padx=5, pady=5)

        # attendance count
        self.attendance_count_label = ttk.Label(
            self.attendance_view_wrapper,
            text="0 students",
            style="Info.TLabel"
        )
        self.attendance_count_label.grid(column=1, row=0, sticky="w", padx=5, pady=5)

        # end session button
        self.end_session_btn = ttk.Button(
            self.attendance_view_wrapper,
            text="End",
            command=self.handle_session_end
        )
        self.end_session_btn.grid(column=2, row=0, sticky="e", padx=5, pady=5)

    # gracefullt exit session using thread to as its nonblocking
    def handle_session_end(self):
        thread = threading.Thread(target=leave_session, daemon=True)
        thread.start()
        thread.join()
        self.destroy()
        global_app_state.attendance_list = {}
        global_app_state.is_session_running = False
        global_app_state.chat = set()
        global_app_state.chat_widgets = {}
        self.attendance_view_wrapper.destroy()
        global_app_state.attendance_list = {}

    # open new window for P2P communication with other nodes. 
    def open_p2p_chat(self, user):
        if user.id != global_app_state.user.id:
            client_port = self.est_conn(user.id)
            self.chat_window = ChatWindow(container=self.container, user=user, client_port=client_port)
        else:
            print("Cannot open chat with the logged-in user.")

    # check connections
    def est_conn(self, user_id) -> int:
        client_port = 0
        for port, user in global_app_state.connections.items():
            if user.id == user_id:
                client_port = port
                
                break

        return client_port

    # create user cards for attendance
    def create_user_card(self):
        style = ttk.Style()
        style.configure("Card.TFrame", background="#ffffff", relief="solid", borderwidth=1)
        style.configure("Card.TLabel", background="#ffffff", font=("Segoe UI", 10))
        style.configure("CardStatus.TLabel", background="#ffffff")
        style.configure("MutedTime.TLabel", background="#ffffff", foreground="#6b7280", font=("Segoe UI", 10))

        for i, user in enumerate(self.users):
            if user.id == global_app_state.user.id:
                continue

            user_id = user.id
            is_active = global_app_state.attendance_list[user]
            color = "green" if is_active else "red"

            if user_id in self.user_cards:
                frame = self.user_cards[user_id]
                status_label = frame.nametowidget(frame.status_label_id)
                status_label.config(background=color)

                if is_active:
                    self.send_chat_btn.state(["!disabled"])
                else:
                    self.send_chat_btn.state(["disabled"])

            else:
                frame = ttk.Frame(
                    self.scroll.user_scrollable_frame,
                    style="Card.TFrame",
                    padding=10
                )
                frame.grid(row=i, column=0, sticky="ew", padx=5, pady=5)
                frame.configure(borderwidth=1, relief="solid")
                frame.configure(style="Card.TFrame")

                # ID Label
                id_label = ttk.Label(frame, text=f"ID: {user.id}", style="Card.TLabel")
                id_label.grid(row=0, column=0, columnspan=3, sticky="w", pady=(0, 5))

                # Status Dot
                status = tk.Label(
                    frame,
                    width=2,
                    height=1,
                    background=color,
                    relief="flat",
                    borderwidth=1,
                    highlightthickness=0
                )
                status.grid(row=1, column=0, sticky="w", padx=(0, 10))

                # Name Label
                name_label = ttk.Label(frame, text=user.name, style="Card.TLabel")
                name_label.grid(row=1, column=1, sticky="w")

                # Chat Button
                self.send_chat_btn = ttk.Button(frame, text="Chat", command=lambda u=user: self.open_p2p_chat(user=u))
                self.send_chat_btn.grid(row=1, column=2, sticky="e", padx=5)

                frame.status_label_id = status._w
                self.user_cards[user_id] = frame

    def handle_chat(self):
        self.chats = global_app_state.chat.copy()

        # clear the existing chat widgets
        for widget in self.scroll.chat_scrollable_frame.winfo_children():
            widget.destroy()

        # sort all chats by timestamp
        sorted_chats = sorted(self.chats, key=lambda chat: chat.timestamp)

        # re-render each chat message to avoid stale data
        for i, chat in enumerate(sorted_chats):
            row_index = len(self.scroll.chat_scrollable_frame.winfo_children())

            frame = ttk.Frame(self.scroll.chat_scrollable_frame, style="Card.TFrame", padding=10)
            frame.grid(row=row_index, column=0, sticky="ew", padx=10, pady=6)

            frame.columnconfigure(0, weight=1)
            frame.columnconfigure(1, weight=0)

            name = "You" if chat.from_user.id == global_app_state.user.id else chat.from_user.name
            timestamp_str = chat.timestamp.strftime("%H:%M:%S")

            # First row: sender + timestamp
            ttk.Label(frame, text=name, style="CardText.TLabel").grid(row=0, column=0, sticky="w")
            ttk.Label(frame, text=timestamp_str, style="CardText.TLabel").grid(row=0, column=1, sticky="e")

            # Second row: message
            if chat.chat_type == ChatType.JOIN_SESSION:
                msg = f"{name} joined session"
            elif chat.chat_type == ChatType.LEAVE_SESSION:
                msg = f"{name} left session"
            elif chat.chat_type == ChatType.FILE:
                ttk.Button(frame, text="download", command=lambda: handle_file_download(chat.text)).grid(row=1, column=1, sticky="w", columnspan=1)
            else:
                msg = chat.text

            ttk.Label(frame, text=msg, style="CardText.TLabel", wraplength=500, justify="left").grid(row=1, column=0, columnspan=2, sticky="w", pady=(4, 0))

            global_app_state.chat_widgets[chat.timestamp] = frame

            # auto scroll to bottom
            self.scroll.chat_canvas.update_idletasks()
            self.scroll.chat_canvas.yview_moveto(1.0)

    # update loop for the window. runs every 1 second
    def update(self):
        
        # update attendace count
        self.attendance_count_label.config(text=f"{len(global_app_state.get_attenace())} active")
        self.users = global_app_state.get_attenace().copy()
        self.create_user_card()
        self.handle_chat()

        self.after(1000, self.update)

    # center window to screen
    def center_window(self, width, height):
        screen_width = self.winfo_screenwidth()
        screen_height = self.winfo_screenheight()
        x = (screen_width // 2) - (width // 2)
        y = (screen_height // 2) - (height // 2)
        self.geometry(f"{width}x{height}+{x}+{y}")

