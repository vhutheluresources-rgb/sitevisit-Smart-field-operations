package com.sitevisit.smartfieldoperations.service;

import com.sitevisit.smartfieldoperations.dto.ApiResponse;
import com.sitevisit.smartfieldoperations.entity.Attendance;
import com.sitevisit.smartfieldoperations.entity.SiteVisit;
import com.sitevisit.smartfieldoperations.repository.AttendanceRepository;
import com.sitevisit.smartfieldoperations.repository.SiteVisitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final SiteVisitRepository siteVisitRepository;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             SiteVisitRepository siteVisitRepository) {
        this.attendanceRepository = attendanceRepository;
        this.siteVisitRepository = siteVisitRepository;
    }

    public ApiResponse checkIn(Long siteVisitId) {
        Optional<SiteVisit> optionalVisit = siteVisitRepository.findById(siteVisitId);

        if (optionalVisit.isEmpty()) {
            return new ApiResponse(false, "Scheduled site visit not found.");
        }

        SiteVisit siteVisit = optionalVisit.get();

        Optional<Attendance> existingAttendance = attendanceRepository.findBySiteVisitId(siteVisitId);
        if (existingAttendance.isPresent()) {
            return new ApiResponse(false, "This visit has already been checked in.");
        }

        Attendance attendance = new Attendance();
        attendance.setSiteVisitId(siteVisitId);
        attendance.setDate(LocalDate.now());
        attendance.setCheckInTime(LocalTime.now());
        attendance.setStatus("Checked In");

        attendanceRepository.save(attendance);

        siteVisit.setStatus("In Progress");
        siteVisitRepository.save(siteVisit);

        return new ApiResponse(true, "Check-in recorded successfully.");
    }

    public ApiResponse checkOut(Long siteVisitId) {
        Optional<Attendance> optionalAttendance = attendanceRepository.findBySiteVisitId(siteVisitId);

        if (optionalAttendance.isEmpty()) {
            return new ApiResponse(false, "You must check in first before checking out.");
        }

        Attendance attendance = optionalAttendance.get();

        if (attendance.getCheckOutTime() != null) {
            return new ApiResponse(false, "This visit has already been checked out.");
        }

        Optional<SiteVisit> optionalVisit = siteVisitRepository.findById(siteVisitId);
        if (optionalVisit.isEmpty()) {
            return new ApiResponse(false, "Scheduled site visit not found.");
        }

        attendance.setCheckOutTime(LocalTime.now());
        attendance.setStatus("Completed");
        attendanceRepository.save(attendance);

        SiteVisit siteVisit = optionalVisit.get();
        siteVisit.setStatus("Completed");
        siteVisitRepository.save(siteVisit);

        return new ApiResponse(true, "Check-out recorded successfully.");
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Optional<Attendance> getAttendanceBySiteVisitId(Long siteVisitId) {
        return attendanceRepository.findBySiteVisitId(siteVisitId);
    }
}