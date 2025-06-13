package com.github.willeylee.remember_this;

import org.springframework.data.jpa.repository.JpaRepository;
import src.main.entities.User.java;

public interface UserRepository extends JpaRepository<User, Integer> {

}
