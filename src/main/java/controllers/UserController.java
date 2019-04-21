package controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

	public void saveAccountsToFile() {
		final String TEMP_FILE_PATH = System.getProperty("user.dir") + "/database/accounts_temp.txt";

		List<String> lines = new ArrayList<>();
		List<String[]> accounts = FILE_CONTROLLER.read(ACCOUNTS_FILE_PATH, " ");
		try {
			for (String[] line : accounts) {
				if (user.getUsername().equals(line[1])) {
					for (Account a : user.getAccounts()) {
						if (a.getAccountNumber().equals(line[0])) {
							lines.add(a.toString());
							break;
						}
					}
				} else {
					lines.add(String.join(" ", line) + "\n");
				}
			}
		} catch (IndexOutOfBoundsException e) {
			LOGGER.fatal("accounts file not well formatted : " + ACCOUNTS_FILE_PATH);
			System.out.println(
					"There was a problem updating the accounts from database. Please contact support or try again later.");
		}
		
		FILE_CONTROLLER.writeLines(TEMP_FILE_PATH, lines);

		// copy new file and delete temporary file
		try {
			Path tempFile = Paths.get(TEMP_FILE_PATH);
			Path accountsFile = Paths.get(ACCOUNTS_FILE_PATH);
			Files.copy(tempFile, accountsFile, StandardCopyOption.REPLACE_EXISTING);
			Files.delete(tempFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		FILE_CONTROLLER.writeObject(ACCOUNTS_FILE_PATH, account);

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
	
	public void destroyInstance() {
		user.destroyInstance();
	}

}
