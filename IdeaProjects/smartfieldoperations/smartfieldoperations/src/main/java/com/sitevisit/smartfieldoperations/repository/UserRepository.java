package com.sitevisit.smartfieldoperations.repository;

import com.sitevisit.smartfieldoperations.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}