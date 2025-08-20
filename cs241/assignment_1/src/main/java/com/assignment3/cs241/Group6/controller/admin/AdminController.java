package com.assignment3.cs241.Group6.controller.admin;

import com.assignment3.cs241.Group6.config.Link;
import com.assignment3.cs241.Group6.config.RedirectionConfig;
import com.assignment3.cs241.Group6.entity.InviteLink;
import com.assignment3.cs241.Group6.entity.Subject;
import com.assignment3.cs241.Group6.entity.Teacher;
import com.assignment3.cs241.Group6.entity.User;
import com.assignment3.cs241.Group6.repository.InviteLinkRepository;
import com.assignment3.cs241.Group6.repository.SubjectRepository;
import com.assignment3.cs241.Group6.repository.TeacherRepository;
import com.assignment3.cs241.Group6.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.*;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private InviteLinkRepository inviteLinkRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    static List<Link> sideNavLinks = new ArrayList<>();

    public AdminController() {
        addLinks();
    }

    private static void addLinks() {
        sideNavLinks.clear();
        sideNavLinks.add(new Link("/admin/subjects", "Subjects"));
        sideNavLinks.add(new Link("/admin/invite-teachers", "Invite Teacher"));
    }

    @GetMapping("/admin")
    public String routeDashboard(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("currentUser");

        return RedirectionConfig.RedirectToDashboard(user, null, null);
    }

    @GetMapping("/admin/subjects")
    public String showSubjectsPage(Model model) {
        // Fetch all subjects from the database
        addLinks();
        model.addAttribute("sideNavLinks", sideNavLinks);
        model.addAttribute("subjects", subjectRepository.findAll());
        return "admin/subjects";
    }

    @GetMapping("/admin/dashboard")
    public String userDashboard(Model model) {
        addLinks();
        model.addAttribute("sideNavLinks", sideNavLinks);
        model.addAttribute("pageTitle", "Admin Dashboard");
        model.addAttribute("message", "Welcome to the Admin Dashboard");
        return "admin/dashboard";  // Refers to src/main/resources/templates/user/dashboard.html
    }

    // Show invite teacher page
    @GetMapping("/admin/invite-teachers")
    public String inviteTeachersPage(Model model) {
        List<Subject> allSubjects = subjectRepository.findSubjectsWithoutTeacher();
        addLinks();

        model.addAttribute("sideNavLinks", sideNavLinks);
        model.addAttribute("subjects", allSubjects);
        return "admin/invite-teacher";
    }

    @GetMapping("/admin/subjects/{subject_name}")
    public String viewSubjectDetails(@PathVariable("subject_name") String subjectName, Model model) {
        // Fetch subject by subject_name from the database
        Subject subject = subjectRepository.findByName(subjectName);

        // Check if subject exists
        if (subject == null) {
            return "error/error-404"; // or handle it appropriately
        }

        // Add subject to the model to pass to the view
        addLinks();
        model.addAttribute("sideNavLinks", sideNavLinks);
        model.addAttribute("subject", subject);

        return "admin/subject-details";
    }

    // Handle form submission to invite teachers
    @PostMapping("/admin/invite-teachers")
    public void inviteTeacher(@RequestParam("email") String email, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("specialization") String specialization, @RequestParam("subject") Long subjectId, Model model) {

        // Create a new user with role = 2 (teacher)
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(2);  // 2 for teacher
        userRepository.save(user);

        // Fetch the selected subject
        Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);

        // Create a new teacher and associate with the user
        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacher.setEmail(email);
        teacher.setSpecialization(specialization);

        // Save the teacher
        teacherRepository.save(teacher);

        // If the subject is present, set the teacher in the subject
        if (subjectOptional.isPresent()) {
            Subject subject = subjectOptional.get();
            subject.setTeacher(teacher);  // Associate the teacher with the subject
            subjectRepository.save(subject);  // Save the subject with the teacher association
        }

        // Create an invite link and associate it with the teacher
        InviteLink inviteLink = new InviteLink();
        inviteLink.setTeacher(teacher);
        inviteLinkRepository.save(inviteLink);

        // Show the generated invite link
        model.addAttribute("inviteLink", "/invite/" + inviteLink.getInviteCode());

    }

    @PostMapping("/admin/subjects/add")
    public void addSubject(@RequestParam("subjectName") String subjectName, Model model) {
        // Create and save the new subject
        Subject subject = new Subject();
        subject.setSubjectName(subjectName);
        subjectRepository.save(subject);

    }
}

