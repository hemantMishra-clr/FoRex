package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.CurrencyConversion;

@Repository
public interface CurrencyConversionRepo extends JpaRepository<CurrencyConversion, Long> {

//    CurrencyConversion findByCode(String code);

}
