package com.example.demo.repository;

import java.util.List;

import com.example.demo.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	@Query("FROM Customer c WHERE c.mobileNumber=?1")
	public List<Customer> findCustomerByMobile(String mobileNumber);
	
}