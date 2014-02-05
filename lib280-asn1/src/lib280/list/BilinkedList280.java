package lib280.list;

 


import lib280.base.*;
import lib280.exception.*;

/**	This list class incorporates the functions of an iterated 
	dictionary such as has, obtain, search, goFirst, goForth, 
	deleteItem, etc.  It also has the capabilities to iterate backwards 
	in the list, goLast and goBack. */
public class BilinkedList280<I> extends LinkedList280<I> implements BilinearIterator280<I>
{
	/* 	Note that because firstRemainder() and remainder() should not cut links of the original list,
		the previous node reference of firstNode is not always correct.
		Also, the instance variable prev is generally kept up to date, but may not always be correct.  
		Use previousNode() instead! */

	/**	Construct an empty list.
		Analysis: Time = O(1) */
	public BilinkedList280()
	{
		// T.ODO
		super ();
	}


	/**
	 * Create a BilinkedNode280 this Bilinked list.  This routine should be
	 * overridden for classes that extend this class that need a specialized node.
	 * @param item - element to store in the new node
	 * @return a new node containing item
	 */
	protected BilinkedNode280<I> createNewNode(I item)
	{
		// T.ODO
		BilinkedNode280<I> newNode = new BilinkedNode280<I>(item);
		return newNode;
	}

	/**
	 * Insert element at the beginning of the list
	 * @param x item to be inserted at the beginning of the list 
	 */
	public void insertFirst(I x) throws ContainerFull280Exception
	{
		// T.ODO
		BilinkedNode280<I> newItem = createNewNode(x);
		newItem.setNextNode(this.head);
		newItem.setPreviousNode(null);
		
		// If the cursor is at the first node, cursor predecessor becomes the new node.
		if( this.position == this.head ) this.prevPosition = newItem;
				
		// Special case: if the list is empty, the new item also becomes the tail.
		if( this.isEmpty() ) this.tail = newItem;
		this.head = newItem;
	}

	
	/**
	 * Insert element at the beginning of the list
	 * @param x item to be inserted at the beginning of the list 
	 */
	public void insert(I x) 
	{
		this.insertFirst(x);
	}

	/**
	 * Insert a new element at the end of the list
	 * @param x item to be inserted at the end of the list 
	 */
	public void insertLast(I x) 
	{
		// T.ODO
		BilinkedNode280<I> newItem = createNewNode(x);
		newItem.setNextNode(null);
		
		// If the cursor is after(), then cursor predecessor becomes the new node.
		if( this.after() ) this.prevPosition = newItem;
		
		// If list is empty, handle special case
		if( this.isEmpty() ) {
			this.head = newItem;
			this.tail = newItem;
		}
		else {
			newItem.setPreviousNode((BilinkedNode280<I>) this.tail);
			this.tail.setNextNode(newItem);
			this.tail = newItem;
		}
	}

	
	/**
	 * Delete the item at which the cursor is positioned
	 * @precond itemExists() must be true (the cursor must be positioned at some element)
	 */
	public void deleteItem() throws NoCurrentItem280Exception
	{
		// T.ODO
		if(!this.itemExists()) throw new NoCurrentItem280Exception("There is no item at the cursor to delete.");
		
		// If we are deleting the first item...
		if( this.position == this.head ) {
			// Handle the special case...
			this.deleteFirst();
			this.position = this.head;
		}
		else {
			// Set the previous node to point to the successor node. 
			this.prevPosition.setNextNode(this.position.nextNode());
			((BilinkedNode280<I>) this.position.nextNode()).setPreviousNode(((BilinkedNode280<I>) this.position).previousNode());
			
			// Reset the tail reference if we deleted the last node.
			if( this.position == this.tail ) {
				this.tail = this.prevPosition;
			}
		}

	}

	
	/**
	 * Remove the first item from the list.
	 * @precond !isEmpty() - the list cannot be empty
	 */
	public void deleteFirst() throws ContainerEmpty280Exception
	{
		// T.ODO
		if( this.isEmpty() ) throw new ContainerEmpty280Exception("Cannot delete an item from an empty list.");
		
		// If the cursor is on the second node, set the prev pointer to null.
		if( this.prevPosition == this.head ) this.prevPosition = null;
		// Otherwise, if the cursor is on the first node, set the cursor to the next node.
		else if (this.position == this.head )  this.position = this.position.nextNode();
		
		// If we're deleting the last item, set the tail to null.
		// Setting the head to null gets handled automatically in the following
		// unlinking.
		if( this.head == this.tail ) this.tail = null;
		
		// Unlink the first node.
		BilinkedNode280<I> oldhead = (BilinkedNode280<I>) this.head;
		this.head = this.head.nextNode();
		oldhead.setNextNode(null);
	}

