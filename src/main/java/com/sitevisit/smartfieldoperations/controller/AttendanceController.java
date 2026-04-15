package com.sitevisit.smartfieldoperations.controller;

import com.sitevisit.smartfieldoperations.dto.ApiResponse;
import com.sitevisit.smartfieldoperations.entity.Attendance;
import com.sitevisit.smartfieldoperations.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/check-in/{siteVisitId}")
    public ResponseEntity<ApiResponse> checkIn(@PathVariable Long siteVisitId) {
        ApiResponse response = attendanceService.checkIn(siteVisitId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-out/{siteVisitId}")
    public ResponseEntity<ApiResponse> checkOut(@PathVariable Long siteVisitId) {
        ApiResponse response = attendanceService.checkOut(siteVisitId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @GetMapping("/{siteVisitId}")
    public ResponseEntity<Optional<Attendance>> getAttendanceBySiteVisitId(@PathVariable Long siteVisitId) {
        return ResponseEntity.ok(attendanceService.getAttendanceBySiteVisitId(siteVisitId));
    }
}