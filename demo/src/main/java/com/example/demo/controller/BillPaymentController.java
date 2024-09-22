package com.example.demo.controller;

import com.example.demo.exceptions.BillPaymentException;
import com.example.demo.exceptions.CustomerException;
import com.example.demo.exceptions.TransactionException;
import com.example.demo.exceptions.WalletException;
import com.example.demo.service.BillPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class BillPaymentController extends BaseController{

    @Autowired
    private BillPaymentService billPayService;

    @PostMapping("/addBillPayment")
    public ResponseEntity<String> addBillPayment(@RequestParam("targetMobile") String targetMobile, @RequestParam("Name") String Name, @RequestParam("amount") double amount, @RequestParam("BillType") String BillType, @RequestParam("key") String key) throws BillPaymentException, WalletException, CustomerException, TransactionException {

        LocalDate date=LocalDate.now();
        String output = billPayService.addBillPayment(targetMobile, Name, amount, BillType,date , 0, key);

        return new ResponseEntity<String>(output, HttpStatus.OK);
    }
}
