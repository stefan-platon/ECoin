package menus;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controllers.AccountController;
import exceptions.DataValidationException;
import utils.Utils;

/**
 * Class responsible the user account menu.
 */
public class AccountMenu extends Menu {

	private static final Logger logger = LogManager.getLogger(AccountMenu.class);

	private final String TILE_FILE_PATH = "files/account_menu_title.txt";

	public void show() {
		printTitle(TILE_FILE_PATH);

		// start the loop to accept commands
		String command;
		boolean sesssion = true;
		do {
			command = console.printForResponse("> ");

			switch (command) {
			case "create":
				console.print(create());
				break;
			case "list":
				console.printTable(Utils.createAccountsListTable(user.getAccounts(), "Number", "Balance", "Type"));
				break;
			case "transfer":
				console.print(transfer());
				break;
			case "back":
				sesssion = false;
				break;
			case "man":
				console.printMultiple("-> create : create new account \n", "-> list   : list available accounts \n",
						"-> back   : go back to main menu \n");
				break;
			case "title":
				printTitle(TILE_FILE_PATH);
				break;
			default:
				console.print("Unknown command! Type 'man' to see available commands.");
			}
		} while (sesssion);
	}

	private String create() {
		// get data from user
		String accountNumber = console.printForResponse("Please enter your: \n -> account number : ");
		String accountType = console.printForResponse(" -> account type : ");

		BigDecimal balance;
		switch (console.printForResponse("Type y if you want to add a sum right away : ")) {
		case "y":
			try {
				balance = new BigDecimal(console.printForResponse(" -> sum : "));
			} catch (NumberFormatException e) {
				return "Invalid sum!";
			}
			break;
		default:
			balance = new BigDecimal(0);
			break;
		}

		try {
			user.createAccount(accountNumber, balance, accountType);
			logger.info("new account : " + user.getUsername());
			return "Account created succesfully!";
		} catch (DataValidationException e) {
			return e.getMessage();
		}
	}

	private String transfer() {
		// list all of the user's accounts
		console.printTable(Utils.createAccountsListTable(user.getAccounts(), "Number", "Balance", "Type"));

		// get source account
		String accountNumber = console.printForResponse("Select one of your accounts: \n -> account : ");
		AccountController accountFrom = user.getAccountByAccountNumber(accountNumber);

		if (accountFrom != null) {
			String accountType = accountFrom.getAccountType();

			// list all compatible accounts
			List<AccountController> destinationAccounts;
//			switch (console.printForResponse("Type y if you want to see other users as well : ")) {
//			case "y":
//				destinationAccounts = ModelController.getAccountsByTypeExcept(accountType, accountNumber);
//				console.printTable(
//						Utils.createAccountsListTable(destinationAccounts, "User", "Number", "Balance", "Type"));
//				break;
//			default:
//				destinationAccounts = user.getAccountsByTypeExcept(accountType, accountNumber);
//				console.printTable(Utils.createAccountsListTable(destinationAccounts, "Number", "Balance", "Type"));
//				break;
//			}
			destinationAccounts = user.getAccountsByTypeExcept(accountType, accountNumber);
			console.printTable(Utils.createAccountsListTable(destinationAccounts, "Number", "Balance", "Type"));

			// get destination account
			accountNumber = console.printForResponse("Select destination account: \n -> account : ");
			AccountController accountTo = null;
			for (AccountController account : destinationAccounts) {
				if(accountNumber.equals(account.getAccountNumber())){
					accountTo = account;
					break;
				}
			}

			if (accountTo != null) {
				try {
					BigDecimal amount = new BigDecimal(
							console.printForResponse("Enter how much do you want to transfer: \n -> amount : "));
					// execute the transfer
					return accountFrom.transfer(accountTo, amount);
				} catch (NumberFormatException e) {
					return "Invalid sum!";
				}
			} else {
				return "Invalid destination account!";
			}
		} else {
			return "Invalid source account!";
		}
	}

}
