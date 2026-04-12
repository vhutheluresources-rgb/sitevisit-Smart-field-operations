package com.sitevisit.smartfieldoperations.config;

import com.sitevisit.smartfieldoperations.entity.User;
import com.sitevisit.smartfieldoperations.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        saveOrUpdateUser("Nomsa Metfula", "nomsa@sitevisit.com", "1234", "PROJECT_MANAGER");
        saveOrUpdateUser("Lindokuhle Zulu", "lindokuhle@sitevisit.com", "1234", "TEAM_MEMBER");
        saveOrUpdateUser("Amahle Mchunu", "amahle@sitevisit.com", "1234", "TEAM_MEMBER");

        System.out.println("Users initialized or updated");
    }

    private void saveOrUpdateUser(String fullName, String email, String password, String role) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setFullName(fullName);
            user.setPassword(password);
            user.setRole(role);
            userRepository.save(user);
            System.out.println("Updated user: " + email);
        } else {
            User newUser = new User(fullName, email, password, role);
            userRepository.save(newUser);
            System.out.println("Inserted user: " + email);
        }
    }
}