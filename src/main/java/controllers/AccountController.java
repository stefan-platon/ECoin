package controllers;

import java.math.BigDecimal;

import models.Account;

public class AccountController extends Account {

	AccountController() {

	}

	AccountController(String accountNumber, String username, BigDecimal balance, String accountType) {
		this.accountNumber = accountNumber;
		this.username = username;
		this.balance = balance;
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return new StringBuilder(this.accountNumber).append(" ").append(this.username).append(" ").append(this.balance)
				.append(" ").append(this.accountType).append("\n").toString();
	}

	public String transfer(AccountController accountTo, BigDecimal amount) {
		if (this.getBalance().compareTo(amount) == -1) {
			return "Entered sum is too big for this account!";
		}

		balance = balance.subtract(amount);
		accountTo.balance = accountTo.balance.add(amount);

		return "Transfer successful!";
	}

}
