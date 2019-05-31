package ecoin.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecoin.exceptions.AccountTransferException;
import ecoin.exceptions.CustomException;
import ecoin.exceptions.UserNotFoundException;
import ecoin.model.Account;
import ecoin.model.User;
import ecoin.repository.AccountRepository;

@Service
public class AccountService {

	private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

	AccountRepository ACCOUNT_REPOSITORY;

	NotificationService NOTIFICATION_SERVICE;

	TransactionService TRANSACTION_SERVICE;

	UserService USER_SERVICE;

	@Autowired
	public AccountService(AccountRepository ACCOUNT_REPOSITORY, NotificationService NOTIFICATION_SERVICE,
			TransactionService TRANSACTION_SERVICE, UserService USER_SERVICE) {
		super();
		this.ACCOUNT_REPOSITORY = ACCOUNT_REPOSITORY;
		this.NOTIFICATION_SERVICE = NOTIFICATION_SERVICE;
		this.TRANSACTION_SERVICE = TRANSACTION_SERVICE;
		this.USER_SERVICE = USER_SERVICE;
	}

	public Account create(String accountNumber, BigDecimal balance, String accountType, long userId) {
		User user = USER_SERVICE.getById(userId);

		if (user == null) {
			throw new UserNotFoundException();
		}

		Account account = new Account();

		if (accountNumber == null) {
			StringBuilder sb = new StringBuilder("RO");
			sb.append(RandomStringUtils.random(22, true, true));
			accountNumber = sb.toString();
		}

		try {
			account.setAccountNumber(accountNumber);
			account.setBalance(balance);
			account.setAccountType(accountType);
			account.setUserObj(user);
		} catch (ConstraintViolationException e) {
			throw new CustomException("Account number already exists!");
		}

		account = ACCOUNT_REPOSITORY.save(account);
		LOGGER.info("new account : " + accountNumber);
		user.getAccounts().add(account);

		return account;
	}

	public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String details) {
		Account accountFrom = ACCOUNT_REPOSITORY.findFirstByAccountNumber(fromAccountNumber);
		if (accountFrom == null) {
			throw new AccountTransferException("Inexistent source account!");
		}

		Account accountTo = ACCOUNT_REPOSITORY.findFirstByAccountNumber(toAccountNumber);
		if (accountTo == null) {
			throw new AccountTransferException("Inexistent destination account!");
		}

		if (accountFrom.getBalance().compareTo(amount) == -1) {
			throw new AccountTransferException("Entered sum is too big for this account!");
		}

		if (!accountFrom.getAccountType().equals(accountTo.getAccountType())) {
			throw new AccountTransferException("The accounts are not of the same type!");
		}

		if (accountFrom.getAccountNumber().equals(accountTo.getAccountNumber())) {
			throw new AccountTransferException("You cannot transfer to the same account!");
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

		NOTIFICATION_SERVICE.create(notificationDetails.toString(), accountFrom.getUserObj());

		// source account
		TRANSACTION_SERVICE.create(accountFrom, accountFrom.getAccountNumber(), amount, details, "outgoing");

		// destination account
		TRANSACTION_SERVICE.create(accountTo, accountTo.getAccountNumber(), amount, null, "incoming");
	}

	public List<Account> findByUser(long userId) {
		return ACCOUNT_REPOSITORY.findByUserObj(userId);
	}

	public Account findFirstByUserAndAccountNumber(long userId, String accountNumber) {
		return ACCOUNT_REPOSITORY.findFirstByUserObjAndAccountNumber(userId, accountNumber);
	}

	public List<Account> findByUserAndTypeExceptAccountNumber(long userId, String accountType, String accountNumber) {
		return ACCOUNT_REPOSITORY.findByUserObjAndTypeExceptAccountNumber(userId, accountType, accountNumber);
	}

}
