package com.sitevisit.smartfieldoperations.controller;

import com.sitevisit.smartfieldoperations.entity.User;
import com.sitevisit.smartfieldoperations.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class PageController {

    private final UserRepository userRepository;

    public PageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage() {
        return "reset-password";
    }

    // ✅ DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        User user = getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        addUserToModel(model, user);
        return "dashboard";
    }

    // ✅ COMPANIES
    @GetMapping("/companies")
    public String companiesPage(Model model, HttpSession session) {
        User user = getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        addUserToModel(model, user);
        return "companies";
    }

    // ✅ MEMBERS
    @GetMapping("/members")
    public String membersPage(Model model, HttpSession session) {
        User user = getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        addUserToModel(model, user);
        return "members";
    }

    // 🔹 Helper: get logged-in user
    private User getLoggedInUser(HttpSession session) {
        String email = (String) session.getAttribute("loggedInUserEmail");

        if (email == null) {
            return null;
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }

    // 🔹 Helper: add data to Thymeleaf
    private void addUserToModel(Model model, User user) {
        model.addAttribute("fullName", user.getFullName());
        model.addAttribute("role", user.getRole());
        model.addAttribute("initial",
                user.getFullName().substring(0, 1).toUpperCase());
    }
}