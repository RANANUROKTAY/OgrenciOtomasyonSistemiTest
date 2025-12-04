package com.example.OgrenciOtomasyonSistemi.service;

import com.example.OgrenciOtomasyonSistemi.model.Role;
import com.example.OgrenciOtomasyonSistemi.model.Student;
import com.example.OgrenciOtomasyonSistemi.model.User;
import com.example.OgrenciOtomasyonSistemi.repository.StudentRepository;
import com.example.OgrenciOtomasyonSistemi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    private StudentRepository studentRepository;
    private UserRepository userRepository;
    private UserService userService;
    private StudentService studentService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository, passwordEncoder);
        studentService = new StudentService(studentRepository, userService);
    }

    @Test
    void createStudent_createsUserAndStudent() {
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(studentRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Student s = studentService.createStudent("u1","p1","F","L");

        assertNotNull(s);
        assertNotNull(s.getUser());
        assertEquals("F", s.getFirstName());
        assertEquals("L", s.getLastName());
        assertTrue(passwordEncoder.matches("p1", s.getUser().getPassword()));

        verify(userRepository, times(1)).save(any());
        verify(studentRepository, times(1)).save(any());
    }
}

