package com.sitevisit.smartfieldoperations.repository;

import com.sitevisit.smartfieldoperations.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findBySiteVisitId(Long siteVisitId);
}