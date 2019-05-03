package repository;

import javax.persistence.NoResultException;

import exceptions.UserNotFoundException;
import model.User;

public class UserRepository extends Repository {

	/**
	 * Get user and his accounts
	 * 
	 * @param username
	 * @param password
	 * @return User
	 */
	public User get(String username, String password) {
		SESSION.beginTransaction();

		try {
			user = (User) SESSION.createQuery("from User where username = :username and password = :password")
					.setParameter("username", username).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
			throw new UserNotFoundException("User not found with the given credentials!");
		} finally {
			SESSION.getTransaction().commit();
		}

		if (user == null) {
			throw new UserNotFoundException("User not found with the given credentials!");
		}

		return user;
	}

}
