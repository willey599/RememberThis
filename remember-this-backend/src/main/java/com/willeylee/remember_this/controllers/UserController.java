package com.willeylee.remember_this.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.willeylee.remember_this.repositories.*;
import com.willeylee.remember_this.entities.User;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepositories;

    @Autowired

    public UserController(UserRepository userRepository){
        this.userRepositories = userRepository;
    }

    @PostMapping("/create")
    public void saveToDB(@RequestBody User user) {
        userRepositories.save(user);
    }
}