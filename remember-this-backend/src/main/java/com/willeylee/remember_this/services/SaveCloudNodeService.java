package com.willeylee.remember_this.services;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.willeylee.remember_this.dto.CloudNodeRequest;
import com.willeylee.remember_this.entities.CloudNode;
import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.repositories.CloudNodeRepository;
import com.willeylee.remember_this.repositories.UserRepository;

@Service
public class SaveCloudNodeService {
    private int nodeId;
    private String nodeText;
    CloudNodeRepository cloudNodeRepository;
    UserRepository userRepository;


    public SaveCloudNodeService(CloudNodeRepository cloudNodeRepository, UserRepository userRepository){
        this.cloudNodeRepository = cloudNodeRepository;
        this.userRepository = userRepository;
    }

    public void SaveCloudNode(@RequestBody CloudNodeRequest cloudNodeRequest, String oidcId){
   
        User user = userRepository.findByOidcId(oidcId).orElseThrow(() -> new RuntimeException("No user oidc found during SaveCloudNode in SaveCloudNodeService"));
        CloudNode cloudNode = cloudNodeRepository.findByNodeId(nodeId).orElseThrow(() -> new RuntimeException("No UserId found during SaveCloudeNode in SaveCloudNodeService"));
        cloudNode.setNodeText(nodeText);
        cloudNodeRepository.save(cloudNode);
        userRepository.save(user);
    }
}
