package com.investhoodit.gr12webapp.model;

import jakarta.persistence.*;

@Entity
public class QuestionPaper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String year;

	private String questionPaperFile;

	@ManyToOne
	@JoinColumn(name = "subject_id")
	private Subject subject;

	public QuestionPaper() {
		super();
	}

	public QuestionPaper(Long id, String title, String year, String questionPaperFile, Subject subject) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.questionPaperFile = questionPaperFile;
		this.subject = subject;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getQuestionPaperFile() {
		return questionPaperFile;
	}

	public void setQuestionPaperFile(String questionPaperFile) {
		this.questionPaperFile = questionPaperFile;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
}
