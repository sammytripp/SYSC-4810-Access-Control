package app;

/**
 * Representation of a SecVault Investments, Inc. system user.
 * 
 * @author Samantha Tripp - ID: 101089563
 *
 */
public class User {
	// User information
	private String username;
	private Role role;
	private String name;
	private String phone;
	private String email;
	
	// Access control policy
	private AccessControl policy;
	private boolean grantSupportAccess; // DAC - Discretionary access control
	
	/**
	 * Construct a SecVault Instruments, Inc. user, and assign them the provided role.
	 * 
	 * @param username String
	 * @param roleEnum RoleEnum
	 * @param name String
	 * @param phone String
	 * @param email String
	 */
	public User(String username, RoleEnum roleEnum, String name, String phone, String email) {
		this.username = username;
		this.name = name;
		this.phone = phone;
		this.email = email;
		
		// Set user role according to access control policy
		policy = new AccessControl();
		role = policy.getRole(roleEnum);
		
		// No support access granted by default
		grantSupportAccess = false;
	}
	
	public String getName() {
		return name;
	}
	
	public Role getRole() {
		return role;
	}

	public String getUsername() {
		return username;
	}
	
	public boolean supportAccess() {
		return grantSupportAccess;
	}
	
	@Override
	public String toString() {
		String s = "";
		s += name + ", " + phone + "," + email;
		return s;
	}
}
