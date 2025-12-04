package com.example.OgrenciOtomasyonSistemi.service;

import com.example.OgrenciOtomasyonSistemi.model.Teacher;
import com.example.OgrenciOtomasyonSistemi.repository.TeacherRepository;
import com.example.OgrenciOtomasyonSistemi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeacherServiceTest {

    private TeacherRepository teacherRepository;
    private UserRepository userRepository;
    private UserService userService;
    private TeacherService teacherService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        teacherRepository = mock(TeacherRepository.class);
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository, passwordEncoder);
        teacherService = new TeacherService(teacherRepository, userService);
    }

    @Test
    void createTeacher_createsUserAndTeacher() {
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(teacherRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Teacher t = teacherService.createTeacher("t1","p2","A","B");

        assertNotNull(t);
        assertNotNull(t.getUser());
        assertEquals("A", t.getFirstName());
        assertEquals("B", t.getLastName());
        assertTrue(passwordEncoder.matches("p2", t.getUser().getPassword()));

        verify(userRepository, times(1)).save(any());
        verify(teacherRepository, times(1)).save(any());
    }
}

