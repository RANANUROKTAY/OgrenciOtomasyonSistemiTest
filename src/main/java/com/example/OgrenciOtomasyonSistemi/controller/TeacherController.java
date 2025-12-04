package com.example.OgrenciOtomasyonSistemi.controller;

import com.example.OgrenciOtomasyonSistemi.model.Teacher;
import com.example.OgrenciOtomasyonSistemi.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public List<Teacher> list() { return teacherService.findAll(); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Teacher> create(@RequestBody Teacher req) {
        Teacher created = teacherService.createTeacher(req.getUser().getUsername(), req.getUser().getPassword(), req.getFirstName(), req.getLastName());
        return ResponseEntity.created(URI.create("/api/teachers/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<Teacher> update(@PathVariable Long id, @RequestBody Teacher req) {
        return teacherService.findById(id).map(t -> {
            t.setFirstName(req.getFirstName());
            t.setLastName(req.getLastName());
            teacherService.save(t);
            return ResponseEntity.ok(t);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

