package ecoin.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecoin.exceptions.UniqueDatabaseConstraintException;
import ecoin.exceptions.UserNotFoundException;
import ecoin.model.Authentication;
import ecoin.model.User;
import ecoin.repository.AuthenticationRepository;
import ecoin.repository.UserRepository;

@Service
public class UserService {

	private static final Logger LOGGER = LogManager.getLogger(UserService.class);

	private UserRepository USER_REPOSITORY;

	private AuthenticationRepository AUTHENTICATION_REPOSITORY;

	@Autowired
	public UserService(UserRepository USER_REPOSITORY, AuthenticationRepository AUTHENTICATION_REPOSITORY) {
		super();
		this.USER_REPOSITORY = USER_REPOSITORY;
		this.AUTHENTICATION_REPOSITORY = AUTHENTICATION_REPOSITORY;
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

	public String login(String username, String password) {
		User user = USER_REPOSITORY.findFirstByUsernameAndPassword(username, password);
		if (user == null) {
			throw new UserNotFoundException();
		}

		String token;
		Authentication authentication;

		// check if user already has a token
		authentication = AUTHENTICATION_REPOSITORY.findFirstByUserObj(user);
		if (authentication != null) {
			return authentication.getToken();
		}

		// create new token
		do {
			token = RandomStringUtils.random(20, true, true);
			authentication = AUTHENTICATION_REPOSITORY.findFirstByToken(token);
		} while (authentication != null);

		authentication = new Authentication();
		authentication.setToken(token);
		authentication.setUserObj(user);
		authentication = AUTHENTICATION_REPOSITORY.save(authentication);

		return authentication.getToken();
	}

	public void logout(String token) {
		AUTHENTICATION_REPOSITORY.deleteByToken(token);
	}

	public User getById(long id) {
		return USER_REPOSITORY.findFirstById(id);
	}

}
