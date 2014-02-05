package lib280.base;

import lib280.list.LinkedIterator280;
import lib280.list.LinkedList280;

/** This class creates a Sparse Array  */
public class SparseArray280<I> {
	
	/** The sparse array */
	protected LinkedList280<Pair280<I, Integer>> sparseItems;
	
	/** creates empty sparse array */
	public SparseArray280(){
		this.sparseItems = new LinkedList280<Pair280<I, Integer>>();
	}

	/** gets the item at a certain index
	 * @param idx - the index to check out
	 * @return returns item at index if found, null if not */
	public I getItemAtIndex(int idx){
		
		LinkedIterator280<Pair280<I, Integer>> iter = new LinkedIterator280<Pair280<I, Integer>>(sparseItems);
		
		iter.goFirst();
		{
			while(iter.itemExists())
			{
				if (iter.item().secondItem()==idx)
				{
					//System.out.println("Found Item!");
					return (I) iter.item().firstItem();
				}
				else
					iter.goForth();
			}
		}
		return null;
		
	}
	
	/** sets an item (I) at idx (int)).
	 * @param item - the item to be inserted
	 * @param idx - the index of which to insert the item */
	public void setItemAtIndex(I item, int idx){
		// Tests if the list is empty, placing the head item if so
		if (this.sparseItems.isEmpty()){
			Pair280<I, Integer> newPair = new Pair280<I, Integer>(item,idx);
			sparseItems.insertFirst(newPair);
			//System.out.println("Inserted first element!");
		}
		else{
			LinkedIterator280<Pair280<I, Integer>> iter = new LinkedIterator280<Pair280<I, Integer>>(sparseItems);
			
			iter.goFirst();
			boolean placed = false;
			while(placed == false)
			{
				// Checks if there is already an item at idx, overwrites it if so
				if (iter.item().secondItem()==idx){ 
					iter.item().setFirstItem(item);
					//System.out.println("changed existing");
					placed = true;
				}
				// 
				else if (iter.item().secondItem()>idx) 
				{
					Pair280<I, Integer> newPair = new Pair280<I, Integer>(item,idx);
					sparseItems.insertBefore(newPair);
					placed = true;
				}
				else{
					if (iter.item() == sparseItems.lastItem()){
						Pair280<I, Integer> newPair = new Pair280<I, Integer>(item,idx);
						sparseItems.insertLast(newPair);
						placed = true;
					}
					iter.goForth();
				}
			}
		}
		
	}
	
	/** Testing for this class */
	public static void main(String[] args) {
		SparseArray280<String> L = new SparseArray280<String>();
		
		// Test first input and follow up input
		L.setItemAtIndex("test", 1);
		L.setItemAtIndex("test2", 3);
		if (L.getItemAtIndex(1) == "test1"){
			System.out.println("Initial setItemAtIndex is working as well as getItemAtIndex");
		}
		else
			System.out.println("setItemAtIndex or getItemAtIndex is not working");
		
		// test placing item such that there is a gap between one index and another
		if (L.getItemAtIndex(3) == "test2"){
			System.out.println("Successfully placed and retrieved item that has an index  gap between the prior item");
		}
		else{
			System.out.println("Unsuccessful in retrieving or setting item with gap between the prior item");
		}
		
		// test overwrite
		L.setItemAtIndex("Overwrite",3);
		if (L.getItemAtIndex(3) == "Overwrite"){
			System.out.println("Successful overwrite of Item portion of Pair");
		}
		else{
			System.out.println("Unsuccessful in overwriting Item portion of Pair");
		}
		
		L.setItemAtIndex("Successful Placement of distant item", 1000);
		L.setItemAtIndex("Successful Placement of near item", 500);
		// test overwrite of firstitem
		L.setItemAtIndex("Successfully overwrite first item", 1);
		
		// more tests for getItemAtIndex
		System.out.println(L.getItemAtIndex(1000));
		System.out.println(L.getItemAtIndex(500));
		System.out.println(L.getItemAtIndex(1));
	}
}
