package com.assignment3.cs241.Group6.repository;

import com.assignment3.cs241.Group6.entity.ParentStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentStudentRepository extends JpaRepository<ParentStudent, Long> {
}
