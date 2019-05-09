package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exceptions.UniqueDatabaseConstraintException;
import model.User;
import repository.UserRepository;

public class UserService {

	private static final Logger LOGGER = LogManager.getLogger(UserService.class);

	private UserRepository USER_REPOSITORY = new UserRepository();

	public User getByCredentials(String username, String password) {
		return USER_REPOSITORY.getByCredentials(username, password);
	}

	public User getById(long id) {
		return USER_REPOSITORY.getById(id);
	}

	public User create(String username, String password, String address, String email, String firstName,
			String lastName) {
		long userId;

		try {
			userId = USER_REPOSITORY.create(username, password);
		} catch (UniqueDatabaseConstraintException e) {
			return null;
		}

		// get created user
		User user = USER_REPOSITORY.getById(userId);

		new PersonService().create(address, email, firstName, lastName, user);

		LOGGER.info("new account : " + user.getUsername());

		return user;
	}

}
