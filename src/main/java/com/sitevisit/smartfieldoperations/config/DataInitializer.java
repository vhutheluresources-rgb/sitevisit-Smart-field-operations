package com.sitevisit.smartfieldoperations.config;

import com.sitevisit.smartfieldoperations.entity.User;
import com.sitevisit.smartfieldoperations.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        saveOrUpdateUser("Nomsa Metfula", "nomsa@sitevisit.com", "1234");
        System.out.println("User initialized");
    }

    private void saveOrUpdateUser(String fullName, String email, String password) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setFullName(fullName);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole("ADMIN"); // fixed role
            userRepository.save(user);
            System.out.println("Updated user: " + email);
        } else {
            User newUser = new User(fullName, email, passwordEncoder.encode(password), "ADMIN");
            userRepository.save(newUser);
            System.out.println("Inserted user: " + email);
        }
    }
}