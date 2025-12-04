package com.example.OgrenciOtomasyonSistemi.service;

import com.example.OgrenciOtomasyonSistemi.model.Student;
import com.example.OgrenciOtomasyonSistemi.model.User;
import com.example.OgrenciOtomasyonSistemi.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;

    public StudentService(StudentRepository studentRepository, UserService userService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    public Student createStudent(String username, String rawPassword, String firstName, String lastName) {
        User user = userService.createUser(username, rawPassword, com.example.OgrenciOtomasyonSistemi.model.Role.ROLE_STUDENT);
        Student student = new Student(user, firstName, lastName);
        return studentRepository.save(student);
    }

    public List<Student> findAll() { return studentRepository.findAll(); }
    public Optional<Student> findById(Long id) { return studentRepository.findById(id); }
    public Student save(Student student) { return studentRepository.save(student); }
    public void deleteById(Long id) { studentRepository.deleteById(id); }
}

