package com.example.OgrenciOtomasyonSistemi.controller;

import com.example.OgrenciOtomasyonSistemi.model.Course;
import com.example.OgrenciOtomasyonSistemi.model.Teacher;
import com.example.OgrenciOtomasyonSistemi.service.CourseService;
import com.example.OgrenciOtomasyonSistemi.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final TeacherService teacherService;

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Course> list() { return courseService.findAll(); }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<Course> create(@RequestBody Course req) {
        Teacher teacher = null;
        if (req.getTeacher() != null && req.getTeacher().getId() != null) {
            teacher = teacherService.findById(req.getTeacher().getId()).orElse(null);
        }
        Course created = courseService.createCourse(req.getCode(), req.getTitle(), req.getDescription(), teacher);
        return ResponseEntity.created(URI.create("/api/courses/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody Course req) {
        return courseService.findById(id).map(c -> {
            c.setCode(req.getCode());
            c.setTitle(req.getTitle());
            c.setDescription(req.getDescription());
            if (req.getTeacher() != null && req.getTeacher().getId() != null) {
                Teacher t = teacherService.findById(req.getTeacher().getId()).orElse(null);
                c.setTeacher(t);
            }
            courseService.save(c);
            return ResponseEntity.ok(c);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

