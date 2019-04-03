package models;

import java.util.List;

import controllers.AccountController;

public class User {

	protected String username;

	protected String password;

	protected List<AccountController> accounts;

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

	public List<AccountController> getAccounts() {
		return accounts;
	}

	protected User() {

	}

}
