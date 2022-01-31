/**
 * This class will be used as an admin class to be able to grab login information from the collection,
 * and allowing the admin to change their username/password if necessary.
 * @author Steven Orsini
 * @version 12/19/2021
 */
public class Admin {
	
	private String username;
	private String password;
	
	public Admin() {
		
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
