package com.sitevisit.smartfieldoperations.service;

import com.sitevisit.smartfieldoperations.dto.ApiResponse;
import com.sitevisit.smartfieldoperations.dto.ForgotPasswordRequest;
import com.sitevisit.smartfieldoperations.dto.LoginRequest;
import com.sitevisit.smartfieldoperations.dto.LoginResponse;
import com.sitevisit.smartfieldoperations.dto.ResetPasswordRequest;
import com.sitevisit.smartfieldoperations.entity.PasswordResetToken;
import com.sitevisit.smartfieldoperations.entity.User;
import com.sitevisit.smartfieldoperations.repository.PasswordResetTokenRepository;
import com.sitevisit.smartfieldoperations.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public AuthService(UserRepository userRepository,
                       PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public LoginResponse login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            return new LoginResponse(false, "Invalid email or password", null, null);
        }

        User user = userOptional.get();

        if (!user.getPassword().equals(request.getPassword())) {
            return new LoginResponse(false, "Invalid email or password", null, null);
        }

        return new LoginResponse(
                true,
                "Login successful",
                user.getFullName(),
                user.getRole()
        );
    }

    public ApiResponse forgotPassword(ForgotPasswordRequest request) {
        if (request == null || request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return new ApiResponse(false, "Email is required");
        }

        Optional<User> userOptional = userRepository.findByEmail(request.getEmail().trim());

        if (userOptional.isEmpty()) {
            return new ApiResponse(false, "No user found with that email");
        }

        User user = userOptional.get();

        passwordResetTokenRepository.findByUser(user)
                .ifPresent(passwordResetTokenRepository::delete);

        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(30);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(expiryDate);

        passwordResetTokenRepository.save(resetToken);

        return new ApiResponse(
                true,
                "Reset token generated successfully. Use it to reset your password.",
                token
        );
    }

    public ApiResponse resetPassword(ResetPasswordRequest request) {
        if (request == null || request.getToken() == null || request.getToken().trim().isEmpty()) {
            return new ApiResponse(false, "Reset token is required");
        }

        if (request.getNewPassword() == null || request.getNewPassword().trim().isEmpty()) {
            return new ApiResponse(false, "New password is required");
        }

        Optional<PasswordResetToken> tokenOptional =
                passwordResetTokenRepository.findByToken(request.getToken().trim());

        if (tokenOptional.isEmpty()) {
            return new ApiResponse(false, "Invalid reset token");
        }

        PasswordResetToken resetToken = tokenOptional.get();

        if (resetToken.isExpired()) {
            passwordResetTokenRepository.delete(resetToken);
            return new ApiResponse(false, "Reset token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(request.getNewPassword().trim());
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);

        return new ApiResponse(true, "Password reset successful");
    }
}