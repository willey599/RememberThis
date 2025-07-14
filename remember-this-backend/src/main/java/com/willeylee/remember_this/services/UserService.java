package com.willeylee.remember_this.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.willeylee.remember_this.dto.UserEmailRequest;
import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    
    public User createUser(UserEmailRequest userEmailRequest){
        if (userRepository.findByEmail(userEmailRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered: " + userEmailRequest.getEmail());
        }
        User user = new User();
        user.setEmail(userEmailRequest.getEmail());
        userRepository.save(user);
        return user;

    }

}
