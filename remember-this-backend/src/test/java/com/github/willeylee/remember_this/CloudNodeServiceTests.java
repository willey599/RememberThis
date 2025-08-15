package com.github.willeylee.remember_this;

import com.willeylee.remember_this.dto.CloudNodeRequest;
import com.willeylee.remember_this.entities.CloudNode;
import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.repositories.CloudNodeRepository;
import com.willeylee.remember_this.repositories.UserRepository;
import com.willeylee.remember_this.services.CloudNodeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CloudNodeServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CloudNodeRepository cloudNodeRepository;

    @InjectMocks
    private CloudNodeService cloudNodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // initialize @Mock and @InjectMocks
    }

    @Test
    void createCloudNode_success() {
        // Arrange
        String oidcId = "testOidcId";
        User mockUser = new User();
        mockUser.setUserId(1);

        when(userRepository.findByOidcId(oidcId)).thenReturn(Optional.of(mockUser));

        when(cloudNodeRepository.save(any(CloudNode.class)))
                .thenAnswer(invocation -> {
                    CloudNode savedNode = invocation.getArgument(0);
                    savedNode.setNodeId(42); // simulate DB assigning ID
                    return savedNode;
                });

        // Act
        int nodeId = cloudNodeService.createCloudNode(oidcId);

        // Assert
        assertEquals(42, nodeId);
        //check whether these methods have been called once
        verify(userRepository).findByOidcId(oidcId);
        verify(cloudNodeRepository).save(any(CloudNode.class));
    }

    @Test
    void createCloudNode_failure(){
        //arrange
        String mockOicdId = "mock";
        User user = new User();
        user.setOidcId(mockOicdId);
        when(userRepository.findByOidcId(mockOicdId)).thenReturn(Optional.empty());
        
        //act
        assertThrows(Exception.class, () -> cloudNodeService.createCloudNode(mockOicdId));
        //assert
        verify(userRepository).findByOidcId(user.getOidcId());

    }

    @Test
    void saveCloudNode_success() {
        CloudNodeRequest mockRequest = new CloudNodeRequest();
        mockRequest.setNodeId(1);
        mockRequest.setNodeText("mock test");
        mockRequest.setNodeContext1("c1");
        mockRequest.setNodeContext2("c2");
        mockRequest.setNodeContext3("c3");

        CloudNode mockNode = new CloudNode();
        mockNode.setNodeId(1);

        when(cloudNodeRepository.findByNodeId(1)).thenReturn(Optional.of(mockNode));
    //act
        cloudNodeService.saveCloudNode(mockRequest);
    //assert
        assertEquals(1, mockNode.getNodeId());
        assertEquals("mock test", mockNode.getNodeText());
        assertEquals("c1", mockNode.getNodeContext1());
        assertEquals("c2", mockNode.getNodeContext2());
        assertEquals("c3", mockNode.getNodeContext3());

        //verify that mock repo methods were used
        verify(cloudNodeRepository).findByNodeId(1);
        verify(cloudNodeRepository).save(mockNode);
    }

    @Test
    void saveCloudnode_failure(){
        //Arrange
        CloudNodeRequest mockRequest = new CloudNodeRequest();
        mockRequest.setNodeId(-1);
        when(cloudNodeRepository.findByNodeId(mockRequest.getNodeId())).thenReturn(Optional.empty());
    
        assertThrows(Exception.class, () -> cloudNodeService.saveCloudNode(mockRequest));
        
        //verify
        verify(cloudNodeRepository).findByNodeId(mockRequest.getNodeId());
    }


    @Test
    void deleteCloudNode_Success(){
        //arrange
        CloudNodeRequest mockRequest = new CloudNodeRequest();
        mockRequest.setNodeId(1);
        CloudNode mockNode = new CloudNode();
        mockNode.setNodeId(mockRequest.getNodeId());

        when(cloudNodeRepository.findByNodeId(mockRequest.getNodeId())).thenReturn(Optional.of(mockNode));
        
        //act
        cloudNodeService.deleteCloudNode(mockNode.getNodeId());
        
        //assert
        verify(cloudNodeRepository).findByNodeId(1);
        verify(cloudNodeRepository).delete(mockNode);
    }

    @Test
    void deleteCloudNode_Failure(){
        //Arrange
        int mockNodeId = -1;
        when(cloudNodeRepository.findByNodeId(mockNodeId)).thenReturn(Optional.empty());
        //Act and Assert
        assertThrows(Exception.class, () -> cloudNodeService.deleteCloudNode(mockNodeId));
        verify(cloudNodeRepository).findByNodeId(mockNodeId);
    }
}