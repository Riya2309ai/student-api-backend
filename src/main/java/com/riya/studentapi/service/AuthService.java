package com.riya.studentapi.service;

import com.riya.studentapi.dto.SignupRequestDTO;
import com.riya.studentapi.entity.User;
import com.riya.studentapi.repository.UserRepository;

import org.springframework.security.crypto.password.
        PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,

            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
    }

    public String signup(
            SignupRequestDTO request) {

        User user = new User();

        user.setUsername(
                request.getUsername()
        );

        user.setPassword(

                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(request.getRole());

        userRepository.save(user);

        return "User registered successfully";
    }

    public boolean login(
            String username,
            String password) {

        User user = userRepository
                .findByUsername(username)
                .orElse(null);

        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(
                password,
                user.getPassword()
        );
    }
}