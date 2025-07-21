package com.willeylee.remember_this.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.willeylee.remember_this.dto.CloudNodeRequest;
import com.willeylee.remember_this.services.SaveCloudNodeService;

@RestController
@RequestMapping("api/")
public class SaveCloudNodeController {

    @PostMapping("/save")
    public void saveeNodetext(@RequestBody CloudNodeRequest nodeRequest){
        
        SaveCloudNodeService saveCloudNodeService;


    }
}
