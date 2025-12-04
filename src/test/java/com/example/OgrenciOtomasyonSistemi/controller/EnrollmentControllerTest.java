package com.example.OgrenciOtomasyonSistemi.controller;

import com.example.OgrenciOtomasyonSistemi.model.Course;
import com.example.OgrenciOtomasyonSistemi.model.Enrollment;
import com.example.OgrenciOtomasyonSistemi.model.Student;
import com.example.OgrenciOtomasyonSistemi.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EnrollmentControllerTest {

    private EnrollmentService enrollmentService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        enrollmentService = mock(EnrollmentService.class);
        EnrollmentController controller = new EnrollmentController(enrollmentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void enroll_returnsOk() throws Exception {
        Student s = new Student(); s.setId(1L);
        Course c = new Course(); c.setId(2L);
        Enrollment e = new Enrollment(s, c);
        e.setId(3L);

        when(enrollmentService.enroll(1L,2L)).thenReturn(e);

        mockMvc.perform(post("/api/enrollments/courses/2/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3));

        verify(enrollmentService,times(1)).enroll(1L,2L);
    }

    @Test
    void unenroll_returnsNoContent() throws Exception {
        doNothing().when(enrollmentService).unenroll(1L,2L);

        mockMvc.perform(delete("/api/enrollments/courses/2/students/1"))
                .andExpect(status().isNoContent());

        verify(enrollmentService,times(1)).unenroll(1L,2L);
    }
}
