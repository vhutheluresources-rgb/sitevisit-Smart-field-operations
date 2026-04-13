package com.sitevisit.smartfieldoperations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sitevisit.smartfieldoperations.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}