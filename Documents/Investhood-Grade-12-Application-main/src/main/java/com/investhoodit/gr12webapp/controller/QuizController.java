package com.investhoodit.gr12webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.investhoodit.gr12webapp.model.QuestionWrapper;
import com.investhoodit.gr12webapp.model.Quiz;
import com.investhoodit.gr12webapp.model.Response;
import com.investhoodit.gr12webapp.model.User;
import com.investhoodit.gr12webapp.service.QuizService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@GetMapping
	public String listQuizzes(Model model, HttpSession session) {
		List<Quiz> quizzes = quizService.getAllQuizzes();
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("quizzes", quizzes);
		return "quizzes"; // Ensure this matches your Thymeleaf template file name
	}

	@PostMapping("create")
	public String createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title,
			@RequestParam int total) {
		quizService.createQuiz(category, numQ, title, total);
		return "";
	}
	
	@GetMapping("get/{id}")
	public List<QuestionWrapper> getQuizQuestions(@PathVariable Long id){
		return quizService.getQuizQuestions(id);
	}
	
	@PostMapping("submit/{id}")
	public Integer submitQuiz(@PathVariable Long id,@RequestBody List<Response> responses) {
		return quizService.calculateResults(id,responses);
	}
	

}
