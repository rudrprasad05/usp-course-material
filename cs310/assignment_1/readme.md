## Application Overview

This application is built using a server-client architecture. The server listens for incoming client requests and processes them accordingly. The communication between the server and client is established over specific ports.

In particular, the server listens on port 5050 (specs said 5000 but this is used by a system process on OS X). P2P ports start from 5051. Local addressing spaces were briefly looked into (127.0.0.x) but was scaped due to complex nature.

This app follows a modular approach, making it really easy to intergrate and remove parts. Moreover, it uses python modules to keep code clean and imports easier to read.

### Ports

- **Server Port**: 5050 (default)
  - Note: Port 5000 was unavailable due to being used by a system service.
- **P2P Port**: 5051 - xxxx (p2p)
  - Note: Assigned by the server as it keeps a log of all connections and ports

### How to Run the Application

1. **Start the Server**:

   - Navigate to the main / root directory, names Assessment 1
   - Run the following command:
     ```bash
     python3 -m a1.server.app
     ```

2. **Start the Client**:

   - Navigate to the main / root directory, names Assessment 1
   - Run the following command:
     ```bash
     python3 -m a1.client.app
     ```

3. Ensure both the server and client are running on the same network AND laptop for proper communication.

### Default Login Credentials

**Teacher**

- Username: `t1`
- Password: `1`

**Student 1**

- Username: `s1`
- Password: `1`

**Student 2**

- Username: `s2`
- Password: `2`

### Folder Structure

- **`/server`**: Contains the server-side code and configuration files.
- **`/client`**: Contains the client-side code and user interface logic.
- **`/context`**: Holds a singleton that stores the global context, ie login status, chat records, active status etc. Serves more or less like a globally accessible class
- **`/common`**: Took a page from ASP.Net practices. Holds common interfaces like the timer.
- **`/db`**: holds the essentials to first create a init the db. not required to run, unless db is deleted
- **`/models`**: holds all the usable classes and models like user, chats, messages, error
- **`/public`**: contains static assests
- **`/utils`**: helper classes and functions

### Extra Features

- **p2p file sharing**. Teacher and student can uplaod a file which can be downloaded by the entire class.
- **active and inactive status**. Attendance card shows green / red sqaure depending on whether or not the user is still logged in
- **downloadable attendance** the tutor can download attendace of the most recent session

--
