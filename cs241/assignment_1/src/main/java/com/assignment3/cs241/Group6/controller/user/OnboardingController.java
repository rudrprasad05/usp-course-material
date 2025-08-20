package com.assignment3.cs241.Group6.controller.user;

import com.assignment3.cs241.Group6.controller.teacher.TeacherController;
import com.assignment3.cs241.Group6.entity.Student;
import com.assignment3.cs241.Group6.repository.StudentRepository;
import com.assignment3.cs241.Group6.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.assignment3.cs241.Group6.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Controller
public class OnboardingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OnboardingController.class);


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserServiceImpl userService;

    // Display the onboarding form
    @GetMapping("/user/onboarding")
    public String onboardingForm(Model model) {
        model.addAttribute("student", new Student());  // Bind form to Student entity
        return "user/onboarding";  // Show the onboarding.html page
    }

    // Handle the POST request when the user submits the form
    @PostMapping("/user/onboarding")
    public String handleOnboarding(@ModelAttribute Student student, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Find the user by username
        User user = (User) userService.findByUsername(username);

        // Associate the student with the user
        student.setUser(user);

        LOGGER.info(student.toString());

        // Save the student
        studentRepository.save(student);

        model.addAttribute("message", "Onboarding complete! Welcome, " + student.getName());
        return "redirect:/user/dashboard";  // Redirect to dashboard after onboarding
    }
}
