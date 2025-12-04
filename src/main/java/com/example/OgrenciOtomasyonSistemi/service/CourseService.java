package com.example.OgrenciOtomasyonSistemi.service;

import com.example.OgrenciOtomasyonSistemi.model.Course;
import com.example.OgrenciOtomasyonSistemi.model.Teacher;
import com.example.OgrenciOtomasyonSistemi.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createCourse(String code, String title, String description, Teacher teacher) {
        Course c = new Course(code, title, description, teacher);
        return courseRepository.save(c);
    }

    public List<Course> findAll() { return courseRepository.findAll(); }
    public Optional<Course> findById(Long id) { return courseRepository.findById(id); }
    public Course save(Course course) { return courseRepository.save(course); }
    public void deleteById(Long id) { courseRepository.deleteById(id); }
}

