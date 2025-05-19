// AccountDriver.java
// tester for UserAccount class

import java.io.*;
import java.util.*;

public class AccountDriver
{

	private static final Scanner IN = new Scanner(System.in);
    private static final PrintStream OUT = System.out;
    
    public static void main(String[] args)
    {
        String name, passwd, otherPasswd;
        
        // get name and password, create my user account
        
        OUT.print("Enter the name of my user account: ");
        name = IN.next();
        
        OUT.print("Enter the password of my user account: ");
        passwd = IN.next();
        
        UserAccount myAccount = new UserAccount(name, passwd);
        
        // calls UserAccount's toString method
         OUT.println("myAccount: " + myAccount); 
        
        OUT.print("What is my account's password? ");
        otherPasswd = IN.next();
        
        if (myAccount.checkPassword(otherPasswd))
        {
        	OUT.println("Valid password");
        }
        else
        {
        	OUT.println("Invalid password");
        }
        
        // get name and password, create your user account
        
        OUT.print("Enter the name of your user account: ");
        name = IN.next();
        
        OUT.print("Enter the password of your user account: ");
        passwd = IN.next();
        
        UserAccount yourAccount = new UserAccount(name, passwd);
        
        // calls UserAccount's toString method
        
        OUT.println("yourAccount: " + yourAccount); 
        
        // deactivate my account
        
        myAccount.deactivateAccount();
        
        OUT.println("myAccount: " + myAccount);
    } // end main method
    
} // end AccountDriver class