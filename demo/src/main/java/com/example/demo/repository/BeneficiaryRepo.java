package com.example.demo.repository;

import java.util.List;

import com.example.demo.dto.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiaryRepo extends JpaRepository<Beneficiary, String>{
   
   @Query(value = "FROM Beneficiary b INNER JOIN b.wallet w WHERE w.walletId=?1 AND b.beneficiaryName =?2")
   public Beneficiary findByNameWallet(Integer walletId,String beneficiaryName);
   
   @Query(value = "FROM Beneficiary b INNER JOIN b.wallet w WHERE w.walletId=?1 AND b.beneficiaryMobileNumber =?2")
   public Beneficiary findByMobileWallet(Integer walletId,String beneficiaryMobileNumber);
   
   @Query(value = "FROM Beneficiary b INNER JOIN b.wallet w WHERE w.walletId=?1")
   public List<Beneficiary> findByWallet(Integer walletId);

}