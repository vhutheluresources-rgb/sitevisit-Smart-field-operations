package com.investhoodit.gr12webapp.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

//import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investhoodit.gr12webapp.model.Email;
import com.investhoodit.gr12webapp.model.PasswordResetToken;
import com.investhoodit.gr12webapp.model.User;
import com.investhoodit.gr12webapp.repository.PasswordResetTokenRepository;
import com.investhoodit.gr12webapp.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
    private PasswordResetTokenRepository tokenRepository;
	
	@Autowired
	private EmailService emailService;

	@PersistenceContext
	private EntityManager em;

	// Add the user to the database and
	// send an email to the user for successful sign up
	public void saveUser(User user) {
		userRepo.save(user);
		emailSender(user);
	}

	// Method for sending an email to the user
	private void emailSender(User user) {

		String body = "Hi, " + user.getFullName() + " " + ".\n" + "Your registration was successful.";
		String recipient = user.getEmail();
		String subject = "Grade 12 Matric past paper Registration";

		Email email = new Email();
		email.setRecipient(recipient);
		email.setBody(body);
		email.setSubject(subject);

		emailService.sendEmail(email);
	}

	public boolean verifyUser(String email, String password) {
		User user = userRepo.findByEmail(email);
		return user != null && user.getPassword().equals(password);
	}

	public boolean verifyEmail(String email) {
		User user = userRepo.findByEmail(email);
		if (user != null) {
			return false; // Email already exists
		}
		return true; // Email does not exist, it's available for registration
	}
	
	public void sendPasswordResetEmail(String email) {
	    User user = userRepo.findByEmail(email);

	    if (user != null) {
	        String otp = generateOTP();
	        PasswordResetToken resetToken = new PasswordResetToken();
	        resetToken.setToken(otp);
	        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(10)); 
	        resetToken.setUser(user);
	        tokenRepository.save(resetToken);

	        String subject = "Password Reset OTP";
	        String body = "Your OTP for password reset is: " + otp;
	        emailService.sendEmail(email, subject, body);
	    }
	}

	private String generateOTP() {
	    SecureRandom secureRandom = new SecureRandom();
	    int otp = 100000 + secureRandom.nextInt(900000); 
	    return String.valueOf(otp);
	}

    public boolean resetPassword(String otp, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(otp);
        if (resetToken != null && resetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            User user = resetToken.getUser();
            user.setPassword(newPassword);
            userRepo.save(user);
            tokenRepository.delete(resetToken);
            return true;
        }
        return false;
    }

	public void updateProfile(String fullName, String phoneNumber, String password, String email,
			byte[] profilePicture) {
		User user = userRepo.findByEmail(email);
		if (user != null) {
			user.setFullName(fullName);
			user.setPhoneNumber(phoneNumber);
			user.setPassword(password);
			// if (profilePicture != null) {
			user.setProfilePicture(profilePicture);
			// }
			userRepo.save(user);
		}
	}

//	public static boolean sendPasswordResetEmail(String email) {
//		// Logic to generate reset token and email content
//		String token = UUID.randomUUID().toString();
//		String resetLink = "http://yourwebsite.com/reset-password?token=" + token;
//
//		// Simulate sending email
//		System.out.println("Sending email to: " + email);
//		System.out.println("Reset link: " + resetLink);
//
//		// Save token to the database associated with the user
//		return true; // Return true if email sent successfully
//	}

//	public boolean updateProfile(String email, UserProfileUpdateRequest request) {
//		// Directly fetch the user without using Optional
//		User user = userRepo.findByEmail(email);
//
//		// Check if user exists
//		if (user != null) {
//			// Update fields
//			user.setFullName(request.getName());
//			user.setPhoneNumber(request.getEmail());
//			// Optionally hash the password if it's being updated
//			/*
//			 * /if (request.getPassword() != null && !request.getPassword().isEmpty()) {
//			 * user.setPassword(hashPassword(request.getPassword())); // Example: Add a
//			 * password hashing method }
//			 */
//
//			// Save the updated user
//			userRepo.save(user);
//			return true;
//		}
//
//		// Return false if the user is not found
//		return false;
//	}

//	@GetMapping("/editProfile")
//	public String editProfile(Model model, @AuthenticationPrincipal UserPrincipal currentUser) {
//		// Fetch the user data using the authenticated user's ID or email
//		User user = ((UserRepository) userService).findByEmail(((User) currentUser).getEmail());
//
//		// Add the user data to the model
//		model.addAttribute("user", user);
//
//		// Return the view name for the edit profile page
//		return "editProfile";
//	}
//
//	@PostMapping("/editProfile")
//	public String saveProfileChanges(@ModelAttribute User user) {
//		userService.saveUser(user); // Save updated user to the database
//		return "redirect:/profile"; // Redirect to the profile page after saving
//	}

}
