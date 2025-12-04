package com.example.OgrenciOtomasyonSistemi.controller;

import com.example.OgrenciOtomasyonSistemi.model.Enrollment;
import com.example.OgrenciOtomasyonSistemi.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/courses/{courseId}/students/{studentId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<Enrollment> enroll(@PathVariable Long studentId, @PathVariable Long courseId) {
        Enrollment e = enrollmentService.enroll(studentId, courseId);
        return ResponseEntity.ok(e);
    }

    @DeleteMapping("/courses/{courseId}/students/{studentId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<Void> unenroll(@PathVariable Long studentId, @PathVariable Long courseId) {
        enrollmentService.unenroll(studentId, courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/courses/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<List<Enrollment>> listByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.findByCourseId(courseId));
    }

    @GetMapping("/students/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public ResponseEntity<List<Enrollment>> listByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(enrollmentService.findByStudentId(studentId));
    }
}

