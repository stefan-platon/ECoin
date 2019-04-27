package repository;

import org.hibernate.Session;

import database.SessionFactoryObject;
import model.User;
import utils.FileController;

abstract class Repository {

	protected static final String USERS_FILE_PATH = System.getProperty("user.dir") + "/database/users.txt";
	protected static final String ACCOUNTS_FILE_PATH = System.getProperty("user.dir") + "/database/accounts.txt";

	protected static final FileController FILE_CONTROLLER = new FileController();

	protected static final Session SESSION = SessionFactoryObject.getSession();

	protected static User user = null;

}
