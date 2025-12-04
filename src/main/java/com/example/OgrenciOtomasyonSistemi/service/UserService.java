package com.example.OgrenciOtomasyonSistemi.service;

import com.example.OgrenciOtomasyonSistemi.model.Role;
import com.example.OgrenciOtomasyonSistemi.model.User;
import com.example.OgrenciOtomasyonSistemi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username, String rawPassword, Role role) {
        User user = new User(username, passwordEncoder.encode(rawPassword), role);
        return userRepository.save(user);
    }
}

