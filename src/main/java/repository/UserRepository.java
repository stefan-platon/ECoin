package repository;

import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;

import exceptions.UniqueDatabaseConstraintException;
import exceptions.UserNotFoundException;
import model.User;

public class UserRepository extends Repository {

	/**
	 * Get user by credentials
	 * 
	 * @param username
	 * @param password
	 * @return User
	 */
	public User getByCredentials(String username, String password) {
		User user = new User();

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

	/**
	 * Get user by id
	 * 
	 * @param id
	 * @return User
	 */
	public User getById(long id) {
		User user = null;

		SESSION.beginTransaction();

		try {
			user = (User) SESSION.createQuery("from User where id = :id").setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			SESSION.getTransaction().commit();
		}

		return user;
	}

	/**
	 * Create user
	 * 
	 * @param user
	 * @return long
	 */
	public long create(String username, String password) {
		long returnId;
		User user = new User();

		SESSION.beginTransaction();

		try {
			user.setUsername(username);
			user.setPassword(password);
			returnId = (long) SESSION.save(user);
			SESSION.getTransaction().commit();
		} catch (ConstraintViolationException e) {
			SESSION.getTransaction().rollback();
			throw new UniqueDatabaseConstraintException("Account number already exists!");
		}

		return returnId;
	}

}
