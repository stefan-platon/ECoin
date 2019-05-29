package utils;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class ConsoleController {

	Scanner scanner = new Scanner(System.in);

	public void print(String string) {
		System.out.println(string);
	}

	public String printForResponse(String string) {
		System.out.print(string);
		return scanner.nextLine();
	}

	public void printMultiple(String... strings) {
		StringBuilder builder = new StringBuilder();

		for (String string : strings) {
			builder.append(string);
		}

		System.out.println(builder.toString());
	}

	public void printTable(String[][] elements) {
		int i, j, difference, lpad, rpad;
		int lineNumber = elements.length;
		int colNumber = elements[0].length;

		StringBuilder builder = new StringBuilder();

		// find the longest element
		int maxLength = 0;
		for (i = 0; i < lineNumber; i++) {
			for (j = 0; j < colNumber; j++) {
				if (elements[i][j].length() > maxLength) {
					maxLength = elements[i][j].length();
				}
			}
		}

		for (i = 0; i < lineNumber; i++) {
			// print top line border
			for (j = 0; j < colNumber; j++) {
				builder.append("+");
				builder.append(StringUtils.repeat("-", maxLength + 2));
			}
			builder.append("+\n|");

			// print line data
			for (j = 0; j < colNumber; j++) {
				difference = maxLength - elements[i][j].length() + 2;
				if (difference % 2 == 0) {
					lpad = rpad = difference / 2;
				} else {
					lpad = difference / 2;
					rpad = difference / 2 + 1;
				}

				elements[i][j] = StringUtils.leftPad(elements[i][j], lpad + elements[i][j].length(), " ");
				elements[i][j] = StringUtils.rightPad(elements[i][j], elements[i][j].length() + rpad, " ");
				builder.append(elements[i][j]);

				builder.append("|");

			}
			builder.append("\n");
		}
		// print final line border
		for (j = 0; j < colNumber; j++) {
			builder.append("+");
			builder.append(StringUtils.repeat("-", maxLength + 2));
		}
		builder.append("+");

		System.out.println(builder.toString());
	}

	public String getLine() {
		return scanner.nextLine();
	}

	public void close() {
		scanner.close();
	}

}
