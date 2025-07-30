package com.willeylee.remember_this.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.willeylee.remember_this.dto.CloudNodeDeleteRequest;
import com.willeylee.remember_this.dto.CloudNodeFetchRequest;
import com.willeylee.remember_this.dto.CloudNodeRequest;
import com.willeylee.remember_this.entities.CloudNode;
import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.repositories.CloudNodeRepository;
import com.willeylee.remember_this.repositories.UserRepository;

@Service
public class CloudNodeService {

    private final UserRepository userRepository;
    private final CloudNodeRepository cloudNodeRepository;
    static final Logger logger = LoggerFactory.getLogger(CloudNodeService.class);

    @Autowired
    public CloudNodeService(UserRepository userRepository, CloudNodeRepository cloudNodeRepository){
        this.userRepository = userRepository;
        this.cloudNodeRepository = cloudNodeRepository;
    }
    
    public int createCloudNode(String oidcId){
        logger.info("Entered CreateCloudNode");
        CloudNode cloudNode = new CloudNode();
        User user = userRepository.findByOidcId(oidcId).orElseThrow(() -> new RuntimeException("user not found.@@@@@@@@@"));
        cloudNode.setUser(user);
        cloudNodeRepository.save(cloudNode);
        logger.info("Cloud Node stored in repository");
        return cloudNode.getNodeId();
    }
    public void saveCloudNode(CloudNodeRequest cloudNodeRequest, String oidcId){
        logger.info("Entered SaveCloudNode, nodeText: ", cloudNodeRequest.getNodeText());
        CloudNode cloudNode = cloudNodeRepository.findByNodeId(cloudNodeRequest.getNodeId()).orElseThrow(() -> new RuntimeException("No NodeId found during SaveCloudeNode in SaveCloudNodeService. Node ID: " + cloudNodeRequest.getNodeId()));
        logger.info("cloudNode found, cloudNodeId: " + cloudNode.getNodeId());
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

    public void deleteCloudNode(int nodeId, String oidcId){
        CloudNode cloudNode = cloudNodeRepository.findByNodeId(nodeId).orElseThrow(() -> new RuntimeException("Error finding CloudNode"));
        logger.info("Node about to be deleted: " + cloudNode.getNodeId());
        cloudNodeRepository.delete(cloudNode);
        logger.info("Node deleted: ");
    }

    public CloudNodeFetchRequest fetchCloudNode(CloudNodeFetchRequest cloudNodeFetchRequest, String oidcId){
        CloudNode cloudNode = cloudNodeRepository.findByNodeId(cloudNodeFetchRequest.getNodeId()).orElseThrow(() -> new RuntimeException("Error in updateCloudNode, failed to find cloudnode in DB"));
        cloudNodeFetchRequest.setNodeId(cloudNode.getNodeId());
        cloudNodeFetchRequest.setNodeText(cloudNode.getNodeText());
        cloudNodeFetchRequest.setXPosition(cloudNode.getXPosition());
        cloudNodeFetchRequest.setYPosition(cloudNode.getYPosition());
        return cloudNodeFetchRequest;
    }
}
