package sorting;

import java.util.ArrayList;


@SuppressWarnings("serial")
public class FacebookUser extends UserAcc implements Comparable<FacebookUser>{

	private String passwordHint;
	private ArrayList<FacebookUser> recommended;
	private ArrayList<FacebookUser> checked;
	private ArrayList<FacebookUser> friends = new ArrayList<FacebookUser>();
	
	public FacebookUser(String username, String password) {
		super(username, password);
		
	}

	public FacebookUser(String username, String password,boolean active, String passwordHint, ArrayList<FacebookUser> friends){
		super(username, password, active);
		this.passwordHint = passwordHint;
		this.friends = friends;

	}
/**
 * Recommend friends method return an
ArrayList that contains all of the friends of the FacebookUser that is passed into it plus
the result of calling the same getRecommendations method on all of that
FacebookUser’s friends. 
 * @return
 */
	public ArrayList<FacebookUser> getRecommendations()
	{
		recommended = new ArrayList<FacebookUser>();
		checked = new ArrayList<FacebookUser>(); 
		for(FacebookUser friend: friends){
			addToRecommended(friend);
				
		}//end for loop
		return recommended;
	}//end getRecommendation
	
	private void addToRecommended(FacebookUser user){
		if (checked.contains(user))
			return;
		checked.add(user);
		boolean recommend = true;
		if (friends.contains(user)) {
			recommend = false;
		}
		if(recommended.contains(user)) {
			recommend= false;
		}
		if(user.isNamed(getUsername())){
			recommend = false;
		}
		if(recommend) {
			
			recommended.add(user);
		}
		// recursively add friends of user to recommended
		
			for(FacebookUser friend : user.getFriends()) {
				addToRecommended(friend);
			}//end for loop
		}//end addtoRecommended
	
	/**
	 * @return the passwordHint and output password hint 
	 */
	public String getPasswordHint() {
		System.out.println(passwordHint);
		return passwordHint;
	}

	/**
	 * @param passwordHint the passwordHint to set
	 */
	public void setPasswordHint(String hint) {
		this.passwordHint = hint;
	}

	/**
	 * @return the friends
	 */
	public ArrayList<FacebookUser> getFriends() {
		ArrayList<FacebookUser> friend = new ArrayList<FacebookUser>(friends);
		return friend;

	}

	/**
	 * @param friends the setter for friends 
	 */
	public void setFriends(ArrayList<FacebookUser> friends) {
		this.friends = friends;
	}
	
	public void friend(FacebookUser newFriend) {

		if ( (FacebookUser)newFriend != null) {
			friends.add(newFriend);
		}

	}
	public void deFriend(FacebookUser formerFriend) {

		friends.remove(formerFriend);

	}

	/**
	 * create the password hint
	 * 
	 */
	@Override
	public void getPasswordHelp() {

		setPasswordHint(getPasswordHint());

	}
	//print the object
	@Override
	public String toString() {

		return String.format("%s",super.getUsername() );
	}// end toString method

	@Override
	public int compareTo(FacebookUser otherName) {
		
		if (super.getUsername().compareToIgnoreCase(otherName.getUsername()) != 0) {
			return super.getUsername().compareToIgnoreCase(otherName.getUsername());
		}

		
		return 0;
	}
}//end FacebookClass method





