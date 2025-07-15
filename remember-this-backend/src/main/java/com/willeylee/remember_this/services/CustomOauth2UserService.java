package com.willeylee.remember_this.services;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.willeylee.remember_this.entities.User;
import com.willeylee.remember_this.repositories.UserRepository;

@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService{
    
    private final UserRepository userRepository;

    public CustomOauth2UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest){
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        String oidcId = oAuth2User.getName();

        if (userRepository.findByOidcId(oidcId).isPresent()){
            System.out.println("@@@@@Matching oidcID found in database. New User not created@@@@@@");
            return oAuth2User;
        }
        else {
            User user = new User();
            user.setOidcId(oidcId);
            userRepository.save(user);
            System.out.println("New User created and added to database, id: " + oidcId);
        }
        return oAuth2User;
    }
}
