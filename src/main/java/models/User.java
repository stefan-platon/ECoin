package models;

import java.util.ArrayList;
import java.util.List;

public class User {

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

	protected User() {
		accounts = new ArrayList<>();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		User other = (User) obj;

		if (!password.equals(other.password))
			return false;

		if (!username.equals(other.username))
			return false;

		return true;
	}

}
