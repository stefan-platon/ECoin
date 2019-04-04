package utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import controllers.AccountController;

public class Utils {

	public static final String[][] createAccountsListTable(List<AccountController> accounts, String... columns) {
		String[][] elements = new String[accounts.size() + 1][columns.length];

		// set column headers
		for (int col = 0; col < columns.length; col++) {
			elements[0][col] = columns[col];
		}

		// fill table with accounts data
		int line = 1;
		for (AccountController account : accounts) {
			for (int col = 0; col < columns.length; col++) {
				switch (elements[0][col]) {
				case "User":
					elements[line][col] = account.getUsername();
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

	/**
	 * Method to populate a hash set with given elements
	 * 
	 * @param objs
	 * @return
	 */
	@SafeVarargs
	public static final <T> Set<T> newHashSet(T... objs) {
		Set<T> set = new HashSet<T>();
		Collections.addAll(set, objs);
		return set;
	}

}
