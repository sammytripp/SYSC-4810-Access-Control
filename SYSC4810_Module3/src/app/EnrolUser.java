package app;

public class EnrolUser {
	
	public static void main(String argv[]) {
		
		System.out.println("Role options:\n");
		
		// Iterate over RoleEnum values
		for(RoleEnum role : RoleEnum.values()) {
			System.out.println(role.toString() + "\n");
		}
	}

}
