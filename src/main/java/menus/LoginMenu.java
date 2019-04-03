package menus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controllers.ModelController;

/**
 * Class responsible the user login menu.
 */
public class LoginMenu extends Menu {

	private static final Logger logger = LogManager.getLogger(LoginMenu.class);

	private final String TILE_FILE_PATH = "files/login_menu_title.txt";

	public void show() {
		printTitle(TILE_FILE_PATH);

		// start the loop to accept commands
		String command;
		boolean sesssion = true;
		do {
			command = console.printForResponse("> ");

			switch (command) {
			case "login":
				console.print(login());
				break;
			case "logout":
				console.print(logout());
				break;
			case "account":
				console.print(account());
				printTitle(TILE_FILE_PATH);
				break;
			case "exit":
				sesssion = false;
				break;
			case "man":
				console.printMultiple("-> login   : login into your account (only if you are not already logged in) \n",
						"-> logout  : logout from your account (only if you are already logged in) \n",
						"-> account : see available accounts and initiate transctions \n",
						"-> exit    : exit from the application \n", "-> title   : print title message \n");
				break;
			case "title":
				printTitle(TILE_FILE_PATH);
				break;
			default:
				console.print("Unknown command! Type 'man' to see available commands.");
			}
		} while (sesssion);

		console.close();
	}

	private String login() {
		if (user == null) {
			// get credentials from console
			String username = console.printForResponse("Enter your: \n -> username : ");
			String password = console.printForResponse(" -> password : ");

			// get user based on credentials
			user = ModelController.getUser(username, password);
			if (user != null) {
				logger.info("user logged in : " + user.getUsername());
				return "Welcome " + user.getUsername() + "!";
			} else {
				return "Wrong credentials! Try again.";
			}
		} else {
			return "You must first log out!";
		}
	}

	private String logout() {
		if (user != null) {
			user = null;
			logger.info("user logged out : " + user.getUsername());
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
			return "";
		} else {
			return "You must first be logged in!";
		}
	}

}
