package com.sitevisit.smartfieldoperations.controller;

import com.sitevisit.smartfieldoperations.entity.Company;
import com.sitevisit.smartfieldoperations.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*") // 🔥 important for frontend requests
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    // ✅ CREATE
    @PostMapping
    public Company createCompany(@RequestBody Company company) {
        System.out.println("Saving company: " + company.getName()); // debug
        return companyRepository.save(company);
    }

    // ✅ READ
    @GetMapping
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company updatedCompany) {
        return companyRepository.findById(id).map(company -> {
            company.setName(updatedCompany.getName());
            company.setRegNumber(updatedCompany.getRegNumber());
            company.setEmail(updatedCompany.getEmail());
            company.setPhone(updatedCompany.getPhone());
            company.setAddress(updatedCompany.getAddress()); // ✅ FIXED
            company.setStatus(updatedCompany.getStatus());
            return companyRepository.save(company);
        }).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyRepository.deleteById(id);
    }
}