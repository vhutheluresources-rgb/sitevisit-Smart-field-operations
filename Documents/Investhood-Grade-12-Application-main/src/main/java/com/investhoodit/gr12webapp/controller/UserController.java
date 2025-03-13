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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.investhoodit.gr12webapp.model.User;
import com.investhoodit.gr12webapp.repository.UserRepository;
import com.investhoodit.gr12webapp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepo;

	// Show the welcome page
	@GetMapping("/")
	public String getWelcomePage(HttpSession session) {
//    	session.invalidate();
		return "welcomePage";
	}

	// Show the login form
	@GetMapping("/login")
	public String getLoginPage() {
		return "loginPage";
	}

	// Show the sign up form
	@GetMapping("/signup")
	public String getSignUpPage(Model model) {
		model.addAttribute("user", new User());
		return "signupPage";
	}


	// Register or Add a user to the database
	@PostMapping("/register")
	public String getRegistered(User user, Model model) {
		boolean verify = userService.verifyEmail(user.getEmail());
		if (verify) {
			userService.saveUser(user); // Save user to the database
			return "redirect:/login"; // Redirect to login page after successful sign up
		} else {
			model.addAttribute("error", "The email already exists."); // Add error message to the model
			return "signupPage"; // Return to the sign-up page with the error message
		}
	}

	// Handle login form submission
	@PostMapping("/login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpServletRequest request) {
		if (userService.verifyUser(email, password)) {
			// Initializing the session
			HttpSession session = request.getSession(true);
			User user = userRepo.findByEmail(email);
			// Set the Full name of the logged in user
			session.setAttribute("user", user);
			if (user.getRole().equalsIgnoreCase("USER")) {
				return "redirect:/dashboard"; // Redirect to the dashboard after successful login
			} else {
				return "teacherDashboard"; // Redirect to the dashboard after successful login
			}

		} else {
			model.addAttribute("error", "Invalid email or password");
			return "loginPage"; // Stay on the login page if authentication fails
		}
	}

	@PostMapping("/updateProfile")
	public String updateProfile(Model model, HttpSession session, @RequestParam String fullName,
			@RequestParam String phoneNumber, @RequestParam String password,
			@RequestParam("profilePicture") MultipartFile file) throws IOException {
		User username = (User) session.getAttribute("user");
		String email = username.getEmail();

		byte[] profilePicture = file.isEmpty() ? null : file.getBytes();
		userService.updateProfile(fullName, phoneNumber, password, email, profilePicture);

		User updatedUser = userRepo.findByEmail(email);
		session.setAttribute("user", updatedUser);
		model.addAttribute("user", updatedUser);
		return "profiles";
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

	@GetMapping("/Math")
	public String getPastPapers( Model model) throws Exception {
		
		String pdfDirectory = "C:/";
		
		// Get the directory for the specific subject
		Path subjectDir = Paths.get(pdfDirectory);

		if (!Files.exists(subjectDir)) {
			throw new RuntimeException("No past papers found for subject: " + null);
		}

		// Fetch all PDF files for the subject
		List<String> pastPapers = Files.list(subjectDir).filter(file -> file.toString().endsWith(".pdf"))
				.map(file -> file.getFileName().toString()).collect(Collectors.toList());

		//model.addAttribute("subjectName", subjectName); // Pass the subject name
		model.addAttribute("pastPapers", pastPapers); // Pass the list of past papers
		return "Math"; // Render the pastPapers.html template
	}

}
