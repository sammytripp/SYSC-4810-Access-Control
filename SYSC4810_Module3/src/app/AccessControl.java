package app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

/**
 * AccessControl.java implements the mechanism for enforcing the access control
 * policy outlined by SecVault Investments, Inc.
 * 
 * @author Samantha Tripp - Student ID: 101089563
 *
 */
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
	
	/**
	 * Return system permissions for indicated role.
	 * 
	 * @param roleEnum
	 * @return Role
	 */
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
	 * Internal method to set the RBAC permissions.
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
		String[] technicalSupportSpecialActions = {
				"view_support_tickets"
		};
		technicalSupport.addReadActions(technicalSupportReadActions);
		technicalSupport.addSpecialActions(technicalSupportSpecialActions);
		
		// Set COMPLIANCE_OFFICER permissions
		String[] COReadActions = {
				"client_information",
				"investment_portfolio"
		};
		String[] COSpecialActions = {
				"validate_modifications"
		};
		complianceOfficer.addReadActions(COReadActions);
		complianceOfficer.addSpecialActions(COSpecialActions);
	}	
	
	/**
	 * Enforce attribute-based access controls specific to an authenticated user.
	 * 
	 * @param User
	 * @return boolean
	 */
	public boolean enforceABAC(User user) {
		
		// Get current time
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH");
	    String time = sdf.format(cal.getTime());
	    System.out.println("Current hour: " + time + ":00");
	    int hour = Integer.parseInt(time);
	    
		// Teller role can only log in to system from 9 - 5.
		if (user.getRole().getRoleEnum().equals(RoleEnum.TELLER)) {
			if (hour < 9 || hour > 16) {
				System.out.println("System access is available between 9:00 to 17:00.");
				return false;
			}
		}
		
		// Add any other ABAC rules ...
		
		return true;
	}

	/**
	 * Implementation of object access control.
	 * 
	 * @param user
	 * @param action
	 * @param object
	 * @return boolean
	 */
	public boolean performAction(User user, String action, String object) {
		Role role = user.getRole();
		if (action.equals("read")) {
			for (String a : role.getReadActions()) {
				if (object.equals(a)) {
					System.out.println("ACCESS GRANTED");
					return true;
				}
			}
			System.out.println("Try again.");
			
		} else if (action.equals("write")) {
			for (String a : role.getWriteActions()) {
				if (object.equals(a)) {
					System.out.println("ACCESS GRANTED");
					// Modifications to investment_portfolio must be validated by compliance officer
					if (a.equals("investment_portfolio")) {
						if (role.getRoleEnum().equals(RoleEnum.PREMIUM_CLIENT) ||
							role.getRoleEnum().equals(RoleEnum.FINANCIAL_ADVISOR) ||
							role.getRoleEnum().equals(RoleEnum.FINANCIAL_PLANNER) ||
							role.getRoleEnum().equals(RoleEnum.INVESTMENT_ANALYST)) {
							System.out.println("Modification awaiting validation.");
							modifications.push(user);
						}
					}
					return true;
				}
			}
			System.out.println("Try again.");
		} else if (action.equals("request_support")) {
			if (role.getRoleEnum().equals(RoleEnum.PREMIUM_CLIENT) ||
				role.getRoleEnum().equals(RoleEnum.CLIENT)) {
				System.out.println("Permissions provided to technical support.");
				accountAccessGranted.push(user);
			} else {
				System.out.println("Try again.");
			}
		} else if (action.equals("validate_modifications")) {
			if (role.getRoleEnum().equals(RoleEnum.COMPLIANCE_OFFICER)) {
				if (modifications.isEmpty()) {
					System.out.println("No modifications to validate.");
					return true;
				}
				while (!modifications.isEmpty()) {
					User modUser = modifications.pop();
					System.out.println("Changes by " + modUser.getName() + " validated.");
				}
				return true;
			} else {
				System.out.println("Try again.");
			}
	    } else if (action.equals("view_support_tickets")) {
	    	if (role.getRoleEnum().equals(RoleEnum.TECHNICAL_SUPPORT)) {
	    		if (accountAccessGranted.isEmpty()) {
	    			System.out.println("No support tickets.");
	    			return true;
	    		}
	    		while (!accountAccessGranted.isEmpty()) {
	    			User supportUser = accountAccessGranted.pop();
	    			System.out.println("Support requested by " + supportUser.getName());
	    		}
    			// Elevate permissions
    			String[] elevatedReadActions = {
    					"account_balance",
    					"investment_portfolio",
    			};
    			user.getRole().addReadActions(elevatedReadActions);
    			System.out.println("Permissions elevated for this session.");
    			System.out.println(user.getRole().toString());
    			return true;
	    	} else {
	    		System.out.println("Try again.");
	    	}
		} else if (action.equals("exit")) {
			// Log out
			System.out.println("Goodbye.");
			return false;
		} 
		return true;
	}
	

}
