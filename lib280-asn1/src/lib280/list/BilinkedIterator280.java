package lib280.list;

 


import lib280.exception.*;
import lib280.base.*;

/**	A LinkedIterator which has functions to move forward and back, 
	and to the first and last items of the list.  It keeps track of 
	the current item, and also has functions to determine if it is 
	before the start or after the end of the list. */
public class BilinkedIterator280<I> extends LinkedIterator280<I> implements BilinearIterator280<I>
{

	/**	Constructor creates a new iterator for list 'list'. <br>
		Analysis : Time = O(1) 
		@param list list to be iterated */
	public BilinkedIterator280(BilinkedList280<I> list)
	{
		super(list);
	}

	/**	Create a new iterator at a specific position in the newList. <br>
		Analysis : Time = O(1)
		@param newList list to be iterated
		@param initialPrev the previous node for the initial position
		@param initialCur the current node for the initial position */
	public BilinkedIterator280(BilinkedList280<I> newList, 
			LinkedNode280<I> initialPrev, LinkedNode280<I> initialCur)
	{
		super(newList, initialPrev, initialCur);
	}
    
	/**
	 * Move the cursor to the last element in the list.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void  goLast()
	{
		// T.ODO
		cur = list.lastNode();
		prev = ((BilinkedNode280) list.lastNode()).previousNode();
	}

	/**
	 * Move the cursor one element closer to the beginning of the list
	 * @precond !before() - the cursor cannot already be before the first element.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void goBack() throws BeforeTheStart280Exception
	{
		// T.ODO
		if (cur == list.firstNode())
		{
			throw new BeforeTheStart280Exception("Cannot position cursor before first element of list");
		}
		cur = prev;
		prev = ((BilinkedNode280) cur).previousNode();
    }

	/**	A shallow clone of this object. <br> 
	Analysis: Time = O(1) */
	public BilinkedIterator280<I> clone()
	{
		return (BilinkedIterator280<I>) super.clone();
	}	  
	
	public static void main(String args[]) {
		// T.ODO
		// Write a regression test for the BilinkedIterator280 class
		// You can assume that the methods inherited from LinkedIterator280 have
		// already been tested in that module.
		
		BilinkedList280<Integer> L = new BilinkedList280<Integer>();
		
		if (!L.isEmpty())
		{
			System.out.print("Error, List is not empty when it should be.");
		}
		
		// Test insert methods
		L.insertFirst(1);
		L.insertFirst(2);
		L.insertLast(9);
		L.insertLast(8);
		
		BilinkedIterator280<Integer> iter = new BilinkedIterator280<Integer>(L);
		
		// Test's goLast and goBack on populated list
		iter.goLast();
		if (iter.item() == 8)
			System.out.println("goLast() worked.");
		else
			System.out.println("goLast() did not work, did not get 8");
		
		iter.goBack();
		if (iter.item() == 9)
			System.out.println("goBack() worked.");
		else
			System.out.println("goBack() did not work, did not get 9");
		
		// Test's goBack() on Head node 

		System.out.println("\nAttempting goBack() before first element in list.");
		iter.goFirst();
		try {
			iter.goBack();
			System.out.println("goBack() should have caused an exception trying to" +
					"go back before the head item");
		}
		catch (BeforeTheStart280Exception e)
		{
			System.out.println("Caught expected exception. OK!");
		}
		
		
		
	}
	
} 
