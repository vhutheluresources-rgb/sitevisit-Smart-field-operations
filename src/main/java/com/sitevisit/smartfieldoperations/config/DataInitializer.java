package com.sitevisit.smartfieldoperations.config;

import com.sitevisit.smartfieldoperations.entity.User;
import com.sitevisit.smartfieldoperations.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {

            userRepository.save(new User("Nomsa Metfula", "nomsa", "1234", "PROJECT_MANAGER"));
            userRepository.save(new User("Lindokuhle Zulu", "lindokuhle", "1234", "TEAM_MEMBER"));
            userRepository.save(new User("Amahle Mchunu", "amahle", "1234", "TEAM_MEMBER"));

            System.out.println("✅ Default users inserted");
        }
    }
}