package com.investhoodit.gr12webapp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.investhoodit.gr12webapp.model.User;
import com.investhoodit.gr12webapp.repository.UserRepository;
import com.investhoodit.gr12webapp.service.ProfileService;
import com.investhoodit.gr12webapp.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	ProfileService profileService;
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping
	public String getProfile(Model model, HttpSession session) {

		User username = (User) session.getAttribute("user");
		model.addAttribute("user", username);
		return "profiles";
	}
	
	@PostMapping("/updateProfilePicture")
	public String updateProfilePicture(User user,@RequestParam("profilePicture") MultipartFile file) throws IOException {
		byte[] profilePicture = file.isEmpty() ? null : file.getBytes();
		profileService.updateProfilePicture(profilePicture,user);
		
		return "/profile";
	}
	@PostMapping("/updatePassword")
	public String updatePassword(@RequestParam String password,User user) {
		
		profileService.updatePassword(password,user);
		
		return "/profile";
	}
	@PostMapping("/updatePhoneNumber")
	public String updatePhoneNumber(@RequestParam String phoneNumber,User user) {
		
		profileService.updatePhoneNumber(phoneNumber,user);
		
		return "/profile";
	}
	
	@GetMapping("/profile-picture/{userId}")
	public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long userId) {
		User user = userRepo.findById(userId).orElse(null);

		if (user == null || user.getProfilePicture() == null) {
			return ResponseEntity.notFound().build();
		}

		byte[] image = user.getProfilePicture();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG) // Use MediaType.IMAGE_PNG if it's a PNG
				.body(image);
	}
}
