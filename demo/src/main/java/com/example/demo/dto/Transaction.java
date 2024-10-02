package com.example.demo.dto;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    private String transactionType;

    private LocalDate transactionDate;

    private double amount;

    private String Description;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    public Transaction(String transactionType, LocalDate transactionDate, double amount, String description) {
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.amount = amount;
        Description = description;
    }
}


