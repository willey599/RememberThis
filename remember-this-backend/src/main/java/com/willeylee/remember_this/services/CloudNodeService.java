package com.willeylee.remember_this.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.willeylee.remember_this.dto.CloudNodePositionRequest;
// import com.willeylee.remember_this.dto.CloudNodeFetchRequest;
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
        try{
            User user = userRepository.findByOidcId(oidcId).orElseThrow(() -> new RuntimeException("user not found.@@@@@@@@@"));
            cloudNode.setUser(user);
            cloudNode.setNodeText("");
            cloudNode.setNodeContext1("");
            cloudNode.setNodeContext2("");
            cloudNode.setNodeContext3("");
            cloudNodeRepository.save(cloudNode);
            logger.info("Cloud Node stored in repository");
        }catch(Exception e){
            logger.info("Cloud Node NOT stored in repository");
        }
        return cloudNode.getNodeId();
    }
    public void saveCloudNode(CloudNodeRequest cloudNodeRequest){
        logger.info("Entered SaveCloudNode, nodeText: ", cloudNodeRequest.getNodeText());
        CloudNode cloudNode = cloudNodeRepository.findByNodeId(cloudNodeRequest.getNodeId()).orElseThrow(() -> new RuntimeException("No NodeId found during SaveCloudeNode in CloudNodeService. Node ID: " + cloudNodeRequest.getNodeId()));
        logger.info("cloudNode found, cloudNodeId: " + cloudNode.getNodeId());
        try{
            cloudNode.setNodeText(cloudNodeRequest.getNodeText());
            cloudNode.setNodeContext1(cloudNodeRequest.getNodeContext1());
            cloudNode.setNodeContext2(cloudNodeRequest.getNodeContext2());
            cloudNode.setNodeContext3(cloudNodeRequest.getNodeContext3());
            logger.info("Node text fields saved: ");
            cloudNodeRepository.save(cloudNode);
        }
        catch (Exception e){
            logger.info("Something went wrong in SaveCloudNode.");
            logger.info(e.getMessage());
        }
    }

    public void savePositionCloudNode(CloudNodePositionRequest cloudNodePositionRequest){
        try{
            CloudNode cloudNode = cloudNodeRepository.findByNodeId(cloudNodePositionRequest.getNodeId()).orElseThrow(() -> new RuntimeException("No CloudNode found during SavePositionCloudNode in CloudNodeService. Node ID: " + cloudNodePositionRequest.getNodeId()));
            cloudNode.setNodeXPosition(cloudNodePositionRequest.getNodeXPosition());
            cloudNode.setNodeYPosition(cloudNodePositionRequest.getNodeYPosition());
            cloudNodeRepository.save(cloudNode);
            logger.info("Node position saved ");
        }catch(Exception e){
            logger.info("Something went wrong in savePositionCloudNode.");
            logger.info(e.getMessage());
        }
    }

    public void deleteCloudNode(int nodeId){
        CloudNode cloudNode = cloudNodeRepository.findByNodeId(nodeId).orElseThrow(() -> new RuntimeException("Error finding CloudNode"));
        logger.info("Node about to be deleted: " + cloudNode.getNodeId());
        cloudNodeRepository.delete(cloudNode);
        logger.info("Node deleted: ");
    }
}
