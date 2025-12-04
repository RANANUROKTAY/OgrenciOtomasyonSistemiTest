package com.example.OgrenciOtomasyonSistemi;

import com.example.OgrenciOtomasyonSistemi.model.Role;
import com.example.OgrenciOtomasyonSistemi.model.User;
import com.example.OgrenciOtomasyonSistemi.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    public DataLoader(UserService userService, StudentService studentService, TeacherService teacherService, CourseService courseService) {
        this.userService = userService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @Override
    public void run(String... args) throws Exception {
        // create admin user
        userService.createUser("admin", "adminpass", Role.ROLE_ADMIN);

        // sample teacher and student
        var t = teacherService.createTeacher("teacher1", "teachpass", "Ali", "Veli");
        var s = studentService.createStudent("student1", "studpass", "Ayse", "Yilmaz");

        // create sample course
        courseService.createCourse("CS101", "Introduction to CS", "Basics of computer science", t);
    }
}

