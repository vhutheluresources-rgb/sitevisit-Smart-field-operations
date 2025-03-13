

package com.investhoodit.gr12webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.investhoodit.gr12webapp.service.UserService;

@Controller
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @GetMapping("/forgot-password")
    public String showRequestForm() {
        return "forgot-password"; 
    }

    @PostMapping("/forgot-password")
    public String requestPasswordReset(@RequestParam String email, Model model) {
        userService.sendPasswordResetEmail(email);
        model.addAttribute("message", "Password reset email sent. Please check your inbox.");
        return "message"; 
    }

    @PostMapping("/reset")
    public String showResetForm() {
        return "reset-password"; 
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String otp, @RequestParam String password, Model model) {
        if (otp == null || otp.isEmpty()) {
            model.addAttribute("error", "OTP must be provided.");
            return "reset-password"; 
        }
        
        if (password == null || password.isEmpty()) {
            model.addAttribute("error", "New password must be provided.");
            return "reset-password"; 
        }

        boolean success = userService.resetPassword(otp, password);
        if (success) {
            model.addAttribute("message", "Password reset successful.");
            return "loginPage"; 
        } else {
            model.addAttribute("error", "Failed to reset password. Please check the OTP and try again.");
            return "reset-password"; 
        }
    }
}
