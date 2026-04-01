package com.investhoodit.gr12webapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
//import java.util.UUID;

@Entity
@Table(name = "study_materials")
public class StudyMaterials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID for each study material

    @Column(nullable = false)
    private String subjectName; // Subject name

    
    @Lob
    @Column(columnDefinition = "LONGBLOB", nullable = false) 
    private byte[] fileUrl;  // Store file path or URL

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false, referencedColumnName = "idNumber") // Foreign key to User table
    private User teacher; 

  //  @Column(nullable = true, updatable = false)
    private LocalDateTime uploadDate; // Date when the note was uploaded

    // Constructors
    public StudyMaterials() {
       
    }

    public StudyMaterials(String subjectName, byte[] fileUrl, User teacher) {
        this.subjectName = subjectName;
        this.fileUrl = fileUrl;
        this.teacher = teacher;
        this.uploadDate = LocalDateTime.now(); // Set upload date when a new record is created
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public byte[] getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(byte[] fileUrl) {
        this.fileUrl = fileUrl;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }
}
