package main;

import database.SessionFactoryObject;
import service.UserService;
import view.LoginMenu;

/**
 * Starting point of the program.
 */
public class Main {

	public static void main(String[] args) {
		new UserService().create("user", "pass", "address", "email", "first", "last");

		LoginMenu loginMenu = new LoginMenu();
		loginMenu.show();

		SessionFactoryObject.closeSessionFactory();
	}

}
