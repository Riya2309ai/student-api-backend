package com.riya.studentapi.repository;

import com.riya.studentapi.entity.User;

import org.springframework.data.jpa.repository.
        JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByUsername(
            String username
    );
}