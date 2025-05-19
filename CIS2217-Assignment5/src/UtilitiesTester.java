import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class UtilitiesTester {

	private static final PrintStream ERR = System.err;
	private static final PrintStream OUT = System.out;
	
	private static final Random GEN = new Random();
	private static final Scanner IN = new Scanner( System.in );
	
	public static void main(String[] args)
	{
		try
		{
			OUT.print("How many items in the list of numbers? ");
			int listSize = IN.nextInt();
			
			OUT.print("\nWhat is the limit of numbers list items? ");
			int listLimit = IN.nextInt();
			
			ArrayList<Integer> numbers = makeRandIntList(listSize, listLimit);
			
			OUT.println("\nnumbers list: " + numbers);
			
			Utilities<Integer> utils = new Utilities<Integer>();
			List<Integer> noDupes = utils.removeDuplicates(numbers);
			
			OUT.println("\nnumbers list without duplicates: " + noDupes);
			
			boolean done = false;
			
			while (!done)
			{
				OUT.print("\nEnter the next integer to search for (-1 to end): ");
				int value = IN.nextInt();
				
				if (value < 0)
				{
					done = true;
					continue;
				}
				
				int index = utils.linearSearch(noDupes, value);
				
				if (index > -1)
				{
					OUT.printf("\n%d is at numbers[%d]\n", value, index);
				}
				else
				{
					OUT.printf("\n%d is not in the numbers list\n", value);
				}
			} // end while loop
		}
		catch (Exception ex)
		{
			ERR.println(ex);
		}
	} // end main method

	private static ArrayList<Integer> makeRandIntList(int size, int limit)
	{
		if (size <= 0)
		{
			throw new IllegalArgumentException("invalid size " + size);
		}
		if (limit <= 0)
		{
			throw new IllegalArgumentException("invalid limit " + limit);
		}
		
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		for (int count=1; count<=size; count++)
		{
			result.add( GEN.nextInt(limit) + 1 );
		} // end for loop
		
		return result;
	} // end makeRandIntList method
} // end UtilitiesTester class
