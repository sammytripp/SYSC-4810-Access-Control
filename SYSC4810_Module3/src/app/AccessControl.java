package app;

import java.util.ArrayList;
import java.util.Stack;

public class AccessControl {
	
	// SecVault Investments, Inc. system roles
	private Role client;
	private Role premiumClient;
	private Role financialAdvisor;
	private Role financialPlanner;
	private Role investmentAnalyst;
	private Role teller;
	private Role technicalSupport;
	private Role complianceOfficer;
	
	// User management
	public ArrayList<User> userList; // ??
	public Stack<User> modifications;
	public Stack<User> accountAccessGranted;
	
	
	/**
	 * Construct an access control matrix.
	 */
	public AccessControl() {
		 
		// Initialize access control for all system roles
		client = new Role(RoleEnum.CLIENT);
		premiumClient = new Role(RoleEnum.PREMIUM_CLIENT);
		financialAdvisor = new Role(RoleEnum.FINANCIAL_ADVISOR);
		financialPlanner = new Role(RoleEnum.FINANCIAL_PLANNER);
		investmentAnalyst = new Role(RoleEnum.INVESTMENT_ANALYST);
		teller = new Role(RoleEnum.TELLER);
		technicalSupport = new Role(RoleEnum.TECHNICAL_SUPPORT);
		complianceOfficer = new Role(RoleEnum.COMPLIANCE_OFFICER);
		
		setPermissions();
		
		// Initialize new stack of modifications for CO to validate
		modifications = new Stack<User>();
		
		// Initialize new stack of users that have granted technical support
		// access to view their account
		accountAccessGranted = new Stack<User>();
		
	}
	
	public Role getRole(RoleEnum roleEnum) {
		Role role = null;
		switch(roleEnum) {
		case CLIENT:
			role = client;
			break;
		case COMPLIANCE_OFFICER:
			role = complianceOfficer;
			break;
		case FINANCIAL_ADVISOR:
			role = financialAdvisor;
			break;
		case FINANCIAL_PLANNER:
			role = financialPlanner;
			break;
		case INVESTMENT_ANALYST:
			role = investmentAnalyst;
			break;
		case PREMIUM_CLIENT:
			role = premiumClient;
			break;
		case TECHNICAL_SUPPORT:
			role = technicalSupport;
			break;
		case TELLER:
			role = teller;
			break;
		default:
			System.err.println("Invalid RoleEnum received.");
			System.exit(1);
			break;
		}
		return role;
	
	}
	
	/**
	 * Internal method to set the default read/write permissions
	 * for each system role.
	 */
	private void setPermissions() {
		// Set CLIENT permissions
		String[] clientReadActions = {
				"client_information",
				"account_balance",
				"investment_portfolio",
				"financial_advisor_contact"
		};
		String[] clientWriteActions = {
				"client_information"
		};
		String[] clientSpecialActions = {
				"request_support"
		};
		client.addReadActions(clientReadActions);
		client.addWriteActions(clientWriteActions);
		client.addSpecialActions(clientSpecialActions);
		
		// Set PREMIUM_CLIENT permissions
		String[] premiumClientReadActions = {
				"client_information",
				"account_balance",
				"investment_portfolio",
				"financial_advisor_contact",
				"financial_planner_contact",
				"investment_analyst_contact"
		};
		String[] premiumClientWriteActions = {
				"client_information",
				"investment_portfolio"
		};
		premiumClient.addReadActions(premiumClientReadActions);
		premiumClient.addWriteActions(premiumClientWriteActions);
		premiumClient.addSpecialActions(clientSpecialActions);
		
		// Set FINANCIAL_ADVISOR permissions
		String[] FAReadActions = {
				"client_information",
				"account_balance",
				"investment_portfolio",
				"financial_advisor_contact",
				"financial_planner_contact",
				"investment_analyst_contact",
				"private_consumer_instruments"
		};
		String[] FAWriteActions = {
				"investment_portfolio",
				"financial_advisor_contact"
		};
		financialAdvisor.addReadActions(FAReadActions);
		financialAdvisor.addWriteActions(FAWriteActions);
		
		// Set FINANCIAL_PLANNER permissions
		String[] FPReadActions = {
				"client_information",
				"account_balance",
				"investment_portfolio",
				"financial_advisor_contact",
				"financial_planner_contact",
				"investment_analyst_contact",
				"private_consumer_instruments",
				"money_market_instruments"
		};
		String[] FPWriteActions = {
				"investment_portfolio",
				"financial_planner_contact"
		};
		financialPlanner.addReadActions(FPReadActions);
		financialPlanner.addWriteActions(FPWriteActions);
		
		// Set INVESTMENT_ANALYST permissions
		String[] IAReadActions = {
				"client_information",
				"account_balance",
				"investment_portfolio",
				"financial_advisor_contact",
				"financial_planner_contact",
				"investment_analyst_contact",
				"private_consumer_instruments",
				"money_market_instruments",
				"interest_instruments",
				"derivatives_trading"
		};
		String[] IAWriteActions = {
				"investment_portfolio",
				"investment_analyst_contact"
		};
		investmentAnalyst.addReadActions(IAReadActions);
		investmentAnalyst.addWriteActions(IAWriteActions);
		
		// Set TELLER permissions
		String[] tellerReadActions = {
				"client_information",
				"account_balance",
				"investment_portfolio",
				"financial_advisor_contact",
				"financial_planner_contact",
				"investment_analyst_contact"
		};
		teller.addReadActions(tellerReadActions);
		
		// Set TECHNICAL_SUPPORT permissions
		String[] technicalSupportReadActions = {
				"client_information",
				"financial_advisor_contact",
				"financial_planner_contact",
				"investment_analyst_contact"
		};
		technicalSupport.addReadActions(technicalSupportReadActions);
		
		// Set COMPLIANCE_OFFICER permissions
		String[] COReadActions = {
				"client_information",
				"investment_portfolio"
		};
		complianceOfficer.addReadActions(COReadActions);
	}	

	
	public boolean performAction(User user, String action, String object) {
		Role role = user.getRole();
		if (action == "read") {
			
			
		}
		return false;
	}
	

}
