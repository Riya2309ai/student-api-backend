package com.riya.studentapi.controller;

import com.riya.studentapi.dto.LoginRequestDTO;
import com.riya.studentapi.dto.LoginResponseDTO;


import com.riya.studentapi.security.JwtService;

import com.riya.studentapi.service.AuthService;

import com.riya.studentapi.dto.SignupRequestDTO;

import org.springframework.web.bind.annotation.*;

import com.riya.studentapi.repository.UserRepository;

import com.riya.studentapi.entity.User;

import org.springframework.security.authentication.
        AuthenticationManager;

import org.springframework.security.authentication.
        UsernamePasswordAuthenticationToken;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final JwtService jwtService;

    public AuthController(JwtService jwtService, AuthService authService,  UserRepository userRepository, AuthenticationManager
            authenticationManager) {
        this.jwtService = jwtService;
        this.authService = authService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }


    //hard coded user login pwd
//    @PostMapping("/login")
//    public LoginResponseDTO login(
//            @RequestBody LoginRequestDTO request) {
//
//        if (request.getUsername().equals("admin")
//                &&
//                request.getPassword().equals("password")) {     //for now doing hard coded credentials
//
//            String token =
//                    jwtService.generateToken(
//                            request.getUsername()
//                    );
//
//            return new LoginResponseDTO(token);
//        }
//
//        throw new RuntimeException(
//                "Invalid username or password"
//        );
//    }

    @PostMapping("/login")
    public LoginResponseDTO login(

            @RequestBody
            LoginRequestDTO request) {

        //springboot use automatic valid so remove login validation
//        boolean isValid = authService.login(
//
//                request.getUsername(),
//
//                request.getPassword()
//        );

//        if (!isValid) {
//
//            throw new RuntimeException(
//                    "Invalid username or password"
//            );
//        }

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getUsername(),

                        request.getPassword()
                )
        );
        
        User user = userRepository
                .findByUsername(
                        request.getUsername()
                )
                .orElseThrow();

        String token =
                jwtService.generateToken(

                        user.getUsername(),

                        user.getRole()
                );

        return new LoginResponseDTO(token);
    }

    @PostMapping("/signup")
    public String signup( @RequestBody SignupRequestDTO request) {
        return authService.signup(request);
    }
}