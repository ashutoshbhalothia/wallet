package com.example.demo.service;

import com.example.demo.dto.Transaction;
import com.example.demo.dto.Wallet;
import com.example.demo.exceptions.CustomerException;
import com.example.demo.exceptions.TransactionException;
import com.example.demo.exceptions.WalletException;
import com.example.demo.repository.CurrentSessionRepo;
import com.example.demo.repository.TransactionRepo;
import com.example.demo.repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private CurrentSessionRepo currentSessionRepo;

    @Autowired
    private TransactionRepo transactionRepository;

    @Autowired
    private WalletRepo walletRepository;

    public Transaction addTransaction(Transaction tran) throws TransactionException, WalletException {
        Optional<Wallet> wallet=   walletRepository.findById(tran.getWallet().getWalletId());
        if(!wallet.isPresent())throw new WalletException("Wallet id worng.");
        if(transactionRepository.save(tran) != null)return tran;
        throw new TransactionException("Data is null");
    }

    public List<Transaction> findByWallet(String key) throws TransactionException, WalletException, CustomerException {

        var currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Wallet wallet = walletRepository.showCustomerWalletDetails(currUserSession.getUserId());

        Optional<Wallet> optional = walletRepository.findById(wallet.getWalletId());
        if(!optional.isPresent()){
            throw new WalletException("Invalid walletId");
        }

        List<Transaction> transactions = transactionRepository.findByWallet(wallet.getWalletId());
        if(transactions.isEmpty()){
            throw new TransactionException("No Transactions to Show");
        }
        return transactions;
    }

    public Transaction findByTransactionId(String key, Integer transactionId) throws TransactionException, CustomerException {

        var currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Optional<Transaction> transaction = transactionRepository.findById(transactionId);

        if(!transaction.isPresent()){
            throw new TransactionException("Invalid transactionId");
        }
        return transaction.get();

    }

    public List<Transaction> findByTransactionType(String key, String transactionType) throws TransactionException, CustomerException {

        var currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        List<Transaction> transactions = transactionRepository.findByTransactionType(transactionType);
        if(transactions.isEmpty()){
            throw new TransactionException("No Transactions to Show");
        }
        return transactions;
    }

    public List<Transaction> viewTransactionBetweenDate(String key, LocalDate startDate, LocalDate endDate) throws TransactionException, CustomerException {

        var currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        LocalDate localDate = LocalDate.now();
        if(startDate.isAfter(localDate)){
            throw new TransactionException("Invalid Start Date [ Future Date ]");
        }
        if(endDate.isAfter(localDate)){
            throw new TransactionException("Invalid End Date [ Future Date ]");
        }
        if(startDate.isAfter(endDate)) {
            throw new TransactionException("Invalid Start Date ");
        }

        List<Transaction> transactions= transactionRepository.findByTransactionDateBetween(startDate, endDate);
        if(transactions.isEmpty()){
            throw new TransactionException("No Transactions to Show");
        }
        return transactions;
    }

    public List<Transaction> viewAllTransaction() throws TransactionException {

        List<Transaction> transactions = transactionRepository.findAll();

        if(transactions.isEmpty()){
            throw new TransactionException("No Transactions to Show");
        }
        return transactions;
    }


}
