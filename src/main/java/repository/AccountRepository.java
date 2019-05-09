package repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;

import model.Account;
import model.User;

public class AccountRepository extends Repository {

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

	public long create(String accountNumber, BigDecimal balance, String accountType, User user) {
		long returnId;
		Account account = new Account();

		account.setAccountNumber(accountNumber);
		account.setBalance(balance);
		account.setAccountType(accountType);
		account.setUserObj(user);

		SESSION.beginTransaction();

		try {
			returnId = (long) SESSION.save(account);
			SESSION.getTransaction().commit();
		} catch (ConstraintViolationException e) {
			SESSION.getTransaction().rollback();
			throw e;
		}

		return returnId;
	}

	@SuppressWarnings("unchecked")
	public List<Account> getForUser(long userId) {
		SESSION.beginTransaction();

		List<Account> accounts = SESSION.createQuery("from Account where user_id = :user_id")
				.setParameter("user_id", userId).list();

		SESSION.getTransaction().commit();

		return accounts;
	}

	public Account getForUserByNumber(long userId, String accountNumber) {
		SESSION.beginTransaction();

		Account account;
		try {
			account = (Account) SESSION
					.createQuery("from Account where user_id = :user_id and account_number = :account_number")
					.setParameter("user_id", userId).setParameter("account_number", accountNumber).getSingleResult();
		} catch (NoResultException e) {
			account = null;
		} finally {
			SESSION.getTransaction().commit();
		}

		return account;
	}

	@SuppressWarnings("unchecked")
	public List<Account> getForUserByTypeExcept(long userId, String accountType, String accountNumber) {
		SESSION.beginTransaction();

		List<Account> accounts = SESSION.createQuery(
				"from Account where user_id = :user_id and account_type = :account_type and account_number != :account_number")
				.setParameter("user_id", userId).setParameter("account_type", accountType)
				.setParameter("account_number", accountNumber).list();

		SESSION.getTransaction().commit();

		return accounts;
	}

}
