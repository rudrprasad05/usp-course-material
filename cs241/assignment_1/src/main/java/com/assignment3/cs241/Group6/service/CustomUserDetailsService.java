package com.assignment3.cs241.Group6.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.assignment3.cs241.Group6.entity.User;
import com.assignment3.cs241.Group6.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);  // Fetch the user from your DB

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Map the integer role to a GrantedAuthority as a string (e.g., "0", "1", "2", "3")
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(String.valueOf(user.getRole()))  // Map role as authority
        );

        // Return UserDetails object with username, password, and authorities
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


}
