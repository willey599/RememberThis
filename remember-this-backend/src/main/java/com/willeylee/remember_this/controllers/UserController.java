package com.willeylee.remember_this.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.willeylee.remember_this.repositories.*;
import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.dto.UserEmailRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/authenticated")
public class UserController {

 
  private final UserRepository userRepositories;

    @Autowired

    public UserController(UserRepository userRepository){
        this.userRepositories = userRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveToDB(@RequestBody UserEmailRequest userEmailRequest) {
        User user = new User();
         
        if (userEmailRequest.getEmail() == null){
            ResponseEntity.badRequest().body("Request Body is empty!");
        }
        user.setEmail(userEmailRequest.getEmail());
        userRepositories.save(user);
        return ResponseEntity.ok().body("U did it. Proud of u");
    }
}