package com.assignment3.cs241.Group6.repository;

import com.assignment3.cs241.Group6.entity.Student;
import com.assignment3.cs241.Group6.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUser(User user);
}
