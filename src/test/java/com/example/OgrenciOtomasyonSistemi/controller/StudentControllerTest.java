package com.example.OgrenciOtomasyonSistemi.controller;

import com.example.OgrenciOtomasyonSistemi.model.Role;
import com.example.OgrenciOtomasyonSistemi.model.Student;
import com.example.OgrenciOtomasyonSistemi.model.User;
import com.example.OgrenciOtomasyonSistemi.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StudentControllerTest {

    private StudentService studentService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        studentService = mock(StudentService.class);
        StudentController controller = new StudentController(studentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void list_returnsJsonArray() throws Exception {
        Student s1 = new Student(new User("stu1","p", Role.ROLE_STUDENT), "A", "B");
        when(studentService.findAll()).thenReturn(List.of(s1));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(studentService, times(1)).findAll();
    }

    @Test
    void get_whenFound_returnsStudent() throws Exception {
        Student s = new Student(new User("stu2","p", Role.ROLE_STUDENT), "X", "Y");
        s.setId(10L);
        when(studentService.findById(10L)).thenReturn(Optional.of(s));

        mockMvc.perform(get("/api/students/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));

        verify(studentService, times(1)).findById(10L);
    }

    @Test
    void get_whenNotFound_returns404() throws Exception {
        when(studentService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/students/99"))
                .andExpect(status().isNotFound());

        verify(studentService, times(1)).findById(99L);
    }

    @Test
    void create_callsService_andReturnsCreated() throws Exception {
        User reqUser = new User("newu","rawpass", Role.ROLE_STUDENT);
        Student req = new Student(reqUser, "FName", "LName");

        Student saved = new Student(new User("newu","enc", Role.ROLE_STUDENT), "FName", "LName");
        saved.setId(5L);

        when(studentService.createStudent(anyString(), anyString(), anyString(), anyString())).thenReturn(saved);

        String json = "{\"user\":{\"username\":\"newu\",\"password\":\"rawpass\"},\"firstName\":\"FName\",\"lastName\":\"LName\"}";
        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/students/5"));

        verify(studentService, times(1)).createStudent(anyString(), anyString(), anyString(), anyString());
    }
}
