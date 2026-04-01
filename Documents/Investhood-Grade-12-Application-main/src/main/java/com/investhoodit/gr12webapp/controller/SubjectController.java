package com.investhoodit.gr12webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.investhoodit.gr12webapp.model.Subject;
import com.investhoodit.gr12webapp.model.User;
import com.investhoodit.gr12webapp.service.SubjectService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/allSubjects")
    public String getAllSubjects(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        
        int subjectCount = subjectService.countSubjectsByUser(user);
        model.addAttribute("subjectCount", subjectCount);

        List<Subject> subjects = subjectService.getAllSubjects(user);
        model.addAttribute("subjects", subjects); // Fix: added attribute name

        return "pastpapers";
    }


    @GetMapping("/addSubject")
    public String getSubjectForm() {
        return "addSubject";
    }

    @PostMapping("/addSubject")
    public String addSubject(@ModelAttribute Subject subject, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "You need to log in first!");
            return "redirect:/login"; // Redirect user to login page
        }

        boolean isAdded = subjectService.addSubject(subject, user);

        if (!isAdded) {
            redirectAttributes.addFlashAttribute("error", "Sorry, you already added the subject: " + subject.getSubjectName());
            return "redirect:/subject/addSubject";
        }

        redirectAttributes.addFlashAttribute("success", "Subject added successfully!");
        return "redirect:/subject/allSubjects";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        subjectService.deleteSubject(id);
        redirectAttributes.addFlashAttribute("success", "Subject deleted successfully!");
        return "redirect:/subject/allSubjects";
    }

    

}
