package menus;

import java.util.List;

import controllers.UserController;
import utils.ConsoleController;
import utils.FileController;

abstract class Menu {

	protected String title = null;

	protected static UserController user = null;

	protected static final ConsoleController CONSOLE = new ConsoleController();

	/**
	 * Function responsible for interacting with the user and executing commands.
	 */
	public abstract void show();

	/**
	 * Print the greetings message to the console.
	 */
	void printTitle(String path) {
		if (title == null) {
			FileController fileReader = new FileController();
			List<String[]> fileContent = fileReader.readResource(path, "\n");

			StringBuilder builder = new StringBuilder();
			fileContent.forEach((line) -> {
				builder.append(line[0] + "\n");
			});
			title = builder.toString();

			CONSOLE.print(title);
		} else {
			CONSOLE.print(title);
		}

		CONSOLE.print("Type 'man' if you want to see available commands!");
	}

}
