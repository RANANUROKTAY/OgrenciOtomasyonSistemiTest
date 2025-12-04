package com.example.OgrenciOtomasyonSistemi.repository;

import com.example.OgrenciOtomasyonSistemi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

