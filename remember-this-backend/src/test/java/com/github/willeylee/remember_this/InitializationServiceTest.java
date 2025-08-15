package com.github.willeylee.remember_this;

import com.willeylee.remember_this.dto.CloudNodeRequest;
import com.willeylee.remember_this.entities.CloudNode;
import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.repositories.CloudNodeRepository;
import com.willeylee.remember_this.repositories.UserRepository;
import com.willeylee.remember_this.services.CloudNodeService;
import com.willeylee.remember_this.services.InitializationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class InitializationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CloudNodeRepository cloudNodeRepository;

    @InjectMocks
    private InitializationService initializationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // initialize @Mock and @InjectMocks
    }

    @Test
    void getAllUserNodes_Success(){
        //arrange
        List<CloudNode> mockNodes = List.of(new CloudNode());
        User user = new User();
        String mockUserId = "mock";
        user.setOidcId(mockUserId);
        user.setUserId(1);
        
        when (userRepository.findByOidcId(mockUserId)).thenReturn(Optional.of(user));
        when (cloudNodeRepository.findAllByUserUserId(user.getUserId())).thenReturn(mockNodes);
        //act
        List<CloudNode> initializationResult = initializationService.getAllUserNodes(mockUserId);

        //assert
        assertEquals(mockNodes, initializationResult);
        assertEquals(mockUserId, user.getOidcId());

        //verify
        verify(userRepository).findByOidcId(mockUserId);
        verify(cloudNodeRepository).findAllByUserUserId(user.getUserId());


    }
}