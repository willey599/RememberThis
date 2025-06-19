package com.willeylee.remember_this.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.willeylee.remember_this.entities.User;

@CrossOrigin(origins = "http://localhost:4200")
public interface UserRepository extends JpaRepository<User, Integer> {

}
