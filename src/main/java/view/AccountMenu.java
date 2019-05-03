package view;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import collections.AccountType;
import model.Account;
import repository.AccountRepository;
import utils.CreateTable;

/**
 * Class responsible the user account menu.
 */
public class AccountMenu extends Menu {

	private final String TILE_FILE_PATH = "files/account_menu_title.txt";

	private final AccountRepository ACCOUNT_REPOSITORY = new AccountRepository();

	public void show() {
		printTitle(TILE_FILE_PATH);

		// start the loop to accept commands
		String command;
		boolean menuSession = true;
		do {
			command = CONSOLE.printForResponse("> ");

			switch (command) {
			case "create":
				CONSOLE.print(create());
				break;
			case "list":
				list();
				break;
			case "transfer":
				CONSOLE.print(transfer());
				break;
			case "back":
				menuSession = false;
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
		} while (menuSession);
	}

	private void list() {
		List<Account> accounts = new AccountRepository().getForCurrentUser();
		CONSOLE.printTable(CreateTable.createAccountsListTable(accounts, "Number", "Balance", "Type"));
	}

	private String create() {
		// get data from user
		String accountNumber;
		switch (CONSOLE.printForResponse("Do you want to autogenerate an account number? (y/n) : ")) {
		case "y":
			StringBuilder sb = new StringBuilder();
			sb.append("RO");
			sb.append(RandomStringUtils.random(22, true, true));
			accountNumber = sb.toString();
			break;
		default:
			accountNumber = CONSOLE.printForResponse("-> account number : ");

			if (accountNumber.length() != 24) {
				return "Account number has wrong length.";
			}

			if (!accountNumber.startsWith("RO")) {
				return "Account number should start with RO.";
			}
			break;
		}

		String accountType = CONSOLE.printForResponse("-> account type : ");

		if (!AccountType.isType(accountType)) {
			return "Account type not supported.";
		}

		BigDecimal balance;
		switch (CONSOLE.printForResponse("Do you want to add a sum right away? (y/n) : ")) {
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

		return new AccountRepository().create(accountNumber, balance, accountType);
	}

	private String transfer() {
		// list all of the user's accounts
		CONSOLE.printTable(CreateTable.createAccountsListTable(user.getAccounts(), "Number", "Balance", "Type"));

		// get source account
		String accountNumber = CONSOLE.printForResponse("Select one of your accounts: \n -> account : ");
		Account accountFrom = ACCOUNT_REPOSITORY.getForCurrentUserByNumber(accountNumber);

		if (accountFrom != null) {
			String accountType = accountFrom.getAccountType();

			// list all compatible accounts
			List<Account> destinationAccounts = ACCOUNT_REPOSITORY.getForCurrentUserByTypeExcept(accountType,
					accountNumber);
			CONSOLE.printTable(CreateTable.createAccountsListTable(destinationAccounts, "Number", "Balance", "Type"));

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
					return ACCOUNT_REPOSITORY.transfer(accountFrom, accountTo, amount);
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
