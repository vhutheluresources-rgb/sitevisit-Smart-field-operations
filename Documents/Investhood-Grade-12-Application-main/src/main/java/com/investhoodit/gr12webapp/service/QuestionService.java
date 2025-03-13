package com.investhoodit.gr12webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investhoodit.gr12webapp.model.Question;
import com.investhoodit.gr12webapp.repository.QuestionDAO;

@Service
public class QuestionService {

	@Autowired
	QuestionDAO questionDao;

	public List<Question> getAllQuestions() {
		return questionDao.findAll();
	}

	public List<Question> getQuestionByCategory(String category) {
		return questionDao.findByCategory(category);
	}

	public void addQuestion(Question question) {
		questionDao.save(question);
	}
	
	
}
