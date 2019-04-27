package repository;

import org.hibernate.SessionFactory;

import database.SessionFactoryObject;
import utils.FileController;

public interface Repository {

	static final String USERS_FILE_PATH = System.getProperty("user.dir") + "/database/users.txt";
	static final String ACCOUNTS_FILE_PATH = System.getProperty("user.dir") + "/database/accounts.txt";

	static final FileController FILE_CONTROLLER = new FileController();

	static final SessionFactory SESSION_FACTORY = SessionFactoryObject.getSessionFactory();

}
