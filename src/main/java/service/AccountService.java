package service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import exceptions.AccountDataValidationException;
import model.Account;
import model.User;
import repository.AccountRepository;

public class AccountService {

	private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

	private AccountRepository ACCOUNT_REPOSITORY = new AccountRepository();

	public String create(String accountNumber, BigDecimal balance, String accountType, long userId) {
		User user = new UserService().getById(userId);

		if (user == null) {
			return "Could not find user!";
		}

		try {
			ACCOUNT_REPOSITORY.create(accountNumber, balance, accountType, user);
		} catch (AccountDataValidationException e) {
			return e.getMessage();
		} catch (ConstraintViolationException e) {
			return "Account number already exists!";
		} catch (Exception e) {
			return e.getMessage();
		}

		LOGGER.info("new account : " + accountNumber);
		return "Account created succesfully!";
	}

	public String transfer(long accountFromId, long accountToId, BigDecimal amount) {
		return ACCOUNT_REPOSITORY.transfer(accountFromId, accountToId, amount);
	}

	public List<Account> getForUser(long userId) {
		return ACCOUNT_REPOSITORY.getForUser(userId);
	}

	public Account getForUserByNumber(long userId, String accountNumber) {
		return ACCOUNT_REPOSITORY.getForUserByNumber(userId, accountNumber);
	}

	public List<Account> getForUserByTypeExcept(long userId, String accountType, String accountNumber) {
		return ACCOUNT_REPOSITORY.getForUserByTypeExcept(userId, accountType, accountNumber);
	}

}
