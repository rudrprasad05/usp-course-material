package com.assignment3.cs241.Group6.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherID;

    private String name;
    private String email;
    private String specialization;

    // One-to-one relationship with User
    @OneToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private User user;

    // One-to-one relationship with InviteLink
    @OneToOne(mappedBy = "teacher", cascade = CascadeType.ALL)
    private InviteLink inviteLink;

    // One-to-Many with Subject
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Subject> subjects;

    // Getters and Setters
    public Long getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Long teacherID) {
        this.teacherID = teacherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public InviteLink getInviteLink() {
        return inviteLink;
    }

    public void setInviteLink(InviteLink inviteLink) {
        this.inviteLink = inviteLink;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

}
