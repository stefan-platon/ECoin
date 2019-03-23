package login;

import java.io.InputStream;
import java.util.Scanner;

import user.User;

/**
 * Class responsible for an user's login.
 */
public class Login {
	/**
	 * The title to be shown to user.
	 */
	private String title = null;
	/**
	 * The path to the file containing the title.
	 */
	private final String titleFilePath = "file/title_message.txt";
	/**
	 * Object to manipulate login credentials.
	 */
	private Credentials credentials;

	/**
	 * Print the greetings message to the console.
	 */
	private void printTitleMenu() {
		if (title == null) {
			// get file content from resources folder
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(titleFilePath);

			// create and print title string
			StringBuilder title = new StringBuilder();
			try (Scanner scanner = new Scanner(inputStream)) {
				while (scanner.hasNext()) {
					title.append(scanner.nextLine() + "\n");
				}
				System.out.println(title);
			} catch (Exception e) {
				System.out.println("Welcome!");
			}
		} else {
			System.out.println(title);
		}

		System.out.println("Type 'list' if you want to see available commands!");
	}

	/**
	 * Function responsible for interacting with the user and executing commands.
	 */
	public void login() {
		// get credentials manipulation object
		try {
			credentials = Credentials.getInstance();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Credentials file is not well formatted!");
			return;
		} catch (NullPointerException e) {
			System.out.println("File not found!");
			return;
		}

		printTitleMenu();

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
					if (credentials.checkCredentials(user.getUsername(), user.getPassword())) {
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
					user = null;
					System.out.println("You have been successfully logged out. We hope you will be back soon!");
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
