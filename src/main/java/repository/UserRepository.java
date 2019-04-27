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

		String query = String.format("from User where username = '%s' and password = '%s'", username, password);
		
		try {
			user = (User) SESSION.createQuery(query).getSingleResult();
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
