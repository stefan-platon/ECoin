package models;

import java.math.BigDecimal;
import java.util.Set;

import exceptions.DataValidationException;
import utils.Utils;

public class Account {

	// static collection to store possible account types
	static Set<String> accountTypeSet = Utils.newHashSet("EUR", "RON");

	protected String accountNumber;

	protected String username;

	protected BigDecimal balance;

	protected String accountType;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) throws DataValidationException {
		if (accountNumber.length() != 24) {
			throw new DataValidationException("Account number has wrong length.");
		}

		if (!accountNumber.startsWith("RO")) {
			throw new DataValidationException("Account number should start with RO.");
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
		return accountType;
	}

	public void setAccountType(String accountType) throws DataValidationException {
		if (!accountTypeSet.contains(accountType)) {
			throw new DataValidationException("Account type not supported");
		}

		this.accountType = accountType;
	}

	protected Account() {

	}

	protected Account(String accountNumber, String username, BigDecimal balance, String accountType) {

	}

}
