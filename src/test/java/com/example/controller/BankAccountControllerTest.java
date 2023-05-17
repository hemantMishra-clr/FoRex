package com.example.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.model.BankAccount;
import com.example.model.Customer;
import com.example.service.BankAccountService;
import com.example.service.CustomerService;

@ExtendWith(MockitoExtension.class)
class BankAccountControllerTest {

	@Mock
	private BankAccountService bankAccountService;

	@Mock
	private CustomerService customerService;

	@Mock
	private TransactionController transactionController;

	@InjectMocks
	private BankAccountController bankAccountController;

	private BankAccount bankAccount;
	private Customer customer;

	@BeforeEach
	void setUp() {
		bankAccount = new BankAccount();
		customer = new Customer();
		customer.setId(1);
		bankAccount.setCustomer(customer);
	}

	@Test
    void testPostAccount() {
        when(customerService.getCustomerById(1)).thenReturn(customer);
        doNothing().when(bankAccountService).postAccount(bankAccount);
        ResponseEntity<String> response = bankAccountController.postAccount(bankAccount, 1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Account added ...");
        verify(customerService).getCustomerById(1);
    }

	@Test
    void testGetBalance() {
        when(bankAccountService.getBalance(1)).thenReturn(100.0);
        Double balance = bankAccountController.getBalance(1);
        assertThat(balance).isEqualTo(100.0);
        verify(bankAccountService).getBalance(1);
    }


	@Test
	public void testDeleteAccount() {
		int acctID = 1;
		doNothing().when(bankAccountService).deleteAccount(acctID);
		doNothing().when(transactionController).deleteLog(acctID);

		bankAccountController.deleteAccount(acctID);

		verify(bankAccountService, times(1)).deleteAccount(acctID);
	}

	@Test
	public void testGetAccountInfo() {
		int acctID = 1;
		BankAccount bankAccount = new BankAccount();
		bankAccount.setAcctID(acctID);
		when(bankAccountService.getAccountInfo(acctID)).thenReturn(bankAccount);

		BankAccount result = bankAccountController.getAccountInfo(acctID);

		verify(bankAccountService, times(1)).getAccountInfo(acctID);
		assertEquals(acctID, result.getAcctID());
	}
}
