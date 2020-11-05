package app;

import java.util.Scanner;

public class EnrolUser {
	
	public static void main(String argv[]) {
		
		// Create password file
		Password passwd = new Password();
		
		// Begin enrollment UI
		System.out.println("\n SecVault Investments, Inc.");
		System.out.println("____________________________");
		
		Scanner scanner = new Scanner(System.in);
		
		String response;
		do {
			// Prompt for username
			String username;
			do {
				System.out.println("Username: ");
				username = scanner.nextLine();
				username = username.trim();
			} while (username.isEmpty() || username.equals("/n"));
			
			// Prompt for password
			String password;
			do {
				System.out.println("Password: ");
				password = scanner.nextLine();
			} while (!passwd.checkPassword(username, password));
			
			// Prompt for name
			String name;
			do {
				System.out.println("Name: ");
				name = scanner.nextLine();
				name = name.trim();
			} while (name.isEmpty() || name.equals("/n"));
			
			// Prompt for phone number
			String phone;
			do {
				System.out.println("Phone number: ");
				phone = scanner.nextLine();
				phone = phone.trim();
			} while (phone.isEmpty() || phone.equals("/n"));
			
			// Prompt for email
			String email;
			do {
				System.out.println("Email address: ");
				email = scanner.nextLine();
				email = email.trim();
			} while (email.isEmpty() || email.equals("/n"));
			
			// Print role options
			System.out.println("Role options-\n");
			for(RoleEnum role : RoleEnum.values()) {
				System.out.println(role.toString());
			}
			
			// Prompt for user role
			String role;
			do {
				System.out.println("Enter role: ");
				role = scanner.nextLine();
				role = role.trim();
			} while (!passwd.checkRole(role));
			
			// Create record for user
			if(passwd.addRecord(username, password, role, name, phone, email)) {
				System.out.println("USER SUCCESSFULLY ENROLLED");
			} else {
				System.err.println("Unable to create user record.");
				System.exit(1);
			}
			
			// Prompt user to register another user
			do {
				System.out.println("Enrol another user (y/n)");
				response = scanner.nextLine();
				response = response.trim();
			} while (!response.equals("y") && !response.equals("n"));
		} while (response.equals("y"));
		
		scanner.close();
		System.out.println("Enrollment complete.");
		System.exit(0);
	}

}
