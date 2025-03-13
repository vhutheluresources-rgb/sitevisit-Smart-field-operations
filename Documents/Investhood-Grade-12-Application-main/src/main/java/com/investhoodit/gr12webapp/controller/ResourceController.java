package com.investhoodit.gr12webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.investhoodit.gr12webapp.model.User;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/resources")
public class ResourceController {
	
	@GetMapping
	public String getResources(Model model, HttpSession session) {
		User username = (User) session.getAttribute("user");
		model.addAttribute("user", username);
		return "resources";
	}
	@GetMapping("/StudyMaterials")
	public String getStudyMaterial(Model model, HttpSession session) {
	    User username = (User) session.getAttribute("user");
	    model.addAttribute("user", username);
	    return "StudyMaterials";
	}
	@GetMapping("/Tutorials")
	public String getMethodName() {
		return "Tutorials";
				
	}
	
}
