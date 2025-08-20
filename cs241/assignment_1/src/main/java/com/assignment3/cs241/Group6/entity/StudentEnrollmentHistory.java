package com.assignment3.cs241.Group6.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "StudentEnrollmentHistory")
public class StudentEnrollmentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentEnrollmentHistoryID;

    private Long studentID;
    private java.sql.Date dateOfEnrollment;

    // Getters and Setters
    public Long getStudentEnrollmentHistoryID() {
        return studentEnrollmentHistoryID;
    }

    public void setStudentEnrollmentHistoryID(Long studentEnrollmentHistoryID) {
        this.studentEnrollmentHistoryID = studentEnrollmentHistoryID;
    }

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public java.sql.Date getDateOfEnrollment() {
        return dateOfEnrollment;
    }

    public void setDateOfEnrollment(java.sql.Date dateOfEnrollment) {
        this.dateOfEnrollment = dateOfEnrollment;
    }
}
