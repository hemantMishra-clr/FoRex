package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.BankAccount;


public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

}
