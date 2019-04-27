package view;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import collections.AccountType;
import model.Account;
import repository.AccountRepository;
import utils.Utils;

/**
 * Class responsible the user account menu.
 */
public class AccountMenu extends Menu {

	private static final Logger LOGGER = LogManager.getLogger(AccountMenu.class);

	private final String TILE_FILE_PATH = "files/account_menu_title.txt";

	public void show() {
		printTitle(TILE_FILE_PATH);

		// start the loop to accept commands
		String command;
		boolean sesssion = true;
		do {
			command = CONSOLE.printForResponse("> ");

			switch (command) {
			case "create":
				CONSOLE.print(create());
				break;
			case "list":
				CONSOLE.printTable(Utils.createAccountsListTable(user.getAccounts(), "Number", "Balance", "Type"));
				break;
			case "transfer":
				CONSOLE.print(transfer());
				break;
			case "back":
				sesssion = false;
				break;
			case "man":
				CONSOLE.printMultiple("-> create  : create new account \n", "-> list    : list available accounts \n",
						"-> transfer: transfer money to another account of the same type",
						"-> back    : go back to main menu \n");
				break;
			case "title":
				printTitle(TILE_FILE_PATH);
				break;
			default:
				CONSOLE.print("Unknown command! Type 'man' to see available commands.");
			}
		} while (sesssion);
	}

	private String create() {
		// get data from user
		String accountNumber = CONSOLE.printForResponse("Please enter your: \n -> account number : ");

		if (accountNumber.length() != 24) {
			return "Account number has wrong length.";
		}

		if (!accountNumber.startsWith("RO")) {
			return "Account number should start with RO.";
		}

		String accountType = CONSOLE.printForResponse(" -> account type : ");

		if (!AccountType.isType(accountType)) {
			return "Account type not supported.";
		}

		BigDecimal balance;
		switch (CONSOLE.printForResponse("Type y if you want to add a sum right away : ")) {
		case "y":
			try {
				balance = new BigDecimal(CONSOLE.printForResponse(" -> sum : "));
			} catch (NumberFormatException e) {
				return "Invalid sum!";
			}
			break;
		default:
			balance = new BigDecimal(0);
			break;
		}

		user.createAccount(accountNumber, balance, AccountType.valueOf(accountType));
		LOGGER.info("new account : " + user.getUsername());

		return "Account created succesfully!";
	}

	private String transfer() {
		// list all of the user's accounts
		CONSOLE.printTable(Utils.createAccountsListTable(user.getAccounts(), "Number", "Balance", "Type"));

		// get source account
		String accountNumber = CONSOLE.printForResponse("Select one of your accounts: \n -> account : ");
		Account accountFrom = user.getAccountByAccountNumber(accountNumber);

		if (accountFrom != null) {
			String accountType = accountFrom.getAccountType();

			// list all compatible accounts
			List<Account> destinationAccounts = user.getAccountsByTypeExcept(accountType, accountNumber);
			CONSOLE.printTable(Utils.createAccountsListTable(destinationAccounts, "Number", "Balance", "Type"));

			// get destination account
			accountNumber = CONSOLE.printForResponse("Select destination account: \n -> account : ");
			Account accountTo = null;
			for (Account account : destinationAccounts) {
				if (accountNumber.equals(account.getAccountNumber())) {
					accountTo = account;
					break;
				}
			}

			if (accountTo != null) {
				try {
					BigDecimal amount = new BigDecimal(
							CONSOLE.printForResponse("Enter how much do you want to transfer: \n -> amount : "));
					// execute the transfer
					return AccountRepository.transfer(accountFrom, accountTo, amount);
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
