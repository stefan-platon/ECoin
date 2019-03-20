package login;

import java.io.InputStream;
import java.util.Scanner;

public class Login {
	// title message information
	private String title = null;
	private String titleFilePath = "file/title_message.txt";

	// object to manipulate login credentials
	private Credentials credentials;

	private void printTitleMenu() {
		if (title == null) {
			// get file content from resources folder
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(titleFilePath);

			// create and print title string
			title = "";
			try (Scanner scanner = new Scanner(inputStream)) {
				while (scanner.hasNext()) {
					title += scanner.nextLine() + "\n";
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
		boolean sesssion = true;
		boolean loggedIn = false;
		String command;
		Scanner scanner = new Scanner(System.in);
		do {
			command = scanner.nextLine();

			switch (command) {
			case "login":
				if (!loggedIn) {
					// get credentials from user
					System.out.println("Please enter your:");
					System.out.print("-> username : ");
					String username = scanner.nextLine();
					System.out.print("-> password : ");
					String password = scanner.nextLine();

					// check if given credentials are correct
					if (credentials.checkCredentials(username, password)) {
						loggedIn = true;
						System.out.println("Welcome " + username + "!");
					} else {
						System.out.println("Wrong credentials! Please try again.");
					}
				} else {
					System.out.println("You must first log out!");
				}
				break;
			case "logout":
				if (loggedIn) {
					loggedIn = false;
					System.out.println("You have been successfully logged out. We hope you will be back soon!");
				} else {
					System.out.println("You must first be logged in!");
				}
				break;
			case "exit":
				sesssion = false;
				break;
			case "list":
				System.out.println("-> login  : login into your account (only if you are not already logged in)");
				System.out.println("-> logout : logout from your account (only if you are already logged in)");
				System.out.println("-> exit   : exit from the application");
				System.out.println("-> list   : see list of available commands");
				System.out.println("-> title  : print title message");
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
