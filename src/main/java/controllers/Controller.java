package controllers;

import utils.FileController;

public interface Controller {

	static final String USERS_FILE_PATH = System.getProperty("user.dir") + "/database/users.txt";
	static final String ACCOUNTS_FILE_PATH = System.getProperty("user.dir") + "/database/accounts.txt";
	static final FileController FILE_CONTROLLER = new FileController();

}
