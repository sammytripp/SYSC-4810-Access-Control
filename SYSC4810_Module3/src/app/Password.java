package app;

import java.io.File;
import java.io.IOException;

public class Password {
	
	/**
	 * Default password file constructor.
	 */
	public Password(String directory) {
		// Create passwd.txt file
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
	
	
	public boolean addRecord() {
		return false;
	}
	
	public String retrieveRecord() {
		return null;
	}
	
	
	
}
