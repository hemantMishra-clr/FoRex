package com.example.dto;

public class CurrencyConversionDto {

	private String fromCurrencyCode;
	private String toCurrencyCode;
	private double amount;
	private double convertedAmount;
	private double exchangeFee;

	public String getFromCurrencyCode() {
		return fromCurrencyCode;
	}

	public void setFromCurrencyCode(String fromCurrencyCode) {
		this.fromCurrencyCode = fromCurrencyCode;
	}

	public String getToCurrencyCode() {
		return toCurrencyCode;
	}

	public void setToCurrencyCode(String toCurrencyCode) {
		this.toCurrencyCode = toCurrencyCode;
	}

	public CurrencyConversionDto(String fromCurrencyCode, String toCurrencyCode, double amount, double convertedAmount,
			double exchangeFee) {
		super();
		this.fromCurrencyCode = fromCurrencyCode;
		this.toCurrencyCode = toCurrencyCode;
		this.amount = amount;
		this.convertedAmount = convertedAmount;
		this.exchangeFee = exchangeFee;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getConvertedAmount() {
		return convertedAmount;
	}

	public void setConvertedAmount(double convertedAmount) {
		this.convertedAmount = convertedAmount;
	}

	public double getexchangeFee() {
		return exchangeFee;
	}

	public void setexchangeFee(double exchangeFee) {
		this.exchangeFee = exchangeFee;
	}

	// constructors, getters, and setters omitted for brevity

}
