package com.example.demo.handler;

import com.example.demo.dto.UserLoginResponse;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(UserHandler.class);

    @Autowired
    private UserService userService;

    public UserLoginResponse handleLoginRequest(String username, String password) {
        LOGGER.info("Login request recieved for user : {}",username);
        UserLoginResponse response = new UserLoginResponse();
        userService.get(username,password);
        return response;
    }
}
