package main;

import java.util.List;

import controller.TibiaAccountManager;
import controller.TibiaCharacterManager;
import controller.UserInterface;

import model.Knight;
import model.Sorcerer;
import model.TibiaCharacter;
import service.BattleManager;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Creating instances of account manager
		// To handle login and registration functionality.
		TibiaAccountManager tibAccntMan = new TibiaAccountManager();
		// Instance of character to handle creating, viewing, updating, and deleting
		// characters.
		TibiaCharacterManager tibCharMan = new TibiaCharacterManager();

		// Boolean flag to keep main menu loop running.
		// Set to true initially, and the program will keep running until this is set to
		// false.
		boolean running = true;

		// Outer loop for main menu (login, register, exit)
		while (running) {
			// Display login/create account menu and get user's selection
			int option = UserInterface.menuLoginOrCreate();

			switch (option) {
			case 1: { // Login option
				// Prompt user for username and password.
				String[] credentials = UserInterface.loginPrompt();
				String username = credentials[0];
				String password = credentials[1];

				// Attempt login using account manager.
				boolean success = tibAccntMan.login(username, password);

				if (success) {
					// Retrieve user ID for further operations
					int userId = tibAccntMan.getUserID(username, password);
					System.out.println("Login successful...");

					// Boolean flag to keep user logged in inside the character menu loop.
					boolean loggedIn = true;

					// Character manager menu loop
					while (loggedIn) {
						int charOption = UserInterface.menuCharacterOptions();

						switch (charOption) {
						case 1: // Create new character
							System.out.println("Character Name: ");
							String name = UserInterface.sc.nextLine();

							System.out.println("Level: ");
							int level = Integer.parseInt(UserInterface.sc.nextLine());

							System.out.println("Description: ");
							String description = UserInterface.sc.nextLine();

							System.out.println("Do you wish for promotion?");
							boolean isPromoted = Boolean.parseBoolean(UserInterface.sc.nextLine());

							System.out.println("Health: ");
							double health = Double.parseDouble(UserInterface.sc.nextLine());

							System.out.println("Enter vocation (Knight, Drui, Sorcerer, Paladin): ");
							String baseVocation = UserInterface.sc.nextLine();

							// Determine correct subclass based on vocation
							TibiaCharacter character;

							switch (baseVocation.toLowerCase()) {
							case "knight":
								character = new Knight(name, level, description, isPromoted, health);
								break;
							case "sorcerer":
								character = new Sorcerer(name, level, description, isPromoted, health);
								break;
							default:
								System.out.println("Unknown vocation. Skipping character: " + name);
								continue; // Skip loop
							}

							// Create the character using the character manager
							boolean created = tibCharMan.createCharacter(userId, character.getName(),
									character.getLevel(), character.getDescription(), character.getIsPromoted(),
									character.getHealth(), character.getVocation());

							System.out.println(created ? "Character created!" : "Failed to create character.");
							break;

						case 2: // View all characters for the logged in user
							List<TibiaCharacter> characters = tibCharMan.viewAllCharacters();

							if (characters.isEmpty()) {
								System.out.println("No characters found.");
							} else {
								System.out.println("Your Characters:");
								for (TibiaCharacter c : characters) {
									System.out.println(c);
								}
							}
							break;

						case 3: // Delete a character
							System.out.println("Enter Character ID to delete: ");
							int deleteId = Integer.parseInt(UserInterface.sc.nextLine());

							boolean deleted = tibCharMan.deleteCharacter(deleteId);
							System.out.println(deleted ? "Character deleted." : "Deletion failed.");
							break;

						case 4: // Update character info
							System.out.println("Enter Character ID to update: ");
							int updateId = Integer.parseInt(UserInterface.sc.nextLine());

							System.out.println("New Name: ");
							String newName = UserInterface.sc.nextLine();

							System.out.println("New Description: ");
							String newDesc = UserInterface.sc.nextLine();

							boolean updated = tibCharMan.updateCharacter(updateId, newName, newDesc);
							System.out.println(updated ? "Character updated." : "Update failed.");
							break;

						case 0: // Logout option
							loggedIn = false;
							System.out.println("Logged out.");
							break;
							
						case 5: // Battle characters option
							  List<TibiaCharacter> allCharacters = tibCharMan.viewAllCharacters();

							    if (allCharacters.size() < 2) {
							        System.out.println("At least two characters are needed for battle.");
							        break;
							    }

							    // Show characters with index
							    System.out.println("Choose two characters to battle:");
							    for (int i = 0; i < allCharacters.size(); i++) {
							        System.out.println(i + ": " + allCharacters.get(i).getName());
							    }

							    System.out.print("Enter index of first character: ");
							    int index1 = Integer.parseInt(UserInterface.sc.nextLine());

							    System.out.print("Enter index of second character: ");
							    int index2 = Integer.parseInt(UserInterface.sc.nextLine());

							    if (index1 == index2 || index1 < 0 || index2 < 0 || 
							        index1 >= allCharacters.size() || index2 >= allCharacters.size()) {
							        System.out.println("Invalid character selection.");
							        break;
							    }

							    TibiaCharacter c1 = allCharacters.get(index1);
							    TibiaCharacter c2 = allCharacters.get(index2);

							    BattleManager.startBattle(c1, c2);
							    break;
						}
					}
				} else {
					// If login fails
					System.out.println("Login failed. Check username or password.");
				}
				break;
			}

			case 2: { // Create new account
				// Prompt user to enter new account credentials
				String[] newAccount = UserInterface.createAccountPrompt();

				// Register account through account manager
				boolean registered = tibAccntMan.register(newAccount[0], newAccount[1]);
				System.out.println(registered ? "Account created!" : "Account creation failed.");
				break;
			}

			case 0: // Exit the program
				running = false;
				System.out.println("Goodbye!");
				break;
			}
		}
	}

}
