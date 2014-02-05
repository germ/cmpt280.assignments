/**
 * 
 */
package lib280.tree;

import lib280.exception.InvalidState280Exception;
import lib280.exception.NoCurrentItem280Exception;

/**
 * @author ken
 *
 */
public class ArrayedHeap280<I extends Comparable<I>> extends ArrayedBinaryTree280<I> {

	/** Creates an ArrayedHeap of size Cap
	 * @param cap the size of the array to create */
	@SuppressWarnings("unchecked")
	public ArrayedHeap280(int cap) {
		super(cap);
		this.items = (I[]) new Comparable[cap+1];
	}
	
	
	/**
	 * Add an item to the tree, moves it to correct position
	 * 
	 * @param item The item to be added.
	 * @precond The tree must not be full.
	 */
	public void insert(I item) throws InvalidState280Exception {  
		
		if( isFull() ) throw new InvalidState280Exception("Attempt to insert into a full tree.");
		else {
			count ++;
			items[count] = item;
		}
		
		int position = count;
		I temp;
		
		// Moves the inserted node to the correct position in the heap array.
		while (items[position] != items[1] && items[position].compareTo(items[findParent(position)]) > 0){
			temp = items[findParent(position)];
			items[findParent(position)] = items[position];
			items[position] = temp;
			position = findParent(position);
		}
		
	}


	/** 
	 * Remove the current item from the tree.
	 * 
	 * @precond There must be an item at the cursor.  The tree must not be empty.
	 */
	public void removeItem() throws NoCurrentItem280Exception {
		
		if(!itemExists() ) throw new NoCurrentItem280Exception();
		
		// If there was more than one item, and we aren't deleting the last item,
		// copy the last item in the array to the current position.
		if( count > 1 && currentNode < count ) {
			items[1] = items[count];
		}
		
		count--;
		
		// If we deleted the last item, make the previous item the new position.
		if( currentNode == count+1 ) currentNode--;
		
		int position = 1;
		I temp;
		while (largerChild(position) != position && items[position].compareTo(items[largerChild(position)]) < 0){
			temp = items[largerChild(position)];
			items[largerChild(position)] = items[position];
			items[position] = temp;
			position = largerChild(position);
		}

		
		
	}	
	
	/** Checks the current node's children for whichever is largest
	 * @return larger of the 2 children (if they exist, else returns current node passed in)*/	
	public int largerChild(int current){
		// check if both left and right children are smaller than count
		if (current*2 <= this.count && current*2+1 <= this.count && current*2 >= 0 && current*2+1 >= 0){
			// left is larger
			if (items[(current*2)].compareTo(items[(current*2+1)])>0) 
				return current*2;
			// right is larger
			else
				return current*2+1;
		}
		// check if just the left is smaller than count
		else if (current*2<=this.count)
			return current*2;
		// no more children
		else
			return current;
	}

	/**
	 * tests the functions
	 */
	public static void main(String[] args) {
		ArrayedHeap280<Integer> T = new ArrayedHeap280<Integer>(10);
		T.insert(8);
		T.insert(4);
		T.insert(9);
		T.insert(1);
		T.insert(100);

		System.out.println("Heap list: should go \n\"100, 9, 8, 1, 4, \"\nThe list is:\n\"" + T + "\"\n" +
				"If they match, insert() worked.");
		T.removeItem();

		System.out.println("Heap list: should go \n\"9, 4, 8, 1, \"\nThe list is:\n\"" + T + "\"\n" +
				"If they match, removeItem() worked.");
	}

}
