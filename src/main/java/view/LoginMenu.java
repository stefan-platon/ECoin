package view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.UserController;
import exceptions.UserNotFoundException;

/**
 * Class responsible the user login menu.
 */
public class LoginMenu extends Menu {

	private static final Logger LOGGER = LogManager.getLogger(LoginMenu.class);

	private final String TILE_FILE_PATH = "files/login_menu_title.txt";

	private final UserController USER_CONTROLLER = new UserController();

	public void show() {
		printTitle(TILE_FILE_PATH);

		// start the loop to accept commands
		String command;
		boolean menuSession = true;
		do {
			command = CONSOLE.printForResponse("> ");

			switch (command) {
			case "login":
				CONSOLE.print(login());
				break;
			case "logout":
				CONSOLE.print(logout());
				break;
			case "account":
				CONSOLE.print(account());
				break;
			case "exit":
				menuSession = false;
				break;
			case "man":
				CONSOLE.printMultiple("-> login   : login into your account (only if you are not already logged in) \n",
						"-> logout  : logout from your account (only if you are already logged in) \n",
						"-> account : see available accounts and initiate transctions \n",
						"-> exit    : exit from the application \n", "-> title   : print title message \n");
				break;
			case "title":
				printTitle(TILE_FILE_PATH);
				break;
			default:
				CONSOLE.print("Unknown command! Type 'man' to see available commands.");
			}
		} while (menuSession);

		CONSOLE.close();
	}

	private String login() {
		if (user == null) {
			// get credentials from console
			String username = CONSOLE.printForResponse("Enter your: \n -> username : ");
			String password = CONSOLE.printForResponse(" -> password : ");

			// get user based on credentials
			try {
				user = USER_CONTROLLER.getByCredentials(username, password);

				LOGGER.info("user logged in : " + user.getUsername());
				return "Welcome " + user.getUsername() + "!";
			} catch (UserNotFoundException e) {
				return e.getMessage();
			}
		} else {
			return "You must first log out!";
		}
	}

	private String logout() {
		if (user != null) {
			LOGGER.info("user logged out : " + user.getUsername());
			user = null;
			return "You have been successfully logged out. We hope you will be back soon!";
		} else {
			return "You must first be logged in!";
		}
	}

	private String account() {
		if (user != null) {
			// got to account menu
			AccountMenu accountMenu = new AccountMenu();
			accountMenu.show();
			printTitle(TILE_FILE_PATH);
			return "";
		} else {
			return "You must first be logged in!";
		}
	}

}
