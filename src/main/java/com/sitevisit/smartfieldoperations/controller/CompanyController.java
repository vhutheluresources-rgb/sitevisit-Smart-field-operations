package com.sitevisit.smartfieldoperations.controller;

import com.sitevisit.smartfieldoperations.entity.Company;
import com.sitevisit.smartfieldoperations.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository repo;

    @GetMapping
    public List<Company> get() {
        return repo.findAll();
    }

    @PostMapping
    public Company save(@RequestBody Company c) {
        return repo.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    @PutMapping("/{id}")
    public Company update(@PathVariable Long id, @RequestBody Company c) {
        Company old = repo.findById(id).orElseThrow();
        old.setName(c.getName());
        old.setRegNumber(c.getRegNumber());
        old.setEmail(c.getEmail());
        old.setStatus(c.getStatus());
        return repo.save(old);
    }
}