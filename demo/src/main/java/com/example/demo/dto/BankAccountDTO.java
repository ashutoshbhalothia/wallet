package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
public class BankAccountDTO {

    @NotNull
    private Integer accountNo;

    @NotNull
    private String IFSCCode;

    @NotNull
    private String bankName;

    @NotNull
    private Double balance;

}
