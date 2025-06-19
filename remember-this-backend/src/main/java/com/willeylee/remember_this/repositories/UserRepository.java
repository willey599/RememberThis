package com.willeylee.remember_this.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.willeylee.remember_this.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
