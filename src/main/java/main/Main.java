package main;

import database.SessionFactoryObject;
import exceptions.HTTPCustomException;
import service.UserService;
import view.LoginMenu;

/**
 * Starting point of the program.
 */
public class Main {

	public static void main(String[] args) {
		try {
			new UserService().create("user", "pass", "address", "email", "first", "last");
		} catch (HTTPCustomException e) {
		}

		LoginMenu loginMenu = new LoginMenu();
		loginMenu.show();

		SessionFactoryObject.closeSessionFactory();
	}

}
