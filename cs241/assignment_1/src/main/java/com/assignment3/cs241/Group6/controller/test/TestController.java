package com.assignment3.cs241.Group6.controller.test;


import com.assignment3.cs241.Group6.repository.InviteLinkRepository;
import com.assignment3.cs241.Group6.repository.TeacherRepository;
import com.assignment3.cs241.Group6.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private InviteLinkRepository inviteLinkRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/test")
    public String userDashboard(Model model) {
        model.addAttribute("message", "Welcome to the User Dashboard");
        return "test/test";  // Refers to src/main/resources/templates/user/dashboard.html
    }


}

