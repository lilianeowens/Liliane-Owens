import java.util.Objects;

public class UserAccount {

	private String username;
	private String password;
	private boolean active;
	
	public UserAccount() {
	    username = null;
	    password = null;
	    active = false;
}
 // Constructor to initialize the username and password and make it active
public UserAccount(String username, String password ) {

	this.username = username;
	this.password = password;
	this.active = true;

}
	
	public String getUsername() {
	return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
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

	//toString method
	@Override
	public String toString() {
		String result = username +" " + "("+ password +") " + " : ";
				if(active)
				{
				result = result + "active";
				}
			else {
				result = result + "inactive";
			}
				return result;
	}
}


	