package com.assignment3.cs241.Group6.config;

import com.assignment3.cs241.Group6.entity.User;
import com.assignment3.cs241.Group6.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        // Store the user in the session
        request.getSession().setAttribute("currentUser", user);

        request.getSession().setAttribute("toastMessage", "Login successful!");
        request.getSession().setAttribute("toastTitle", "Success");
        request.getSession().setAttribute("toastClass", "bg-success");

        switch (user.getRole()){
            case 0:
                response.sendRedirect("/user/dashboard");
                break;
            case 1:
                response.sendRedirect("/parent/dashboard");
                break;
            case 2:
                response.sendRedirect("/teacher/dashboard");
                break;
            case 3:
                response.sendRedirect("/admin/dashboard");
                break;
            default:
                response.sendRedirect("/");
                break;

        }
//        if (user.getRole() == 0) {
//            response.sendRedirect("/user/dashboard");
//        } else if (user.getRole() == 1) {
//            response.sendRedirect("/parent/dashboard");
//        } else if (user.getRole() == 2) {
//            response.sendRedirect("/teacher/dashboard");
//        } else if (user.getRole() == 3) {
//            response.sendRedirect("/admin/dashboard");
//        } else {
//            response.sendRedirect("/dashboard");
//        }
    }
}

