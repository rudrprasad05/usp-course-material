# Attendance Management System

An **Attendance Management System** designed to streamline attendance tracking and management for educational institutions. This application supports multiple user roles with distinct functionalities and provides robust authentication and alert systems.

## Features

- **Multi-User Authentication**  
  Secure login system with roles:
    - **Admin**: Manages the system and users.
    - **Teacher**: Tracks and manages student attendance.
    - **Student**: Views attendance records.
    - **Parent**: Monitors their child's attendance.

- **Admin Panel**
    - User management (create, update, delete users).
    - Monitor attendance system-wide.

- **Parent Login**
    - Access child's attendance records.
    - Receive email alerts.

- **Alert System**
    - Email notifications for password changes.
    - Email confirmations during registration.
    - Attendance-related alerts using [Resend](https://resend.com/).

- **Password Management**
    - Password reset functionality with email verification.

- **Attendance Tracking**  
  Teachers can track attendance, and students/parents can view it in real-time.

## Technologies Used

- **Backend**: Java 21 with Spring Boot
- **Frontend**: Bootstrap
- **Database**: MySQL
- **Email Services**: Resend

## Installation and Setup

### Prerequisites

- **Java 21** installed
- **MySQL** installed and configured
- **Maven** installed
- **Resend API key** for email functionality

### Steps

1. **Clone the Repository**
```bash
git clone https://github.com/your-repo/attendance-management-system.git;
cd attendance-management-system;
```

2. **Set Up MySQL Database**
- Create a database named attendance_db.
- Update the application.properties file with your MySQL credentials
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/attendance_db
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
```
3. **Configure Email Service**
```properties
resend.api.key=<your-resend-api-key>
```

4. **Build the project**
```bash
mvn clean install
```

4. **Run the application**
```bash
mvn spring-boot:run
```