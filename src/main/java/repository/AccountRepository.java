package repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.NoResultException;

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

		SESSION.beginTransaction();

		accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
		accountTo.setBalance(accountTo.getBalance().add(amount));
		SESSION.persist(accountFrom);
		SESSION.persist(accountTo);

		SESSION.getTransaction().commit();

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
			user.getAccounts().add(account);
			SESSION.persist(user);
			SESSION.getTransaction().commit();
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

	public Account getForCurrentUserByNumber(String accountNumber) {
		SESSION.beginTransaction();

		Account account;
		try {
			account = (Account) SESSION
					.createQuery("from Account where user_id = :user_id and account_number = :account_number")
					.setParameter("user_id", user.getId()).setParameter("account_number", accountNumber)
					.getSingleResult();
		} catch (NoResultException e) {
			account = null;
		} finally {
			SESSION.getTransaction().commit();
		}

		return account;
	}

	@SuppressWarnings("unchecked")
	public List<Account> getForCurrentUserByTypeExcept(String accountType, String accountNumber) {
		SESSION.beginTransaction();

		List<Account> accounts;
		try {
			accounts = SESSION.createQuery(
					"from Account where user_id = :user_id and account_type = :account_type and account_number != :account_number")
					.setParameter("user_id", user.getId()).setParameter("account_type", accountType)
					.setParameter("account_number", accountNumber).list();
		} catch (NoResultException e) {
			accounts = null;
		} finally {
			SESSION.getTransaction().commit();
		}

		return accounts;
	}

}
