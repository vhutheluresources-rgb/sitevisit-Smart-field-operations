package com.investhoodit.gr12webapp.model;

import jakarta.persistence.*;

@Entity
public class Resource {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String resourceName;
	private String resourceUrl;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Resource() {
		super();
	}

	public Resource(Long id, String resourceName, String resourceUrl, User user) {
		super();
		this.id = id;
		this.resourceName = resourceName;
		this.resourceUrl = resourceUrl;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
