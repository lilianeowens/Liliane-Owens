package multiLevelUndo;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import com.sun.tools.classfile.StackMap_attribute.stack_map_frame;

public class UndoTester {

	/**
	 * Fields
	 */
	private static ArrayList<FacebookUser> users;
	private static ArrayList<FacebookUser> likes;
	//private static LinkedList<FacebookUser> list = new LinkedList<>();
	private static final String FILENAME = "facebook.dat";
	private static final Scanner IN = new Scanner(System.in);
	private static final PrintStream OUT = System.out;
	private static final PrintStream ERR = System.err;
	//private int undoRedoPointer = -1;
	//static UndoRedoStack<FacebookUser>stack ;
	private static final Stack<FacebookUser> stack = new Stack<FacebookUser>();
	/**
	 * main method start
	 * @param args
	 * @throws Exception
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) {

		/*
		 * try { stack = new UndoRedoStack<FacebookUser>(); } catch (Exception e1) { //
		 * TODO Auto-generated catch block OUT.println(e1.getMessage()); }
		 */
		
		/**
		 * calling the readerUser method to deserializeand throws exception in case class not found
		 */
		try {
			readUsers();
		} catch (ClassNotFoundException | IOException e) {
			OUT.println(e.getMessage());
		}
		int choice;
		users = new ArrayList<FacebookUser>();
		likes = new ArrayList<FacebookUser>();
		
