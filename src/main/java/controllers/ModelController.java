package controllers;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelController implements Controller {

	private static final Logger logger = LogManager.getLogger(ModelController.class);

	public static UserController findUser(String username, String password) {
		UserController user = null;
		List<String[]> fileContent = null;

		// search credentials in user file
		fileContent = fileController.read(USERS_FILE_PATH, " ");
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
			logger.fatal("file not well formatted : " + USERS_FILE_PATH);
			System.out.println("There was a problem reading the users from database.");
			System.exit(-1);
		}

		if (user != null) {
			// get user's accounts
			fileContent = fileController.read(ACCOUNTS_FILE_PATH, " ");
			try {
				for (String[] line : fileContent) {
					if (username.equals(line[1])) {
						user.addAccount(line[0], line[1], new BigDecimal(line[2]), line[3]);
					}
				}
			} catch (IndexOutOfBoundsException e) {
				logger.fatal("accounts file not well formatted : " + ACCOUNTS_FILE_PATH);
				System.out.println("There was a problem reading your accounts from database.");
				System.exit(-1);
			}
		}

		return user;
	}

}
