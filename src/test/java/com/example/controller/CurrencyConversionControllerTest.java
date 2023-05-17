package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.model.Currency;
import com.example.service.CurrencyConversionService;

@ExtendWith(MockitoExtension.class)
public class CurrencyConversionControllerTest {

	@Mock
	private CurrencyConversionService currencyConversionService;

	@InjectMocks
	private CurrencyConversionController currencyConversionController;

	private Currency currency;

	@BeforeEach
	void setup() {
		currency = new Currency(1L, "USD", "US Dollar", 1.0);
	}

	@Test
	public void testAddCurrency() {
		when(currencyConversionService.addCurrency(any(Currency.class))).thenReturn(currency);

		ResponseEntity<Object> response = currencyConversionController.addCurrency(currency);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(currency, response.getBody());
	}

	@Test
	public void testGetAllExchangeRates() {
		Currency currency1 = new Currency(1L, "USD", "US Dollar", 1.0);
		Currency currency2 = new Currency(2L, "EUR", "Euro", 0.82);
		List<Currency> currencies = Arrays.asList(currency1, currency2);
		when(currencyConversionService.getCurrentExchangeRates()).thenReturn(currencies);

		ResponseEntity<List<Currency>> response = currencyConversionController.getAllExchangeRates();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(currencies, response.getBody());
	}

	@Test
	public void testGetCurrency() {
		when(currencyConversionService.getCurrencyRate("USD")).thenReturn(Optional.of(currency));

		ResponseEntity<Object> response = currencyConversionController.getCurrency("USD");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	public void testUpdateCurrency() {

		when(currencyConversionService.getCurrencyRate("USD")).thenReturn(Optional.of(currency));
		when(currencyConversionService.addCurrency(any(Currency.class))).thenReturn(currency);

		ResponseEntity<String> response = currencyConversionController.updateCurrency("USD", 0.85);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Currency Updated", response.getBody());
		assertEquals(0.85, currency.getExchangeRate());
	}

}
