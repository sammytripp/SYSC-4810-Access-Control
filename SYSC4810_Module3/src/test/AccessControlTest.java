package test;

import app.AccessControl;
import app.RoleEnum;
import app.User;

/**
 * Test cases for the AccessControl.java class.
 * 
 * @author Samantha Tripp - Student ID: 101089563
 * 
 */
public class AccessControlTest {
	
	public static void main(String argv[]) {
		
		// Test system roles created by access control policy
		testRoles();
		
	}
	
	public static void testRoles() {
		AccessControl policy = new AccessControl();
		
		System.out.println("*Test CLIENT permissions*\n");
		User client = new User("mlowery", RoleEnum.CLIENT, "Mischa Lowery", "613 111 1111", "mlowery@secvault.ca");
		System.out.println("Expected:");
		System.out.println(
				"Role: Client\n" 
				+ "read <action>: "
				+ "[client_information, account_balance, investment_portfolio, financial_advisor_contact]\n"
				+ "write <action>: "
				+ "[client_information]\n"
				+ "Additional: [request_support]");
		System.out.println("Actual:");
		System.out.println(client.getRole().toString());
		
		System.out.println("\n*Test PREMIUM_CLIENT permissions*\n");
		User premiumClient = new User("nperez", RoleEnum.PREMIUM_CLIENT, "Nick Perez", "613 222 2222", "nperez@secvault.ca");
		System.out.println("Expected:");
		System.out.println(
				"Role: Premium Client\n" 
				+ "read <action>: "
				+ "[client_information, account_balance, investment_portfolio, financial_advisor_contact, "
				+ "financial_planner_contact, investment_analyst_contact]\n"
				+ "write <action>: "
				+ "[client_information, investment_portfolio]\n"
				+ "Additional: [request_support]");
		System.out.println("Actual:");
		System.out.println(premiumClient.getRole().toString());
	}

}
