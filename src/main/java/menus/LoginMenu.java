package menus;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cache.UsersCache;
import models.User;
import utils.FileController;

/**
 * Class responsible the user login menu.
 */
public class LoginMenu implements Menu {

	private static final Logger logger = LogManager.getLogger(LoginMenu.class);

	private String title = null;

	private final String TILE_FILE_PATH = "files/title_message.txt";

	private UsersCache usersCache;

	/**
	 * Print the greetings message to the console.
	 */
	private void printTitleMenu() {
		if (title == null) {
			FileController fileReader = new FileController();
			List<String[]> fileContent = fileReader.read(TILE_FILE_PATH, "\n");

			StringBuilder builder = new StringBuilder();
			fileContent.forEach((line) -> {
				builder.append(line[0] + "\n");
			});
			title = builder.toString();

			console.print(title);
		} else {
			console.print("Welcome!");
		}

		console.print("Type 'list' if you want to see available commands!");
	}

	/**
	 * Function responsible for interacting with the user and executing commands.
	 */
	public void show() {
		printTitleMenu();

		// get credentials manipulation object
		usersCache = UsersCache.getInstance();

		// start the loop to accept commands
		String command;
		User user = null;
		boolean sesssion = true;
		do {
			command = console.getLine();

			switch (command) {
			case "login":
				if (user == null) {
					// instantiate user
					user = new User();

					// get credentials from user
					user.setUsername(console.printForResponse("Please enter your: \n -> username : "));
					user.setPassword(console.printForResponse("-> password : "));

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
			case "exit":
				sesssion = false;
				break;
			case "list":
				console.printMultiple("-> login  : login into your account (only if you are not already logged in)",
						"-> logout : logout from your account (only if you are already logged in)",
						"-> exit   : exit from the application", "-> list   : see list of available commands",
						"-> title  : print title message");
				break;
			case "title":
				printTitleMenu();
				break;
			default:
				console.print("Unknown command! Please type 'list' to see available commands.");
			}
		} while (sesssion);

		console.close();
	}

}
