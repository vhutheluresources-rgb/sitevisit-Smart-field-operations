package com.investhoodit.gr12webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investhoodit.gr12webapp.model.User;
import com.investhoodit.gr12webapp.repository.UserRepository;

@Service
public class ProfileService {

	@Autowired
	UserRepository userRepo;
	
	public void updateFullName(String fullName,User user) {
		user.setFullName(fullName);
		userRepo.save(user);
	}
	
	public void updateProfilePicture(byte[] profilePicture,User user) {
		user.setProfilePicture(profilePicture);
		userRepo.save(user);
	}
	public void updatePassword(String password,User user) {
		user.setPassword(password);
		userRepo.save(user);
	}
	public void updatePhoneNumber(String phoneNumber,User user) {
		user.setPhoneNumber(phoneNumber);
		userRepo.save(user);
	}
	
}
