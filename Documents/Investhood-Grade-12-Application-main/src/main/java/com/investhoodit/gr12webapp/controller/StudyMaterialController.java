package com.investhoodit.gr12webapp.controller;

import com.investhoodit.gr12webapp.model.StudyMaterials;
import com.investhoodit.gr12webapp.model.User;
import com.investhoodit.gr12webapp.service.StudyMaterialsService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

//@RestController
@Controller
//@RequestMapping("/api/study-materials")
public class StudyMaterialController {

	@Autowired
	private StudyMaterialsService studyMaterialsService;

	@PostMapping("/upload")
	public String createStudyMaterial(
			@RequestParam String subjectName,
			@RequestParam MultipartFile fileUrl,
			Model model, 
			HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		
	    try {
			studyMaterialsService.saveStudyMaterial(subjectName, user, fileUrl.getBytes());
			model.addAttribute("message", "File uploaded successfully!");
		} catch (IOException e) {
			model.addAttribute("error", e.getMessage());
		}

			
			
		
		return "StudyMaterials";
		
	}

	@GetMapping
	public List<StudyMaterials> getAllStudyMaterials() {
		return studyMaterialsService.getAllStudyMaterials();
	}

	/*@GetMapping("/{id}")
	public ResponseEntity<StudyMaterials> getStudyMaterialById(@PathVariable Long id) {
		Optional<StudyMaterials> material = studyMaterialsService.getStudyMaterialById(id);
		return material.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
*/
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudyMaterial(@PathVariable Long id) {
		try {
			studyMaterialsService.deleteStudyMaterial(id);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
