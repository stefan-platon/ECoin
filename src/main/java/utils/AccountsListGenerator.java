package utils;

import java.util.List;

import org.hibernate.Session;

import database.SessionFactoryObject;
import model.Account;
import model.User;

public class AccountsListGenerator {

	private final Session SESSION = SessionFactoryObject.getSession();

	public List<Account> generateForUser(User user) {
		SESSION.beginTransaction();

		List<Account> accounts = user.getAccounts();

		SESSION.getTransaction().commit();

		return accounts;
	}

}
