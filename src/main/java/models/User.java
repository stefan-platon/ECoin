package models;

import java.util.ArrayList;
import java.util.List;

public class User {

	private static User instance = null;

	protected String username;

	protected String password;

	protected List<Account> accounts;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	private User() {
		accounts = new ArrayList<>();
	}

	/**
	 * Create or return instance of this class.
	 * 
	 * @return class instance
	 */
	public static User getInstance() {
		if (instance == null)
			instance = new User();

		return instance;
	}
	
	/**
	 * Destroy instance of this class.
	 */
	public void destroyInstance() {
		instance = null;
	}

}
