package lib280.dispenser;



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

