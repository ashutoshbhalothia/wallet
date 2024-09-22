package com.example.demo.controller;

import com.example.demo.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserHandler userHandler;

    @GetMapping("/login")
    public ResponseEntity<String> loginUser(@RequestHeader String username, @RequestHeader String password) {
        //TODO : Write some service logic
        userHandler.handleLoginRequest(username, password);
        return new ResponseEntity<>("Hi " + username + ", Welcome to the new world", HttpStatus.OK);
    }
}
