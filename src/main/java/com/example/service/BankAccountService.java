package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.BankAccount;
import com.example.model.CurrencyConversion;
import com.example.repository.BankAccountRepository;
import com.example.repository.CurrencyConversionRepo;


@Service
public class BankAccountService {
	@Autowired
	private BankAccountRepository bankAccountRepository;
	@Autowired 
	private CurrencyConversionRepo currencyTransferRepo;
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

	public void transferAmount(int acctID, int destAcctID, double d) {
		Optional<BankAccount> sourceAccountOptional = bankAccountRepository.findById(acctID);
		Optional<BankAccount> destinationAccountOptional = bankAccountRepository.findById(destAcctID);

		if (!sourceAccountOptional.isPresent() || !destinationAccountOptional.isPresent()) {
			throw new RuntimeException("Source or destination account not found");
		}

		BankAccount sourceAccount = sourceAccountOptional.get();
		BankAccount destinationAccount = destinationAccountOptional.get();

		if (sourceAccount.getBalance() < d) {
			throw new RuntimeException("Insufficient balance in the source account ");
		}
		sourceAccount.setBalance(sourceAccount.getBalance() - d);
		destinationAccount.setBalance(destinationAccount.getBalance() + d);

		bankAccountRepository.save(sourceAccount);
		bankAccountRepository.save(destinationAccount);

	}
	
	public void transferCurrency(int acctID, int destAcctID, long id) {
		Optional<BankAccount> sourceAccountOptional = bankAccountRepository.findById(acctID);
		Optional<BankAccount> destinationAccountOptional = bankAccountRepository.findById(destAcctID);
		Optional<CurrencyConversion> currencyConverted=currencyTransferRepo.findById(id);
		if (!sourceAccountOptional.isPresent() || !destinationAccountOptional.isPresent()) {
			throw new RuntimeException("Source or destination account not found");
		}

		BankAccount sourceAccount = sourceAccountOptional.get();
		BankAccount destinationAccount = destinationAccountOptional.get();

		if (sourceAccount.getBalance() < currencyConverted.get().getAmount()) {
			throw new RuntimeException("Insufficient balance in the source account ");
		}
		sourceAccount.setBalance(sourceAccount.getBalance() - currencyConverted.get().getAmount());
		destinationAccount.setBalance(destinationAccount.getBalance() + currencyConverted.get().getConvertedAmount());

		bankAccountRepository.save(sourceAccount);
		bankAccountRepository.save(destinationAccount);

	}


	public void postAccount(BankAccount account) {
		// TODO Auto-generated method stub
		bankAccountRepository.save(account);
	}
	}