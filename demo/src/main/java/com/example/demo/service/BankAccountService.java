package com.example.demo.service;


import com.example.demo.dto.BankAccount;
import com.example.demo.dto.BankAccountDTO;
import com.example.demo.dto.Wallet;
import com.example.demo.exceptions.BankAccountException;
import com.example.demo.exceptions.CustomerException;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {

    public Wallet addBankAccount(String key, BankAccountDTO bankAccountDTO) throws CustomerException,BankAccountException;

    public Wallet removeBankAccount(String key, BankAccountDTO bankAccountDTO) throws CustomerException,BankAccountException;

    public Optional<BankAccount> viewBankAccount(String key, Integer accountNo) throws CustomerException, BankAccountException;

    public List<BankAccount> viewAllBankAccounts(String key) throws CustomerException,BankAccountException;

}
