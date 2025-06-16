package com.willeylee.remember_this.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.willeylee.remember_this.repositories.*;
import com.willeylee.remember_this.entities.User;

@RestController
public class UserController {
    
    UserRepository userRepositories;
    User user;

    @Autowired
    public UserController(UserRepository userRepository, User user){
        this.userRepositories = userRepository;
        this.user = user;
    }

    @GetMapping("/create")
    void saveToDB(User user, String email) {
        user.setEmail(email);
        userRepositories.save(user);
    }
}
