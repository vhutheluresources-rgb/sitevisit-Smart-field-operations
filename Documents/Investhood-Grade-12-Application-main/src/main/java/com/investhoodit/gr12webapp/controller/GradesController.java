package com.investhoodit.gr12webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.investhoodit.gr12webapp.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/grades")
public class GradesController {
	
	@GetMapping
	public String getGrades(Model model, HttpSession session) {
		User username = (User) session.getAttribute("user");
		model.addAttribute("user", username);
		return "grades";
	}

}
