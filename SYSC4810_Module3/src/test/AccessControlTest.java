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
		
		// Test access control policy enforcement for object accesses
		testPerformAction();
		
	}
	
	public static void testRoles() {
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
		
		System.out.println("\n*Test FINANCIAL_ADVISOR permissions*\n");
		User financialAdvisor = new User("kchang", RoleEnum.FINANCIAL_ADVISOR, "Kelsie Chang", "613 333 3333", "kchang@secvault.ca");
		System.out.println("Expected:");
		System.out.println(
				"Role: Financial Advisor\n" 
				+ "read <action>: "
				+ "[client_information, account_balance, investment_portfolio, financial_advisor_contact, "
				+ "financial_planner_contact, investment_analyst_contact, private_consumer_instruments]\n"
				+ "write <action>: "
				+ "[investment_portfolio, financial_advisor_contact]");
		System.out.println("Actual:");
		System.out.println(financialAdvisor.getRole().toString());
		
		System.out.println("\n*Test FINANCIAL_PLANNER permissions*\n");
		User financialPlanner = new User("smacleod", RoleEnum.FINANCIAL_PLANNER, "Seth Macleod", "613 444 4444", "smacleod@secvault.ca");
		System.out.println("Expected:");
		System.out.println(
				"Role: Financial Planner\n" 
				+ "read <action>: "
				+ "[client_information, account_balance, investment_portfolio, financial_advisor_contact, "
				+ "financial_planner_contact, investment_analyst_contact, private_consumer_instruments, money_market_instruments]\n"
				+ "write <action>: "
				+ "[investment_portfolio, financial_planner_contact]");
		System.out.println("Actual:");
		System.out.println(financialPlanner.getRole().toString());
		
		System.out.println("\n*Test INVESTMENT_ANALYST permissions*\n");
		User investmentAnalyst = new User("jcallahan", RoleEnum.INVESTMENT_ANALYST, "Jimmie Callahan", "613 555 5555", "jcallahan@secvault.ca");
		System.out.println("Expected:");
		System.out.println(
				"Role: Investment Analyst\n" 
				+ "read <action>: "
				+ "[client_information, account_balance, investment_portfolio, financial_advisor_contact, "
				+ "financial_planner_contact, investment_analyst_contact, private_consumer_instruments, "
				+ "money_market_instruments, interest_instruments, derivatives_trading]\n"
				+ "write <action>: "
				+ "[investment_portfolio, investment_analyst_contact]");
		System.out.println("Actual:");
		System.out.println(investmentAnalyst.getRole().toString());
		
		System.out.println("\n*Test TELLER permissions*\n");
		User teller = new User("wgarza", RoleEnum.TELLER, "Willow Garza", "613 666 6666", "wgarza@secvault.ca");
		System.out.println("Expected:");
		System.out.println(
				"Role: Teller\n" 
				+ "read <action>: "
				+ "[client_information, account_balance, investment_portfolio, financial_advisor_contact, "
				+ "financial_planner_contact, investment_analyst_contact]\n"
				+ "write <action>: []");
		System.out.println("Actual:");
		System.out.println(teller.getRole().toString());
		
		System.out.println("\n*Test TECHNICAL_SUPPORT permissions*\n");
		User technicalSupport = new User("clopez", RoleEnum.TECHNICAL_SUPPORT, "Caroline Lopez", "613 777 7777", "clopez@secvault.ca");
		System.out.println("Expected:");
		System.out.println(
				"Role: Technical Support\n" 
				+ "read <action>: "
				+ "[client_information, financial_advisor_contact, financial_planner_contact, investment_analyst_contact]\n"
				+ "write <action>: []\n"
				+ "Additional: [view_support_tickets]");
		System.out.println("Actual:");
		System.out.println(technicalSupport.getRole().toString());
		
		System.out.println("\n*Test COMPLIANCE_OFFICER permissions*\n");
		User complianceOfficer = new User("mwu", RoleEnum.COMPLIANCE_OFFICER, "Malikah Wu", "613 888 8888", "mwu@secvault.ca");
		System.out.println("Expected:");
		System.out.println(
				"Role: Compliance Officer\n" 
				+ "read <action>: "
				+ "[client_information, investment_portfolio]\n"
				+ "write <action>: []\n"
				+ "Additional: [validate_modifications]");
		System.out.println("Actual:");
		System.out.println(complianceOfficer.getRole().toString());
	}
	
	public static void testPerformAction() {
		AccessControl policy = new AccessControl();
		
		System.out.println("\n*Test authenticated read <action>*\n");
		User premiumClient = new User("nperez", RoleEnum.PREMIUM_CLIENT, "Nick Perez", "613 222 2222", "nperez@secvault.ca");
		System.out.println("Expected: ");
		System.out.println("ACCESS GRANTED");
		System.out.println("Actual: ");
		policy.performAction(premiumClient, "read", "financial_planner_contact");
		
		System.out.println("\n*Test authenticated write <action>*\n");
		System.out.println("Expected: ");
		System.out.println("ACCESS GRANTED");
		System.out.println("Actual: ");
		policy.performAction(premiumClient, "write", "client_information");
		
		System.out.println("\n*Test invalid read <action>*\n");
		System.out.println("Expected: ");
		System.out.println("Try again.");
		System.out.println("Actual: ");
		policy.performAction(premiumClient, "read", "derivatives_trading");
		
		System.out.println("\n*Test invalid write <action>*\n");
		System.out.println("Expected: ");
		System.out.println("Try again.");
		System.out.println("Actual: ");
		policy.performAction(premiumClient, "write", "account_balance");
		
		System.out.println("\n*Test modification to investment portfolio*\n");
		User financialPlanner = new User("smacleod", RoleEnum.FINANCIAL_PLANNER, "Seth Macleod", "613 444 4444", "smacleod@secvault.ca");
		System.out.println("Expected: ");
		System.out.println("ACCESS GRANTED.\nModification awaiting validation.");
		System.out.println("Actual: ");
		policy.performAction(financialPlanner, "write", "investment_portfolio");
		
		User financialPlanner2 = new User("nwilkins", RoleEnum.FINANCIAL_ADVISOR, "Nelson Wilkins", "705 333 444", "nwilkins@secvault.ca");
		policy.performAction(financialPlanner2, "write", "investment_portfolio");
		
		System.out.println("\n*Test validate modifications (compliance officer)*\n");
		User complianceOfficer = new User("mwu", RoleEnum.COMPLIANCE_OFFICER, "Malikah Wu", "613 888 8888", "mwu@secvault.ca");
		System.out.println("Expected: ");
		System.out.println("Changes by <user> validated.");
		System.out.println("Actual: ");
		policy.performAction(complianceOfficer, "validate_modifications", null);
		
		System.out.println("\n*Test client support request*\n");
		System.out.println("Expected: ");
		System.out.println("Permissions provided to technical support.");
		System.out.println("Actual: ");
		policy.performAction(premiumClient, "request_support", null);
		
		System.out.println("\n*Test permissions elevation (technical support)*\n");
		User technicalSupport = new User("clopez", RoleEnum.TECHNICAL_SUPPORT, "Caroline Lopez", "613 777 7777", "clopez@secvault.ca");
		System.out.println("Expected: ");
		System.out.println("Support requested by user.");
		System.out.println("Permissions elevated for this session.");
		System.out.println("Actual: ");
		policy.performAction(technicalSupport, "view_support_tickets", null);
		
		
		System.out.println("\n*Test reading with elevated permissions (technical support)*\n");
		System.out.println("Expected: ");
		System.out.println("ACCESS GRANTED");
		System.out.println("Actual: ");
		policy.performAction(technicalSupport, "read", "investment_portfolio");	
	}

}
