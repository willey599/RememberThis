package com.willeylee.remember_this.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.willeylee.remember_this.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
