package repository;

import org.hibernate.Session;

import exceptions.UserNotFoundException;
import model.User;

public class UserRepository implements Repository {

	/**
	 * Get user and his accounts
	 * 
	 * @param username
	 * @param password
	 * @return User
	 */
	public User get(String username, String password) {
		Session session = SESSION_FACTORY.getCurrentSession();
		session.beginTransaction();

		String query = String.format("from User where username = '%s' and password = '%s'", username, password);
		User user = (User) session.createQuery(query).getSingleResult();

		session.getTransaction().commit();

		if (user == null) {
			throw new UserNotFoundException("User not found with the given credentials!");
		}

		return user;
	}

}
