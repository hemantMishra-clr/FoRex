package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Transaction;
import com.example.repository.TransactionRepository;


@Service
public class TransactionService {
	@Autowired
	TransactionRepository transactionRepository;

	public void addLog(Transaction transaction) {
		transactionRepository.save(transaction);
	}

//	public Optional<Transaction> showLog(int acctID) {
//		return transactionRepository.findById(acctID);
//	}

	public void deleteLog(int acctID) {
		transactionRepository.deleteById(acctID);
	}

//	public Optional<Transaction> findById(int acctID) {
//		Optional<Transaction> optional = transactionRepository.findById(acctID);
//		 return optional;
//	}

	public Optional<Transaction> getTransactionsByAccountID(int acctID) {
		return transactionRepository.findById(acctID);
	}
}
