package com.assignment3.cs241.Group6.service;

import com.assignment3.cs241.Group6.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User save(User user); // Save a user during registration
    User findByUsername(String username); // Find user by username for login
}
