package com.example.demo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "CurrentUserSession")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserSession {

    @Id
    @Column(unique = true)
    private Integer userId;

    private String uuid;

    private LocalDateTime localDateTime;


}

