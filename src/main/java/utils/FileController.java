package utils;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

public class FileController {

	private static final Logger LOGGER = LogManager.getLogger(FileController.class);

	public List<String[]> read(String path, String delimiter) {
		// parse file content
		List<String[]> response = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File(path))) {
			String line;
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				response.add(line.split(delimiter, -1));
			}
		} catch (Exception e) {
			LOGGER.error("file not found : " + path);
			System.out.println("Database file not found. Please contact support or try again later.");
		}

		return response;
	}

	public List<String[]> readResource(String path, String delimiter) {
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
		} catch (Exception e) {
			LOGGER.error("file not found : " + path);
			System.out.println("Resource file not found. Please contact support or try again later.");
		}

		return response;
	}

	public void write(String path, Object object) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
			bw.write(object.toString());
		} catch (Exception e) {
			LOGGER.error("could not insert " + object.getClass() + " : " + path + " : " + e.getMessage());
			System.out.println(
					"There was a problem while saving the data. If the problem persists, please contact support.");
		}
	}

}
