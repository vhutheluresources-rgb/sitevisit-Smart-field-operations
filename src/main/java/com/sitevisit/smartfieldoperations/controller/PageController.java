package com.sitevisit.smartfieldoperations.controller;

import com.sitevisit.smartfieldoperations.entity.User;
import com.sitevisit.smartfieldoperations.repository.CompanyRepository;
import com.sitevisit.smartfieldoperations.repository.SiteVisitRepository;
import com.sitevisit.smartfieldoperations.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

<<<<<<< HEAD
/**
 * PageController - Serves HTML pages via Thymeleaf
 * Maps URL paths to template files in src/main/resources/templates/
 */
=======
import java.util.Optional;

>>>>>>> main
@Controller
public class PageController {

    private final UserRepository userRepository;
    private final SiteVisitRepository siteVisitRepository;
    private final CompanyRepository companyRepository;

    public PageController(UserRepository userRepository,
                          SiteVisitRepository siteVisitRepository,
                          CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.siteVisitRepository = siteVisitRepository;
        this.companyRepository = companyRepository;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        addUserToModel(model, user);
        return "dashboard";
    }

    @GetMapping("/reports")
    public String reports() {
        return "reports";  // ✅ Fixes navigation from dashboard
    }

    @GetMapping("/companies")
<<<<<<< HEAD
    public String companies() {
=======
    public String companiesPage(Model model, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        addUserToModel(model, user);
>>>>>>> main
        return "companies";
    }

    @GetMapping("/members")
<<<<<<< HEAD
    public String members() {
=======
    public String membersPage(Model model, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        addUserToModel(model, user);
>>>>>>> main
        return "members";
    }

    @GetMapping("/site-visits")
<<<<<<< HEAD
    public String siteVisits() {
        return "site-visits";
    }

    @GetMapping("/team-activity")
    public String teamActivity() {
        return "team-activity";
    }

    @GetMapping("/reminders")
    public String reminders() {
        return "reminders";
    }

    @GetMapping("/notifications")
    public String notifications() {
        return "notifications";
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/change-password")
    public String changePassword() {
        return "change-password";
=======
    public String siteVisitsPage(Model model, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        addUserToModel(model, user);
        model.addAttribute("companies", companyRepository.findAll());
        model.addAttribute("siteVisits", siteVisitRepository.findAll());

        return "site-visits";
    }

    @GetMapping("/reports")
    public String reportsPage(Model model, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        addUserToModel(model, user);
        return "reports";
    }

    private User getLoggedInUser(HttpSession session) {
        String email = (String) session.getAttribute("loggedInUserEmail");

        if (email == null) {
            return null;
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }

    private void addUserToModel(Model model, User user) {
        model.addAttribute("fullName", user.getFullName());
        model.addAttribute("role", user.getRole());
        model.addAttribute("initial", user.getFullName().substring(0, 1).toUpperCase());
>>>>>>> main
    }
}