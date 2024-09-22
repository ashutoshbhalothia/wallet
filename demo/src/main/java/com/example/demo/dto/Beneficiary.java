package com.example.demo.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiary {

    @Id
    private String beneficiaryMobileNumber;

    private String beneficiaryName;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "walletId" ,referencedColumnName = "walletId")
    private Wallet wallet;

    public Beneficiary(String beneficiaryMobileNumber, String beneficiaryName) {
        this.beneficiaryMobileNumber = beneficiaryMobileNumber;
        this.beneficiaryName = beneficiaryName;
    }
}
