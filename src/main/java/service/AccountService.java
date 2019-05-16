package service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import exceptions.AccountDataValidationException;
import exceptions.HTTPClientCustomException;
import model.Account;
import model.User;
import repository.AccountRepository;

public class AccountService {

	private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

	@Autowired
	private AccountRepository ACCOUNT_REPOSITORY;

	public Account create(String accountNumber, BigDecimal balance, String accountType, long userId) {
		User user = new UserService().getById(userId);

		if (user == null) {
			throw new HTTPClientCustomException("Could not find user!");
		}

		Account account = new Account();

		try {
			account.setAccountNumber(accountNumber);
			account.setBalance(balance);
			account.setAccountType(accountType);
			account.setUserObj(user);
		} catch (AccountDataValidationException e) {
			throw new HTTPClientCustomException(e.getMessage());
		} catch (ConstraintViolationException e) {
			throw new HTTPClientCustomException("Account number already exists!");
		} catch (Exception e) {
			throw new HTTPClientCustomException(e.getMessage());
		}

		account = ACCOUNT_REPOSITORY.save(account);
		LOGGER.info("new account : " + accountNumber);
		user.getAccounts().add(account);

		return account;
	}

	public void transfer(long accountFromId, long accountToId, BigDecimal amount, String details) {
		Account accountFrom = ACCOUNT_REPOSITORY.findById(accountFromId);
		if (accountFrom == null) {
			throw new HTTPClientCustomException("Inexistent source account!");
		}

		Account accountTo = ACCOUNT_REPOSITORY.findById(accountToId);
		if (accountTo == null) {
			throw new HTTPClientCustomException("Inexistent destination account!");
		}

		if (accountFrom.getBalance().compareTo(amount) == -1) {
			throw new HTTPClientCustomException("Entered sum is too big for this account!");
		}

		accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
		accountTo.setBalance(accountTo.getBalance().add(amount));
		ACCOUNT_REPOSITORY.save(accountFrom);
		ACCOUNT_REPOSITORY.save(accountTo);

		StringBuilder notificationDetails = new StringBuilder();
		notificationDetails.append(String.format("From : %s; ", accountFrom.getAccountNumber()));
		notificationDetails.append(String.format("To : %s; ", accountTo.getAccountNumber()));
		notificationDetails.append(String.format("Amount : %s; ", amount.toString()));
		notificationDetails.append(String.format("Details : %s; ", details == null ? "" : details));

		new NotificationService().create(notificationDetails.toString(), accountFrom.getUserObj());

		// source account
		new TransactionService().create(accountFrom, accountFrom.getAccountNumber(), amount, details, "outgoing");

		// destination account
		new TransactionService().create(accountTo, accountTo.getAccountNumber(), amount, null, "incoming");
	}

	public List<Account> findByUser(long userId) {
		return ACCOUNT_REPOSITORY.findByUser(userId);
	}

	public Account findFirstByUserAndAccountNumber(long userId, String accountNumber) {
		return ACCOUNT_REPOSITORY.findFirstByUserAndAccountNumber(userId, accountNumber);
	}

	public List<Account> findByUserAndTypeExceptAccountNumber(long userId, String accountType, String accountNumber) {
		return ACCOUNT_REPOSITORY.findByUserAndTypeExceptAccountNumber(userId, accountType, accountNumber);
	}

}
