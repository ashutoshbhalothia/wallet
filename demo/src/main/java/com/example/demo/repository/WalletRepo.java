package com.example.demo.repository;

import com.example.demo.dto.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepo extends JpaRepository<Wallet, Integer> {

    @Query("SELECT w FROM wallet w JOIN w.customer c WHERE c.customerId=?1")
    Wallet showCustomerWalletDetails(Integer customerId);
}
