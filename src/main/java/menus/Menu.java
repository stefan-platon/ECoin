package menus;

import java.util.List;

import utils.ConsoleController;
import utils.FileController;

abstract class Menu {
	
	protected String title = null;
	
	protected ConsoleController console = new ConsoleController();

	/**
	 * Function responsible for interacting with the user and executing commands.
	 */
	public abstract void show();
	
	/**
	 * Print the greetings message to the console.
	 */
	void printTitle(String TILE_FILE_PATH) {
		if (title == null) {
			FileController fileReader = new FileController();
			List<String[]> fileContent = fileReader.readResource(TILE_FILE_PATH, "\n");

			StringBuilder builder = new StringBuilder();
			fileContent.forEach((line) -> {
				builder.append(line[0] + "\n");
			});
			title = builder.toString();

			console.print(title);
		} else {
			console.print(title);
		}

		console.print("Type 'man' if you want to see available commands!");
	}

}
