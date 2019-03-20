package login;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Credentials {
	// static variable instance of type Singleton
	private static Credentials instance = null;

	// path to the credentials file
	private String loginFilePath = "file/login_credentials.txt";

	// hash map to store credentials in memory
	private Map<String, String> credentials;

	// store credentials in memory
	private Credentials() throws IndexOutOfBoundsException, NullPointerException {
		// get file content from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(loginFilePath);

		// parse file data and load it into memory
		try (Scanner scanner = new Scanner(inputStream)) {
			credentials = new HashMap<String, String>();

			// variables to help split the user name from password
			String line;
			String[] splitLine;
			while (scanner.hasNext()) {
				line = scanner.nextLine();
				splitLine = line.split(" ");
				credentials.put(splitLine[0], splitLine[1]);
			}
		}
	}

	// create or return instance of class
	public static Credentials getInstance() {
		if (instance == null)
			instance = new Credentials();

		return instance;
	}

	// check if given credentials are valid
	public boolean checkCredentials(String username, String password) {
		if (credentials.containsKey(username) && credentials.get(username).equals(password)) {
			return true;
		} else {
			return false;
		}
	}

}
