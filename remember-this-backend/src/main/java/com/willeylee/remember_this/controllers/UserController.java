package com.willeylee.remember_this.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.willeylee.remember_this.repositories.*;
import com.willeylee.remember_this.services.UserService;
import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.dto.UserEmailRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService; 
    
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserEmailRequest userEmailRequest) {
        if (userEmailRequest.getEmail() == null || userEmailRequest.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body("Email cannot be empty or null.");
        }

        try {
            User createdUser = userService.createUser(userEmailRequest);
            return ResponseEntity.ok().body("User created successfully with ID: " + createdUser.getEmail());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage()); 
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
