package controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import exceptions.DataValidationException;
import models.User;

public class UserController extends User implements Controller {

	private static UserController instance = null;

	public UserController() {
		accounts = new ArrayList<>();
	}

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

	public void addAccount(String accountNumber, BigDecimal balance, String accountType) {
		accounts.add(new AccountController(accountNumber, this.username, balance, accountType));
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
	public void createAccount(String accountNumber, BigDecimal balance, String accountType)
			throws DataValidationException {
		AccountController account = new AccountController();
		account.setAccountNumber(accountNumber);
		account.setUsername(this.username);
		account.setBalance(balance);
		account.setAccountType(accountType);

		fileController.write(ACCOUNTS_FILE_PATH, account);

		accounts.add(account);
	}

	public AccountController getAccountByAccountNumber(String accountNumber) {
		for (AccountController account : accounts) {
			if (account.getAccountNumber().equals(accountNumber)) {
				return account;
			}
		}
		return null;
	}

	public List<AccountController> getAccountsByType(String accountType) {
		List<AccountController> response = new ArrayList<>();

		for (AccountController account : accounts) {
			if (account.getAccountType().equals(accountType)) {
				response.add(account);
			}
		}

		return response;
	}

	/**
	 * Find accounts of the same type except a certain account
	 * 
	 * @param accountType
	 * @param accountNumber
	 * @return List<Account>
	 */
	public List<AccountController> getAccountsByTypeExcept(String accountType, String accountNumber) {
		List<AccountController> response = new ArrayList<>();

		for (AccountController account : accounts) {
			if (account.getAccountType().equals(accountType) && !account.getAccountNumber().equals(accountNumber)) {
				response.add(account);
			}
		}

		return response;
	}

}
