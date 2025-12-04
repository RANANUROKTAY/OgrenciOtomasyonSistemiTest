package com.example.OgrenciOtomasyonSistemi.controller;

import com.example.OgrenciOtomasyonSistemi.model.Teacher;
import com.example.OgrenciOtomasyonSistemi.model.User;
import com.example.OgrenciOtomasyonSistemi.model.Role;
import com.example.OgrenciOtomasyonSistemi.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TeacherControllerTest {

    private TeacherService teacherService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        teacherService = mock(TeacherService.class);
        TeacherController controller = new TeacherController(teacherService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void list_returnsOk() throws Exception {
        Teacher t = new Teacher(new User("t1","p", Role.ROLE_TEACHER), "A", "B");
        when(teacherService.findAll()).thenReturn(List.of(t));

        mockMvc.perform(get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(teacherService, times(1)).findAll();
    }

    @Test
    void create_callsService_andReturnsCreated() throws Exception {
        Teacher req = new Teacher(new User("t2","p2", Role.ROLE_TEACHER), "F", "L");
        Teacher saved = new Teacher(new User("t2","enc", Role.ROLE_TEACHER), "F", "L");
        saved.setId(7L);

        when(teacherService.createTeacher(anyString(), anyString(), anyString(), anyString())).thenReturn(saved);

        String json = "{\"user\":{\"username\":\"t2\",\"password\":\"p2\"},\"firstName\":\"F\",\"lastName\":\"L\"}";
        mockMvc.perform(post("/api/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/teachers/7"));

        verify(teacherService, times(1)).createTeacher(anyString(), anyString(), anyString(), anyString());
    }
}
