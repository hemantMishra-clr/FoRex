package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CurrencyConversionDto;
import com.example.model.Currency;
import com.example.repository.CurrencyRepository;
import com.example.service.CurrencyConversionService;



@RestController
@RequestMapping("/currency")
public class CurrencyConversionController {

    @Autowired
    private CurrencyConversionService currencyConversionService;
    //Admin Part to Add Currencies
    @Autowired
    private CurrencyRepository currencyRepository;
    
    @PostMapping("/add")
    public ResponseEntity<Object> addCurrency(@RequestBody Currency currency){

    	
    	currencyConversionService.addCurrency(currency);
    	return ResponseEntity.status(HttpStatus.OK).body(currency);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Currency>> getAllExchangeRates() {
        List<Currency> exchangeRateDtos = currencyConversionService.getCurrentExchangeRates();
        return ResponseEntity.ok(exchangeRateDtos);
    }
    @GetMapping("/{code}")
	public ResponseEntity<Object> getCurrency(@PathVariable("code") String code) {
		Optional<Currency> currency =currencyConversionService.getCurrencyRate(code);
		if(!currency.isPresent())
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Code") ;
	return ResponseEntity.ok(currency);	
	}
    @PutMapping("/update")
    public ResponseEntity<String> updateCurrency(@RequestParam String code,@RequestParam double rate){
    	
    	Optional<Currency> optional=currencyConversionService.getCurrencyRate(code);
    	
    	if(optional==null) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Code") ;
    	}
    	Currency currency=optional.get();
    	
    	if(rate!=0.0)
    		currency.setExchangeRate(rate);
    	currencyConversionService.addCurrency(currency);
    	return ResponseEntity.status(HttpStatus.OK).body("Currency Updated");
    }
    //View Rate
    @GetMapping("/convert")
    public CurrencyConversionDto viewConvertCurrency(
        @RequestParam String fromCurrency,
        @RequestParam String toCurrency,
        @RequestParam double amount
    ) {
        CurrencyConversionDto result = currencyConversionService.convert(fromCurrency, toCurrency, amount);
        return result;
    }
    
    //Add Rate
    @GetMapping("/convert/view")
    public CurrencyConversionDto convertCurrency(
        @RequestParam String fromCurrency,
        @RequestParam String toCurrency,
        @RequestParam double amount
    ) {
        CurrencyConversionDto result = currencyConversionService.convertSave(fromCurrency, toCurrency, amount);
        return result;
    }


}

