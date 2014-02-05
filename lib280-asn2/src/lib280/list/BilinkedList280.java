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
		super();
	}

	/**
	 * Create a BilinkedNode280 this Bilinked list.  This routine should be
	 * overridden for classes that extend this class that need a specialized node.
	 * @param item - element to store in the new node
	 * @return a new node containing item
	 */
	protected BilinkedNode280<I> createNewNode(I item)
	{
		return new BilinkedNode280<I>(item);
	}

	/**
	 * Insert element at the beginning of the list
	 * @param x item to be inserted at the beginning of the list 
	 */
	public void insertFirst(I x) 
	{
		BilinkedNode280<I> newNode = new BilinkedNode280<I>(x);
		newNode.setNextNode(this.head);
		newNode.setPreviousNode(null);
		
		// If the list was empty, new node becomes tail as well.
		if(this.isEmpty()) {
			this.tail = newNode;
		}
		else {
			// Otherwise, make the old head point back to the new head.
			((BilinkedNode280<I>)this.head).setPreviousNode(newNode);
		}
		// If the cursor was on the first item, then the cursor's predecessor has to be the new first item.
		if( this.position == this.head ) 
			this.prevPosition = newNode;
		
		// New node becomes the head.
		this.head = newNode;
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
	 * Insert an item before the current position.
	 * @param x - The item to be inserted.
	 */
	public void insertBefore(I x) throws InvalidState280Exception {
		if( this.before() ) throw new InvalidState280Exception("Cannot insertBefore() when the cursor is already before the first element.");
		
		// If the item goes at the beginning or the end, handle those special cases.
		if( this.head == position ) {
			insertFirst(x);  // special case - inserting before first element
		}
		else if( this.after() ) {
			insertLast(x);   // special case - inserting at the end
		}
		else {
			// Otherwise, insert the node between the current position and the previous position.
			BilinkedNode280<I> newNode = new BilinkedNode280<I>(x);
			newNode.setNextNode(position);
			newNode.setPreviousNode((BilinkedNode280<I>)this.prevPosition);
			prevPosition.setNextNode(newNode);
			((BilinkedNode280<I>)this.position).setPreviousNode(newNode);
			
			// since position didn't change, but we changed it's predecessor, prevPosition needs to be updated to be the new previous node.
			prevPosition = newNode;			
		}
	}
	
	
	/**	Insert x before the current position and make it current item. <br>
		Analysis: Time = O(1)
		@param x item to be inserted before the current position */
	public void insertPriorGo(I x) 
	{
		this.insertBefore(x);
		this.goBack();
	}

	/**	Insert x after the current item. <br>
		Analysis: Time = O(1) 
		@param x item to be inserted after the current position */
	public void insertNext(I x) 
	{
		if (isEmpty() || before())
			insertFirst(x); 
		else if (this.position==lastNode())
			insertLast(x); 
		else if (after()) // if after then have to deal with previous node  
		{
			insertLast(x); 
			this.position = this.prevPosition.nextNode();
		}
		else // in the list, so create a node and set the pointers to the new node 
		{
			BilinkedNode280<I> temp = createNewNode(x);
			temp.setNextNode(this.position.nextNode());
			temp.setPreviousNode((BilinkedNode280<I>)this.position);
			((BilinkedNode280<I>) this.position.nextNode()).setPreviousNode(temp);
			this.position.setNextNode(temp);
		}
	}

	/**
	 * Insert a new element at the end of the list
	 * @param x item to be inserted at the end of the list 
	 */
	public void insertLast(I x) 
	{
		if (this.isEmpty())
			this.insertFirst(x); 
		else	// make a new node and insert it at after the last node
		{
			BilinkedNode280<I> temp = this.createNewNode(x);
			this.tail.setNextNode(temp); 
			temp.setPreviousNode((BilinkedNode280<I>)this.tail);
			this.tail = temp;
			if (this.after()) 
				this.prevPosition = this.tail;
		}
	}

	/**
	 * Delete the item at which the cursor is positioned
	 * @precond itemExists() must be true (the cursor must be positioned at some element)
	 */
	public void deleteItem() throws NoCurrentItem280Exception
	{
		if (!this.itemExists())
			throw new NoCurrentItem280Exception("Cannot delete an item that does not exist.");  
		
		if (this.position==this.head)
			this.deleteFirst(); 
		else // have to delete the node from the list and update the pointers of prev and cur 
		{ 
			this.prevPosition = ((BilinkedNode280<I>)this.position).previousNode();  // this line may not be necessary
			this.prevPosition.setNextNode(this.position.nextNode());
			if(this.position.nextNode() != null)
				((BilinkedNode280<I>) this.position.nextNode()).setPreviousNode((BilinkedNode280<I>)this.prevPosition);
			if (this.position==this.tail)
				this.tail = this.prevPosition;
			this.position = this.position.nextNode();
		}     
	}

	/**
	 * Remove the first item from the list.
	 * @precond !isEmpty() - the list cannot be empty
	 */
	public void deleteFirst() throws ContainerEmpty280Exception
	{
		if (this.isEmpty())
			throw new ContainerEmpty280Exception("Cannot delete an item from an empty list.");
		
		super.deleteFirst(); 
		if (!this.isEmpty())
			((BilinkedNode280<I>)this.head).setPreviousNode(null);
	}

	/**
	 * Remove the last item from the list.
	 * @precond !isEmpty() - the list cannot be empty
	 */
	public void deleteLast() throws ContainerEmpty280Exception
	{
		if (this.isEmpty())
			throw new ContainerEmpty280Exception("Cannot delete last item of an empty list.");
		
		if (this.head==this.tail)
			deleteFirst(); 
		else // delete the last node and update prev and cur if necessary
		{ 
			if (this.prevPosition==this.tail)
				this.prevPosition = ((BilinkedNode280<I>)this.tail).previousNode();
			else if (this.position==this.tail)
				this.position = null;
			this.tail = ((BilinkedNode280<I>)this.tail).previousNode();
			
			if (this.tail!=null)
				this.tail.setNextNode(null);
		}
	}

	
	/**
	 * Move the cursor to the last item in the list.
	 */
	public void goLast()
	{
		this.position = this.tail;
		if (this.position==null)
			this.prevPosition = null;
		else
			this.prevPosition = ((BilinkedNode280<I>)this.position).previousNode();
	}
  
	/**	Move back one item in the list. 
		Analysis: Time = O(1)
		@precond !before() 
	 */
	public void goBack() throws BeforeTheStart280Exception
	{
		if (this.before()) 
			throw new BeforeTheStart280Exception("Cannot go back since already before list.");
		
		if (this.after()) // move to the last node 
			this.goLast(); 
		else
		{ 
			this.position = ((BilinkedNode280<I>)this.position).previousNode();     
			if (this.position != null)
			{
				if (this.position == this.head)
					this.prevPosition = null; 
				else
					this.prevPosition = ((BilinkedNode280<I>)this.position).previousNode();
			}
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


} 
