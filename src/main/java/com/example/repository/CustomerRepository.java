package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
