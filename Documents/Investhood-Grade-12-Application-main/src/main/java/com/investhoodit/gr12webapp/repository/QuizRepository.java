package com.investhoodit.gr12webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investhoodit.gr12webapp.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
