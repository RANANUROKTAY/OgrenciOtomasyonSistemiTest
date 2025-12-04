package com.example.OgrenciOtomasyonSistemi.service;

import com.example.OgrenciOtomasyonSistemi.model.Course;
import com.example.OgrenciOtomasyonSistemi.model.Enrollment;
import com.example.OgrenciOtomasyonSistemi.model.Student;
import com.example.OgrenciOtomasyonSistemi.repository.EnrollmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EnrollmentServiceTest {

    private EnrollmentRepository enrollmentRepository;
    private StudentService studentService;
    private CourseService courseService;
    private EnrollmentService enrollmentService;

    @BeforeEach
    void setUp() {
        enrollmentRepository = mock(EnrollmentRepository.class);
        studentService = mock(StudentService.class);
        courseService = mock(CourseService.class);
        enrollmentService = new EnrollmentService(enrollmentRepository, studentService, courseService);
    }

    @Test
    void enroll_savesEnrollment() {
        Student s = new Student(); s.setId(1L);
        Course c = new Course(); c.setId(2L);

        when(studentService.findById(1L)).thenReturn(Optional.of(s));
        when(courseService.findById(2L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Enrollment e = enrollmentService.enroll(1L, 2L);

        assertNotNull(e);
        assertEquals(s, e.getStudent());
        assertEquals(c, e.getCourse());

        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    void unenroll_deletesEnrollmentIfExists() {
        Student s = new Student(); s.setId(1L);
        Course c = new Course(); c.setId(2L);
        Enrollment en = new Enrollment(s, c);
        en.setId(5L);

        List<Enrollment> list = new ArrayList<>();
        list.add(en);

        when(enrollmentRepository.findByStudentId(1L)).thenReturn(list);

        enrollmentService.unenroll(1L, 2L);

        verify(enrollmentRepository, times(1)).delete(en);
    }

    @Test
    void findByCourseId_delegates() {
        when(enrollmentRepository.findByCourseId(3L)).thenReturn(new ArrayList<>());
        var res = enrollmentService.findByCourseId(3L);
        assertNotNull(res);
        verify(enrollmentRepository, times(1)).findByCourseId(3L);
    }

    @Test
    void findByStudentId_delegates() {
        when(enrollmentRepository.findByStudentId(4L)).thenReturn(new ArrayList<>());
        var res = enrollmentService.findByStudentId(4L);
        assertNotNull(res);
        verify(enrollmentRepository, times(1)).findByStudentId(4L);
    }
}

