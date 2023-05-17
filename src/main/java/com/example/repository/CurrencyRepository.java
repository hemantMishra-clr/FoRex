package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Currency;



@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findByCode(String code);

}