package com.example.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Transaction;
import com.example.service.TransactionService;


@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/bank")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	
	// addLog
	public void addLog(Transaction transaction) {
		transaction.setTransaction_date(LocalDate.now());
		transaction.setTransaction_time(LocalTime.now());
		 transactionService.addLog(transaction);
	}

	// showLog
	@GetMapping("/account/logs/{acctID}")
	public Optional<Transaction> showLog(@PathVariable int acctID) {
//		Optional<Transaction> optional = transactionService.findById(acctID);
//		if (optional == null)
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID Given");
//
//		Transaction transaction = optional.get();
//		return ResponseEntity.status(HttpStatus.OK).body(transaction);
//		return transactionService.showLog(acctID);
		Optional<Transaction> transactions = transactionService.getTransactionsByAccountID(acctID);
		
		return transactions;
	}

	public void deleteLog(int acctID) {
		transactionService.deleteLog(acctID);
	}
}

