package com.investhoodit.gr12webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.investhoodit.gr12webapp.model.User;
import com.investhoodit.gr12webapp.service.SubjectService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class StudentDashboardController {

	@Autowired
	SubjectService subjectService;
	
	
	// Show the dashboard page
	@GetMapping
	public String getDashboardPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);

		int subjectCount = subjectService.countSubjectsByUser(user);
		model.addAttribute("subjectCount", subjectCount);
		
		return "studentDashboard";
	}

}
