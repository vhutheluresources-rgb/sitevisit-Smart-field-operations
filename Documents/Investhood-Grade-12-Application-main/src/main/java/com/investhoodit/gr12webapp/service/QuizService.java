package com.investhoodit.gr12webapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investhoodit.gr12webapp.model.Question;
import com.investhoodit.gr12webapp.model.QuestionWrapper;
import com.investhoodit.gr12webapp.model.Quiz;
import com.investhoodit.gr12webapp.model.Response;
import com.investhoodit.gr12webapp.repository.QuestionDAO;
import com.investhoodit.gr12webapp.repository.QuizRepository;

@Service
public class QuizService {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	QuestionDAO questionDao;

	// get all quizzes
	public List<Quiz> getAllQuizzes() {
		return quizRepository.findAll();
	}

	//Create a quiz by randomizing the questions from the database based on their categories
	public void createQuiz(String category, int numQ, String title, int total) {

//		List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setTotalMarks(total);
	//	quiz.setQuestions(questions);
		quizRepository.save(quiz);

	}
    // get all question(without the answers) based on the quiz id
	public List<QuestionWrapper> getQuizQuestions(Long id) {
		Optional<Quiz> quiz = quizRepository.findById(id);
		List<Question> questionFromDB = quiz.get().getQuestions();
		List<QuestionWrapper> questionForUser = new ArrayList<>();
		for (Question q : questionFromDB) {
			QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(),
					q.getOption3(), q.getOption4());
			questionForUser.add(qw);
		}
		return questionForUser;
	}
  
	// Calculate the score of the user by
	public Integer calculateResults(Long id, List<Response> responses) {
		Quiz quiz = quizRepository.findById(id).get();
		List<Question> questions = quiz.getQuestions();
		int right= 0;
		int i = 0;
		for(Response response : responses) {
			if(response.getResponse().equals(questions.get(i).getCorrectAnswer())) {
				right++;
			}
			i++;
		}
		return right;
	}
}
