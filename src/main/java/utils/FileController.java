package utils;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

public class FileController {

	private static final Logger logger = LogManager.getLogger(FileController.class);

	public List<String[]> read(String path, String delimiter) {
		// get file content
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(path);

		// parse file content
		List<String[]> response = new ArrayList<>();
		try (Scanner scanner = new Scanner(inputStream)) {
			String line;
			while (scanner.hasNext()) {
				line = scanner.nextLine();
				response.add(line.split(delimiter, -1));
			}
		} catch (NullPointerException e) {
			logger.error("file not found : " + path);
			System.out.println("Resource file not found. Please contact support.");
			System.exit(-1);
		}

		return response;
	}

}
