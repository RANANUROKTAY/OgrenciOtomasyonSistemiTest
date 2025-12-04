package com.example.OgrenciOtomasyonSistemi.service;

import com.example.OgrenciOtomasyonSistemi.model.Course;
import com.example.OgrenciOtomasyonSistemi.model.Teacher;
import com.example.OgrenciOtomasyonSistemi.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    private CourseRepository courseRepository;
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        courseRepository = mock(CourseRepository.class);
        courseService = new CourseService(courseRepository);
    }

    @Test
    void createCourse_savesCourse() {
        when(courseRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Teacher t = new Teacher(); t.setId(1L);
        Course c = courseService.createCourse("C1","Title","Desc", t);

        assertNotNull(c);
        assertEquals("C1", c.getCode());
        assertEquals("Title", c.getTitle());
        assertEquals(t, c.getTeacher());

        verify(courseRepository, times(1)).save(any());
    }
}

