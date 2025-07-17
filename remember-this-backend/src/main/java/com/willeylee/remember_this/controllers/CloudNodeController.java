package com.willeylee.remember_this.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import com.willeylee.remember_this.services.UserService;
import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.dto.NodeRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CloudNodeController {

    private final UserService userService; 
    
    @Autowired
    public CloudNodeController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody NodeRequest NodeRequest) {
        if (NodeRequest.getNode() == null || NodeRequest.getNode().isEmpty()){
            return ResponseEntity.badRequest().body("Node cannot be empty or null.");
        }

        try {
            User createdUser = userService.createNode(NodeRequest.getNode());
            return ResponseEntity.ok().body("Node created successfully under ID: " + createdUser.getNode());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage()); 
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
