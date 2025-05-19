// Main.java

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JFileChooser;

public class Driver
{
	private static PrintStream out = System.out;
	private static Scanner in = new Scanner(System.in);
	private static Map<Integer,TwitterUser> users = new HashMap<Integer,TwitterUser>(); 
	
	public static void main(String[] args)
	{
		try
		{
			JFileChooser chooser = new JFileChooser();
			
			int option = chooser.showDialog(null, "Select EdgeList File");
			
			if (option == JFileChooser.CANCEL_OPTION) {
				out.println("Execution canceled; bye");
				return;
			}
			
			out.println("Welcome to the Twitter EdgeList File Reader\n");
			
			File edgelistFile = chooser.getSelectedFile();
			
			if (!edgelistFile.exists())
			{
				throw new Exception("edgelist file does not exist");
			}
			
			out.print("Enter the edgelist file read limit (non-neg. int; 0 for no limit): ");
			int lineLimit = in.nextInt();
			
			if (lineLimit < 0)
			{
				throw new Exception("invalid line limit: " + lineLimit);
			}
			
			Twitter tweets = new Twitter(lineLimit, edgelistFile);
			
			out.print("Enter a Twitter user's id: ");
			int id = in.nextInt();
			
			out.print("Enter the neighbor search depth: ");
			int depth = in.nextInt();
			
			if (depth < 1)
			{
				out.printf("invalid depth %d\n", depth);
			}
			else
			{
				List<TwitterUser> neighbors = tweets.getNeighborhood(id, depth);
				System.out.println("Neighbors: " + neighbors);
			}

			out.println("\nCloning Twitter user 0...");
			TwitterUser clone0 = tweets.cloneUser(0);
			out.println("Emptying clone's followed list...");
			clone0.emptyFollowed();
			out.println("\nuser 0's followers: " + tweets.getFollowed(0));
			out.println("\nclone's followers: " + clone0.getFollowed());
			
			
			
			  System.out.print("\nTest for the followers function:" +
			  "\n\tEnter the user you would like see list of followers: ");
			  
			  id = in.nextInt();
			  
			  test_getFollowing(getUser(id));
			 
				// Followers test end // Popularity test begin

				 System.out .print("\nTest for the followers getByPopularity function:" + "\n\tEnter the user index for which you would like to see popularity: "); 

				int idx = in.nextInt();

				// TwitterUser u = getByPopularity(idx); 

	//			System.out.print("\nThe " + idx + "th popular user is :" + u.getId() + " number of followers " + u.getFollowers().size());

				 // Popularity test end 
			out.println("\nOK, it's all good!  bye! :)");
		}
		catch (InputMismatchException ex)
		{
			out.println("fatal error: invalid data; terminating...");
		}
		catch (Exception ex)
		{
			out.println("fatal error: " + ex.getMessage() + "; terminating...");
		}
	} // end main method
	
	
	 public static TwitterUser getUser(int userID) {
		 TwitterUser temp; 
		temp = new TwitterUser(userID);
		 while (!users.containsKey(temp)) { 

		System.out.print("\tInvalid user ID. Enter a valid user id: "); 

		userID = in.nextInt(); 
		temp.setUserID(userID); 
		}
		 return users.get(users.get(temp));
		 }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void test_getFollowing(TwitterUser user) {

		 Collection<TwitterUser> followerUsers = user.getFollowing(user); 

		List followers = new ArrayList();
		 for (TwitterUser nextUser : followerUsers) { 
		followers.add(nextUser.getId()); } 

		System.out.println("User " + user.getId() + " has " + followers + " followers. "); 
		}
	
	/**
	 * GetPopularity method which return the xth TwitterUserstaarting from(0)when the user have been sorted
	 * by numbers of Users who are following a given user being 
	 * followed
	 * If two different users have the same number of users who are following the given user being followed 
	 * is following(Largest to smallest) 
	 * If two different users have the same number of users who are ollowing a given user being followed, 
	 * and 2 different users have the the same number  of people the user being followed.
	 * @param idx
	 * @return
	 */

		// @SuppressWarnings("unused")
		private static TwitterUser getByPopularity(int idx) {

			 Map<Integer, TwitterUser> treemap = new TreeMap<>(users);
		 (treemap.values().Comparator());

		 
		/* class FriendComparator implements Comparator<LikingThings > {
				@Override
				public int compare(LikingThings o1, LikingThings o2) {
					if (o1.getFriends().size() == o2.getFriends().size()) {
						return 0;	
					}else if (o1.getFriends().size() > o2.getFriends().size()) {
						return -1;
					}else {
						return 1;
					}
				}// end if statement
*/		 
		/* OUT.printf("Current Facebook users name:\n", Collections.max(users));
			Collections.sort(users, new FriendComparator().thenComparing(LikingThings::getUsername));		

			for (LikingThings nUser : users) {
				OUT.printf("%s| Number of Friends: %d%n", nUser, nUser.getFriends().size());
			}*/
		 return users.get(idx); 
		 
		 
		 }// Followers && Popularity end 

	
	
} // end Main