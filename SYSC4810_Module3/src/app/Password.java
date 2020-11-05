package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Password {
	
	public ArrayList<String> weakPasswords;
	
	/**
	 * Default password file constructor.
	 */
	public Password() {
		
		// Create passwd.txt file in current directory
		File passwd = new File("./passwd.txt");
		try {
			passwd.createNewFile();
			passwd.getParentFile().mkdirs();
			
		} catch (IOException e) {
			System.err.println("Unable to create passwd.txt file.");
			e.printStackTrace();
		} 
		
		// Set permissions for passwd.txt
		// Read - all system users
		// Write - owner
		passwd.setReadable(true, false);
		passwd.setWritable(true);
		
		// Initialize list of common weak passwords
		String[] passwords = {
				"Qwerty_123!",
				"Qw3rtyu!op",
				"12345@abcde",
				"123456a@A",
				"*98765mnbvc"
		};
		weakPasswords = new ArrayList<String>();
		addWeakPasswords(passwords);
		
	}
	
	/**
	 * Add user record to password file.
	 * Format - username : salt : hash : role : contact_information
	 * 
	 * @param username String
	 * @param password String
	 * @param role String
	 * @param name String
	 * @param phone String
	 * @param email String
	 * @return boolean
	 */
	public boolean addRecord(String username, String password, String role, String name, String phone, String email) {
		
		// Generate 16-byte salt
		Random random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		String saltString = Arrays.toString(salt);
		
		// Create hashed password + salt
		byte[] hash = hashPW(password, salt);
		
		// Convert hash to string
		String hashString = Arrays.toString(hash);
		
		// Create record string
		String record = "";
		record += username + " : " + saltString + " : " + hashString + " : " + role;
		record += " : " + name + "," + phone + "," + email;
		
		
		// Append record to passwd.txt
		Writer output;
		try {
			output = new BufferedWriter(new FileWriter("./passwd.txt", true));
			output.append(record +"\n");
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		// Success!
		return true;
	}
	
	/**
	 * Retrieve user record from password file.
	 * 
	 * @param username String
	 * @param password String
	 * @return User
	 */
	public User retrieveRecord(String username, String password) {
		User user;
		String[] attributes;
		
		// Parse passwd.txt for input username
		try {
			BufferedReader reader = new BufferedReader(new FileReader("./passwd.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
				// Split line with " : " as delimiter
				attributes = line.split(" : "); 
				if (attributes[0].equals(username)) {
					System.out.println("Made it here");
					// Hash input password using salt from record
					byte[] hash = hashPW(password, attributes[1].getBytes("UTF-8"));
					// Compare hash byte arrays
					if (Arrays.equals(hash, attributes[2].getBytes("UTF-8"))) { 
						// Password verified
						String[] contact = attributes[4].split(",");
						user = new User(attributes[0], RoleEnum.getEnum(attributes[3]), contact[0], contact[1], contact[2]);
						reader.close();
						return user;
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Unable to read password file.");
			e.printStackTrace();
			return null;
		}
		// Unable to validate user
		return null;
	}
	
	/**
	 * Internal method to compute hashed password.
	 * Reference https://www.baeldung.com/java-password-hashing
	 * 
	 * @param password
	 * @param salt
	 * @return Byte array
	 */
	private byte[] hashPW(String password, byte[] salt) {
		byte[] hash = null;
		try {
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
			return hash;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Pro-active password checker.
	 * 
	 * @param password String
	 * @return boolean
	 */
	public boolean checkPassword(String username, String password) {
		
		// Ensure password length is 8-12 characters
		if (password.length() < 8 || password.length() > 12) {
			System.err.println("Password must be 8-12 characters in length.");
			return false;
		}
		
		// Ensure password is not equal to username
		if (password.equals(username)) {
			System.err.println("Password must be different from the provided username.");
			return false;
		}
		
		// Ensure password contains at least one upper case letter, lower case letter, numerical digit, and special character
		// Excludes license plate numbers, because they are numerical and upper case only (E.g. ABCD 123)
		// Excludes numerical date formats (E.g. 2020-11-03)
		// Excludes telephone numbers (E.g. 888 999 1234)
		char[] specialCharacters = {'!', '@', '#', '$', '%', '?', '*'};
		boolean uppercase = false;
		boolean lowercase = false;
		boolean number = false; 
		boolean specialChar = false;
		
		for (int i = 0; i < password.length(); i++) {
			if (Character.isUpperCase(password.charAt(i))) {
				uppercase = true;
			}
			if (Character.isLowerCase(password.charAt(i))) {
				lowercase = true;
			}
			if (Character.isDigit(password.charAt(i))) {
				number = true;
			}
			for(char c : specialCharacters) {
				if (password.charAt(i) == c) {
					specialChar = true;
				}
			}
			if (i == password.length() - 1) {
				if (!uppercase || !lowercase || !number || !specialChar) {
					System.err.println("Password must contain an uppercase character, lowercase character, "
										+"number, and a special character from the set {!, @, #, $, %, ?, *}.");
					return false;
				}
			}	
		}
		
		// Ensure that the password does not match any of the listed weak passwords
		for (String pw : weakPasswords) {
			if (password.equals(pw)) {
				System.err.println("The chosen password is too weak.");
				return false;
			}
		}
		
		// Password OK
		return true;
	}
	
	/**
	 * Check if valid role is provided in user enrollment.
	 * 
	 * @param role String
	 * @return boolean 
	 */
	public boolean checkRole(String role) {
		for(RoleEnum r : RoleEnum.values()) {
			if (r.toString().equals(role)) return true;
		}
		return false;
	}
	
	/**
	 * Dynamically add a password to the list of most common weak passwords.
	 */
	public void addWeakPasswords(String[] passwords) {
		for(String pw : passwords) {
			weakPasswords.add(pw);
		}
	}
	
	
	
}
