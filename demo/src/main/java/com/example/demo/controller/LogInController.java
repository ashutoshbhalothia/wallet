package com.example.demo.controller;

import com.example.demo.dto.UserLogin;
import com.example.demo.exceptions.LoginException;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LogInController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> loginMapping(@RequestBody UserLogin userLogin) throws LoginException {

        String output = loginService.login(userLogin);

        return new ResponseEntity<String>(output,HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutMapping(@RequestParam String key) throws LoginException{

        String output = loginService.logout(key);

        return new ResponseEntity<String>(output, HttpStatus.OK);
    }
}
