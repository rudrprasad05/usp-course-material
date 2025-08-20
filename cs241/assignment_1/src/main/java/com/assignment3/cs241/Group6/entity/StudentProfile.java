package com.assignment3.cs241.Group6.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "StudentProfile")
public class StudentProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentProfileID;

    private Long studentID;
    private String studentMedicalHistory;

    // Getters and Setters
    public Long getStudentProfileID() {
        return studentProfileID;
    }

    public void setStudentProfileID(Long studentProfileID) {
        this.studentProfileID = studentProfileID;
    }

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public String getStudentMedicalHistory() {
        return studentMedicalHistory;
    }

    public void setStudentMedicalHistory(String studentMedicalHistory) {
        this.studentMedicalHistory = studentMedicalHistory;
    }
}