	/**
	 * Remove the last item from the list.
	 * @precond !isEmpty() - the list cannot be empty
	 */
	public void deleteLast() throws ContainerEmpty280Exception
	{
		// T.ODO
		// Special cases if there are 0 or 1 nodes.
		if( this.isEmpty() ) throw new ContainerEmpty280Exception("Cannot delete an item from an empty list.");
		else if( this.head != null && this.head == this.tail ) this.deleteFirst();
		else {
			// There are at least two nodes.
		
			// If the cursor is on the last node, we need to update the cursor.
			if( this.position == this.tail )
				this.position = this.prevPosition;
		
			// 	Find the second-last node -- note this makes the deleteLast() algorithm O(n)
			LinkedNode280<I> penultimate = this.head;
			while(penultimate.nextNode() != this.tail) penultimate = penultimate.nextNode();
		
			// Unlink the last node.
			penultimate.setNextNode(null);
			this.tail = penultimate;
			this.prevPosition = ((BilinkedNode280<I>) this.tail).previousNode();
		}
	}

	
	/**
	 * Move the cursor to the last item in the list.
	 */
	public void goLast() throws ContainerEmpty280Exception
	{
		// T.ODO
		if( this.isEmpty() ) throw new ContainerEmpty280Exception("Cannot position cursor at last element of an empty list.");
		this.position = this.tail;
	}
  
	
	/**
	 * Move the cursor one element closer to the beginning of the list.
	 * 	@precond !before() - the cursor cannot already be before the first element. 
	 */
	public void goBack() throws BeforeTheStart280Exception
	{
		// T.ODO
		if (this.position == this.head)
		{
			throw new BeforeTheStart280Exception("Cannot position cursor before first element.");
		}
		else
		{
			this.position = this.prevPosition;
		}

	}

	/**	Iterator for list initialized to first item. 
		Analysis: Time = O(1) 
	*/
	public BilinkedIterator280<I> iterator()
	{
		return new BilinkedIterator280<I>(this);
	}

	/**	Go to the position in the list specified by c. <br>
		Analysis: Time = O(1) 
		@param c position to which to go */
	@SuppressWarnings("unchecked")
	public void goPosition(CursorPosition280 c)
	{
		if (!(c instanceof BilinkedIterator280))
			throw new InvalidArgument280Exception("The cursor position parameter" 
					    + " must be a BilinkedIterator280<I>");
		BilinkedIterator280<I> lc = (BilinkedIterator280<I>) c;
		this.position = (BilinkedNode280<I>) lc.cur;
		this.prevPosition = (BilinkedNode280<I>) lc.prev;
	}

	/**	The current position in this list. 
		Analysis: Time = O(1) */
	public BilinkedIterator280<I> currentPosition()
	{
		return  new BilinkedIterator280<I>(this, this.prevPosition, this.position);
	}

	
  
	/**	A shallow clone of this object. 
		Analysis: Time = O(1) */
	public BilinkedList280<I> clone() throws CloneNotSupportedException
	{
		return (BilinkedList280<I>) super.clone();
	}

	
	public static void main(String args[]) {
		// T.ODO
		// Write a regression test for the BilinkedList280<I> class.
		
		// Test creation of list, createNewNode is tested via insertFirst
		// insertFirst, insertLast, deleteItem, deleteFirst, deleteLast
		// goLast, goBack,  
		
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
		System.out.print("Insert Test\nShould read: 2, 1, 9, 8, \n  and it is: ");
		System.out.print(L);
		
		// Test delete methods
		L.deleteFirst();
		L.deleteLast();
		System.out.print("\n\nDelete Test\nShould read: 1, 9, \n  and it is: ");
		System.out.print(L);
		
		// Test goLast and goBack
		L.goLast();
		if (L.item() == 9)
			System.out.print("\n\ngoLast worked.");
		else
			System.out.print("\n\ngoLast resulted in the incorrect number, it should have been 9.");

		
		L.goBack();
		if (L.item() == 1)
			System.out.print("\ngoBack worked.");
		else
			System.out.print("\n\ngoBack resulted in the incorrect number, it should have been 1.");
		
		
		// Finally, test's deleteItem
		L.deleteItem();
		System.out.print("\n\ndeleteItem should delete 1.\n The only remaining number" +
				" should be: 9\n                           and it is: ");
		System.out.print(L.item().toString());
		
		
		// Test deleting from empty list
		BilinkedList280<Integer> E = new BilinkedList280<Integer>();
		
		if (!E.isEmpty())
		{
			System.out.println("Error, List is not empty when it should be.");
		}
		
		System.out.println("\n\nAttempting deleteItem on empty list");
		try {
			E.deleteItem();
			System.out.println("\n\nError: deleteItem worked, should have thrown exception");}
		catch (NoCurrentItem280Exception e)
		{
			System.out.println("Caught expected exception. OK!");
		};
		System.out.println("Attempting deleteFirst on empty list");
		try {
			E.deleteFirst();
			System.out.println("Error: deleteFirst worked, should have thrown exception");}
		catch (ContainerEmpty280Exception e)
		{
			System.out.println("Caught expected exception. OK!");
		};
		System.out.println("Attempting deleteLast on empty list");
		try {
			E.deleteLast();
			System.out.println("Error: deleteLast worked, should have thrown exception");}
		catch (ContainerEmpty280Exception e)
		{
			System.out.println("Caught expected exception. OK!");
		};
		
		
		
		// Test goLast and goBack on empty list, or before elements.
		System.out.println("\nAttempting goLast() on empty list:");
		try {
			E.goLast();
			System.out.println("\nError: goLast worked, should have thrown exception");}
		catch (ContainerEmpty280Exception e)
		{
			System.out.println("Caught expected exception. OK!");
		};
		
		System.out.println("Attempting goBack() on empty list:");
		try {
			E.goBack();
			System.out.println("Error: goBack worked, should have thrown exception");}
		catch (BeforeTheStart280Exception e)
		{
			System.out.println("Caught expected exception. OK!");
		};
		
		E.insert(5);
		E.goFirst();
		System.out.println("Attempting goBack() before first element in list:");
		try {
			E.goBack();
			System.out.println("Error: goBack worked, should have thrown exception");}
		catch (BeforeTheStart280Exception e)
		{
			System.out.println("Caught expected exception. OK!");
		};
	}
}
	
