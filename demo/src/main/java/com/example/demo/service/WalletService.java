package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.exceptions.BankAccountException;
import com.example.demo.exceptions.CustomerException;
import com.example.demo.exceptions.TransactionException;
import com.example.demo.exceptions.WalletException;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CurrentSessionRepo currentSessionRepo;

    @Autowired
    private BeneficiaryRepo beneficiaryRepo;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankAccountRepo bankAccountRepo;

    public Customer createCustomerAccount(Customer customer) throws CustomerException {

        var customers = customerRepo.findCustomerByMobile(customer.getMobileNumber());

        if(customers.isEmpty()) {

            Wallet wallet = new Wallet();
            wallet.setBalance(BigDecimal.valueOf(0));

            wallet.setCustomer(customer);
            walletRepo.save(wallet);

            return customerRepo.save(customer);
        }
        throw new CustomerException("Duplicate Mobile Number [ Already Registered with different customer ] ");

    }


    public BigDecimal showBalance(String mobile, String key) throws CustomerException {

        var currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Integer userId = currUserSession.getUserId();
        var wallet = walletRepo.showCustomerWalletDetails(userId);

        return wallet.getBalance();

    }

    public String fundTransfer( String name, String targetMobileNumber, BigDecimal amount, String key) throws WalletException, CustomerException, TransactionException {

        var currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }


        Integer userId = currUserSession.getUserId();
        var wallet = walletRepo.showCustomerWalletDetails(userId);

        var beneficiary = new Beneficiary(targetMobileNumber, name);

        var beneficiaries = beneficiaryRepo.findByWallet(wallet.getWalletId());

        if(!beneficiaries.contains(beneficiary)) beneficiaryRepo.save(beneficiary);


        var customers =  customerRepo.findCustomerByMobile(targetMobileNumber);

        if(customers.isEmpty()) {
            throw new CustomerException("Customer with mobile number "+ targetMobileNumber +" does not exist");
        }

        var targetWallet = walletRepo.showCustomerWalletDetails(customers.get(0).getCustomerId());

        if(wallet.getBalance().compareTo(amount)<0) throw new WalletException("Add more amount in wallet for transaction");

        targetWallet.setBalance(targetWallet.getBalance().add(amount));
        walletRepo.save(targetWallet);

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepo.save(wallet);


        var transaction = new Transaction("Bank transfer", LocalDate.now(),  amount.doubleValue(),amount +" transferred to "+ targetMobileNumber);
        transaction.setWallet(wallet);

        transactionService.addTransaction(transaction);

        return "Fund Transferred successfully";
    }

    public String depositAmount(BigDecimal amount, Integer accountNo, String key) throws BankAccountException, CustomerException, WalletException, TransactionException {

        var currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Integer userId = currUserSession.getUserId();
        var wallet = walletRepo.showCustomerWalletDetails(userId);

        var accounts = bankAccountRepo.findAllByWallet(wallet.getWalletId());

        if(accounts.isEmpty()) {
            throw new BankAccountException("Add bank account for transaction");
        }

        BankAccount bankAccount = null;

        for(BankAccount b : accounts) {
            if((b.getAccountNo().toString()).equals(accountNo.toString())) {
                bankAccount = b;
                break;
            }

        }

        if(bankAccount==null){
            throw new BankAccountException("Bank account number does not match the data of saved accounts");
        }

        if(bankAccount.getBalance() < amount.doubleValue()) {
            throw new BankAccountException("Insufficient balance in account");
        }

        bankAccount.setBalance(bankAccount.getBalance() - amount.doubleValue());
        wallet.setBalance(wallet.getBalance().add(amount));

        bankAccountRepo.save(bankAccount);

        double value = amount.doubleValue();
        var transaction = new Transaction("Bank transfer", LocalDate.now(), value,"transferred from bank "+bankAccount.getBankName()+" to wallet");
        transaction.setWallet(wallet);


        transactionService.addTransaction(transaction);


        return "Your bank account no "+ accountNo +" debited for "+ amount +" Rs" ;

    }

    public Customer updateAccount(Customer customer, String key) throws CustomerException {

        var currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Optional<Customer> customer1 = customerRepo.findById(currUserSession.getUserId());


        if(!customer1.isPresent()) {
            throw new CustomerException("Customer with given CustomerId not exist");
        }

        customer.setCustomerId(currUserSession.getUserId());

        return customerRepo.save(customer);
    }

    public String addMoney(Wallet wallet, Integer accountNo, BigDecimal amount, String key) throws WalletException, BankAccountException, CustomerException, TransactionException {

        var currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Integer userId = currUserSession.getUserId();
        wallet = walletRepo.showCustomerWalletDetails(userId);

        var accounts = bankAccountRepo.findAllByWallet(wallet.getWalletId());

        if(accounts.isEmpty()) {
            throw new BankAccountException("Add bank account for transaction");
        }

        BankAccount bankAccount = null;

        for(BankAccount b: accounts) {
            if((b.getAccountNo().toString()).equals(accountNo.toString())) {
                bankAccount = b;
                break;
            }

        }

        if(bankAccount == null){
            throw new BankAccountException("Bank account number does not match the data of saved accounts");
        }

        if(bankAccount.getBalance() < amount.doubleValue()) {
            throw new BankAccountException("Insufficient balance in account");
        }

        bankAccount.setBalance(bankAccount.getBalance() - amount.doubleValue());
        wallet.setBalance(wallet.getBalance().add(amount));

        bankAccountRepo.save(bankAccount);

        double value = amount.doubleValue();
        Transaction transaction = new Transaction("Bank transfer", LocalDate.now(), value,"transferred from bank "+bankAccount.getBankName()+" to wallet");
        transaction.setWallet(wallet);


        transactionService.addTransaction(transaction);


        return "Your bank account no "+ accountNo +" debited for "+ amount +" Rs" ;

    }

}
