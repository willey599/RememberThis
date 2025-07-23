package com.willeylee.remember_this.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import com.willeylee.remember_this.services.CloudNodeService;
import com.willeylee.remember_this.services.InitializationService;
import com.willeylee.remember_this.dto.CloudNodeRequest;
import com.willeylee.remember_this.entities.CloudNode;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CloudNodeController {
    private final CloudNodeService cloudNodeService; 
    private final InitializationService initializationService;
    Logger logger = LoggerFactory.getLogger(CloudNodeController.class);

    @Autowired
    public CloudNodeController(CloudNodeService cloudNodeService, InitializationService initializationService ){
        this.cloudNodeService = cloudNodeService;
        this.initializationService = initializationService;
    }

    @GetMapping("/initialize")
    public ResponseEntity<?> InitializeCloudNode(@AuthenticationPrincipal OidcUser oidcUser){
        try{
            String oidcId = oidcUser.getSubject();
            List<CloudNode> cloudNodes =  initializationService.getAllUserNodes(oidcId);
            if(cloudNodes.isEmpty()){
                logger.info("In initializeCloudNodes(), CloudNodes were not returned returned from DB.");
            }
            else{
                logger.info("initializeCloudNodes() was a success, returning CloudNodes.");
            }
            return ResponseEntity.ok(cloudNodes); 
            
        }
        catch (Exception e){
            logger.error("Exception in initializeCloudNode: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/create")
    public ResponseEntity<?> createCloudNode(@AuthenticationPrincipal OidcUser oidcUser) {
        try {
            String oidcId = oidcUser.getSubject();
            int nodeId = cloudNodeService.createCloudNode(oidcId);
            logger.info("Successfully acquired nodeId");
            return ResponseEntity.ok(nodeId);
        }catch (Exception e) {
            logger.info("@@@@@@@@@ Exception found, did not acquire nodeId.");
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        } 
    }
    @PostMapping("/save")
    public ResponseEntity<?> saveNodetext(@RequestBody CloudNodeRequest nodeRequest, @AuthenticationPrincipal OidcUser oidcUser){
        String oidcId = oidcUser.getSubject();
        try{
            cloudNodeService.SaveCloudNode(nodeRequest, oidcId);
            return ResponseEntity.ok().body("Node text successfully stored in DB");
        }catch(IllegalArgumentException e){
            logger.info("IllegalArgumentException, did not store node text in DB");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch(Exception e){
            logger.info("Exception, did not store node text in DB");
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occured");
        }
    }
}
