package com.willeylee.remember_this.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import com.willeylee.remember_this.services.CloudNodeService;
import com.willeylee.remember_this.dto.CloudNodeRequest;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CloudNodeController {

    private final CloudNodeService cloudNodeService; 
    Logger logger = LoggerFactory.getLogger(CloudNodeController.class);

    @Autowired
    public CloudNodeController(CloudNodeService cloudNodeService){
        this.cloudNodeService = cloudNodeService;
    }

    @GetMapping("/create")
    public ResponseEntity<?> createCloudNode(@AuthenticationPrincipal OidcUser oidcUser) {
        try {
            String oidcID = oidcUser.getSubject();
            int nodeId = cloudNodeService.createCloudNode(oidcID);
            logger.info("Successfully acquired nodeId");
            return ResponseEntity.ok(nodeId);
        } catch (IllegalArgumentException e) {
            logger.info("@@@@@@@@@ IllegalArgumentException, did not acquire nodeId");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
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
