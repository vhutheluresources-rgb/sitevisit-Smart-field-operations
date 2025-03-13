package com.investhoodit.gr12webapp.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String subjectName;

	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Quiz> quizzes;

	public Subject() {
		super();
	}

	public Subject(Long id, String subjectName, User user, List<QuestionPaper> questionPapers, List<Quiz> quizzes) {
		this.id = id;
		this.subjectName = subjectName;
		this.user = user;
		this.quizzes = quizzes;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Quiz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<Quiz> quizzes) {
		this.quizzes = quizzes;
	}
}
