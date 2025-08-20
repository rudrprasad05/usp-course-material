from socket import *
import threading
import tkinter as tk
from tkinter import ttk, PhotoImage
from typing import List
from a1.context.global_state import global_app_state
from a1.models.chat import Chat, ChatType
from a1.models.user import User
import os

'''
Creates a window for P2P chat system for all nodes. Universal so its used by both client and server
'''
class ChatWindow(tk.Toplevel):
    def __init__(self, container, user, client_port):
        super().__init__(container)

        self.bg_color = "#EFF6FF"
        self.primary_color = "#0095F6"
        self.my_msg_color = "#2b7fff"
        self.other_msg_color = "#00a6f4"
        self.text_color = "#262626"
        self.light_text_color = "#8E8E8E"


        self.user = user
        self.client_port = client_port

        print(f"chat windown opening with user {self.user.name} on port {self.client_port}")

        self.p2p_chats: dict[User, List[Chat]] = {}

        # init window props
        self.title(f"Chat with ({self.user.name})")
        self.center_window(600, 450)
        self.resizable(False, False)
        self.columnconfigure(0, weight=1)
        self.rowconfigure(0, weight=1)

        self.configure(bg=self.bg_color)

        # call init functions to setup UI
        self.show_chat()
        self.update()

        self.chat_entry.bind("<Return>", lambda event: self.send_message())
        self.chat_entry_var = None

        # setup styles
        style = ttk.Style()
        style.configure("MyMessage.TFrame", background=self.my_msg_color, relief="solid", borderwidth=1)
        style.configure("OtherMessage.TFrame", background=self.other_msg_color, relief="solid", borderwidth=1)
        style.configure("NameLabel.TLabel", font=("Helvetica", 9, "bold"))
        style.configure("TimeLabel.TLabel", font=("Helvetica", 8), foreground="gray")
        style.configure("SystemLabel.TLabel", font=("Helvetica", 9, "italic"), foreground="gray")


    def show_chat(self):
        self.chat_frame = tk.Frame(self, bg=self.bg_color )
        self.chat_frame.grid(row=0, column=0, sticky="nsew")
        self.chat_frame.columnconfigure(0, weight=1)
        self.chat_frame.rowconfigure(0, weight=1)  
        self.chat_frame.rowconfigure(1, weight=0)  

        # canvas and scrollbar for chat
        self.chat_canvas = tk.Canvas(self.chat_frame, highlightthickness=0)
        self.chat_scrollbar = ttk.Scrollbar(self.chat_frame, orient="vertical", command=self.chat_canvas.yview)

        self.chat_canvas = tk.Canvas(self.chat_frame, highlightthickness=0, background=self.bg_color)
        self.chat_scrollbar = ttk.Scrollbar(self.chat_frame, orient="vertical", command=self.chat_canvas.yview)

        self.chat_scrollable_frame = ttk.Frame(self.chat_canvas, style="Chat.TFrame")
        self.chat_scrollable_frame.bind(
            "<Configure>",
            lambda e: self.chat_canvas.configure(scrollregion=self.chat_canvas.bbox("all"))
        )

        self.chat_scrollable_frame.columnconfigure(0, weight=1)
        self.scrollable_window_id = self.chat_canvas.create_window(
            (0, 0), window=self.chat_scrollable_frame, anchor="nw"
        )
        self.chat_canvas.bind(
            "<Configure>",
            lambda event: self.chat_canvas.itemconfig(self.scrollable_window_id, width=event.width)
        )

        self.chat_canvas.configure(yscrollcommand=self.chat_scrollbar.set)
        self.chat_canvas.grid(row=0, column=0, sticky="nsew")
        self.chat_scrollbar.grid(row=0, column=1, sticky="ns")

        # add message input area
        self.text_message_frame()


    def text_message_frame(self):
        # wrapper frame
        text_frame = ttk.Frame(self.chat_frame, style="Chat.TFrame", padding=10)
        text_frame.grid(row=1, column=0, sticky="ew", padx=0, pady=5, columnspan=2)

        # config rows
        text_frame.columnconfigure(0, weight=1)
        text_frame.columnconfigure(1, weight=0)
        text_frame.rowconfigure(0, weight=1)

        # config input
        self.chat_entry_var = tk.StringVar()
        self.chat_entry = ttk.Entry(
            text_frame, 
            style="ChatEntry.TEntry", 
            textvariable=self.chat_entry_var
        ).grid(
            column=0, 
            columnspan=1, 
            sticky="ew",
            row=0, 
            rowspan=1, 
            padx=5, 
            pady=5
        )           

        # set up send message button with custom svg background
        CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
        BASE_DIR = os.path.abspath(os.path.join(CURRENT_DIR, '..', '..'))
        SEND_TEXT_ICON_PATH = os.path.join(BASE_DIR, 'a1', 'public', 'send-horizontal.png')
        
        self.send_icon = PhotoImage(file=SEND_TEXT_ICON_PATH)
        self.submit_btn = tk.Button(
            text_frame,
            image=self.send_icon,
            command=self.send_message,
            padx=5,
            pady=5,
            relief="flat",
            bd=0,
            bg=self.bg_color,
            activebackground=self.bg_color,
            highlightthickness=0
        )
        self.submit_btn.grid(column=1, row=0, pady=2, padx=2)

    
    # fuction to display p2p chats
    def handle_chat(self) -> None:
        old_chats = self.p2p_chats.get(self.user, []) if self.p2p_chats else []
        new_chats = global_app_state.p2p_chats.get(self.user, []) if global_app_state.p2p_chats else []
        diff = new_chats[len(old_chats):]

        if not diff:
            return

        self.p2p_chats[self.user] = new_chats.copy()

        for i, chat in enumerate(diff):
            row_index = len(self.chat_scrollable_frame.winfo_children())
            is_my_message = chat.from_user.id == global_app_state.user.id

            # Container frame for the entire row
            row_frame = ttk.Frame(self.chat_scrollable_frame, style="Chat.TFrame")
            row_frame.grid(row=row_index + i, column=0, sticky="ew", pady=5)
            row_frame.columnconfigure(0, weight=1)

            if chat.chat_type in [ChatType.JOIN_SESSION, ChatType.LEAVE_SESSION]:
                # Centered system message
                system_frame = ttk.Frame(row_frame, style="Chat.TFrame")
                system_frame.grid(row=0, column=0, sticky="ew")
                system_frame.columnconfigure(0, weight=1)

                name = "You" if is_my_message else chat.from_user.name
                action_text = f"{name} {'joined' if chat.chat_type == ChatType.JOIN_SESSION else 'left'} session"

                ttk.Label(
                    system_frame,
                    text=action_text,
                    style="SystemLabel.TLabel"
                ).grid(row=0, column=0, sticky="n")
            else:
                # Message container with alignment logic
                msg_container = ttk.Frame(row_frame, style="Chat.TFrame")
                msg_container.grid(row=0, column=0, sticky="ew")
                msg_container.columnconfigure(0, weight=1 if is_my_message else 0)
                msg_container.columnconfigure(1, weight=0 if is_my_message else 1)

                msg_frame = ttk.Frame(
                    msg_container,
                    borderwidth=1,
                    relief="solid",
                    padding=10,
                    width=275,
                    style="MyMessage.TFrame" if is_my_message else "OtherMessage.TFrame"
                )

                msg_frame.grid(
                    row=0,
                    column=1 if is_my_message else 0,
                    sticky="e" if is_my_message else "w",
                    padx=10
                )

                if is_my_message:
                    ttk.Label(msg_frame, text="You", style="NameLabel.TLabel").grid(row=0, column=0, sticky="e", pady=(0, 5))
                    ttk.Label(msg_frame, text=chat.timestamp.strftime("%H:%M"), style="TimeLabel.TLabel").grid(row=0, column=1, sticky="e", pady=(0, 5))
                    ttk.Label(msg_frame, text=chat.text, wraplength=350, justify="right", style="MessageText.TLabel").grid(row=1, column=0, columnspan=2, sticky="e")
                else:
                    ttk.Label(msg_frame, text=chat.from_user.name, style="NameLabel.TLabel").grid(row=0, column=0, sticky="w", pady=(0, 5))
                    ttk.Label(msg_frame, text=chat.timestamp.strftime("%H:%M"), style="TimeLabel.TLabel").grid(row=0, column=1, sticky="e", pady=(0, 5))
                    ttk.Label(msg_frame, text=chat.text, wraplength=350, justify="left", style="MessageText.TLabel").grid(row=1, column=0, columnspan=2, sticky="w")

                global_app_state.p2p_chat_widgets[chat.timestamp] = msg_frame

        self.chat_canvas.update_idletasks()
        self.chat_canvas.yview_moveto(1.0)

    # sends message to node
    def send_message(self):
        from a1.client.client import send_p2p_chat

        if self.chat_entry_var is None:
            return
        txt = self.chat_entry_var.get()
        chat_msg = Chat(
            chat_type=ChatType.PRIVATE_CHAT, 
            text=txt, 
            from_user=global_app_state.user, 
            to_user=self.user
        )
        
        # clear the input field
        self.chat_entry_var.set("")

        if self.user not in global_app_state.p2p_chats:
            global_app_state.p2p_chats[self.user] = []

        global_app_state.add_to_p2p(user=self.user, chat=chat_msg)

        thread = threading.Thread(target=send_p2p_chat, args=(chat_msg, self.user, self.client_port), daemon=True)
        thread.start() 

    # update loop
    def update(self):
        self.handle_chat()
        self.after(1000, self.update)

    # center window
    def center_window(self, width, height):
        screen_width = self.winfo_screenwidth()
        screen_height = self.winfo_screenheight()
        x = (screen_width // 2) - (width // 2)
        y = (screen_height // 2) - (height // 2)
        self.geometry(f"{width}x{height}+{x}+{y}")

