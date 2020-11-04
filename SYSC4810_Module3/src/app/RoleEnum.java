package app;

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
}
