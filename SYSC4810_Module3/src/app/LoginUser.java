package app;

import java.util.Scanner;

public class LoginUser {
	
	public static void main(String argv[]) {
 
		// Create password file
		Password passwd = new Password();
		
		// Begin enrollment UI
		System.out.println("\n SecVault Investments, Inc.");
		System.out.println("____________________________");
		
		Scanner scanner = new Scanner(System.in);
		String username;
		String password;
		do {
			// Prompt for username
			do {
				System.out.println("Username: ");
				username = scanner.nextLine();
				username = username.trim();
			} while (username.isEmpty() || username.equals("/n"));
			
			// Prompt for password
			do {
				System.out.println("Password: ");
				password = scanner.nextLine();
				password = password.trim();
			} while (password.isEmpty() || password.equals("/n"));
			
			
			// Retrieve user record from login information
			User user = passwd.retrieveRecord(username, password);
			if (user != null) {
				AccessControl policy = new AccessControl();
				
				// Enforce ABAC
				if(policy.enforceABAC(user)) {
					System.out.println("LOGIN SUCCESSFUL!");
					System.out.println("Welcome " + user.getUsername());
					
					// Print available actions
					System.out.println(user.getRole().toString());
					boolean processActions = true;
					String input;
					String [] delimitedInput;
					while (processActions) {
						System.out.println("Enter action (or 'exit'):");
						input = scanner.nextLine();
						delimitedInput = input.split(" ");
						if (delimitedInput.length > 1) {
							processActions = policy.performAction(user, delimitedInput[0], delimitedInput[1]);
						} else {
							processActions = policy.performAction(user, delimitedInput[0], null);
						}
					}
				}
			} else System.err.println("Unable to validate password.");
		} while (true);
	}
}
