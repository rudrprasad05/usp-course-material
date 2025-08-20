package com.assignment3.cs241.Group6.entity;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "Notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationID;

    private Long userID;
    private String description;
    private Time timeSent;

    // Getters and Setters
    public Long getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(Long notificationID) {
        this.notificationID = notificationID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Time getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Time timeSent) {
        this.timeSent = timeSent;
    }
}
