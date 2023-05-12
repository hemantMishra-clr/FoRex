package com.example.controller;

import java.time.LocalDate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.BankAccount;
import com.example.model.Customer;
import com.example.model.Transaction;
import com.example.service.BankAccountService;
import com.example.service.CustomerService;


@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/bank")
public class BankAccountController {
	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private TransactionController transactionController;
	

	// createAccount happens upon createCustomer
//	public void createAccount(int acctID, int balance, String acctStatus) {
//		Account acct = new Account(acctID, balance, acctStatus);
//		accountService.createAccount(acct);
//	}
	

	@PostMapping("/account/add/{cid}")
	public ResponseEntity<String> postAccount(@RequestBody BankAccount account,@PathVariable("cid")int cid ) {
		
		Customer customer = customerService.getCustomerById(cid);
		account.setCustomer(customer);
		account.setStartDate(LocalDate.now());
		bankAccountService.postAccount(account);
		
		return ResponseEntity.status(HttpStatus.OK).body("Account added ...");
		
	}

	// checkBalance
	@GetMapping("/account/balance/{acctID}")
	public Double getBalance(@PathVariable int acctID) {
		return bankAccountService.getBalance(acctID);
	}

	// depositAmount
	@PutMapping("/account/deposit/{acctID}/{amount}")
	public ResponseEntity<String> depositAmount(@PathVariable int acctID, @PathVariable int amount) {
		Double initBal = getBalance(acctID);
		bankAccountService.depositAmount(acctID, amount);
		Transaction transaction = new Transaction(acctID, "Deposited", "Success", initBal, initBal + amount);
		transactionController.addLog(transaction);
		return ResponseEntity.status(HttpStatus.OK).body("Money Credited ...");
	}

	// withdrawAmount
	@PutMapping("/account/withdraw/{acctID}/{amount}")
	public ResponseEntity<String> withdrawAmount(@PathVariable int acctID, @PathVariable int amount) {
		Double initBal = getBalance(acctID);
		bankAccountService.withdrawAmount(acctID, amount);
		Transaction transaction = new Transaction(acctID, "Withdrawn", "Success", initBal, initBal - amount);
		transactionController.addLog(transaction);
		return ResponseEntity.status(HttpStatus.OK).body("Money Debited ...");
	}

	// transferAmount
	@PutMapping("/account/transfer/{acctID}/{destAcctID}/{amount}")
	public ResponseEntity<String> transferAmount(@PathVariable int acctID, @PathVariable int destAcctID, @PathVariable int amount) {
		
		Double initBalSender = getBalance(acctID);
		Double initBalReceiver = getBalance(destAcctID);
		bankAccountService.transferAmount(acctID, destAcctID, amount);
		Transaction transactionSender = new Transaction(acctID, "Transferred", "Success", initBalSender, initBalSender - amount);
		transactionController.addLog(transactionSender);
		Transaction loggerReceiver = new Transaction(destAcctID, "Received", "Success", initBalReceiver,
				initBalReceiver + amount);
		transactionController.addLog(loggerReceiver);
		return ResponseEntity.status(HttpStatus.OK).body("Amount "+amount+ "Transfered from "+acctID+" to "+destAcctID);
	}

	// deleteAccount
	@DeleteMapping("/account/{acctID}")
	public void deleteAccount(@PathVariable int acctID) {
		bankAccountService.deleteAccount(acctID);
		transactionController.deleteLog(acctID);
	}

	// getAccountInfo
	@GetMapping("/account/{acctID}")
	public BankAccount getAccountInfo(@PathVariable int acctID) {
		return bankAccountService.getAccountInfo(acctID);
	}
}