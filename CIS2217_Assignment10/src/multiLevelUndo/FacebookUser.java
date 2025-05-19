package multiLevelUndo;

import java.util.ArrayList;

/**
 * EXPLANATION: I chose the ArrayList data structure because 
 * it is the one which doesnot care about the duplicate
 * and help me to sort alphabetically.
 * 
 *
 */
@SuppressWarnings("serial")
public class FacebookUser extends UserAccount implements Comparable<FacebookUser>{

	private String passwordHint;
	private String things;
	private ArrayList <FacebookUser> likes = new ArrayList<FacebookUser>();
	private ArrayList<FacebookUser> recommended;
	private ArrayList<FacebookUser> checked;
	private ArrayList<FacebookUser> friends = new ArrayList<FacebookUser>();
	
	

	public FacebookUser(String username, String password) {
		super(username, password);

	}

	public FacebookUser(String username, String password,boolean active, String passwordHint, String things, ArrayList<FacebookUser> friends){
		super(username, password, active);
		this.passwordHint = passwordHint;
		this.friends = friends;
		this.things = things;
	}
	
	
	/**
	 * @return the things
	 */
	public String getThings() {

		return things;
	}

	
	/**
	 * @param things the things to set
	 */
	public void setThings(String things) {
		this.things = things;
	}


	/**
	 * @return the likes
	 */
	public ArrayList<FacebookUser> getLikes() {

		ArrayList<FacebookUser> like = new ArrayList<FacebookUser>(likes);

		return like;
	}

	
	/**
	 * @param likes the likes to set
	 * @return 
	 */
	public ArrayList<FacebookUser> setLikes(ArrayList<FacebookUser> likes) {
		return this.likes = likes;
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
	//Adding friend method

	public void friend(FacebookUser newFriend) {

		if ( (FacebookUser)newFriend != null) {
			friends.add(newFriend);
		}

	}
	
	//unfriending friends
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

		return String.format("%s",super.getUsername() + getThings() );
	}// end toString method

	@Override
	public int compareTo(FacebookUser otherName) {

		if (super.getUsername().compareToIgnoreCase(otherName.getUsername()) != 0) {
			return super.getUsername().compareToIgnoreCase(otherName.getUsername());
		}

		if (this.passwordHint.compareTo(otherName.passwordHint) != 0) {
			return this.passwordHint.compareTo(otherName.passwordHint);
		}
		return 0;
	}

	
}//end FacebookClass method



