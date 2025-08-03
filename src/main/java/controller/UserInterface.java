package controller;

import java.util.Scanner;

public class UserInterface {
	
	// Shared scanner for reading user input from the console.
		static public Scanner sc = new Scanner(System.in);

		// Display the menu to login or create account
		public static int menuLoginOrCreate() {
			int option;
			do {
				System.out.println("Tibia RPG - Main Menu");
				System.out.println("1] Login");
				System.out.println("2] Create Account");
				System.out.println("0] Exit");
				option = sc.nextInt();
				sc.nextLine();
			} while (option < 0 || option > 2);
			return option;
		}

		// Menu: Character options after successful login.
		public static int menuCharacterOptions() {
			int option = -1;
			do {
				System.out.println("Character Menu");
				System.out.println("1] Create New Character");
				System.out.println("2] View All Character");
				System.out.println("3] Delete Character");
				System.out.println("4] Update Character");
				System.out.println("5] Battle Characters!");
				System.out.println("0] Logout");

				if (sc.hasNextInt()) {
					option = sc.nextInt();
					sc.nextLine();
				} else {
					System.out.println("Please enter a valid number.");
					sc.nextLine();
				}
			} while (option < 0 || option > 5);
			return option;
		}

		// Prompts user to enter a new account's username and password.
		public static String[] createAccountPrompt() {
			String[] account = new String[2];
			System.out.println("Create New Account");
			System.out.println("Enter Username: ");
			account[0] = sc.nextLine();
			System.out.println("Enter password: ");
			account[1] = sc.nextLine();
			return account;
		}

		// Prompt for login using their username and password.
		public static String[] loginPrompt() {
			String[] credentials = new String[2];
			System.out.println("Login");
			System.out.println("Username: ");
			credentials[0] = sc.nextLine();
			System.out.println("Password: ");
			credentials[1] = sc.nextLine();
			return credentials;
		}

}
