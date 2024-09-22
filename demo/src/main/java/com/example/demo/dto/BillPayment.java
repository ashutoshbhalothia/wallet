package com.example.demo.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BillPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer billId;

    private Double amount;

    private String billType;

    private LocalDate billPaymentDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Wallet wallet;


    public BillPayment(Double amount, String billType, LocalDate billPaymentDate) {
        this.amount = amount;
        this.billType = billType;
        this.billPaymentDate = billPaymentDate;
    }
}

