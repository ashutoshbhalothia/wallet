package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserLoginResponse implements Serializable {
    private String isSuccessful;
    private String error;
    private String responseTime;
}
