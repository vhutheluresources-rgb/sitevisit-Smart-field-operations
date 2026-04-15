package com.sitevisit.smartfieldoperations.controller;

import com.sitevisit.smartfieldoperations.entity.Report;
import com.sitevisit.smartfieldoperations.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    private final ReportRepository reportRepository;

    @Autowired
    public ReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        try {
            logger.info("📝 Creating report: title='{}'", report.getTitle());
            if (report.getUpdatedAt() == null) {
                report.setUpdatedAt(LocalDateTime.now());
            }
            Report saved = reportRepository.save(report);
            logger.info("✅ Saved report ID: {}", saved.getId());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            logger.error("❌ Failed to create report", e);
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        try {
            return ResponseEntity.ok(reportRepository.findAll());
        } catch (Exception e) {
            logger.error("Error fetching reports", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return reportRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report details) {
        return reportRepository.findById(id)
                .map(existing -> {
                    if (details.getTitle() != null) existing.setTitle(details.getTitle());
                    if (details.getSiteVisitId() != null) existing.setSiteVisitId(details.getSiteVisitId());
                    if (details.getSiteVisitRef() != null) existing.setSiteVisitRef(details.getSiteVisitRef());
                    if (details.getSummary() != null) existing.setSummary(details.getSummary());
                    if (details.getFindings() != null) existing.setFindings(details.getFindings());
                    if (details.getIssues() != null) existing.setIssues(details.getIssues());
                    if (details.getActionsTaken() != null) existing.setActionsTaken(details.getActionsTaken());
                    if (details.getRecommendations() != null) existing.setRecommendations(details.getRecommendations());
                    if (details.getNotes() != null) existing.setNotes(details.getNotes());
                    existing.setUpdatedAt(LocalDateTime.now());
                    Report updated = reportRepository.save(existing);
                    logger.info("✅ Updated report ID: {}", id);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> {
                    logger.warn("Report not found for update: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            logger.info("✅ Deleted report ID: {}", id);
            return ResponseEntity.noContent().build();
        }
        logger.warn("Report not found for delete: {}", id);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/pdf")
    public void downloadReport(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found: " + id));

        response.setContentType("application/pdf");
        String safeTitle = report.getTitle() != null 
            ? report.getTitle().replaceAll("[^a-zA-Z0-9-_]", "_").substring(0, Math.min(30, report.getTitle().length()))
            : "report";
        response.setHeader("Content-Disposition", 
            "attachment; filename=SiteVisit_Report_" + safeTitle + "_" + id + ".pdf");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        try {
            Document document = new Document(PageSize.A4, 40, 40, 30, 30);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

            document.add(new Paragraph("SITEVISIT FIELD REPORT", titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Report ID: " + report.getId(), valueFont));
            document.add(new Paragraph("Title: " + report.getTitle(), valueFont));
            document.add(new Paragraph("Summary: " + report.getSummary(), valueFont));
            
            document.close();
            logger.info("Generated PDF for report: {}", id);
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}