package com.willeylee.remember_this.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.willeylee.remember_this.dto.NodeRequest;
import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    
    public User createUser(NodeRequest userNodeRequest){
        if (userRepository.findByNode(userNodeRequest.getNode()).isPresent()) {
            throw new IllegalArgumentException("Node already registered: " + userNodeRequest.getNode());
        }
        User user = new User();
        user.setNode(userNodeRequest.getNode());
        userRepository.save(user);
        return user;

    }

}
