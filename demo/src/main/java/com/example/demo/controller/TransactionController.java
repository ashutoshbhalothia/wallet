package com.example.demo.controller;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.exceptions.CustomerException;
import com.example.demo.exceptions.TransactionException;
import com.example.demo.exceptions.WalletException;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/wallet")
    public ResponseEntity<List<TransactionDTO>> viewByWallet(@RequestParam String key) throws TransactionException, WalletException, CustomerException {

        var transactions = transactionService.findByWallet(key);

        List<TransactionDTO> transactionDTOS = new ArrayList<>();

        for (var t : transactions) {

            TransactionDTO transactionDTO = new TransactionDTO(t.getTransactionId(), t.getTransactionType(), t.getTransactionDate(), t.getAmount(), t.getDescription());
            transactionDTOS.add(transactionDTO);
        }
        return new ResponseEntity<List<TransactionDTO>>(transactionDTOS, HttpStatus.OK);
    }


    @GetMapping("/transactionId")
    public ResponseEntity<TransactionDTO> findById(@RequestParam String key, @RequestParam Integer transactionId) throws TransactionException, CustomerException {

        var transaction = transactionService.findByTransactionId(key, transactionId);
        var transactionDTO = new TransactionDTO(transaction.getTransactionId(), transaction.getTransactionType(), transaction.getTransactionDate(), transaction.getAmount(), transaction.getDescription());


        return new ResponseEntity<TransactionDTO>(transactionDTO, HttpStatus.CREATED);
    }


    @GetMapping("/type")
    public ResponseEntity<List<TransactionDTO>> viewAllTransacationByType(@RequestParam String key, @RequestParam String type) throws TransactionException, CustomerException {

        var transactions = transactionService.findByTransactionType(key, type);

        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        for (var t : transactions) {

            TransactionDTO transactionDTO = new TransactionDTO(t.getTransactionId(), t.getTransactionType(), t.getTransactionDate(), t.getAmount(), t.getDescription());

            transactionDTOS.add(transactionDTO);
        }
        return new ResponseEntity<List<TransactionDTO>>(transactionDTOS, HttpStatus.ACCEPTED);

    }


    @GetMapping("/between")
    public ResponseEntity<List<TransactionDTO>> viewByTwoDate(@RequestParam String key, @RequestParam("one") String one, @RequestParam("two") String two) throws TransactionException, CustomerException {

        var firstDate = LocalDate.parse(one);
        var secondDate = LocalDate.parse(two);
        var transactions = transactionService.viewTransactionBetweenDate(key, firstDate, secondDate);

        List<TransactionDTO> transactionDTOS = new ArrayList<>();

        for (var t : transactions) {

            var transactionDTO = new TransactionDTO(t.getTransactionId(), t.getTransactionType(), t.getTransactionDate(), t.getAmount(), t.getDescription());

            transactionDTOS.add(transactionDTO);
        }
        return new ResponseEntity<List<TransactionDTO>>(transactionDTOS, HttpStatus.ACCEPTED);

    }


    @GetMapping("/all")
    public ResponseEntity<List<TransactionDTO>> viewAllTransactionByAdmin() throws TransactionException {

        var transactions = transactionService.viewAllTransaction();

        List<TransactionDTO> transactionDTOS = new ArrayList<>();

        for (var t : transactions) {
            var transactionDTO = new TransactionDTO(t.getTransactionId(), t.getTransactionType(), t.getTransactionDate(), t.getAmount(), t.getDescription());

            transactionDTOS.add(transactionDTO);
        }
        return new ResponseEntity<List<TransactionDTO>>(transactionDTOS, HttpStatus.OK);
    }


}
