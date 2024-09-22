package com.example.demo.entity;

import com.example.demo.util.TableConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = TableConstants.user)
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneNo;
}
