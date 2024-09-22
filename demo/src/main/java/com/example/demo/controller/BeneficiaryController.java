package com.example.demo.controller;

import com.example.demo.dto.Beneficiary;
import com.example.demo.dto.BeneficiaryDTO;
import com.example.demo.exceptions.BeneficiaryException;
import com.example.demo.exceptions.CustomerException;
import com.example.demo.exceptions.WalletException;
import com.example.demo.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beneficiaries")
public class BeneficiaryController extends BaseController{

    @Autowired
    private BeneficiaryService beneficiaryService;


    @PostMapping("/add")
    public ResponseEntity<Beneficiary> addBeneneficiaryMapping(@RequestBody Beneficiary beneficiary, @RequestParam String key) throws BeneficiaryException, WalletException, CustomerException {

        return new ResponseEntity<Beneficiary>(beneficiaryService.addBeneficiary(beneficiary, key),HttpStatus.ACCEPTED);

    }


    @GetMapping("/view/walletId")
    public ResponseEntity<Beneficiary> getBeneneficiaryByWalletIdMapping(@RequestParam Integer walletId ,@RequestParam String key) throws BeneficiaryException, CustomerException{

        return new ResponseEntity<Beneficiary>((Beneficiary) beneficiaryService.findAllByWallet(walletId),HttpStatus.FOUND);

    }

    @GetMapping("/view/name")
    public ResponseEntity<Beneficiary> getBeneneficiaryByNameMapping(@RequestParam String name,@RequestParam String key) throws BeneficiaryException, CustomerException{

        return new ResponseEntity<Beneficiary>(beneficiaryService.viewBeneficiary(name,key),HttpStatus.FOUND);

    }

    @GetMapping("/viewall")
    public ResponseEntity<List<Beneficiary>> getAllBeneneficiaryMapping(@RequestParam String key) throws BeneficiaryException, CustomerException{

        return new ResponseEntity<List<Beneficiary>>(beneficiaryService.viewAllBeneficiary(key), HttpStatus.FOUND);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<Beneficiary> deleteBeneneficiaryMapping(@RequestBody BeneficiaryDTO beneficiary , @RequestParam String key) throws BeneficiaryException, CustomerException{

        return new ResponseEntity<Beneficiary>(beneficiaryService.deleteBeneficiary(key,beneficiary),HttpStatus.OK);

    }


}
