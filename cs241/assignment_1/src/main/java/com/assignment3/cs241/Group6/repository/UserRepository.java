package com.assignment3.cs241.Group6.repository;

import com.assignment3.cs241.Group6.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); // Find user by username
}