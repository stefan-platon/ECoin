package models;

import java.math.BigDecimal;

import collections.AccountType;
import exceptions.AccountDataValidationException;

public class Account {

	protected String accountNumber;

	protected String username;

	protected BigDecimal balance;

	protected AccountType accountType;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		if (accountNumber.length() != 24) {
			throw new AccountDataValidationException("Account number has wrong length.");
		}

		if (!accountNumber.startsWith("RO")) {
			throw new AccountDataValidationException("Account number should start with RO.");
		}

		this.accountNumber = accountNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getAccountType() {
		return accountType.getType();
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public void setAccountType(String accountType) {
		if (!AccountType.isType(accountType)) {
			throw new AccountDataValidationException("Account type not supported.");
		}

		this.accountType = AccountType.valueOf(accountType);
	}

	public Account() {

	}

	@Override
	public String toString() {
		return new StringBuilder(this.accountNumber).append(" ").append(this.username).append(" ").append(this.balance)
				.append(" ").append(this.accountType).append("\n").toString();
	}

}
