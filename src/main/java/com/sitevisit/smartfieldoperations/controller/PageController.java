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

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        String email = (String) session.getAttribute("loggedInUserEmail");

        if (email == null) {
            return "redirect:/login";
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return "redirect:/login";
        }

        User user = optionalUser.get();

        model.addAttribute("fullName", user.getFullName());
        model.addAttribute("role", user.getRole());
        model.addAttribute("initial", user.getFullName().substring(0, 1).toUpperCase());

        return "dashboard";
    }

    @GetMapping("/companies")
    public String companiesPage() {
        return "companies";
    }

    @GetMapping("/members")
    public String membersPage() {
        return "members";
    }
}