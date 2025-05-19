package multiLevelUndo;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public abstract class UserAccount implements Serializable{

	private String username;
	private String password;
	private boolean active;

	public UserAccount(String username, String password) {
		this.username = username;
		this.password  = password;
	}
	// Constructor to initialize the username and password and active

	public UserAccount(String username, String password, boolean active) {
		this.username = username;
		this.password = password;
		this.active = true;
	}
	/**
	 * *
	 * @return username
	 */
	public String getUsername() {

		return username;
	}
	/**
	 * 
	 * @param setter of username
	 */
	public void setUsername(String username) {

		this.username = username;
	} // end name mutator
	/**
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 
	 * @param setter for password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 
	 * @return active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * 
	 * @param setter for active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	/**
	 * 
	 * @param otherName
	 * @return username and check if the username is the one the user has typed
	 */
	public boolean isNamed(String otherName) {

		return username.equalsIgnoreCase(otherName);
	}
	// checkPassword method to check if password is valid  or invalid 
	public boolean checkPassword(String password) {

		if(getPassword().equalsIgnoreCase(password)) {
			return true;
		}
		else {
			return false;
		}
	}
	// deactivate method  
	public void deactivateAccount() {
		if(active == true) {
			active = false;
		}
	}

	// HashCode and equals methods
	@Override
	public int hashCode() {
		return Objects.hash(active, password, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof UserAccount))
			return false;
		UserAccount other = (UserAccount) obj;
		return active == other.active && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}
	/**
	 * abstract method
	 */
	public abstract void getPasswordHelp();
	/**
	 * returns a string representation of objects. 
	 */

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FacebookUserAccountClass [username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", active=");
		builder.append(active);
		builder.append("]");
		return builder.toString();
	}//end toString method

}//end UserAccount class



