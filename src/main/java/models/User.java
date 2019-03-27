package models;

/**
 * Class describing an user.
 */
public class User {

	private String username, password;

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

	public User() {

	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
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

		if (password == null && other.password != null) {
			return false;
		} else if (!password.equals(other.password))
			return false;

		if (username == null && other.username != null) {
			return false;
		} else if (!username.equals(other.username))
			return false;

		return true;
	}

}
