package com.willeylee.remember_this.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.willeylee.remember_this.dto.UserNoteRequest;
import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    
    public User createUser(UserNoteRequest userNoteRequest){
        if (userRepository.findByNote(userNoteRequest.getNote()).isPresent()) {
            throw new IllegalArgumentException("Note already registered: " + userNoteRequest.getNote());
        }
        User user = new User();
        user.setNote(userNoteRequest.getNote());
        userRepository.save(user);
        return user;

    }

}
