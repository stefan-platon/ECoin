package utils;

import java.util.Scanner;

public class ConsoleController {
	
	Scanner scanner = new Scanner(System.in);
	
	public void print(String string) {
		System.out.println(string);
	}
	
	public void printMultiple(String... strings) {
		StringBuilder builder = new StringBuilder();
		
	    for (String string : strings) {
	        builder.append(string + "\n");
	    }
	    
	    System.out.println(builder.toString());
	}
	
	public String getLine() {
		return scanner.nextLine();
	}
	
	public String printForResponse(String string) {
		System.out.print(string);
		return scanner.nextLine();
	}
	
	public void close() {
		scanner.close();
	}

}
