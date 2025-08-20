import json
from a1.utils.constants import CARRIAGE_RETURN

# helper function to receiver message
def receive_message(socket):
    buffer = ""
    while not buffer.endswith(CARRIAGE_RETURN):
        chunk = socket.recv(1024).decode()
        if not chunk:
            break
        buffer += chunk

    return buffer.strip()

# helper function to decode message
def decode_message(message: str):
    from a1.models.message import Message
    if not message:
        return None
    try:
        msg_dct = json.loads(message)
        return Message.from_dict(msg_dct)
    except json.JSONDecodeError as e:
        print(f"[Error]: {e}")
        return None
    