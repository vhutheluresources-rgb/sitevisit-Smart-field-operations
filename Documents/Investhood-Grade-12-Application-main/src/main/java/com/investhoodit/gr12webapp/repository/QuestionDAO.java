package com.investhoodit.gr12webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.investhoodit.gr12webapp.model.Question;

public interface QuestionDAO extends JpaRepository<Question, Long> {
	
    List<Question> findByCategory(String category);

   // @Query(value = "SELECT * FROM question q where q.category=:category ORDER BY RANDOM() LIMIT :numQ",nativeQuery = true)
	List<Question> findRandomQuestionsByCategory(String category);
}
 