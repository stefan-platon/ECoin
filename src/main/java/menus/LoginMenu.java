package menus;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cache.UsersCache;
import models.User;
import utils.FileController;

/**
 * Class responsible the user login menu.
 */
public class LoginMenu {
	
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

			StringBuilder titleBuilder = new StringBuilder();
			fileContent.forEach((line) -> {
				titleBuilder.append(line[0] + "\n");
			});
			title = titleBuilder.toString();

			System.out.println(title);
		} else {
			System.out.println("Welcome!");
		}

		System.out.println("Type 'list' if you want to see available commands!");
	}

	/**
	 * Function responsible for interacting with the user and executing commands.
	 */
	public void login() {
		printTitleMenu();

		// get credentials manipulation object
		usersCache = UsersCache.getInstance();

		// start the loop to accept commands
		String command;
		User user = null;
		boolean sesssion = true;
		Scanner scanner = new Scanner(System.in);
		do {
			command = scanner.nextLine();

			switch (command) {
			case "login":
				if (user == null) {
					// instantiate user
					user = new User();

					// get credentials from user
					System.out.println("Please enter your:");
					System.out.print("-> username : ");
					user.setUsername(scanner.nextLine());
					System.out.print("-> password : ");
					user.setPassword(scanner.nextLine());

					// check if given credentials are correct
					if (usersCache.verifyUser(user)) {
						logger.info("user logged in " + user.getUsername());
						System.out.println("Welcome " + user.getUsername() + "!");
					} else {
						System.out.println("Wrong credentials! Please try again.");
					}
				} else {
					System.out.println("You must first log out!");
				}
				break;
			case "logout":
				if (user != null) {
					logger.info("user logged out " + user.getUsername());
					System.out.println("You have been successfully logged out. We hope you will be back soon!");	
					user = null;
				} else {
					System.out.println("You must first be logged in!");
				}
				break;
			case "exit":
				sesssion = false;
				break;
			case "list":
				System.out.printf("%s%s%s%s%s",
						"-> login  : login into your account (only if you are not already logged in) \n",
						"-> logout : logout from your account (only if you are already logged in) \n",
						"-> exit   : exit from the application \n", "-> list   : see list of available commands \n",
						"-> title  : print title message \n");
				break;
			case "title":
				printTitleMenu();
				break;
			default:
				System.out.println("Unknown command! Please type 'list' to see available commands.");
			}
		} while (sesssion);

		scanner.close();
	}

}
