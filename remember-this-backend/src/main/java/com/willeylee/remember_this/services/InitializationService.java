package com.willeylee.remember_this.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.willeylee.remember_this.entities.CloudNode;
import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.repositories.CloudNodeRepository;
import com.willeylee.remember_this.repositories.UserRepository;

@Service
public class InitializationService {
    private final UserRepository userRepository;
    private final CloudNodeRepository cloudNodeRepository;

    public InitializationService (UserRepository userRepository, CloudNodeRepository cloudNodeRepository){
        this.userRepository = userRepository;
        this.cloudNodeRepository = cloudNodeRepository;
    }
    
    public List<CloudNode> getAllUserNodes(String oidcUser){
        User user = userRepository.findByOidcId(oidcUser).orElseThrow(() -> new RuntimeException("In GetAllUserNodes method, could not find user"));
        List<CloudNode> cloudNodes = cloudNodeRepository.findAllByUserUserId(user.getUserId());
        return cloudNodes;
    }
}
