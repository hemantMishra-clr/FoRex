

package com.example.service;

import java.util.List;
import java.util.Optional;
import java.text.DecimalFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.CurrencyConversionDto;
import com.example.model.Currency;
import com.example.model.CurrencyConversion;
import com.example.repository.CurrencyConversionRepo;
import com.example.repository.CurrencyRepository;

@Service
public class CurrencyConversionService {

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CurrencyConversionRepo currencyConversionRepository;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public Currency addCurrency(Currency currency) {
    	
    	currencyRepository.save(currency);
    	return currency;
    }
    public List<Currency> getCurrentExchangeRates() {
        List<Currency> currencyList = currencyRepository.findAll();
        return currencyList;
    }
    public Optional<Currency> getCurrencyRate(String code) {
    	
    	if(currencyRepository.findByCode(code)==null) {
    		return null;
    	}
    	Currency currency=currencyRepository.findByCode(code);
    	Optional<Currency> optional=currencyRepository.findById(currency.getId());
//    	if(currency.getCode()!=null) {
//    		return currency;
//    	}
    	return optional;
    }
     
    public CurrencyConversion getCurrencyByID(long id) {
        currencyConversionRepository.findById(id);
    	return currencyConversionRepository.findById(id).get();
    }
    


    public CurrencyConversionDto convert(String fromCurrencyCode, String toCurrencyCode, double amount) {
        Currency fromCurrency = currencyRepository.findByCode(fromCurrencyCode);
        Currency toCurrency = currencyRepository.findByCode(toCurrencyCode);
        double exchangeFee=amount*0.04;
        double orignal=amount-exchangeFee;
        double exchangeRate = toCurrency.getExchangeRate() / fromCurrency.getExchangeRate();
        double convertedAmount = orignal * exchangeRate;
        convertedAmount=Double.parseDouble(df.format(convertedAmount));
        
        return new CurrencyConversionDto(fromCurrencyCode, toCurrencyCode, amount, convertedAmount, exchangeFee);
    }
   
    public CurrencyConversionDto convertSave(String fromCurrencyCode, String toCurrencyCode, double amount) {
        Currency fromCurrency = currencyRepository.findByCode(fromCurrencyCode);
        Currency toCurrency = currencyRepository.findByCode(toCurrencyCode);
        double exchangeFee=amount*0.04;
        double orignal=amount-exchangeFee;
        double exchangeRate = toCurrency.getExchangeRate() / fromCurrency.getExchangeRate();
        double convertedAmount = orignal * exchangeRate;
        CurrencyConversion currencyConverted=new CurrencyConversion();
        currencyConverted.setFromCurrencyCode(fromCurrency);
        currencyConverted.setToCurrencyCode(toCurrency);
        currencyConverted.setAmount(amount);
        currencyConverted.setConvertedAmount(convertedAmount);
        currencyConverted.setexchangeFee(exchangeFee);
        currencyConversionRepository.save(currencyConverted);
        convertedAmount=Double.parseDouble(df.format(convertedAmount));
        return new CurrencyConversionDto(fromCurrencyCode, toCurrencyCode, amount, convertedAmount, exchangeFee);
    }

}