		do {
			choice = displayMenu();

			switch (choice) {
			case 1:// List Users Alphabetically
				if (users.isEmpty()) {
					OUT.println("Can'list User, no user");
				} else {
					listUsersAlphab(); 	
					
				}
				break;
			case 2:// List Users by number of friends
				if (users.isEmpty()) {
					OUT.println("Can'list User, no user");
				} else {
					ListUsernumOfFriends();
				}
				break;

			case 3: // Add a user
				addUser();
				
				break;

			case 4: // Delete a user
				deleteUser();
				
				break;

			case 5: // Get Password Hint
				getPasswordHint();
				break;

			case 6://Friend Someone
				addFriends();
				break;

			case 7://Defriend Someone
				unfriend();
				break;

			case 8://List friends
				listFriends();
				break;

			case 9: // Recommend new friends
				getRecommendations();
				break;
			case 10: //Liking Things
				likingThings();
				break;
			case 11: // List of Likes
				listLikes();
				break;

			case 12: //Undo Last Action
				Undo();
				break;
			case 13:// Quit

				OUT.println("Goodbye!");
				try { 
					writeUsers();
				}catch(IOException e)
				{
					OUT.println(e.getMessage()); } //end catch
				break;
			default:// unknown option
				OUT.println("Sorry, " + choice + " was not one of the options.\n");

			}// end switch statement
		} while (choice != 13);// end do while loop

	}//end main method	

	
	/* The ObjectInputStream class contains readObject() method for deserializing an object.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void readUsers() throws IOException, ClassNotFoundException {
		File inputFile = new File(FILENAME);

		if (!inputFile.exists()) {
			return;
		}

		ObjectInputStream reader = new ObjectInputStream(new FileInputStream(FILENAME));

		users = new ArrayList<FacebookUser>();
		likes = new ArrayList<FacebookUser>();
		
		try {
			while (true) // not infinite loop
			{
				FacebookUser nextUser = (FacebookUser) reader.readObject();
				users.add(nextUser);
				likes.add(nextUser);
				
			}
		} catch (EOFException ex) {
			OUT.println(ex.getMessage());

		}

		reader.close();
	}// end reader users
	

	/**
	 * The ObjectOutputStream class contains writeObject() method for serializing an Object
	 * @throws IOException
	 */
	public static void writeUsers() throws IOException {

		ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(FILENAME));

		for (FacebookUser nextUser : users) {
			writer.writeObject(nextUser);
		}
		for (FacebookUser nextlike : likes) {
			writer.writeObject(nextlike);
		}

		
		/*
		 * for (FacebookUser nextstack : stack) { writer.writeObject(nextstack); }
		 */
		writer.close();
	}// end write users
	
	
	/**
	 * method to display the option the user will choose from
	 * @return menu
	 */
	private static int displayMenu() {
		int menu = 0;
		OUT.println("\nMenu\n " + 
				"1. List Users alphabetically,\n" + 
				"2. List Users by number of Friends, \n" +
				"3. Add a user,\n" + 
				"4. Delete a user,\n"+ 
				"5. Get Password Hint, \n" + 
				"6. Friend Someone,\n"+
				"7. Defriend Someone,\n"+
				"8. List friends, \n" +
				"9. Recommended, \n" +
				"10. Liking Things, \n" +
				"11. List of Likes, \n" +
				"12. Undo Last action, \n"+
				"13.Quit \n");

		OUT.println("What would you like to do?:");
		menu = IN.nextInt();
		IN.nextLine();
		return menu;
	}//end displayMenu method
	
	
	/**
	 * method to list the users alphabetically
	 */
	private static final void listUsersAlphab() {
		OUT.println("Current Facebook users name:");
		Collections.sort(users);

		for (FacebookUser nUser : users) {
			
			OUT.printf("%s \n", nUser);
			
		}
		System.out.println();

	} // end listUsersAlphab method
	
	

	/*
	 * method for listing users by number of friends
	 */
	private static void ListUsernumOfFriends() {
		OUT.printf("Current Facebook users name:\n", Collections.max(users));
		Collections.sort(users, new FriendComparator().thenComparing(FacebookUser::getUsername));		

		for (FacebookUser nUser : users) {
			OUT.printf("%s| Number of Friends: %d%n", nUser, nUser.getFriends().size());
		}

	} // end listUsernumOfFriends method	
	
	

	/**
	 * methods to add a user to FacebookUser object	
	 * @throws Exception
	 */
	private static final void addUser() {

		OUT.print("Enter the Facebook user's name : ");
		String name = IN.nextLine();  
		/**
		 * Check if there is no user added and check to see if the users ArrayList already contains 
		 * a facebookUser object with that usernameand display error if it does
		 * and if it is unique to prompt the user for a password and hint.
		 */
		if (name.isEmpty()) {

			ERR.printf("No users added; bye \n");
			return;
		}
		for(FacebookUser i: users) {
			if (i.getUsername().equalsIgnoreCase(name)) {
				ERR.printf("...there's already a user with the name %s\n",name);
				return;
			}
		}

		OUT.printf("Enter %s's password: ", name);
		String pwd = IN.nextLine();

		OUT.printf("Enter a password hint for %s's password: ", name);
		String hint = IN.nextLine();

		// add new FacebookUser to users
		FacebookUser newUser = new FacebookUser(name,pwd);
		newUser.setPasswordHint(hint); 
		users.add(newUser );
		
		
	}// end addUser method
	
	

	/**
	 * method to delete a user from FacebookUser object	
	 */
	private static final void deleteUser() 
	{
		OUT.println("Enter the facebook user's name: ");
		String name = IN.nextLine();

		FacebookUser nextUser = findUser(name);

		/**
		 * checking to see that the users arraylist contains a facebookclass with that
		username check that if Facebookclass object with this username has the same password
		as the one entered if it doesn't display error message
		 */

		if (nextUser != null) {
			OUT.println("Enter the facebook user's password: ");
			String password = IN.nextLine();

			if (nextUser.getPassword().equals(password)) {
				users.remove(nextUser);
				
				OUT.printf("%s removed successfully%n", name);
			} else {
				ERR.println("Incorrect Password");
			}
		} else {
			ERR.printf("...there's no user with the name %s%n", name);

		}//end if else
	} // end deleteUser method
	

	/**
	 * method to get password hint
	 */
	private static final void getPasswordHint() {

		OUT.println("Enter the facebook user's name:");
		String name = IN.nextLine();

		FacebookUser nextUser = findUser(name);
		if (nextUser != null) {
			OUT.printf("%s's hint is : ", nextUser.toString());
			nextUser.getPasswordHint();
		} else {
			ERR.printf("...there's no user with the name %s\n", name);

		}// end if else statement

	}//end getPasswordHint method
	

	/**
	 * Add friends to the user
	 */

	private static final void addFriends() {

		OUT.println("What is your facebook username?\n ");
		String name = IN.nextLine();
		FacebookUser nextFriend = findUser(name);

		if (nextFriend != null)
		{
			OUT.printf("%s: what is your password? Here's a hint:", nextFriend.toString());

			nextFriend.getPasswordHelp();
			String pwdInput= IN.nextLine();

			if(nextFriend.getPassword().equals(pwdInput)) {
				OUT.printf("Ok. %s, who would you like to friend?",name);
				name = IN.nextLine();

				FacebookUser nextUser = findUser(name);
				nextFriend.friend(nextUser);

			}
			else
			{
				ERR.println("Incorrect Password");
			}}else {
				ERR.printf(" %s is unknown friend\n", name);
			}

	}// end addFriends method
	
	
	/**
	 * Unfriend friend from a user
	 */
	private static void unfriend() {
		OUT.println("What is your facebook username? \n");
		String name = IN.nextLine();

		FacebookUser nextFriend = findUser(name);

		if (nextFriend != null)
		{
			OUT.printf("%s: what is your password? Here's a hint:", nextFriend.toString());

			nextFriend.getPasswordHelp();
			String pwdInput = IN.nextLine();

			if(nextFriend.getPassword().equals(pwdInput)) {
				OUT.printf("Ok. %s, who would you like to unfriend?",name);

				name = IN.nextLine();

				FacebookUser nextUser = findUser(name);
				nextFriend.deFriend(nextUser);
			}
			else
			{
				ERR.println("Incorrect Password");
			}}else {
				ERR.printf(" %s is unknown friend\n", name);
			}

	}	//end unfriend method
	
	
	/**
	 * List the friends for a user		
	 */

	private static void listFriends() {
		OUT.print("What is your Facebook user's name: ");
		String name = IN.nextLine();

		FacebookUser nextUser = findUser(name);

		if (nextUser == null)
		{
			ERR.printf("%s: not a Facebook user\n", name);
		}
		else {
			ArrayList<FacebookUser> friends = nextUser.getFriends();
			Collections.sort(friends);
			OUT.printf("%s's friends: ", name);
			for(FacebookUser i: friends) {
				OUT.print(i+ "," );
			}//end for

		}//end if statement
	}//end ListFriends Method
	
	

	/**
	 * recommend friend
	 */
	private static void getRecommendations() {
		OUT.println("What is your facebook username");
		String name = IN.nextLine();
		OUT.printf("recommended friends for %s:", name);

		FacebookUser nextUser = findUser(name);
		ArrayList<FacebookUser> recommendations = nextUser.getRecommendations();

		Collections.sort(recommendations);

		for (FacebookUser i : recommendations) {
			System.out.print(i+",");

		}//end for
	}//End getRecommendations method
	
	

	/**
	 * this method likingThings it adds likes to the user and 
	 * and indicate that they like something
	 */

	private static final void likingThings() {

		OUT.println("What is your facebook username?\n ");
		String name = IN.nextLine();
		FacebookUser nextFriend = findUser(name);
		String things;

		if (nextFriend != null)
		{
			OUT.printf("%s: what is your password? Here's a hint:", nextFriend.toString());

			nextFriend.getPasswordHelp();
			String pwdInput= IN.nextLine();

			if(nextFriend.getPassword().equals(pwdInput)) {
				OUT.printf("%s, what would you like ?",name);
				things = IN.nextLine();
				// add something to users
				FacebookUser nextlikes = new FacebookUser(things,things);
				likes.add(nextlikes);;
				// it indicates the user has like something
				if(!things.isEmpty()){
					OUT.println(name +" now likes "+ things);

				}				
				else if (likes.contains(things)) {
					ERR.printf("%s already likes %s",name,things);
					return;
				}

			}
			else
			{
				ERR.println("Incorrect Password");
			}
		}else {
			ERR.printf(" %s is unknown friend\n", name);

		} //end if statement

	}//ends likeThings
	
	

	/**
	 * this method listLike list and number of the likes for the specific user
	 *  after validate their username and password
	 */

	@SuppressWarnings({ "unused", "unchecked" })
	private static final void listLikes() {

		OUT.print("What is your Facebook user's name: ");
		String name = IN.nextLine();

		FacebookUser nextUser = findUser(name);


		if (nextUser != null)
			OUT.printf("%s: what is your password? Here's a hint:", nextUser.toString());
		else{

			ERR.printf("%s: not a Facebook user\n", name);

		}
		nextUser.getPasswordHelp();
		String pwdInput= IN.nextLine();

		if(nextUser.getPassword().equals(pwdInput)) {
		
			
			//here the arraylist is displaying the user's number of likes 
			OUT.printf("Here are %s's %s likes: %n",name,likes.size());
			Collections.sort(likes);
			for(FacebookUser i : likes)
				OUT.printf("%s %n",i);

		}

		else{
			ERR.println("Incorrect Password");

		}	//end if statement

	}//end ListLikes Method
	
	private static void stack_push(Stack<FacebookUser> stack)
	    {
	        for(FacebookUser i : users)
	        {
	            stack.push(i);
	            
	        }
	    }
	     
	    // Popping element from the top of the stack
	    static void stack_pop(Stack<FacebookUser> stack)
	    {
	        System.out.println("Pop Operation:");
	 
	        for(FacebookUser i: users)
	        {
	           FacebookUser user = (FacebookUser) stack.pop();
	           
	            System.out.println(user);
	        }
	    }
	 
	    // Displaying element on the top of the stack
	    static void stack_peek(Stack<FacebookUser> stack)
	    {
	        FacebookUser element = (FacebookUser) stack.peek();
	        System.out.println("Element on stack top: " + element);
	    }
	     
	
	  private static final void Undo() {
		 Stack<FacebookUser> user = new Stack<FacebookUser>();
		  user.addAll(users);
		 OUT.println(user); 
		  if(!user.isEmpty()) {
			  stack_peek(user);
			 stack_pop(user); 
			
		  }
		  else {
			  user.addAll(users);
			  
			  stack_push(user); 
			  
		  }
	  }
	  
	 
	/**
	 * method returns the first FacebookUser with
	 *   the given name, or null if there is no such
	 *   FacebookUser
	 *   
	 * @param name the given name (String)
	 * @return the 1st FacebookUser with the name, or null
	 */

	private static final FacebookUser findUser(String name) {
		FacebookUser result = null;

		for (FacebookUser nextUser : users) {
			if (nextUser.isNamed(name)) {
				result = nextUser;
				break; // end loop
			}
		} // end for each loop

		return result;
	} // end findUser method
	
	

}//end undoTester class


	/**
	 * 
	 * compares the friends 
	 *
	 */
	class FriendComparator implements Comparator<FacebookUser > {
		@Override
		public int compare(FacebookUser o1, FacebookUser o2) {
			if (o1.getFriends().size() == o2.getFriends().size()) {
				return 0;	
			}else if (o1.getFriends().size() > o2.getFriends().size()) {
				return -1;
			}else {
				return 1;
			}
		}// end if statement

	}	// end FriendComparator class

	
	/*
	 * private int undoRedoPointer = -1; private Stack<Command> commandStack = new
	 * Stack<>();
	 * 
	 * private void insertCommand() { deleteElementsAfterPointer(undoRedoPointer);
	 * Command command = new InsertCharacterCommand(); command.execute();
	 * commandStack.push(command); undoRedoPointer++; }
	 * 
	 * private void deleteElementsAfterPointer(int undoRedoPointer) {
	 * if(commandStack.size()<1)return; for(int i = commandStack.size()-1; i >
	 * undoRedoPointer; i--) { commandStack.remove(i); } }
	 * 
	 * private void undo() { Command command = commandStack.get(undoRedoPointer);
	 * command.unExecute(); undoRedoPointer--; }
	 * 
	 * private void redo() { if(undoRedoPointer == commandStack.size() - 1) return;
	 * undoRedoPointer++; Command command = commandStack.get(undoRedoPointer);
	 * command.execute(); }
	 */

	 /* //list.addAll(users);
	  
	 * String name= users.toString();
	 * 
	 * FacebookUser nextuser = findUser(name); UndoRedoStack<FacebookUser> list =
	 * new UndoRedoStack<FacebookUser>(); list.addAll(users); OUT.println(list);
	 * //for(FacebookUser user : users) {
	 * 
	 * //list.add(nextuser); // boolean canUndo = true;
	 * 
	 * //canUndo= false;
	 * 
	 * if(list.contains(users) ){ list.peek();
	 * 
	 * list.redo(); OUT.println(list); } //stack.canUndo(); //stack.pop();
	 * 
	 * else
	 * 
	 * if(list.isEmpty()) { // stack.search(nextuser); //stack.push(nextuser);
	 * list.undo();} //stack.remove(users); OUT.println(list.push(nextuser));
	 */

