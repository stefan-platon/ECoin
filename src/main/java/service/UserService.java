package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exceptions.UniqueDatabaseConstraintException;
import model.User;
import repository.UserRepository;

@Service
public class UserService {

	private static final Logger LOGGER = LogManager.getLogger(UserService.class);

	@Autowired
	private UserRepository USER_REPOSITORY;

	public User getByCredentials(String username, String password) {
		return USER_REPOSITORY.findFirstByUsernameAndPassword(username, password);
	}

	public User getById(long id) {
		return USER_REPOSITORY.findFirstById(id);
	}

	public User create(String username, String password, String address, String email, String firstName,
			String lastName) {
		User user = new User();

		try {
			user.setUsername(username);
			user.setPassword(password);
		} catch (UniqueDatabaseConstraintException e) {
			throw new UniqueDatabaseConstraintException("Account number already exists!");
		}

		user = USER_REPOSITORY.save(user);
		LOGGER.info("new user : " + user.getUsername());

		return user;
	}

}
