package com.assignment3.cs241.Group6.service;

import com.assignment3.cs241.Group6.entity.Student;
import com.assignment3.cs241.Group6.entity.User;
import com.assignment3.cs241.Group6.repository.StudentRepository;
import com.assignment3.cs241.Group6.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode the password
        return userRepository.save(user);
    }

    // Method to check if the user is associated with the Student table
    public boolean isUserAssociatedWithStudent(Long userID) {
        User user = userRepository.findById(userID).orElse(null);

        // If the User is not found, return false
        if (user == null) {
            return false;
        }

        // Now find the Student associated with the User
        Student student = studentRepository.findByUser(user);

        // Return true if a Student is associated with the User
        return student != null;
    }


}
