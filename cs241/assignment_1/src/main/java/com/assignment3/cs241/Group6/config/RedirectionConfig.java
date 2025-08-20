package com.assignment3.cs241.Group6.config;

import com.assignment3.cs241.Group6.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RedirectionConfig {
    public static String RedirectToDashboard(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(user == null) {
            return "redirect:/login";
        }
        if (user.getRole() == 0) {
            return "redirect:/user/dashboard";
        } else if (user.getRole() == 1) {
            return "redirect:/parent/dashboard";
        } else if (user.getRole() == 2) {
            return "redirect:/teacher/dashboard";
        } else if (user.getRole() == 3) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/";
        }

    }
}
