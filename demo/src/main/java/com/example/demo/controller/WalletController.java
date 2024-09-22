package com.example.demo.controller;

import com.example.demo.dto.Customer;
import com.example.demo.exceptions.BankAccountException;
import com.example.demo.exceptions.CustomerException;
import com.example.demo.exceptions.TransactionException;
import com.example.demo.exceptions.WalletException;
import com.example.demo.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/mywallet")
public class WalletController extends BaseController{

    @Autowired
    public WalletService walletService;

    @PostMapping("/createaccount")
    public ResponseEntity<Customer> createAccount(@RequestBody Customer customer) throws CustomerException {

        return new ResponseEntity<Customer>(walletService.createCustomerAccount(customer), HttpStatus.CREATED);
    }


    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> showBalance(@RequestParam String key, @RequestParam String mobileNumber) throws CustomerException{

        return new ResponseEntity<BigDecimal>(walletService.showBalance(mobileNumber, key), HttpStatus.ACCEPTED);
    }


    @PostMapping("/updateaccount")
    public ResponseEntity<Customer> updateCustomerDetails(@RequestBody Customer customer, @RequestParam String key) throws CustomerException{

        return new ResponseEntity<Customer>(walletService.updateAccount(customer,key), HttpStatus.ACCEPTED);
    }

    @PostMapping("/deposit/wallet")
    public ResponseEntity<String> depositToWallet(@RequestParam Integer accountNo, @RequestParam BigDecimal amount, @RequestParam String key) throws BankAccountException, CustomerException, TransactionException, WalletException {

        return new ResponseEntity<String>(walletService.depositAmount(amount, accountNo,  key), HttpStatus.OK);
    }

    @PostMapping("/transfer/mobile")
    public ResponseEntity<String> fundTransfer(@RequestParam String mobile, @RequestParam String name, @RequestParam BigDecimal amount, @RequestParam String key) throws WalletException, CustomerException, TransactionException{

        return new ResponseEntity<String>(walletService.fundTransfer(mobile, name, amount, key), HttpStatus.OK);
    }


}
