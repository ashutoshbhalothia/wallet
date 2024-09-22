package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TransactionDTO {

    private Integer transactionId;

    private String transactionType;

    private LocalDate transactionDate;

    private double amount;

    private String Description;

}
