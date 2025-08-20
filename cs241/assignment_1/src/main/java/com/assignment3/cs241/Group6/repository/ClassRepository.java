package com.assignment3.cs241.Group6.repository;

import com.assignment3.cs241.Group6.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
}
