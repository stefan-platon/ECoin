package caches;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exceptions.DataValidationException;
import models.Account;
import models.User;

/**
 * Class responsible for loading and manipulating all stored accounts.
 */
public class AccountsCache extends Cache {

	private static final Logger logger = LogManager.getLogger(AccountsCache.class);

	private final String ACCOUNTS_FILE_PATH = System.getProperty("user.dir") + "/database/accounts.txt";

	private static AccountsCache instance = null;

	private List<Account> accounts;

	/**
	 * Read and store accounts in memory.
	 */
	private AccountsCache() {
		loadData();
	}
	
	protected void loadData() {
		List<String[]> fileContent = fileController.read(ACCOUNTS_FILE_PATH, " ");

		accounts = new ArrayList<>();

		// create new user with user name and password
		try {
			fileContent.forEach((line) -> {
				Account account = new Account(line[0], line[1], new BigDecimal(line[2]), line[3]);
				accounts.add(account);
			});
		} catch (IndexOutOfBoundsException e) {
			logger.fatal("accounts file not well formatted!");
			System.out.println("There was a problem reading the accounts from database.");
			System.exit(-1);
		}
	}

	/**
	 * Create or return instance of this class.
	 * 
	 * @return class instance
	 */
	public static AccountsCache getInstance() {
		if (instance == null)
			instance = new AccountsCache();

		return instance;
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
	public void saveAccount(String accountNumber, String username, BigDecimal balance, String accountType)
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

	/**
	 * Get accounts for an user
	 * 
	 * @param user
	 * @return array of accounts
	 */
	public List<Account> getAccountsforUser(User user) {
		return accounts.stream().filter(account -> account.checkUser(user)).collect(Collectors.toList());
	}

}
