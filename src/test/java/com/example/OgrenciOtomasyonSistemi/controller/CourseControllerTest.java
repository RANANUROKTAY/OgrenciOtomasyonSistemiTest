package com.example.OgrenciOtomasyonSistemi.controller;

import com.example.OgrenciOtomasyonSistemi.model.Course;
import com.example.OgrenciOtomasyonSistemi.model.Teacher;
import com.example.OgrenciOtomasyonSistemi.model.User;
import com.example.OgrenciOtomasyonSistemi.model.Role;
import com.example.OgrenciOtomasyonSistemi.service.CourseService;
import com.example.OgrenciOtomasyonSistemi.service.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class CourseControllerTest {

    private CourseService courseService;
    private TeacherService teacherService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        courseService = mock(CourseService.class);
        teacherService = mock(TeacherService.class);
        CourseController controller = new CourseController(courseService, teacherService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void list_returnsOk() throws Exception {
        Course c = new Course("C1","Title","Desc", null);
        when(courseService.findAll()).thenReturn(List.of(c));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(courseService, times(1)).findAll();
    }

    @Test
    void create_callsService_andReturnsCreated() throws Exception {
        Teacher t = new Teacher(); t.setId(2L);
        Course req = new Course("C2","T","D", t);
        Course saved = new Course("C2","T","D", t); saved.setId(9L);

        when(teacherService.findById(2L)).thenReturn(Optional.of(t));
        when(courseService.createCourse(anyString(), anyString(), anyString(), any())).thenReturn(saved);

        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/courses/9"));

        verify(courseService, times(1)).createCourse(anyString(), anyString(), anyString(), any());
    }
}

