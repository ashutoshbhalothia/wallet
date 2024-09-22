package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController extends BaseController{

    @GetMapping("/health-check")
    public ResponseEntity<String> getHealth(){
        return new ResponseEntity<>("Application is working fine", HttpStatus.OK);
    }
}
