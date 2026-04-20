package com.sitevisit.smartfieldoperations.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    // ✅ FIX: Match database column 'site_visit_id' (NOT NULL)
    @Column(name = "site_visit_id")
    private Long siteVisitId;

    // Optional text reference (nullable)
    @Column(name = "site_visit_ref", length = 50)
    private String siteVisitRef;

    @Column(name = "summary", columnDefinition = "TEXT", nullable = false)
    private String summary;

    @Column(name = "findings", columnDefinition = "TEXT")
    private String findings;

    @Column(name = "issues", columnDefinition = "TEXT")
    private String issues;

    @Column(name = "actions_taken", columnDefinition = "TEXT")
    private String actionsTaken;

    @Column(name = "recommendations", columnDefinition = "TEXT")
    private String recommendations;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ✅ Auto-set timestamps before persist/update
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ✅ Default constructor (required by JPA + Jackson)
    public Report() {}

    // ===== Getters =====
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public Long getSiteVisitId() { return siteVisitId; }
    public String getSiteVisitRef() { return siteVisitRef; }
    public String getSummary() { return summary; }
    public String getFindings() { return findings; }
    public String getIssues() { return issues; }
    public String getActionsTaken() { return actionsTaken; }
    public String getRecommendations() { return recommendations; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ===== Setters =====
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setSiteVisitId(Long siteVisitId) { this.siteVisitId = siteVisitId; }
    public void setSiteVisitRef(String siteVisitRef) { this.siteVisitRef = siteVisitRef; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setFindings(String findings) { this.findings = findings; }
    public void setIssues(String issues) { this.issues = issues; }
    public void setActionsTaken(String actionsTaken) { this.actionsTaken = actionsTaken; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Report{id=" + id + ", title='" + title + "', summary='" + summary + "'}";
    }
}