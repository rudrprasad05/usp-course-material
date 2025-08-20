from tkinter import ttk
from a1.models.chat import Chat, ChatType, send_chat
from a1.models.user import UserType
from a1.utils.constants import MAX_SESSION_TIME, SESSION_WARNING_TIME
from a1.context.global_state import global_app_state

seconds_in_minute = 60
tick_delay = 1000  # in ms

'''
timer frame, used by both client & server
display time left in session
'''
class TimerFrame(ttk.Frame):
    def __init__(self, container, is_server: bool = False, server_time=None, *args, **kwargs):
        super().__init__(container, *args, **kwargs)

        # Theme colors
        bg_color = "#eff6ff"        
        primary_color = "#3b82f6"   

        # Style setup
        style = ttk.Style()
        style.configure("Login.TFrame", background=bg_color)
        style.configure("Timer.TLabel", font=("Segoe UI", 14, "bold"), background=bg_color, foreground=primary_color)

        self.configure(style="Login.TFrame")

        self.label = ttk.Label(self, text="Timer", style="Timer.TLabel")
        self.label.grid(row=0, column=0, sticky="e", padx=5, pady=5)

        self.remaining = MAX_SESSION_TIME * seconds_in_minute if server_time is None else server_time
        self.is_server = is_server
        self.warning_sent = False

        self.update_timer()

    def update_timer(self):
        mins, secs = divmod(self.remaining, seconds_in_minute)
        global_app_state.time_left = self.remaining
        self.label.config(text=f"{mins:02}:{secs:02}")

        if self.remaining == SESSION_WARNING_TIME and not self.warning_sent and global_app_state.user.role == UserType.TEACHER:
            self.on_timer_warning()
            self.warning_sent = True

        if self.remaining > 0:
            self.remaining -= 1
            self.after(tick_delay, self.update_timer)
        else:
            self.on_timer_complete()

    def on_timer_warning(self):
        if self.is_server:
            chat = Chat(
                chat_type=ChatType.GLOBAL_CHAT,
                text="Session ending in 5 minutes",
                from_user=global_app_state.user,
                to_user=global_app_state.user
            )
            send_chat(chat)

    def on_timer_complete(self):
        if self.is_server:
            chat = Chat(
                chat_type=ChatType.GLOBAL_CHAT,
                text="Session has ended",
                from_user=global_app_state.user,
                to_user=global_app_state.user
            )
            send_chat(chat)
