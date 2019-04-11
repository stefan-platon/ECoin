package controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import collections.AccountType;
import exceptions.AccountDataValidationException;
import models.Account;
import models.User;

public class UserController implements Controller {
	
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);

	private User user;

	public UserController() {
		user = User.getInstance();
	}

	public void addAccount(String accountNumber, BigDecimal balance, String accountType) {
		Account account = new Account();

		try {
			account.setAccountNumber(accountNumber);
			account.setUsername(user.getUsername());
			account.setBalance(balance);
			account.setAccountType(accountType);
		} catch (AccountDataValidationException e) {
			LOGGER.error("could not add account to user : " + user.getUsername());
		}

		user.getAccounts().add(account);
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
	public void createAccount(String accountNumber, BigDecimal balance, AccountType accountType) {
		Account account = new Account();

		account.setAccountNumber(accountNumber);
		account.setUsername(user.getUsername());
		account.setBalance(balance);
		account.setAccountType(accountType);

		FILE_CONTROLLER.write(ACCOUNTS_FILE_PATH, account);

		user.getAccounts().add(account);
	}

	public Account getAccountByAccountNumber(String accountNumber) {
		for (Account account : user.getAccounts()) {
			if (account.getAccountNumber().equals(accountNumber)) {
				return account;
			}
		}
		return null;
	}

	public List<Account> getAccountsByType(AccountType accountType) {
		List<Account> response = new ArrayList<>();

		for (Account account : user.getAccounts()) {
			if (account.getAccountType().equals(accountType.getType())) {
				response.add(account);
			}
		}

		return response;
	}

	/**
	 * Find accounts of the same type except certain accounts
	 * 
	 * @param accountType
	 * @param accountNumber
	 * @return List<AccountController>
	 */
	public List<Account> getAccountsByTypeExcept(String accountType, String accountNumber) {
		List<Account> response = new ArrayList<>();

		for (Account account : user.getAccounts()) {
			if (account.getAccountType().equals(accountType) && !account.getAccountNumber().equals(accountNumber)) {
				response.add(account);
			}
		}

		return response;
	}

	public String getUsername() {
		return user.getUsername();
	}

	public void setUsername(String username) {
		user.setUsername(username);
	}

	public String getPassword() {
		return user.getPassword();
	}

	public void setPassword(String password) {
		user.setPassword(password);
	}

	public List<Account> getAccounts() {
		return user.getAccounts();
	}

}
