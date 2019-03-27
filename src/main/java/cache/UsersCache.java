package cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import models.User;
import utils.FileController;

/**
 * Class responsible for loading and manipulating all stored users.
 */
public class UsersCache {

	private static final Logger logger = LogManager.getLogger(UsersCache.class);

	private final String LOGIN_FILE_PATH = "files/users.txt";

	private static UsersCache instance = null;

	private List<User> accounts;

	/**
	 * Read and store credentials in memory.
	 */
	private UsersCache() {
		FileController fileController = new FileController();
		List<String[]> fileContent = fileController.read(LOGIN_FILE_PATH, " ");

		accounts = new ArrayList<>();

		// create new user with user name and password
		try {
			fileContent.forEach((line) -> {
				User user = new User(line[0], line[1]);
				accounts.add(user);
			});
		} catch (IndexOutOfBoundsException e) {
			logger.fatal("credentials file not well formatted!");
			System.out.println("There was a problem reading the users from database.");
			System.exit(-1);
		}
	}

	/**
	 * Create or return instance of this class.
	 * 
	 * @return class instance
	 */
	public static UsersCache getInstance() {
		if (instance == null)
			instance = new UsersCache();

		return instance;
	}

	/**
	 * Check if given user has account.
	 * 
	 * @param user
	 * @return true or false
	 */
	public boolean verifyUser(User user) {
		Optional<User> response = accounts.stream().filter(account -> user.equals(account)).findFirst();
		return response.isPresent();
	}

}
