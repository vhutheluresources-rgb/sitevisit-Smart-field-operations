package com.investhoodit.gr12webapp.controller;

import com.investhoodit.gr12webapp.model.Tutorial;
import com.investhoodit.gr12webapp.model.User;
import com.investhoodit.gr12webapp.service.TutorialService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tutorials")
public class TutorialController {

    @Autowired
    private TutorialService tutorialService;

    // Show tutorial form
    @GetMapping("/form")
    public String showTutorialForm() {
        return "tutorials"; // Ensure this matches your HTML file name
    }

    // Save tutorial
    @PostMapping("/save")
    public String saveTutorial(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String videoUrl,
            @RequestParam(required = false) String additionalResourceUrl,
            Model model,
            HttpSession session) {

        User user = (User) session.getAttribute("user"); // Retrieve logged-in user

        if (user == null) {
            model.addAttribute("error", "User session expired. Please log in again.");
            return "tutorials";
        }

        // Save tutorial
        Tutorial tutorial = new Tutorial(title, description, videoUrl, additionalResourceUrl, user);
        tutorialService.saveTutorial(tutorial);

        model.addAttribute("message", "Tutorial uploaded successfully!");
        return "redirect:/tutorials/my-tutorials"; // Redirect to user's tutorials page
    }

    // Get user's tutorials
    @GetMapping("/my-tutorials")
    public String getUserTutorials(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            model.addAttribute("error", "User session expired. Please log in again.");
            return "tutorials";
        }

        List<Tutorial> tutorials = tutorialService.getTutorialsByUser(user.getId());
        model.addAttribute("tutorials", tutorials);

        return "tutorials"; // Page that displays uploaded tutorials
    }
}
