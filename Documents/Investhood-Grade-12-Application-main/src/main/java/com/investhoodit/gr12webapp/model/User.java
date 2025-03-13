
package com.investhoodit.gr12webapp.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fullName;
	private String email;
	private String idNumber;
	private String phoneNumber;
	private String password;
	private String role;
	@Lob
	private byte[] profilePicture;

	@OneToMany
	private List<Subject> subjects;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Notification> notifications;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Message> messages;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Resource> resources;

	// Default constructor
	public User() {
	}

	// Constructor with all fields
	public User(String fullName, String email, String idNumber, String phoneNumber, String password, String role,
			byte[] profilePicture) {
		this.fullName = fullName;
		this.email = email;
		this.idNumber = idNumber;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.role = role;
		this.profilePicture = profilePicture;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}
}
