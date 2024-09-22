package com.example.demo.service.impl;

import com.example.demo.dto.BankAccount;
import com.example.demo.dto.BankAccountDTO;
import com.example.demo.dto.CurrentUserSession;
import com.example.demo.dto.Wallet;
import com.example.demo.exceptions.BankAccountException;
import com.example.demo.exceptions.CustomerException;
import com.example.demo.repository.BankAccountRepo;
import com.example.demo.repository.CurrentSessionRepo;
import com.example.demo.repository.WalletRepo;
import com.example.demo.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepo bankAccountRepo;

    @Autowired
    private CurrentSessionRepo currentSessionRepo;

    @Autowired
    private WalletRepo walletRepo;



    @Override
    public Wallet addBankAccount(String key, BankAccountDTO bankAccountDTO) throws CustomerException,BankAccountException{

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Optional<BankAccount> optional = bankAccountRepo.findById(bankAccountDTO.getAccountNo());
        if(optional.isEmpty()){

            Wallet wallet =  walletRepo.showCustomerWalletDetails(currUserSession.getUserId());

            BankAccount createBankAccount = new BankAccount(bankAccountDTO.getAccountNo(), bankAccountDTO.getIFSCCode(), bankAccountDTO.getBankName(), bankAccountDTO.getBalance());
            createBankAccount.setWallet(wallet);

            bankAccountRepo.save(createBankAccount);

            return wallet;
        }
        throw new BankAccountException("Bank Account already exist With Given AccountNumber... Try Different");


    }


    @Override
    public Wallet removeBankAccount(String key, BankAccountDTO bankAccountDTO) throws CustomerException,BankAccountException{

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Optional<BankAccount> optional = bankAccountRepo.findById(bankAccountDTO.getAccountNo());
        if(optional.isPresent()) {

            bankAccountRepo.delete(optional.get());
            Wallet wallet = optional.get().getWallet();

            return wallet;

        }
        throw new BankAccountException("No Bank Account exist");

    }


    @Override
    public Optional<BankAccount> viewBankAccount(String key, Integer accountNo) throws CustomerException,BankAccountException{

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession == null) {
            throw new CustomerException("No Customer LoggedIn");
        }


        Optional<BankAccount> bankAccount = bankAccountRepo.findById(accountNo);
        if(bankAccount == null) {
            throw new BankAccountException("No Bank Account exist");
        }
        return bankAccount;
    }


    @Override
    public List<BankAccount> viewAllBankAccounts(String key) throws CustomerException,BankAccountException{

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if(currUserSession == null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        List<BankAccount> bankAccounts = bankAccountRepo.findAllByWallet(walletRepo.showCustomerWalletDetails(currUserSession.getUserId()).getWalletId());
        if(bankAccounts == null) {
            throw new BankAccountException("No Bank Account exist");
        }
        return bankAccounts;
    }

}
