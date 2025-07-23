package com.willeylee.remember_this.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.repositories.UserRepository;

@Service
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser>{
    
    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(CustomOidcUserService.class);

    public CustomOidcUserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public OidcUser loadUser (OidcUserRequest oidcUserRequest){
        OidcUserService oidcUserService = new OidcUserService();
        OidcUser oidcUser = oidcUserService.loadUser(oidcUserRequest);
        String oidcId = oidcUser.getSubject();
        logger.info("@@@@@Your CustomOauth2UserService is being called@@@@@@");

        if (userRepository.findByOidcId(oidcId).isPresent()){
            logger.info("@@@@@Matching oidcID found in database. New User not created");
            return oidcUser;
        }
        else {
            User user = new User();
            user.setOidcId(oidcId);
            userRepository.save(user);
            logger.info("New User created and added to database, id: " + oidcId);
        }
        return oidcUser;
    }
}
