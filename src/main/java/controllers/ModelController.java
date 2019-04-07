package controllers;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelController implements Controller {

	private static final Logger LOGGER = LogManager.getLogger(ModelController.class);

	/**
	 * Get user and its accounts
	 * 
	 * @param username
	 * @param password
	 * @return UserController
	 */
	public static UserController getUser(String username, String password) {
		UserController user = null;
		List<String[]> fileContent = null;

		// search credentials in user file
		fileContent = FILE_CONTROLLER.read(USERS_FILE_PATH, " ");
		try {
			for (String[] line : fileContent) {
				if (username.equals(line[0]) && password.equals(line[1])) {
					user = UserController.getInstance();
					user.setUsername(username);
					user.setPassword(password);
					break;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			LOGGER.fatal("file not well formatted : " + USERS_FILE_PATH);
			System.out.println("There was a problem reading the users from database.");
			System.exit(-1);
		}

		if (user != null) {
			// get user's accounts
			fileContent = FILE_CONTROLLER.read(ACCOUNTS_FILE_PATH, " ");
			try {
				for (String[] line : fileContent) {
					if (username.equals(line[1])) {
						user.addAccount(line[0], new BigDecimal(line[2]), line[3]);
					}
				}
			} catch (IndexOutOfBoundsException e) {
				LOGGER.fatal("accounts file not well formatted : " + ACCOUNTS_FILE_PATH);
				System.out.println("There was a problem reading your accounts from database.");
				System.exit(-1);
			}
		}

		return user;
	}

}
