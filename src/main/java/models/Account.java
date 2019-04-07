package models;

import java.math.BigDecimal;
import java.util.Set;

import collections.AccountType;
import utils.Utils;

public class Account {

	// static collection to store possible account types
	static Set<String> accountTypeSet = Utils.newHashSet("EUR", "RON");

	protected String accountNumber;

	protected String username;

	protected BigDecimal balance;

	protected AccountType accountType;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
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

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	protected Account() {
		
	}

}
