package com.example.demo.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankAccount {

    @Id
    @NotNull
    private Integer accountNo;

    @NotNull
    private String IFSCCode;

    @NotNull
    private String bankName;

    @NotNull
    private Double balance;


    @ManyToOne(cascade= CascadeType.PERSIST)
    private Wallet wallet;


    public BankAccount(@NotNull Integer accountNo, @NotNull String iFSCCode,
                       @NotNull  String bankName,
                       @NotNull Double balance) {
        super();
        this.accountNo = accountNo;
        IFSCCode = iFSCCode;
        this.bankName = bankName;
        this.balance = balance;
    }

}