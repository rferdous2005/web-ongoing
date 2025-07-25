package com.cirt.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cirt.web.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}