package menus;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import caches.AccountsCache;
import exceptions.DataValidationException;
import models.Account;
import models.User;

/**
 * Class responsible the user account menu.
 */
public class AccountMenu extends Menu {

	private static final Logger logger = LogManager.getLogger(AccountMenu.class);

	private final String TILE_FILE_PATH = "files/account_menu_title.txt";

	private User user;

	private AccountsCache accountsCache;

	public AccountMenu(User user) {
		this.user = user;
	}

	public void show() {
		printTitle(TILE_FILE_PATH);

		// get accounts manipulation object
		accountsCache = AccountsCache.getInstance();

		// start the loop to accept commands
		String command;
		boolean sesssion = true;
		do {
			command = console.printForResponse("> ");

			switch (command) {
			case "create":
				// get data from user
				String accountNumber = console.printForResponse("Please enter your: \n -> account number : ");
				String accountType = console.printForResponse(" -> account type : ");

				BigDecimal balance;
				switch (console.printForResponse("Type y if you want to add a sum right away : ")) {
				case "y":
					balance = new BigDecimal(console.printForResponse(" -> sum : "));
					break;
				default:
					balance = new BigDecimal(0);
					break;
				}

				try {
					accountsCache.saveAccount(accountNumber, user.getUsername(), balance, accountType);

					logger.info("new account " + user.getUsername());
					console.print("Account created succesfully!");
				} catch (DataValidationException e) {
					console.print(e.getMessage());
				}
				break;
			case "list":
				// list all of the user's accounts
				List<Account> accounts = accountsCache.getAccountsforUser(user);
				accounts.forEach((account) -> {
					console.printMultiple("<------------------------------->", "\n", "Number : ",
							account.getAccountNumber(), "\n", "Balance : ", account.getBalance().toString(), " ",
							account.getAccountType());
				});
				console.print("<------------------------------->");
				break;
			case "back":
				sesssion = false;
				break;
			case "man":
				console.printMultiple("-> create  : login into your account (only if you are not already logged in) \n",
						"-> list : logout from your account (only if you are already logged in) \n",
						"-> back : go back to main menu \n", "-> list   : see list of available commands \n");
				break;
			case "title":
				printTitle(TILE_FILE_PATH);
				break;
			default:
				console.print("Unknown command! Please type 'man' to see available commands.");
			}
		} while (sesssion);
	}

}
