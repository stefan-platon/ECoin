package models;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import exceptions.DataValidationException;

public class Account {

	// static collection to store possible account types
	static Set<String> accountTypeSet = newHashSet("EUR", "RON");

	private String accountNumber;

	private String username;

	private BigDecimal balance;

	private String accountType;

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

	public Account() {

	}

	public Account(String accountNumber, String username, BigDecimal balance, String accountType) {
		this.accountNumber = accountNumber;
		this.username = username;
		this.balance = balance;
		this.accountType = accountType;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Account other = (Account) obj;

		if (!accountNumber.equals(other.accountNumber))
			return false;

		if (accountType != other.accountType)
			return false;

		if (!balance.equals(other.balance))
			return false;

		if (!username.equals(other.username))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder(this.accountNumber).append(" ").append(this.username).append(" ").append(this.balance)
				.append(" ").append(this.accountType).append("\n").toString();
	}
	
	public boolean checkUser(User user) {
		return this.username.equals(user.getUsername());
	}

	/**
	 * Method to populate a hash set with given elements
	 * 
	 * @param objs
	 * @return
	 */
	@SafeVarargs
	public static final <T> Set<T> newHashSet(T... objs) {
		Set<T> set = new HashSet<T>();
		Collections.addAll(set, objs);
		return set;
	}

}
