package com.example.OgrenciOtomasyonSistemi.service;

import com.example.OgrenciOtomasyonSistemi.model.Teacher;
import com.example.OgrenciOtomasyonSistemi.model.User;
import com.example.OgrenciOtomasyonSistemi.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserService userService;

    public TeacherService(TeacherRepository teacherRepository, UserService userService) {
        this.teacherRepository = teacherRepository;
        this.userService = userService;
    }

    public Teacher createTeacher(String username, String rawPassword, String firstName, String lastName) {
        User user = userService.createUser(username, rawPassword, com.example.OgrenciOtomasyonSistemi.model.Role.ROLE_TEACHER);
        Teacher teacher = new Teacher(user, firstName, lastName);
        return teacherRepository.save(teacher);
    }

    public List<Teacher> findAll() { return teacherRepository.findAll(); }
    public Optional<Teacher> findById(Long id) { return teacherRepository.findById(id); }
    public Teacher save(Teacher teacher) { return teacherRepository.save(teacher); }
    public void deleteById(Long id) { teacherRepository.deleteById(id); }
}

