package com.sitevisit.smartfieldoperations.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * PageController - Serves HTML pages via Thymeleaf
 * Maps URL paths to template files in src/main/resources/templates/
 */
@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/reports")
    public String reports() {
        return "reports";  // ✅ Fixes navigation from dashboard
    }

    @GetMapping("/companies")
    public String companies() {
        return "companies";
    }

    @GetMapping("/members")
    public String members() {
        return "members";
    }

    @GetMapping("/site-visits")
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
    }
}