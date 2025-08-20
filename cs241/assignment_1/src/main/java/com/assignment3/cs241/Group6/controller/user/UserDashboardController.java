package com.assignment3.cs241.Group6.controller.user;


import com.assignment3.cs241.Group6.config.Link;
import com.assignment3.cs241.Group6.entity.User;
import com.assignment3.cs241.Group6.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserDashboardController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/user")
    public String redirectToDashboard(Model model) {
        return "redirect:/user/dashboard";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(Model model) {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        List<Link> sideNavLinks = new ArrayList<>();

        sideNavLinks.add(new Link("/user/dashboard", "Dashboard"));
        sideNavLinks.add(new Link("/user/subjects", "subjects"));
        sideNavLinks.add(new Link("/user/profile", "Profile"));

        model.addAttribute("sideNavLinks", sideNavLinks);
        model.addAttribute("pageTitle", "User Dashboard");

        // Check if the user is associated with the Student table
        if (userService.isUserAssociatedWithStudent(user.getUserID())) {
            model.addAttribute("message", "Welcome to the Dashboard!");
            return "user/dashboard";  // User is associated with a student, show dashboard
        } else {
            return "redirect:/user/onboarding";
        }
    }

    @GetMapping("/user/subjects")
    public String userSubjects(Model model) {
        // Get the authenticated user
        return "user/subjects";
    }
}
