package com.assignment3.cs241.Group6.controller;

import com.assignment3.cs241.Group6.entity.User;
import com.assignment3.cs241.Group6.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
public class AuthController {

    public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model, HttpServletResponse response) throws IOException {
        checkAuth(response);

        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model, HttpServletResponse response) throws IOException {
        checkAuth(response);

        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {

        userService.save(user); // Save user in the database
        model.addAttribute("user", user);
        return "redirect:/login"; // Redirect to login after successful registration
    }

    private void checkAuth(HttpServletResponse response) throws IOException {
        if (userIsAuthenticated()) {
            String role = getRoleOfAuthenticatedUser();
            switch (role) {
                case "3":  // Admin
                    response.sendRedirect("/admin/dashboard");
                    break;
                case "2":  // Teacher
                    response.sendRedirect("/teacher/dashboard");
                    break;
                case "1":  // Parent
                    response.sendRedirect("/parent/dashboard");
                    break;
                case "0":  // User
                    response.sendRedirect("/user/dashboard");
                    break;
            }
        } else {
            response.sendRedirect("/login");
        }
    }

    private boolean userIsAuthenticated() {
        // Logic to check if the user is authenticated
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    private String getRoleOfAuthenticatedUser() {
        // Logic to get the role of the authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().iterator().next().getAuthority();
    }
}
