package com.assignment3.cs241.Group6.controller;

import com.assignment3.cs241.Group6.config.RedirectionConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.assignment3.cs241.Group6.entity.User;
import com.assignment3.cs241.Group6.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;


@Controller
public class DashboardController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/dashboard")
    public void dashboard(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = (User) userService.findByUsername(username);

        RedirectionConfig.RedirectToDashboard(user, request, response);

    }

}
