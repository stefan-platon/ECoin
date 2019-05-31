package ecoin.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecoin.exceptions.AccountDataValidationException;
import ecoin.exceptions.HTTPCustomClientException;
import ecoin.exceptions.UserNotFoundException;
import ecoin.model.Account;
import ecoin.model.User;
import ecoin.repository.AccountRepository;

@Service
public class AccountService {

	private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

	@Autowired
	AccountRepository ACCOUNT_REPOSITORY;

	@Autowired
	UserService USER_SERVICE;

	public Account create(String token, Account account) {
		User user = USER_SERVICE.getById(account.getUserObj().getId());

		if (user == null) {
			throw new UserNotFoundException("Could not find user!");
		}

		Account newAccount = ACCOUNT_REPOSITORY.save(account);
		LOGGER.info("new account : " + newAccount.getAccountNumber());

		return newAccount;
	}

	public Account create(String token, String accountNumber, BigDecimal balance, String accountType, long userId) {
		User user = new UserService().getById(userId);

		if (user == null) {
			throw new UserNotFoundException("Could not find user!");
		}

		Account account = new Account();

		try {
			account.setAccountNumber(accountNumber);
			account.setBalance(balance);
			account.setAccountType(accountType);
			account.setUserObj(user);
		} catch (AccountDataValidationException e) {
			throw new HTTPCustomClientException(e.getMessage());
		} catch (ConstraintViolationException e) {
			throw new HTTPCustomClientException("Account number already exists!");
		} catch (Exception e) {
			throw new HTTPCustomClientException(e.getMessage());
		}

		account = ACCOUNT_REPOSITORY.save(account);
		LOGGER.info("new account : " + accountNumber);
		user.getAccounts().add(account);

		return account;
	}

	public void transfer(String token, String fromAccountNumber, String toAccountNumber, BigDecimal amount,
			String details) {
		Account accountFrom = ACCOUNT_REPOSITORY.findFirstByAccountNumber(fromAccountNumber);
		if (accountFrom == null) {
			throw new HTTPCustomClientException("Inexistent source account!");
		}

		Account accountTo = ACCOUNT_REPOSITORY.findFirstByAccountNumber(toAccountNumber);
		if (accountTo == null) {
			throw new HTTPCustomClientException("Inexistent destination account!");
		}

		if (accountFrom.getBalance().compareTo(amount) == -1) {
			throw new HTTPCustomClientException("Entered sum is too big for this account!");
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

	public List<Account> findByUser(String token, long userId) {
		return ACCOUNT_REPOSITORY.findByUserObj(userId);
	}

	public Account findFirstByUserAndAccountNumber(String token, long userId, String accountNumber) {
		return ACCOUNT_REPOSITORY.findFirstByUserObjAndAccountNumber(userId, accountNumber);
	}

	public List<Account> findByUserAndTypeExceptAccountNumber(String token, long userId, String accountType,
			String accountNumber) {
		return ACCOUNT_REPOSITORY.findByUserObjAndTypeExceptAccountNumber(userId, accountType, accountNumber);
	}

}