/*
 * if(users.contains(user)) { canUndo = false; } if(list.contains(user)) {
 * 
 * canUndo = false; } if(canUndo) { list.removeLast(); }
 * 
 * 
 * //list.addAll(likes); for (FacebookUser user1: user.getFriends()) { {
 * //list.addLast(user); //list.removeLast(); list.remove(list.size()-1);
 * OUT.println(list); OUT.println(user1); }
 * 
 * // list.removeLast().deFriend(user); //list.remove();
 */		//}
//}

/*
 * private void insertCommand() { deleteElementsAfterPointer(undoRedoPointer);
 * FacebookUser command = new FacebookUser(); command.execute();
 * commandStack.push(command); undoRedoPointer++;
 * 
 * }
 * 
 * private void deleteElementsAfterPointer(int undoRedoPointer) {
 * if(commandStack.size()<1) return;
 * 
 * for(int i = commandStack.size()-1; i > undoRedoPointer; i--) {
 * commandStack.remove(i); } }
 * 
 * private void undo() { Command command = commandStack.get(undoRedoPointer);
 * command.unExecute(); undoRedoPointer--; }
 * 
 * private void redo() { if(undoRedoPointer == commandStack.size() - 1) return;
 * undoRedoPointer++; Command command = commandStack.get(undoRedoPointer);
 * command.execute(); }
 */

