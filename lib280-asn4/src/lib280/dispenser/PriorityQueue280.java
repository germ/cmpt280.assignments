package lib280.dispenser;
import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.ContainerFull280Exception;
import lib280.exception.InvalidState280Exception;
import lib280.tree.ArrayedHeap280;

public class PriorityQueue280<I extends Comparable<? super I>>{
	
	/* The ArrayedHeap used for priority queue */
	ArrayedHeap280<I> queue;
	
	
	/* Constructs a Priority queue from an ArrayedHeap
	 * @param cap the capacity */
	public PriorityQueue280(int cap){
		this.queue = new ArrayedHeap280<I>(cap);
	}
	
	/* inserts I item into queue
	 * @param queue is not full*/
	public void insert (I item) {
		try {queue.insert(item);}
		catch(InvalidState280Exception e) {throw new ContainerFull280Exception("Attempt to insert into a full tree.");}
		
	}
	
	/* remove item from queuee that has highest priority
	 * @param queue is not empty */
	public void deleteItem(){
		queue.removeItem();
	}
	
	/* Determines whether or not the queue is full
	 * @return true or false depending on queue state */
	public boolean isFull(){
		return queue.isFull();
	}
	
	/* determines whether or not the queue is empty
	 * @return true or false depending on queue state */
	public boolean isEmpty(){
		return queue.isEmpty();
	}
	
	/* obtains the number of items in the queue
	 * @return count the number of items in the queue*/
	public int count(int num){
		return queue.count();
	}
	
	/* obtains the item in queue that has the highest priority
	 * @precond the queue is not empty
	 * @return item the item that was the highest priority */
	public I item(){
		queue.root();
		return queue.item();
	}
	
	
	/* returns the values in the queue
	 * @return the items in the string */
	public String toString(){
		return queue.toString();
	}

    /* ***********************
	 * Regression test for PriorityQueue280. 
	 * Place this within the PriorityQueue280 class that you define.
     */

	public static void main(String [] args) {

		PriorityQueue280<Integer> Q = new PriorityQueue280<Integer>(5);

		// Test maxItem() on empty queue.
		Exception ex = null;
		try {Q.item();}
		catch(ContainerEmpty280Exception e) { ex = e; }

		if( ex == null ) 
			System.out.printf("maxItem() on an empty queue failed to produce an excption.");

		// Test deleteMax() on empty queue.
		ex = null;
		try {Q.deleteItem();}
		catch(ContainerEmpty280Exception e) { ex = e; }

		if( ex == null ) 
			System.out.printf("deleteMax() on an empty queue failed to produce an excption.");

		// Insert 5 items to make the queue full.
		for(int i=0; i < 5; i++) Q.insert(i);

		// Test insert on full queue.
		ex = null;
		try {Q.insert(5);}
		catch(ContainerFull280Exception e) { ex = e; }

		if( ex == null ) 
			System.out.printf("insert() on an full queue failed to produce an excption.");

		// Check the contents of the queue:
        // NOTE: The expected output is the order in which the elements
        // appear in the array of the Arrayed Heap.  This means your
        // toString() method for the priority queue should just return
        // the toString() result of the arrayed heap.
		System.out.println("Queue Contents: " + Q);
		System.out.println("Expected:       4, 3, 1, 0, 2,");

		// Check that we get the items off the queue in the proper order:
		for(int i=4; i >=0; i--) {
			Integer front = Q.item();
			Q.deleteItem();
			if( front != i ) 
				System.out.println("maxItem() failed: expected " + i + ", got " + front + ".");

		}
	}
}
