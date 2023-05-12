package com.example.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
	private BankAccount account;

	public Transaction() {

	}
	
	

	public Transaction(int acctID, String transacType, String transacStatus, Double initBal, Double finalBal) {
		super();
		this.acctID = acctID;
		this.transacType = transacType;
		this.transacStatus = transacStatus;
		this.initBal = initBal;
		this.finalBal = finalBal;
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

	public LocalTime getTransaction_time() {
		return transaction_time;
	}

	public void setTransaction_time(LocalTime transaction_time) {
		this.transaction_time = transaction_time;
	}

	public BankAccount getAccount() {
		return account;
	}

	public void setAccount(BankAccount account) {
		this.account = account;
	}
	
	
	
	
}
