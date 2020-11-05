package app;
/**
 * Enumeration class to represent SecVault Investments, Inc. system roles.
 * 
 * @author Samantha Tripp - ID: 101089563
 *
 */
public enum RoleEnum {
	CLIENT 				("Client"),
	PREMIUM_CLIENT 		("Premium Client"),
	FINANCIAL_ADVISOR 	("Financial Advisor"),
	FINANCIAL_PLANNER	("Financial Planner"),
	INVESTMENT_ANALYST	("Investment Analyst"),
	TELLER				("Teller"),	
	TECHNICAL_SUPPORT	("Technical Support"),
	COMPLIANCE_OFFICER	("Compliance Officer");
	
	private String role;
	
	private RoleEnum(String role) {
		this.role = role;
	} 
	
	@Override
	public String toString() {
		return role;
	}
	
	public static RoleEnum getEnum(String role) {
			switch(role) {
			case "Client":
				return CLIENT;
			case "Premium Client":
				return PREMIUM_CLIENT;
			case "Financial Advisor":
				return FINANCIAL_ADVISOR;
			case "Financial Planner":
				return FINANCIAL_PLANNER;
			case "Investment Analyst":
				return INVESTMENT_ANALYST;
			case "Teller":
				return TELLER;
			case "Technical Support":
				return TECHNICAL_SUPPORT;
			case "Compliance Officer":
				return COMPLIANCE_OFFICER;
			default:
				return null;
			}
	}
	
}
