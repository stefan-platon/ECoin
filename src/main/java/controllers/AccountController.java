package controllers;

import java.math.BigDecimal;

import models.Account;

public class AccountController implements Controller {

	public static String transfer(Account accountFrom, Account accountTo, BigDecimal amount) {
		if (accountFrom.getBalance().compareTo(amount) == -1) {
			return "Entered sum is too big for this account!";
		}

		accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
		accountTo.setBalance(accountTo.getBalance().add(amount));

		return "Transfer successful!";
	}

}
