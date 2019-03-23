package login;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Class responsible for storing and manipulating all credentials.
 */
class Credentials {
	/**
	 * Instance of type Singleton.
	 */
	private static Credentials instance = null;
	/**
	 * The path to the file containing all the credentials.
	 */
	private final String loginFilePath = "file/login_credentials.txt";
	/**
	 * Hash map to store credentials in memory.
	 */
	private Map<String, String> credentials;

	/**
	 * Read and store credentials in memory.
	 */
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

	/**
	 * Create or return instance of this class.
	 * 
	 * @return class instance
	 */
	public static Credentials getInstance() {
		if (instance == null)
			instance = new Credentials();

		return instance;
	}

	/**
	 * Check if given credentials are valid.
	 * 
	 * @param username
	 * @param password
	 * @return true or false
	 */
	public boolean checkCredentials(String username, String password) {
		if (credentials.containsKey(username) && credentials.get(username).equals(password)) {
			return true;
		} else {
			return false;
		}
	}

}
