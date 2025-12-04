package com.example.OgrenciOtomasyonSistemi.service;

import com.example.OgrenciOtomasyonSistemi.model.*;
import com.example.OgrenciOtomasyonSistemi.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentService studentService, CourseService courseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enroll(Long studentId, Long courseId) {
        Student student = studentService.findById(studentId).orElseThrow();
        Course course = courseService.findById(courseId).orElseThrow();
        Enrollment e = new Enrollment(student, course);
        return enrollmentRepository.save(e);
    }

    public void unenroll(Long studentId, Long courseId) {
        List<Enrollment> list = enrollmentRepository.findByStudentId(studentId);
        list.stream().filter(e -> e.getCourse().getId().equals(courseId)).findFirst().ifPresent(enrollmentRepository::delete);
    }

    public List<Enrollment> findByCourseId(Long courseId) { return enrollmentRepository.findByCourseId(courseId); }
    public List<Enrollment> findByStudentId(Long studentId) { return enrollmentRepository.findByStudentId(studentId); }
}

