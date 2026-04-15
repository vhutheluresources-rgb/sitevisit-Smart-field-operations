package com.sitevisit.smartfieldoperations.controller;

import com.sitevisit.smartfieldoperations.entity.Company;
import com.sitevisit.smartfieldoperations.entity.SiteVisit;
import com.sitevisit.smartfieldoperations.repository.CompanyRepository;
import com.sitevisit.smartfieldoperations.repository.SiteVisitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SiteVisitController {

    private final SiteVisitRepository siteVisitRepository;
    private final CompanyRepository companyRepository;

    public SiteVisitController(SiteVisitRepository siteVisitRepository, CompanyRepository companyRepository) {
        this.siteVisitRepository = siteVisitRepository;
        this.companyRepository = companyRepository;
    }
    @PostMapping("/site-visits/save")
    public String saveSiteVisit(@RequestParam Long companyId,
                                @RequestParam String visitDate,
                                @RequestParam String visitTime,
                                @RequestParam(required = false) String notes,
                                RedirectAttributes redirectAttributes) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        SiteVisit siteVisit = new SiteVisit();
        siteVisit.setCompany(company);
        siteVisit.setVisitDate(java.time.LocalDate.parse(visitDate));
        siteVisit.setVisitTime(java.time.LocalTime.parse(visitTime));
        siteVisit.setStatus("Scheduled");
        siteVisit.setNotes(notes);

        siteVisitRepository.save(siteVisit);

        redirectAttributes.addFlashAttribute("successMessage", "Site visit scheduled successfully.");
        return "redirect:/site-visits";
    }
    @PostMapping("/site-visits/update-status/{id}")
    @ResponseBody
    public java.util.Map<String, Object> updateVisitStatus(@PathVariable Long id,
                                                           @RequestParam String status) {
        java.util.Map<String, Object> response = new java.util.HashMap<>();

        try {
            SiteVisit visit = siteVisitRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Site visit not found"));

            visit.setStatus(status);
            siteVisitRepository.save(visit);

            response.put("success", true);
            response.put("message", "Status updated successfully.");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to update status.");
        }

        return response;
    }
}