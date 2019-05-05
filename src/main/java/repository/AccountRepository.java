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

	public String transfer(long accountFromId, long accountToId, BigDecimal amount) {
		Account accountFrom = SESSION.get(Account.class, accountFromId);
		Account accountTo = SESSION.get(Account.class, accountToId);

		if (accountFrom.getBalance().compareTo(amount) == -1) {
			return "Entered sum is too big for this account!";
		}

		SESSION.beginTransaction();

		accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
		accountTo.setBalance(accountTo.getBalance().add(amount));
		SESSION.save(accountFrom);
		SESSION.save(accountTo);

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
			SESSION.save(account);
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

		List<Account> accounts = SESSION.createQuery(
				"from Account where user_id = :user_id and account_type = :account_type and account_number != :account_number")
				.setParameter("user_id", user.getId()).setParameter("account_type", accountType)
				.setParameter("account_number", accountNumber).list();

		SESSION.getTransaction().commit();

		return accounts;
	}

}
