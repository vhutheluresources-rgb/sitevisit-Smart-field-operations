package com.investhoodit.gr12webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.investhoodit.gr12webapp.model.Subject;
import com.investhoodit.gr12webapp.model.User;

@Repository
public interface SubjectDao extends JpaRepository<Subject, Long> {
     List<Subject> findAllByUserId(Long id);
	Subject findBySubjectNameAndUser(String subjectName, User user);
	int countByUser(User user);
}
