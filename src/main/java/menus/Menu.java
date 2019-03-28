package menus;

import utils.ConsoleController;

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
	abstract void printTitle();

}
