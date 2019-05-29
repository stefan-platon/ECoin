package utils;

import java.util.List;

import model.Account;

public class CreateTable {

	public static final String[][] createAccountsListTable(List<Account> accounts, String... columns) {
		String[][] elements = new String[accounts.size() + 1][columns.length];

		// set column headers
		for (int col = 0; col < columns.length; col++) {
			elements[0][col] = columns[col];
		}

		// fill table with accounts data
		int line = 1;
		for (Account account : accounts) {
			for (int col = 0; col < columns.length; col++) {
				switch (elements[0][col]) {
				case "User":
					elements[line][col] = account.getUserObj().getUsername();
					break;
				case "Number":
					elements[line][col] = account.getAccountNumber();
					break;
				case "Balance":
					elements[line][col] = account.getBalance().toString();
					break;
				case "Type":
					elements[line][col] = account.getAccountType();
					break;
				}
			}
			line++;
		}

		return elements;
	}

}
