package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.dto.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer>{

   List<Transaction> findByTransactionType(String transactionType);
   
   @Query(value = "FROM Transaction t INNER JOIN t.wallet w WHERE w.walletId=?1")
   List<Transaction> findByWallet(Integer walletId);
   
   List<Transaction> findByTransactionDate(LocalDate transactionDate);
   
   List<Transaction> findByTransactionDateBetween(LocalDate startSate, LocalDate endDate);

}