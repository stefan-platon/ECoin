package controllers;

import java.math.BigDecimal;

import exceptions.DataValidationException;
import models.Account;
import models.User;

public class UserController extends User implements Controller {

	private static UserController instance = null;

	/**
	 * Create or return instance of this class.
	 * 
	 * @return class instance
	 */
	public static UserController getInstance() {
		if (instance == null)
			instance = new UserController();

		return instance;
	}

	public void addAccount(String accountNumber, String username, BigDecimal balance, String accountType) {
		accounts.add(new Account(accountNumber, username, balance, accountType));
	}

	/**
	 * Create account for an user
	 * 
	 * @param accountNumber
	 * @param username
	 * @param balance
	 * @param accountType
	 * @throws DataValidationException
	 */
	public void createAccount(String accountNumber, String username, BigDecimal balance, String accountType)
			throws DataValidationException {
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setUsername(username);
		account.setBalance(balance);
		account.setAccountType(accountType);

		// save to file
		fileController.write(ACCOUNTS_FILE_PATH, account);

		// add to collection
		accounts.add(account);
	}

}
