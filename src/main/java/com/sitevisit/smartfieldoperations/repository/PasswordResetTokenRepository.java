package com.sitevisit.smartfieldoperations.repository;

import com.sitevisit.smartfieldoperations.entity.PasswordResetToken;
import com.sitevisit.smartfieldoperations.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(User user);
    void deleteByUser(User user);
}