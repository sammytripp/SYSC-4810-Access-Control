package app;

import java.io.File;
import java.io.IOException;

public class Password {
	
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
		
	}
	
	
	public boolean addRecord(String username, String password, String role, String name, String phone, String email) {
		return true;
	}
	
	public String retrieveRecord() {
		return null;
	}
	
	public boolean checkPassword(String password) {
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
	
	
	
}
