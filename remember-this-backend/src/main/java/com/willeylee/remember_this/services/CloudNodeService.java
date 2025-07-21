package com.willeylee.remember_this.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.willeylee.remember_this.dto.CloudNodeRequest;
import com.willeylee.remember_this.entities.CloudNode;
import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.repositories.CloudNodeRepository;
import com.willeylee.remember_this.repositories.UserRepository;

@Service
public class CloudNodeService {

    private final UserRepository userRepository;
    private final CloudNodeRepository cloudNodeRepository;
    Logger logger = LoggerFactory.getLogger(CloudNodeService.class);

    @Autowired
    public CloudNodeService(UserRepository userRepository, CloudNodeRepository cloudNodeRepository){
        this.userRepository = userRepository;
        this.cloudNodeRepository = cloudNodeRepository;
    }
    
    public void createCloudNode(CloudNodeRequest userNodeRequest, String oidcId){
        CloudNode cloudNode = new CloudNode();
        User user = userRepository.findByOidcId(oidcId).orElseThrow(() -> new RuntimeException("@@@@@@@@@user not found.@@@@@@@@@"));
        cloudNode.setUser(user);
        cloudNodeRepository.save(cloudNode);
        logger.info("Cloud Node stored in repository");
    }
    public void SaveCloudNode(CloudNodeRequest cloudNodeRequest, String oidcId){
   
        User user = userRepository.findByOidcId(oidcId).orElseThrow(() -> new RuntimeException("No user oidc found during SaveCloudNode in SaveCloudNodeService"));
        CloudNode cloudNode = cloudNodeRepository.findByNodeId(cloudNodeRequest.getNodeId()).orElseThrow(() -> new RuntimeException("No UserId found during SaveCloudeNode in SaveCloudNodeService"));
        cloudNode.setNodeText(cloudNodeRequest.getNodeText());
        cloudNodeRepository.save(cloudNode);
        userRepository.save(user);
    }
}
