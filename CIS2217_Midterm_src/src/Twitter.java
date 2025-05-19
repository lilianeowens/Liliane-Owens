// Twitter.java

import java.io.*;
import java.util.*;

public class Twitter
{
	private List<TwitterUser> users;
	private int lineLimit;
	private File edgelistFile;
	
	public Twitter(int lineLimit, File edgelistFile) throws Exception
	{
		this.lineLimit = lineLimit;
		this.edgelistFile = edgelistFile;
		users = new ArrayList<TwitterUser>();
		readUsers();
	} // end constructor
	
	private final void readUsers() throws Exception
	{
		try (BufferedReader reader = new BufferedReader(new FileReader(edgelistFile)))
		{
			boolean done = false;
			int lineCounter = 0;
			
			while (!done)
			{
				String nextLine = reader.readLine();
				
				if (nextLine == null || ((lineLimit > 0) && (lineCounter >= lineLimit)))
				{
					done = true;
					continue;
				}
				
				lineCounter++;
				
				if (lineCounter%1000 == 0)
				{
					System.out.printf("%d lines read\n", lineCounter);
				}
			
				String[] nextTokens = nextLine.split(" ");
			
				if (nextTokens.length >= 2)
				{
					int firstId = Integer.parseInt(nextTokens[0]);
					int secondId = Integer.parseInt(nextTokens[1]);
					
					int userIndex1 = findUser(firstId);
					TwitterUser newUser1;
					if (userIndex1 >= 0)
					{
						newUser1 = users.get(userIndex1);
					}
					else
					{
						newUser1 = new TwitterUser(firstId);
						users.add(newUser1);
					}
					
					int userIndex2 = findUser(secondId);
					TwitterUser newUser2;
					if (userIndex2 >= 0)
					{
						newUser2 = users.get(userIndex2);
					}
					else
					{
						newUser2 = new TwitterUser(secondId);
						users.add(newUser2);
					}
					
					newUser1.addFollowed(newUser2);
				}
			} // end while loop
			
			System.out.println();
		}
		catch (IOException ex)
		{
			System.err.println(ex.getMessage());
			throw new Exception(ex.getMessage());
		}
		catch (NumberFormatException ex)
		{
			System.err.println(ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	} // end readUsers
	
	private int findUser(int id)
	{
		int result = -1;
		
		for (int index=0; index<users.size(); index++)
		{
			TwitterUser nextUser = users.get(index);
			if (nextUser.hasId(id))
			{
				result = index;
				break;
			}
		} // end for loop
		
		return result;
	} // end findUser
	
	public List<TwitterUser> getNeighborhood(int userId, int depth)
	{
		List<TwitterUser> result = null;
		
		int userIndex = findUser(userId);
		
		if (userIndex > -1)
		{
			TwitterUser user = users.get(userIndex);
			result = user.getNeighborhood(depth);
		}
		
		return result;
	}
	
	public TwitterUser cloneUser(int userId) throws CloneNotSupportedException
	{
		TwitterUser result = null;
		
		int userIndex = findUser(userId);
		
		if (userIndex > -1)
		{
			TwitterUser user = users.get(userIndex);
			result = (TwitterUser)user.clone();
		}
		
		return result;
	}
	
	public List<TwitterUser> getFollowed(int userId)
	{
		List<TwitterUser> followers = null;
		
		int userIndex = findUser(userId);
		
		if (userIndex > -1)
		{
			TwitterUser user = users.get(userIndex);
			followers = user.getFollowed();
		}
		
		return followers;
	}
	
	
		 
	@Override
	public String toString()
	{
		StringBuffer result = new StringBuffer("Twitter users:\n");
		
		ArrayList<TwitterUser> sortedUsers = new ArrayList<TwitterUser>(users);
		
		Collections.sort(sortedUsers);
		
		for (TwitterUser user : sortedUsers)
		{
			result.append(user);
			result.append("\n");
		} // end for loop
		
		return result.toString();
	} // end toString
} // end Twitter class

 class UserComparator implements Comparator<TwitterUser> {
	 @Override public int compare(TwitterUser o1, TwitterUser o2)
	 { int res = 0;
	 
	 // 1. Number of followers (largest to smallest) 

	if (o1.getFollowing().size() > o2.getFollowing().size()) res = 1;
	
	// 2. If two users have the same number of followers, sort by the number // of people that user is following (largest to smallest) 

	if (o1.getFollowed().size() == o2.getFollowed().size()) res = o1.getFollowing().size() > o2.getFollowing().size() ? 1 : 0;

	 

	 // 3. If two users have the same number of followers and are following // the same number of people, sort by user id (smallest to largest) 

	if ((o1.getFollowing().size() == o2.getFollowing().size()) && (o1.getFollowing().size() == o2.getFollowing().size())) res = (o1.getId() );
	

	 return res;
	 }
 }

	
