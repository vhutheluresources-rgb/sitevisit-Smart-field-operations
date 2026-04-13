package com.sitevisit.smartfieldoperations.controller;

import com.sitevisit.smartfieldoperations.dto.ApiResponse;
import com.sitevisit.smartfieldoperations.dto.ForgotPasswordRequest;
import com.sitevisit.smartfieldoperations.dto.LoginRequest;
import com.sitevisit.smartfieldoperations.dto.LoginResponse;
import com.sitevisit.smartfieldoperations.dto.ResetPasswordRequest;
import com.sitevisit.smartfieldoperations.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        LoginResponse response = authService.login(request);

        if (response.isSuccess()) {
            session.setAttribute("loggedInUserEmail", request.getEmail());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        ApiResponse response = authService.forgotPassword(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest request) {
        ApiResponse response = authService.resetPassword(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }
}