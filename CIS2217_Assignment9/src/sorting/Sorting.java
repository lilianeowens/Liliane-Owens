package sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sorting{


	//Implementing the Insertionsort method 
	public static <E extends Comparable<E>> void insertionSort(E[] list) {

		int currentMinIndex;
		E value;

		for(int j = 0; j< list.length-1; j++) {

			// Find the minimum in the list [j+1 ...list.length-2]
			value = list[j];

			currentMinIndex = j;

			for(int l = j +1 ; l< list.length; l++) {

				if (value.compareTo(list[l]) > 0) {
					value = list[l];

					currentMinIndex = l;

				}

			}

			// Swap list [j] with list [currentminindex] if necessary

			if(currentMinIndex != j) {
				list[currentMinIndex] = list[j];
				list[j] = value;
			}
		}

	}

	//Implementing the Quicksort method 
	@SuppressWarnings("unchecked")
	public static <E extends Comparable<E>> void quickSort(E[] list,int low, int high) {

		if( low == high || low == (high-1) ) return;
		E p = list[low];
		int a = low +1;
		int b = a;
		for( ; b < high; b++) {
			// The object type array must be implemented Comparable Interface so you can use it compareTo Function comparison 
			if( ((Comparable<Object>)list[b]).compareTo(p) < 0) {
				if(a == b){a++; 
				continue;
				}
				E temp = list[a];
				System.out.printf("\n",temp);
				list[a] = list[b];
				list[b] = temp;
				System.out.printf("\n",list[a]);

				System.out.printf("\n",list[b]);
				a++;
			}
		}
		list[low] = list[a-1];
		list[a-1] = p;
		if( a-1 > low){
			quickSort(list,low, a);
		} 
		if( high-1 > a ) {
			quickSort(list,a, high);
		} 
		return;
	}


	//Invoking quicksort to sort the integer array 
	public static void sort(Integer[] input){
		Integer[] t = new Integer[input.length];
		for(int i = 0; i < input.length; i++){
			t[i] = input[i];// encapsulation 
		}
		quickSort(t,0,t.length);// The sorting 
		for(int i = 0; i < input.length; i++){
			input[i] = t[i];// decapsulation 
		}
	}


	//Invoking quicksort to sort the string array
	public static void sort(String[] input){
		String[] t = new String[input.length];
		for(int i = 0; i < input.length; i++){
			t[i] = input[i];// encapsulation 
		}
		quickSort(t,0,t.length);// The sorting 
		for(int i = 0; i < input.length; i++){
			input[i] = t[i];// decapsulation 
		}
	}

	//Invoking quicksort to sort the object array
	public static void sort(FacebookUser[] input){
		FacebookUser[] t = new FacebookUser[input.length];
		for(int i = 0; i < input.length; i++){
			t[i] = input[i];//encapsulation
		}
		quickSort( t,0,t.length);//the sorting
		for(int i = 0; i < input.length; i++){
			input[i] = t[i];//decapsulation
		}
	}
	//Main Method

	@SuppressWarnings("static-access")
	public static void main(String[] args) {

		@SuppressWarnings("removal")
		Integer [] intArray = {	9,3,0,95,19,20};
		String[] stringArray= {"Ryan", "Liliane","Blessing","Scovia", "Gabby"};
		FacebookUser[] fcbkArray = new FacebookUser[3];
		
		
		fcbkArray[0] = new FacebookUser("Ryan", "123");
		fcbkArray[1] = new FacebookUser("Lily", "234");
		fcbkArray[2] = new FacebookUser("Blessing", "456");
		//Integer type

		Sorting sortInteger= new Sorting();

		//String type

		Sorting sortString= new Sorting();

		// object type
		Sorting sortObject = new Sorting();

		/*Printing the string array before sorting it */

		System.out.println("Unsorted String Array");

		for(String s:stringArray)

		{

			System.out.printf("%s",s);
			System.out.println();
		}

		/*Sorting the string array by calling the sortString method*/

		sortString.insertionSort(stringArray);

		/*Now printing the sorted array*/

		System.out.println("\nString Array after insertionSort:");

		for(String s:stringArray)

		{

			System.out.printf("\n%s", s);
		}

		//Quicksort method is called
		sort(stringArray); 

		System.out.println("\n\nString[] after quickSort");
		for(String name: stringArray) {

			System.out.printf("%s",name);
			System.out.println();
		}

		/*Printing the Integer array before sorting it */

		System.out.println("\nUnsorted Integer Array");

		for(Integer i:intArray)

		{

			System.out.printf("%d", i);
			System.out.println();

		}

		/*Sorting the intarray array by calling the sortinteger method*/

		sortInteger.insertionSort(intArray);

		/*Now printing the sorted array*/

		System.out.println("\nInteger Array after insertionSort: ");

		for(Integer i:intArray)

		{

			System.out.printf("%d",i);
			System.out.println();

		}

		//Quicksort method is called
		sort(intArray);
		System.out.println("\nInteger[] after quickSort:");
		for(Integer number : intArray) {
			System.out.printf( "%d",number);

			System.out.println();
		}

		/*Printing the string array before sorting it */

		System.out.println("\nUnsorted fcbk user Array:");

		for(FacebookUser o:fcbkArray)

		{

			System.out.printf("%s",o);
			System.out.println();
		}


		/*Sorting the facebookuser array by calling the sortObject method*/
		sortObject.insertionSort( fcbkArray);
		/*Now printing the sorted array*/

		System.out.println("\nFacebookUser Array after insertionSort: ");

		for(FacebookUser fc:fcbkArray)

		{

			System.out.printf("%s",fc);
			System.out.println();

		}

		//Quicksort method is called
		sort(fcbkArray);
		System.out.println("\nFacebookUser[] after quickSort :");
		for(FacebookUser user:  fcbkArray) {
			System.out.printf( "%s", user);

			System.out.println();
		}

		// after converting the array to list and use shuffle method

		List<String> list = convertArrayToList(stringArray);

		Collections.shuffle(list);

		System.out.println("\nString after shuffle list:");
		for (String str : list) {
			System.out.printf("%s ",str);

			System.out.println();


		}

		// after converting the array to list and use shuffle method
		List<Integer> intlist = convertArrayToList(intArray);

		Collections.shuffle(intlist);

		System.out.println("\nNumbers after shuffle intlist:");
		for (Integer numbers : intlist) {
			System.out.printf("%d ",numbers);

			System.out.println();

		}

		// after converting the array to list and use shuffle method
		List<FacebookUser> facebookUser = convertArrayToList(fcbkArray);
		Collections.shuffle(facebookUser);

		System.out.printf("\nUser after shuffle facebookuser: %s" ,facebookUser);
		System.out.println();
	}


	//Method to convert array to list
	public static <E> List<E> convertArrayToList(E array[])
	{

		// Create an empty List
		List<E> list = new ArrayList<>();

		// Iterate through the array
		for (E s : array) {
			// Add each element into the list
			list.add(s);
		}

		// Return the converted List
		return list;
	}//end convertingArray to List method


}//End sorting Class
