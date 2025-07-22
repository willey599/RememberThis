package com.willeylee.remember_this.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    
    public int createCloudNode(String oidcId){
        CloudNode cloudNode = new CloudNode();
        User user = userRepository.findByOidcId(oidcId).orElseThrow(() -> new RuntimeException("@@@@@@@@@user not found.@@@@@@@@@"));
        cloudNode.setUser(user);
        cloudNodeRepository.save(cloudNode);
        logger.info("Cloud Node stored in repository");
        return cloudNode.getNodeId();
    }
    public void SaveCloudNode(CloudNodeRequest cloudNodeRequest, String oidcId){
        CloudNode cloudNode = cloudNodeRepository.findByNodeId(cloudNodeRequest.getNodeId()).orElseThrow(() -> new RuntimeException("No NodeId found during SaveCloudeNode in SaveCloudNodeService. Node ID: " + cloudNodeRequest.getNodeId()));
        try{
            cloudNode.setNodeText(cloudNodeRequest.getNodeText());
            logger.info("Node text saved: " + cloudNodeRequest.getNodeText());
            cloudNodeRepository.save(cloudNode);
        }
        catch (Exception e){
            logger.info("Something went wrong in SaveCloudNode.");
            logger.info(e.getMessage());
        }
    }
}
