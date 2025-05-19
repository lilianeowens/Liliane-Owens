
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class Utilities<L> {

	
	/*
	 * removesDuplicate method that removes duplicate items in the arrayList
	 * and check if arrayList is empty
	 * 
	 */
	@SuppressWarnings("hiding")
	public <L> List <L> removeDuplicates(List<L> list){

		List <L> newList = new ArrayList<>(); 
		if(list != null && list.size() > 1) {
			Set<L> linkedSet = new LinkedHashSet<>();

			linkedSet.addAll(list);
			newList.addAll(linkedSet);
		} //end if
		return newList;
	}//end of removesDuplicate method

	/*
	 * Linearsearch method to help the user to know the position of the item in the 
	 * arrayList 
	 * 	
	 */
	public <E> int linearSearch(List<E> list, E key) {
		if(list!= null && key!= null) {
			for(int i =0; i< list.size(); i++) {
				if(list.get(i).equals(key)) 
				{
					return i;

				}//end if

			}//end if

		}// end for
		return -1;
	}//end linearSearch method
}//end Utilities Class
