package com.example.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="transactions")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int acctID;
	private String transacType;
	private String transacStatus;
	private Double initBal;
	private Double finalBal;
	private LocalDate transaction_date;
	private LocalTime transaction_time;
//	private int senderID;
//	private int receiverID;
	
	@ManyToOne
	private BankAccount senderAccount;
	@ManyToOne
	private BankAccount receiverAccount;
	public Transaction(int acctID, String transacType, String transacStatus, Double initBal, Double finalBal,
		LocalDate transaction_date, LocalTime transaction_time, BankAccount senderAccount,BankAccount receiverAccount,
		CurrencyConversion currencyConverted) {
	super();
	this.acctID = acctID;
	this.transacType = transacType;
	this.transacStatus = transacStatus;
	this.initBal = initBal;
	this.finalBal = finalBal;
	this.transaction_date = transaction_date;
	this.transaction_time = transaction_time;
	this.senderAccount=senderAccount;
	this.receiverAccount=receiverAccount;
	this.currencyConverted = currencyConverted;
}

	@OneToOne
	private CurrencyConversion currencyConverted;

	public Transaction() {

	}
	
	

	public CurrencyConversion getCurrencyConverted() {
		return currencyConverted;
	}



	public void setCurrencyConverted(CurrencyConversion currencyConverted) {
		this.currencyConverted = currencyConverted;
	}



	public Transaction(int acctID, String transacType, String transacStatus, Double initBal, Double finalBal) {
		super();
		this.acctID = acctID;
		this.transacType = transacType;
		this.transacStatus = transacStatus;
		this.initBal = initBal;
		this.finalBal = finalBal;
	}
	public Transaction(int acctID, String transacType, String transacStatus, Double initBal, Double finalBal,CurrencyConversion currencyConverted, BankAccount senderAccount,BankAccount receiverAccount) {
		super();
		this.acctID = acctID;
		this.transacType = transacType;
		this.transacStatus = transacStatus;
		this.initBal = initBal;
		this.finalBal = finalBal;
		this.currencyConverted=currencyConverted;
		this.senderAccount=senderAccount;
		this.receiverAccount=receiverAccount;
	}


	public int getAcctID() {
		return acctID;
	}

	public void setAcctID(int acctID) {
		this.acctID = acctID;
	}

	public String getTransacType() {
		return transacType;
	}

	public void setTransacType(String transacType) {
		this.transacType = transacType;
	}

	public String getTransacStatus() {
		return transacStatus;
	}

	public void setTransacStatus(String transacStatus) {
		this.transacStatus = transacStatus;
	}

	public Double getInitBal() {
		return initBal;
	}

	public void setInitBal(Double initBal) {
		this.initBal = initBal;
	}

	public BankAccount getSenderAccount() {
		return senderAccount;
	}



	public void setSenderAccount(BankAccount senderAccount) {
		this.senderAccount = senderAccount;
	}



	public BankAccount getReceiverAccount() {
		return receiverAccount;
	}



	public void setReceiverAccount(BankAccount receiverAccount) {
		this.receiverAccount = receiverAccount;
	}



	public Double getFinalBal() {
		return finalBal;
	}

	public void setFinalBal(Double finalBal) {
		this.finalBal = finalBal;
	}

	public LocalDate getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(LocalDate transaction_date) {
		this.transaction_date = transaction_date;
	}

	@Override
	public String toString() {
		return "Transaction [acctID=" + acctID + ", transacType=" + transacType + ", transacStatus=" + transacStatus
				+ ", initBal=" + initBal + ", finalBal=" + finalBal + ", transaction_date=" + transaction_date
				+ ", transaction_time=" + transaction_time + ", senderAccount=" + senderAccount + ", receiverAccount="
				+ receiverAccount + ", currencyConverted=" + currencyConverted + "]";
	}



	public LocalTime getTransaction_time() {
		return transaction_time;
	}

	public void setTransaction_time(LocalTime transaction_time) {
		this.transaction_time = transaction_time;
	}

	
	
	
	
	
}
