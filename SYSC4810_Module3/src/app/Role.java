package app;

import java.util.ArrayList;

public class Role {
	
	private RoleEnum role;	
	private ArrayList<String> readActions;
	private ArrayList<String> writeActions;
	private ArrayList<String> specialActions;
	
	public Role (RoleEnum role) {
		// Set role type
		this.role = role;
		
		// Initialize action lists		
		readActions = new ArrayList<String>();
		writeActions = new ArrayList<String>();
		specialActions = new ArrayList<String>(); 
		
	}
	
	public RoleEnum getRoleEnum() {
		return role;
	}
	
	/**
	 * Add the array of read actions to the role.
	 * @param actions String[]
	 */
	public void addReadActions(String[] actions) {
		for(String action : actions) {
			readActions.add(action);
		}
	}
	
	/**
	 * Remove the array of read actions from the role.
	 * @param actions String[]
	 */
	public void removeReadActions(String[] actions) {
		for(String action : actions) {
			readActions.remove(action);
		}
	}
	
	/**
	 * Add the array of write actions to this role.
	 * @param actions String[]
	 */
	public void addWriteActions(String[] actions) {
		for(String action : actions) {
			writeActions.add(action);
		}
	}
	
	/**
	 * Remove the array of write actions from the role.
	 * @param actions String[]
	 */
	public void removeWriteActions(String[] actions) {
		for(String action : actions) {
			writeActions.remove(action);
		}
	}
	
	/**
	 * Add the array of special actions to this role.
	 * @param actions String[]
	 */
	public void addSpecialActions(String[] actions) {
		for(String action : actions) {
			specialActions.add(action);
		}
	}
	
	/**
	 * Remove the array of special actions from the role.
	 * @param actions String[]
	 */
	public void removeSpecialActions(String[] actions) {
		for(String action : actions) {
			specialActions.remove(action);
		}
	}
	
	public ArrayList<String> getReadActions() {
		return readActions;
	}
	
	public ArrayList<String> getWriteActions() {
		return writeActions;
	}
	
	public ArrayList<String> getSpecialActions() {
		return specialActions;
	}
	
	@Override
	public String toString() {
		String s = "";
		s += "Role: " + role.toString(); 
		s += "\nread <action>: " + readActions.toString();
		s += "\nwrite <action>: " + writeActions.toString();
		if (!specialActions.isEmpty()) {
			s += "\nAdditional: " + specialActions.toString();
		}
		return s;
	}
}
