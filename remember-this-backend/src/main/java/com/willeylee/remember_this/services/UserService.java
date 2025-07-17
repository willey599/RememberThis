package com.willeylee.remember_this.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.willeylee.remember_this.dto.NodeRequest;
import com.willeylee.remember_this.entities.CloudNode;
import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.repositories.CloudNodeRepository;
import com.willeylee.remember_this.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CloudNodeRepository cloudNodeRepository;

    @Autowired
    public UserService(UserRepository userRepository, CloudNodeRepository cloudNodeRepository){
        this.userRepository = userRepository;
        this.cloudNodeRepository = cloudNodeRepository;
    }
    
    public void createCloudNode(NodeRequest userNodeRequest){
        CloudNode cloudNode = new CloudNode();
        cloudNode.setNodeText(userNodeRequest.getNode());
        cloudNodeRepository.save(cloudNode);
    }

}
