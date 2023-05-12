package com.example.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class BankAccount {
	
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private int acctID;
		private double balance;
		private LocalDate startDate;

		@ManyToOne
		private Customer customer;
		
		public BankAccount(){
			
		}

		public BankAccount(int acctID, double balance, LocalDate startDate) {
			super();
			this.acctID = acctID;
			this.balance = balance;
			this.startDate = startDate;
		}

		public int getAcctID() {
			return acctID;
		}

		public void setAcctID(int acctID) {
			this.acctID = acctID;
		}

		public double getBalance() {
			return balance;
		}

		public void setBalance(double balance) {
			this.balance = balance;
		}

		public LocalDate getStartDate() {
			return startDate;
		}

		public void setStartDate(LocalDate startDate) {
			this.startDate = startDate;
		}

		public Customer getCustomer() {
			return customer;
		}

		public void setCustomer(Customer customer) {
			this.customer = customer;
		}

		
		
		
	}

