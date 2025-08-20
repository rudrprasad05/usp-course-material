package com.assignment3.cs241.Group6.repository;

import com.assignment3.cs241.Group6.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
}
