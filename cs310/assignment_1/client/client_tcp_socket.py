import json
from socket import *

from a1.utils.constants import SERVER_NAME, SERVER_PORT
from a1.utils.libs import receive_message    
from a1.utils.libs import decode_message

# create a tcp socket connection. For mac and windows where raw sockets access was not allowed by OS
def tcp_socket(port: int = SERVER_PORT, msg: str = ""):
    clientSocket = socket(AF_INET,SOCK_STREAM)
    clientSocket.connect((SERVER_NAME,port))

    clientSocket.sendall(msg.encode())
    sentence = receive_message(clientSocket)
    decoded = decode_message(sentence)

    clientSocket.close() 

    return decoded
