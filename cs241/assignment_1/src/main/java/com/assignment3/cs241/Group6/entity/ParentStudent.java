package com.assignment3.cs241.Group6.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ParentStudent")
public class ParentStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentStudentID;

    private Long userID;
    private Long parentID;

    // Getters and Setters
    public Long getParentStudentID() {
        return parentStudentID;
    }

    public void setParentStudentID(Long parentStudentID) {
        this.parentStudentID = parentStudentID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getParentID() {
        return parentID;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
    }
}
