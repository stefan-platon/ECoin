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

}
