package repository;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.Account;

public class AccountRepository extends Repository {

	private static final Logger LOGGER = LogManager.getLogger(AccountRepository.class);

	public String transfer(Account accountFrom, Account accountTo, BigDecimal amount) {
		if (accountFrom.getBalance().compareTo(amount) == -1) {
			return "Entered sum is too big for this account!";
		}

		accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
		accountTo.setBalance(accountTo.getBalance().add(amount));

		return "Transfer successful!";
	}

	public String create(String accountNumber, BigDecimal balance, String accountType) {
		Account account = new Account();

		try {
			account.setAccountNumber(accountNumber);
			account.setBalance(balance);
			account.setAccountType(accountType);
			account.setUser(user);
		} catch (Exception e) {
			return e.getMessage();
		}

		SESSION.beginTransaction();
		SESSION.save(account);
		SESSION.getTransaction().commit();

		LOGGER.info("new account : " + user.getUsername());
		return "Account created succesfully!";
	}

}
