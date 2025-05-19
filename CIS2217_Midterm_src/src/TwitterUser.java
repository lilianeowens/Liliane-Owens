// TwitterUser.java

import java.util.*;

public class TwitterUser implements Cloneable, Comparable<TwitterUser>
{
	private int id;
	private List<TwitterUser> followed;
	
	/*
		constructor with given id;
		 followed list defaults to new empty list
	 */
	public TwitterUser(int id)
	{
		this.id = id;
		this.followed = new ArrayList<TwitterUser>();
	} // end constructor
	
	/*
		copy constructor
	 */
	public TwitterUser(TwitterUser other)
	{
		this.id = other.id;
		this.followed = new ArrayList<TwitterUser>(other.getFollowed());
	} // end constructor
	
	/*
		empties followed list
	 */
	public void emptyFollowed()
	{
		this.followed.clear();
	}
	
	/*
		accessor for id
	 */
	public int getId() {
		return id; 
		}
	
	public void setUserID(int newId) {

		 id = newId;

		 } 
	
	/*
		accessor for followed list
			returns copy of list
	 */
	public List<TwitterUser> getFollowed()
	{
		return new ArrayList<TwitterUser>(followed);
	} // end getFollowed
	
	/*
		implements Comparable.compareTo
			based on id (smaller id first)
	 */
	@Override
	public int compareTo(TwitterUser other)
	{
		return this.id - other.id;
	} // end compareTo
	
	/*
		returns whether given id matches id field
	 */
	public boolean hasId(int id)
	{
		return this.id == id;
	} // end hasId
	
	/*
		adds the other TwitterUser to the followed list
	 */
	public void addFollowed(TwitterUser other)
	{
		if (other == null)
		{
			throw new IllegalArgumentException("null user");
		}
		followed.add(other);
	} // end addFollowed
	
	/*
		returns the index of the first
			followed TwitterUser with the
			given id
		returns -1 if no such TwitterUser
			exists in the followed list
	 */
	public int findFollowed(int otherId)
	{
		int result = -1;
		
		for (int index=0; index<followed.size(); index++)
		{
			TwitterUser followedUser = followed.get(index);
			if (followedUser.hasId(otherId))
			{
				result = index;
				break;
			}
		} // end for loop
		
		return result;
	} // end findFollowed
	
	private List<TwitterUser> neighborhood;
	private List<TwitterUser> visited;
	private int depth;
	private int currDepth;
	
	/*
		returns the neighborhood to the given depth
		  using the addToNeighborhood recursive helper
	 */
	public List<TwitterUser> getNeighborhood(int depth)
	{
		neighborhood = new ArrayList<TwitterUser>();
		visited = new ArrayList<TwitterUser>();
		this.depth = depth;
		currDepth = 0;
		
		for (TwitterUser followedUser : followed)
		{
			addToNeighborhood(followedUser);
		} // end for loop
		
		return neighborhood;
	} // end getNeighborhood
	
	/*
		recursively builds the neighborhood list
		 with the current user:
			1. simple case 1: currDepth is past depth
					action: do nothing
		   2. increase current depth by 1
			3. simple case 1: current user already visited
					action: do nothing
			4. simple case 1: current user is this user
					action: do nothing
			5. recursive case:
				i. if current user not in neighborhood list,
				   add current user to neighborhood list
			  ii. recursively add all of current user's
			      followed users to neighborhood list
			6. reduce current depth by 1
	 */
	private void addToNeighborhood(TwitterUser user)
	{
		if (currDepth > depth)
		{
			return;
		}
		
		currDepth++;
		
		if (visited.contains(user))
		{
			return;
		}
		
		visited.add(user);
		
		if (this.hasId(user.id))
		{
			return;
		}
		
		if (!neighborhood.contains(user))
		{
			neighborhood.add(user);
		}
		
		List<TwitterUser> userFollowed = user.followed;
		
		for (TwitterUser followedUser : userFollowed)
		{
			addToNeighborhood(followedUser);		
		} // end for loop
		
		currDepth--;
	} // end addToNeighborhood
	
	/*
		return users in followed list as a comma-separated String
	 */
	
	
	public ArrayList<TwitterUser> getFollowing() {
		 ArrayList<TwitterUser>copy = new ArrayList<TwitterUser>();

		 for (TwitterUser t : followed) {
		 copy.add(t);
		 }
		 
		return copy;
		 }
	
	
	@SuppressWarnings("unused")
	private String followedAsStr()
	{
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		
		for (TwitterUser followedUser : followed)
		{
			if (first) { first = false;}
			else { sb.append(", "); }
			sb.append(Integer.toString(followedUser.id));
		}
		
		return sb.toString();
	} // end followedAsStr
	
	
	@Override public int hashCode() {

		 final int prime = 31;
		 int result = 1;
		 result = prime * result + id;
		 return result; 
		}
	
	
	@Override
	public boolean equals(Object other)
	{
		boolean result = false;
		
		if (other instanceof TwitterUser)
		{
			TwitterUser otherUser = (TwitterUser)other;
			result = (this.id == otherUser.getId());
		}
		
		return result;
	} // end equals
	
	/*
		return TwitterUser's id as String
	 */
	@Override
	public String toString()
	{
		String result = "user id "+ id;
		/*
		if (!followed.isEmpty())
		{
			result += " follows " + followedAsStr();
		}
		*/
		return result;
	} // end toString
	
	// Overriding clone() method
   // using copy constructor
   @Override
   protected Object clone()
       throws CloneNotSupportedException
   {
       return new TwitterUser(this);
   }

   
   /**
    * GetFollowing method which return a 
    * a collection of the people who are are following
    *  a given user being followed
    * @param user
    * @return
    */
@SuppressWarnings("rawtypes")
public Collection <TwitterUser>getFollowing(TwitterUser user) {
	Map<Integer,TwitterUser> followingMap = new HashMap<Integer,TwitterUser>();
	
	
	Set<Integer> followingKey = followingMap.keySet();
	Set<TwitterUser> following = new TreeSet<TwitterUser>();
	
	for(Integer nextKey: followingKey) {
		TwitterUser followingUser = followingMap.get(nextKey);
		following.add(followingUser);
	}
	return following;
	
}
	
	/*
	 * Collection tUser = new ArrayList(); if (user.hasId(id)) {
	 * System.out.print("\nuser id: " + user.getId()); }
	 * 
	 * for (TwitterUser t : user.getFollowers()) {
	 * 
	 * tUser.add(new TwitterUser(t)); } return tUser;
	 */

	 
	 private List followers = new ArrayList<>();
	 
		/*
		 * public void setFollowers(List follower) {
		 * 
		 * for (TwitterUser i : follower) { if (!followers.contains(i))
		 * followers.add(i); } }
		 */
	 public List<TwitterUser> getFollowers() {
		 return followers; 
		
}
} // end TwitterUser class