package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.BankAccount;
import com.example.repository.BankAccountRepository;


@Service
public class BankAccountService {
	@Autowired
	private BankAccountRepository bankAccountRepository;

	public void createAccount(BankAccount acct) {
		bankAccountRepository.save(acct);
	}

	public BankAccount getAccountInfo(int acctID) {
		return bankAccountRepository.findById(acctID).orElse(null);
	}

	public void deleteAccount(int acctID) {
		bankAccountRepository.deleteById(acctID);
	}

	public Double getBalance(int acctID) {
		Optional<BankAccount> account = bankAccountRepository.findById(acctID);
		if (account.isPresent()) {
			return account.get().getBalance();
		} else {
			return null;
		}

	}

	public void depositAmount(int acctID, int amount) {
//		accountRepository.saveBalanceByAcctID(acctID, amount);
		Optional<BankAccount> account =bankAccountRepository.findById(acctID);
		if (account.isPresent()) {
			BankAccount acct = account.get();
			acct.setBalance(acct.getBalance() + amount);
			bankAccountRepository.save(acct);
		}
	}

	public void withdrawAmount(int acctID, int amount) {
//		accountRepository.withdrawAmountByAcctID(acctID, amount);
		Optional<BankAccount> account = bankAccountRepository.findById(acctID);
		if (account.isPresent()) {
			BankAccount acct = account.get();
			if (acct.getBalance() >= amount) {
				acct.setBalance(acct.getBalance() - amount);
				bankAccountRepository.save(acct);
			} else {
				//
			}
		}
	}

	public void transferAmount(int acctID, int destAcctID, int amount) {
		Optional<BankAccount> sourceAccountOptional = bankAccountRepository.findById(acctID);
		Optional<BankAccount> destinationAccountOptional = bankAccountRepository.findById(destAcctID);

		if (!sourceAccountOptional.isPresent() || !destinationAccountOptional.isPresent()) {
			throw new RuntimeException("Source or destination account not found");
		}

		BankAccount sourceAccount = sourceAccountOptional.get();
		BankAccount destinationAccount = destinationAccountOptional.get();

		if (sourceAccount.getBalance() < amount) {
			throw new RuntimeException("Insufficient balance in the source account ");
		}
		sourceAccount.setBalance(sourceAccount.getBalance() - amount);
		destinationAccount.setBalance(destinationAccount.getBalance() + amount);

		bankAccountRepository.save(sourceAccount);
		bankAccountRepository.save(destinationAccount);

	}

	public void postAccount(BankAccount account) {
		// TODO Auto-generated method stub
		bankAccountRepository.save(account);
	}
	}