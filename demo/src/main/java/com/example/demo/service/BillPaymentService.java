package com.example.demo.service;

import com.example.demo.dto.BillPayment;
import com.example.demo.exceptions.BillPaymentException;
import com.example.demo.repository.BillPaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class BillPaymentService {

    @Autowired
    private BillPaymentRepo billPaymentRepo;

    public String addBillPayment(String targetMobile, String Name, double amount, String billType, LocalDate paymentDate, Integer walletId, String key) throws BillPaymentException {

        BigDecimal value = BigDecimal.valueOf(amount);

        var billPayment = new BillPayment(amount, billType, LocalDate.now());

        billPaymentRepo.save(billPayment);

        return "";
    }
}
