package test;

import app.Password;

/**
 * Test cases for the Password.java class, which implements and manages 
 * the password file for SecVault Investments, Inc.
 * 
 * @author Samantha Tripp - Student ID: 101089563
 * 
 */
public class PasswordTest {
	
	public static void main(String argv[]) {
		
		// Test adding records to passwd.txt
		testAddRecord();
		
		// Test retrieving records from passwd.txt
		testRetrieveRecord();
		
		// Test password checker for user enrollment
		testCheckPassword();
	}
	
	public static void testAddRecord() {
		Password passwd = new Password();
		
	}
	
	public static void testRetrieveRecord() {
		
	}
	
	/**
	 * Test cases for checkPassword()
	 */
	public static void testCheckPassword() {
		Password passwd = new Password();
		
		System.out.println("Testing: password too short");
		boolean result = passwd.checkPassword("admin", "pass");
		System.out.println("Expected: false Actual: " + result);
		
		System.out.println("Testing: password too long");
		boolean result2 = passwd.checkPassword("admin", "Iloveeatingbagels");
		System.out.println("Expected: false Actual: " + result2);
		
		System.out.println("Testing: password equal to username");
		boolean result3 = passwd.checkPassword("moderator", "moderator");
		System.out.println("Expected: false Actual: " + result3);
				
		System.out.println("Testing: password without uppercase character");
		boolean result4 = passwd.checkPassword("admin", "thskg67%");
		System.out.println("Expected: false Actual: " + result4);
		
		System.out.println("Testing: password without lowercase character");
		boolean result5 = passwd.checkPassword("admin", "ABCD$123");
		System.out.println("Expected: false Actual: " + result5);
		
		System.out.println("Testing: password without a number");
		boolean result6 = passwd.checkPassword("admin", "Donkey_Ice*");
		System.out.println("Expected: false Actual: " + result6);
		
		System.out.println("Testing: password without a special character");
		boolean result7 = passwd.checkPassword("admin", "GrapeSk8");
		System.out.println("Expected: false Actual: " + result7);
		
		System.out.println("Testing: password present on list of weak passwords");
		boolean result8 = passwd.checkPassword("admin", "Qw3rtyu!op");
		System.out.println("Expected: false Actual: " + result8);
		
		System.out.println("Testing: OK password");
		boolean result9 = passwd.checkPassword("admin", "R!ght_56ikj7");
		System.out.println("Expected: true Actual: " + result9);
	}

}
