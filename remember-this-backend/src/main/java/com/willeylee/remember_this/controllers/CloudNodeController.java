package com.willeylee.remember_this.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import com.willeylee.remember_this.services.CloudNodeService;
import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.dto.NodeRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CloudNodeController {

    private final CloudNodeService cloudNodeService; 
    
    @Autowired
    public CloudNodeController(CloudNodeService cloudNodeService){
        this.cloudNodeService = cloudNodeService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCloudNode(@RequestBody NodeRequest NodeRequest, @AuthenticationPrincipal OidcUser oidcUser) {
        if (NodeRequest.getNode() == null || NodeRequest.getNode().isEmpty()){
            return ResponseEntity.badRequest().body("Node cannot be empty or null.");
        }

        try {
            String oidcID = oidcUser.getSubject();
            cloudNodeService.createCloudNode(NodeRequest, oidcID);
            return ResponseEntity.ok().body("Node created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage()); 
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
