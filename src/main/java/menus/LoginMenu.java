package menus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import caches.UsersCache;
import models.User;

/**
 * Class responsible the user login menu.
 */
public class LoginMenu extends Menu {

	private static final Logger logger = LogManager.getLogger(LoginMenu.class);

	private final String TILE_FILE_PATH = "files/login_menu_title.txt";

	private UsersCache usersCache;

	public void show() {
		printTitle(TILE_FILE_PATH);

		// get credentials manipulation object
		usersCache = UsersCache.getInstance();

		// start the loop to accept commands
		String command;
		User user = null;
		boolean sesssion = true;
		do {
			command = console.printForResponse("> ");

			switch (command) {
			case "login":
				if (user == null) {
					user = new User();

					// get credentials from user
					user.setUsername(console.printForResponse("Please enter your: \n -> username : "));
					user.setPassword(console.printForResponse(" -> password : "));

					// check if given credentials are correct
					if (usersCache.verifyUser(user)) {
						logger.info("user logged in " + user.getUsername());
						console.print("Welcome " + user.getUsername() + "!");
					} else {
						console.print("Wrong credentials! Please try again.");
					}
				} else {
					console.print("You must first log out!");
				}
				break;
			case "logout":
				if (user != null) {
					logger.info("user logged out " + user.getUsername());
					console.print("You have been successfully logged out. We hope you will be back soon!");
					user = null;
				} else {
					console.print("You must first be logged in!");
				}
				break;
			case "account":
				if (user != null) {
					// got to account menu
					AccountMenu accountMenu = new AccountMenu(user);
					accountMenu.show();
					// on return, show title message
					printTitle(TILE_FILE_PATH);
				} else {
					console.print("You must first be logged in!");
				}
				break;
			case "exit":
				sesssion = false;
				break;
			case "man":
				console.printMultiple("-> login  : login into your account (only if you are not already logged in \n)",
						"-> logout : logout from your account (only if you are already logged in) \n",
						"-> exit   : exit from the application \n", "-> list   : see list of available commands \n",
						"-> title  : print title message \n");
				break;
			case "title":
				printTitle(TILE_FILE_PATH);
				break;
			default:
				console.print("Unknown command! Please type 'man' to see available commands.");
			}
		} while (sesssion);

		console.close();
	}

}
