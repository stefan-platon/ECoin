package main;

import database.SessionFactoryObject;
import view.LoginMenu;

/**
 * Starting point of the program.
 */
public class Main {

	public static void main(String[] args) {
		LoginMenu loginMenu = new LoginMenu();
		loginMenu.show();

		SessionFactoryObject.closeSessionFactory();
	}

}
