package com.investhoodit.gr12webapp.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Table(name = "tutorial")
@Entity
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String videoUrl;  
    private String additionalResourceUrl;

    @Column(nullable = false, updatable = false, name = "update_date")
    private LocalDateTime uploadDate1;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false, referencedColumnName = "idNumber")
    private User user;

    // Default constructor
    public Tutorial() {
    }

    // Parameterized constructor
    public Tutorial(String title, String description, String videoUrl, String additionalResourceUrl, User user) {
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.additionalResourceUrl = additionalResourceUrl;
        this.user = user;
        this.uploadDate1 = LocalDateTime.now(); // Set the current date and time
    }

    @PrePersist
    protected void onCreate() {
        this.uploadDate1 = LocalDateTime.now(); // Automatically set before saving to database
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getAdditionalResourceUrl() {
        return additionalResourceUrl;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getUploadDate1() {
        return uploadDate1;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setAdditionalResourceUrl(String additionalResourceUrl) {
        this.additionalResourceUrl = additionalResourceUrl;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUploadDate1(LocalDateTime uploadDate1) {
        this.uploadDate1 = uploadDate1;
    }
}
