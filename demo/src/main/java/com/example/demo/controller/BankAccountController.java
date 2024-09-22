package com.example.demo.controller;

import com.example.demo.dto.BankAccount;
import com.example.demo.dto.BankAccountDTO;
import com.example.demo.dto.Wallet;
import com.example.demo.exceptions.BankAccountException;
import com.example.demo.exceptions.CustomerException;
import com.example.demo.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bankaccount")
public class BankAccountController extends BaseController{

    @Autowired
    private BankAccountService bankAccountService;


    @PostMapping("/add")
    public ResponseEntity<String> addAccountMapping(@RequestParam String key, @RequestBody BankAccountDTO bankAccountDTO) throws CustomerException, BankAccountException {

        bankAccountService.addBankAccount(key, bankAccountDTO);

        return new ResponseEntity<String>("Bank Account Added Successfully",HttpStatus.CREATED);

    }


    @DeleteMapping("/delete")
    public ResponseEntity<Wallet> removeAccountMapping(@RequestParam String key, @RequestBody BankAccountDTO bankAccount) throws CustomerException,BankAccountException{

        return new ResponseEntity<>(bankAccountService.removeBankAccount(key, bankAccount),HttpStatus.OK);
    }


    @GetMapping("/details")
    public ResponseEntity<Optional<BankAccount>> getBankAccountDetailsMapping(@RequestParam String key, @RequestParam Integer accountNo) throws CustomerException,BankAccountException{

        return new ResponseEntity<Optional<BankAccount>>(bankAccountService.viewBankAccount(key, accountNo),HttpStatus.OK);

    }


    @GetMapping("/all")
    public ResponseEntity<List<BankAccount>> getAllBankAccountMapping(@RequestParam String key) throws CustomerException,BankAccountException{

        return new ResponseEntity<List<BankAccount>>(bankAccountService.viewAllBankAccounts(key), HttpStatus.FOUND);

    }


}
