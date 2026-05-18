package com.riya.studentapi.config;

import com.riya.studentapi.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.
        web.builders.HttpSecurity;

import org.springframework.security.config.http.
        SessionCreationPolicy;

import org.springframework.security.web.
        SecurityFilterChain;

import org.springframework.security.web.authentication.
        UsernamePasswordAuthenticationFilter;

import org.springframework.security.crypto.bcrypt.
        BCryptPasswordEncoder;

import org.springframework.security.crypto.password.
        PasswordEncoder;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.
        AuthenticationManager;

import org.springframework.security.config.annotation.
        authentication.configuration.
        AuthenticationConfiguration;

import com.riya.studentapi.security.
        CustomAuthenticationEntryPoint;

import com.riya.studentapi.security.
        CustomAccessDeniedHandler;

@Configuration
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    private final CustomAccessDeniedHandler accessDeniedHandler;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter, CustomAuthenticationEntryPoint authenticationEntryPoint,
            CustomAccessDeniedHandler accessDeniedHandler) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->

                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                //without role ke case me
//                .authorizeHttpRequests(auth -> auth
//
//                        .requestMatchers("/auth/**")
//                        .permitAll()
//
//                        .anyRequest()
//                        .authenticated()
//                )
                .exceptionHandling(ex -> ex

                        .authenticationEntryPoint(
                                authenticationEntryPoint
                        )

                        .accessDeniedHandler(
                                accessDeniedHandler
                        )
                )
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/auth/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET,
                                "/students/**")
                        .hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.POST,
                                "/students/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/students/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/students/**")
                        .hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                )

                .addFilterBefore(

                        jwtAuthenticationFilter,

                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager
    authenticationManager(

            AuthenticationConfiguration config)

            throws Exception {

        return config
                .getAuthenticationManager();
    }
}








//without jwtconfig ,without protected students/departments
//package com.riya.studentapi.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//import org.springframework.security.web.SecurityFilterChain;
//
//import org.springframework.security.config.http.
//        SessionCreationPolicy;
//
//import com.riya.studentapi.security.JwtAuthenticationFilter;
//import org.springframework.security.web.authentication.
//        UsernamePasswordAuthenticationFilter;
//
//@Configuration
//public class SecurityConfig {
//
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    public SecurityConfig(
//            JwtAuthenticationFilter jwtAuthenticationFilter) {
//
//        this.jwtAuthenticationFilter =
//                jwtAuthenticationFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(
//            HttpSecurity http) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())      //Useful for REST APIs
//
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/students/**").permitAll()                 //allow all student APIs without login
//                        .requestMatchers("/departments/**").permitAll()
//                        .requestMatchers("/auth/**").permitAll()
//                        .anyRequest().authenticated()         //EVERYTHING ELSE requires authentication.
//                );
//
//        return http.build();
//    }
//}