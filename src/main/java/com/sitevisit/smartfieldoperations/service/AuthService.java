package com.sitevisit.smartfieldoperations.service;

import com.sitevisit.smartfieldoperations.dto.LoginRequest;
import com.sitevisit.smartfieldoperations.dto.LoginResponse;
import com.sitevisit.smartfieldoperations.entity.User;
import com.sitevisit.smartfieldoperations.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        return new LoginResponse(true, "Login successful", user.getFullName(), user.getRole());
    }
}