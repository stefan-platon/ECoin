package repository;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import exceptions.AccountDataValidationException;
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
		} catch (AccountDataValidationException e) {
			return e.getMessage();
		}

		SESSION.beginTransaction();

		try {
			SESSION.save(account);
			SESSION.getTransaction().commit();
			user.getAccounts().add(account);
		} catch (ConstraintViolationException e) {
			return "Account number already exists!";
		}

		LOGGER.info("new account : " + user.getUsername());
		return "Account created succesfully!";
	}

	public List<Account> getForCurrentUser() {
		SESSION.beginTransaction();

		List<Account> accounts = user.getAccounts();

		SESSION.getTransaction().commit();

		return accounts;
	}

}
