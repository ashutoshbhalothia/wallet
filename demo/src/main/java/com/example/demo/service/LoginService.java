package com.example.demo.service;

import com.example.demo.dto.CurrentUserSession;
import com.example.demo.dto.UserLogin;
import com.example.demo.exceptions.LoginException;
import com.example.demo.repository.CurrentSessionRepo;
import com.example.demo.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CurrentSessionRepo currentSessionRepo;

    public String login(UserLogin userLogin) throws LoginException {

        var customer= customerRepo.findCustomerByMobile(userLogin.getMobileNumber());

        var existingCustomer = customer.get(0);

        if(existingCustomer == null) {
            throw new LoginException("Invalid MobileNumber!");
        }



        Optional<CurrentUserSession> optional =  currentSessionRepo.findByUserId(existingCustomer.getCustomerId());

        if(optional.isPresent()) {

            throw new LoginException("User Already Exists in the System.");

        }

        if(existingCustomer.getPassword().equals(userLogin.getPassword())) {

            String key= "DFGHI9";

            CurrentUserSession currentUserSession = new CurrentUserSession(existingCustomer.getCustomerId(),key, LocalDateTime.now());

            currentSessionRepo.save(currentUserSession);

            return currentUserSession.toString();
        }

        throw new LoginException("Wrong password");

    }

    public String logout(String key) throws LoginException {

        var currentUserSession = currentSessionRepo.findByUuid(key);

        if(currentUserSession == null) {
            throw new LoginException("Invalid Unique userId (Session Key).");

        }

        currentSessionRepo.delete(currentUserSession);

        return "Logged Out Successfully!";
    }

}
