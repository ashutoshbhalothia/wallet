package com.example.demo.service;

import com.example.demo.dto.Beneficiary;
import com.example.demo.dto.BeneficiaryDTO;
import com.example.demo.dto.Customer;
import com.example.demo.dto.Wallet;
import com.example.demo.exceptions.BeneficiaryException;
import com.example.demo.exceptions.CustomerException;
import com.example.demo.exceptions.WalletException;
import com.example.demo.repository.BeneficiaryRepo;
import com.example.demo.repository.CurrentSessionRepo;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficiaryService {

    @Autowired
    private BeneficiaryRepo beneficiaryRepo;

    @Autowired
    private CurrentSessionRepo currentSessionRepo;

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private CustomerRepo customerRepo;

    public Beneficiary addBeneficiary(Beneficiary beneficiary, String key) throws BeneficiaryException, CustomerException, WalletException {

        var currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Optional<Customer> customer = customerRepo.findById(currUserSession.getUserId());
        Optional<Wallet> wallet = walletRepo.findById(walletRepo.showCustomerWalletDetails(currUserSession.getUserId()).getWalletId());

        if (!customer.isPresent()) {
            throw new CustomerException("Beneficiary is not Registered to the Application.");
        }

        if (!wallet.isPresent()){
            throw new WalletException("Invalid User.");
        }

        Optional<Beneficiary> optional=beneficiaryRepo.findById(beneficiary.getBeneficiaryMobileNumber());

        if(optional.isEmpty()) {
            return beneficiaryRepo.save(beneficiary);
        }
        throw new BeneficiaryException("Duplicate Details [ Beneficiary Already Exist ]");


    }

    public List<Beneficiary> findAllByWallet(Integer walletId) throws BeneficiaryException {

        List<Beneficiary> beneficiaries = beneficiaryRepo.findByWallet(walletId);

        if(beneficiaries.isEmpty()) {
            throw new BeneficiaryException("No Beneficiary Exist");
        }
        return beneficiaries;

    }

    public Beneficiary viewBeneficiary(String beneficiaryName, String key) throws BeneficiaryException, CustomerException {

        var currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Wallet wallet = walletRepo.showCustomerWalletDetails(currUserSession.getUserId());

        Beneficiary beneficiaries = beneficiaryRepo.findByNameWallet(wallet.getWalletId(),beneficiaryName);

        if(beneficiaries == null) {
            throw new BeneficiaryException("No Beneficiary Exist");
        }
        return beneficiaries;

    }

    public List<Beneficiary> viewAllBeneficiary(String key) throws BeneficiaryException, CustomerException {

        var currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession == null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Wallet wallet = walletRepo.showCustomerWalletDetails(currUserSession.getUserId());

        List<Beneficiary> beneficiaries = beneficiaryRepo.findByWallet(wallet.getWalletId());

        if(beneficiaries.isEmpty()) {
            throw new BeneficiaryException("No Beneficiary Exist");
        }
        return beneficiaries;
    }

    public Beneficiary deleteBeneficiary(String key, BeneficiaryDTO beneficiaryDTO) throws BeneficiaryException, CustomerException {

        var currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession == null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Wallet wallet = walletRepo.showCustomerWalletDetails(currUserSession.getUserId());

        Beneficiary beneficiaries = beneficiaryRepo.findByMobileWallet(wallet.getWalletId(),beneficiaryDTO.getBeneficiaryMobileNumber());

        if(beneficiaries == null) {
            throw new BeneficiaryException("No Beneficiary found");
        }

        beneficiaryRepo.delete(beneficiaries);

        return beneficiaries;
    }

}
