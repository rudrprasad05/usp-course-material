package com.assignment3.cs241.Group6.controller.teacher;

import com.assignment3.cs241.Group6.entity.InviteLink;
import com.assignment3.cs241.Group6.entity.Subject;
import com.assignment3.cs241.Group6.entity.Teacher;
import com.assignment3.cs241.Group6.entity.User;
import com.assignment3.cs241.Group6.repository.InviteLinkRepository;
import com.assignment3.cs241.Group6.repository.TeacherRepository;
import com.assignment3.cs241.Group6.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Controller
public class TeacherController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private InviteLinkRepository inviteLinkRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/teacher/dashboard")
    public String userDashboard(Model model) {
        model.addAttribute("message", "Welcome to the User Dashboard");

        Teacher teacher = getLoggedTeacher();  // Implement method to get logged-in teacher
        assert teacher != null;
        Set<Subject> subjects = teacher.getSubjects();
        model.addAttribute("subjects", subjects);

        LOGGER.info(subjects.toString());
        return "teacher/dashboard";  // Refers to src/main/resources/templates/user/dashboard.html
    }

    private Teacher getLoggedTeacher() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            return teacherRepository.findByUserUsername(username);
        }

        return null;
    }


}

