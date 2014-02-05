// Kennedy Slawinski
// kts192
// 11053598
// CMPT 280
// Asn2q7


package lib280.tree;


import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.InvalidState280Exception;
import lib280.exception.NoCurrentItem280Exception;




public class ArrayedBinaryTree280<I> implements ArrayedTree280<I> {

	/** the array holding everything */
	protected I arr[];
	
	/** the cursor that travels through the array */
	private int curs = 1;
	
	/** the next empty spot of the array (if not full)  */
	private int last = 1;
	/**
	 * Initializes arrTree to size
	 * @param size - the size of the array
	 */
	@SuppressWarnings("unchecked")
	public ArrayedBinaryTree280(int size) {
		this.arr = (I[])new Object[size+1]; // + 1 because the first node must be empty, so it goes 1-size+1, not 0-size
	}
	
	/** gets the parent index of current spot 
	 * @param idx - array index in arrayed tree
	 * @return parent index of given index */
	private int getParentIndex(int idx){
		if (idx%2 ==0)
			return idx = idx-1;
		return idx/2;
	}
	
	/** get left child index of current spot
	 * @param idx an index of arrayed tree
	 * @return left child of given index */
	private int getLeftChildIndex(int idx){
		return idx*2;
	}
	
	/** gets the right child index of arrayed tree
	 * @param idx an index of arrayed tree
	 * @return right child of given index */
	private int getRightChildIndex(int idx){
		return idx*2+1;
	}

	/**
	 * Add an item to the tree.
	 * @param item The item to be added.
	 * @precond The tree must not be full.
	 */
	public void insert(I item){
		if (isEmpty()){
			arr[1] = item;
			last++;
		}
		else{
			arr[last] = item;
			last++;
		}
			
	}
	
	
	/**
	 * Set the cursor position to the parent of the current item.
	 * @precond Tree cannot be empty and the current node must not be the root.
	 */
	public void parent(){
		if (curs == 1)
			throw new InvalidState280Exception("Cursor must not be root");
		else if (isEmpty())
			throw new ContainerEmpty280Exception("The tree is empty");
		else
			curs = getParentIndex(curs);
	}
	
	/**
	 * Set the cursor position to the left child of the current item.
  	 * @precond The tree must not be empty and the current node must have a left child.
	 * 
	 */
	public void goLeftChild(){
		if (isEmpty())
			throw new ContainerEmpty280Exception("The tree is empty");
		else
			curs = getLeftChildIndex(curs);
		if (arr[curs] == null)
			throw new InvalidState280Exception("No left child.");			
		
	}
	
	/**
	 * set the cursor position to the right child of the current item.
	 * @precond The tree must not be empty and the current node must have a right child.
	 */
	public void goRightChild(){
		if (isEmpty())
			throw new ContainerEmpty280Exception("The tree is empty");
		else
			curs = getRightChildIndex(curs);
		if (arr[curs] == null)
			throw new InvalidState280Exception("No right child.");			
		
	}
	
	/** 
	 * Set the cursor position to the sibling of the current item. 
	 * @precond The current node must have a sibling.  The tree must not be empty.
	 */
	public void goSibling(){
		if (isEmpty())
			throw new ContainerEmpty280Exception("The tree is empty");
		else{
			int temp = curs;
			parent();
			goLeftChild(); 
			if (arr[curs] == null) 	// stays at left if not null
				throw new InvalidState280Exception("No sibling.");
			if (curs == temp){		// arrived at position we left, go to other child (if exists)
				parent();
				goRightChild();
				if (arr[curs] == null)	// if not null, stay here
						throw new InvalidState280Exception("No sibling");
			}
		}
	}
	
	/**
	 * Set the cursor position to the root item.
	 * @precond The tree must not be empty.
	 */
	public void root(){
		if (isEmpty())
			throw new ContainerEmpty280Exception("Tree is empty");
		curs = 1;
	}
	
	/**
	 * Remove the item at the cursor from the tree.
	 * @precond There must be an item at the cursor.  The tree must not be empty.
	 */
	public void removeItem(){
		if (isEmpty())
			throw new ContainerEmpty280Exception("Tree is empty");
		else if (arr[curs] == null)
			throw new InvalidState280Exception("No item at cursor.");
		else{
			//System.out.println(last);
			arr[curs]=arr[last-1];
			arr[last-1]=null;
			last--;
		}
		

	}
	
	
	
	/** returns the current item at the cursor
	 * @return the item the cursor is currently at */
	public I item() throws NoCurrentItem280Exception {
		return arr[curs];
	}
	
	/** checks if there is currently an item at the cursor
	 * @return true if item found, false if null */
	public boolean itemExists() {
		if (arr[curs] != null)
			return true;
		return false;
	}
	
	/** checks if the array is empty
	 * @return true if it is empty, false otherwise */
	public boolean isEmpty() {
		if (arr[1]== null)
			return true;
		return false;
	}
	

	/** checks if the array is full
	 * @return true if full, false if not  */
	public boolean isFull() {
		if (last >= arr.length)
			return true;
		return false;
	}
	

	/** clearst he array, setting everything to null */
	public void clear() {
		for (int i = 0; i<arr.length;i++){
			arr[i]=null;
			last = 1;
			curs = 1;
		}
	}
	
	
	
	
	
	/**
	 * This tests the class
	 */
	public static void main(String[] args) {
		ArrayedBinaryTree280<Integer> r = new ArrayedBinaryTree280<Integer>(20);
		if (r.isEmpty() != true)
			System.out.println("error: The array is not empty, it should be.");
		else
			System.out.println("The array is empty, as it should be.");
		

		
		r.insert(1);
		if (r.item() == 1)
			System.out.println("insert() works, item()works.");
		else
			System.out.println("error: insert() did not work");
		
		
		try{
			r.goRightChild();
		} catch (InvalidState280Exception e){System.out.println("Caught no right child exception");}
		
		try{
			r.goLeftChild();
		} catch (InvalidState280Exception e){System.out.println("Caught no left exception");}
		r.curs = 1; // reset curs to where it was
		
		// populate array tree
		for (int i = 2; i<21;i++)
			r.insert(i);
		if (r.isFull())
			System.out.println("isFull() is working");
		else
			System.out.println("error: isFull() is not working.");
	
		r.goLeftChild();
		if (r.item() == 2)
			System.out.println("goLeftChild() is working");
		else
			System.out.println("error: goLeftChild() is not working");
		
		
		r.parent();
		if (r.item() ==1)
			System.out.println("parent() is working");
		else
			System.out.println("error: parent() is not working.");
		
		r.goRightChild();
		if (r.item()==3)
			System.out.println("goRightChild() is working");
		else
			System.out.println("error: goRightChild() is not working.");
		
		r.goSibling();
		if (r.item() ==2)
			System.out.println("goSibling() is working right sibling to left");
		else
			System.out.println("error: goSibling() is not working right sibling to left.");
		
		r.goSibling();
		if (r.item() ==3)
			System.out.println("goSibling() is working left sibling to right");
		else
			System.out.println("error: goSibling() is not working left sibling to right.");
		
		r.root();
		if (r.item() == 1)
			System.out.println("root() is working.");
		else
			System.out.println("error: root() is not working.");
		
		r.removeItem();
		if (r.item() == 20)
			System.out.println("remove() is working.");
		else
			System.out.println("error: remove() is not working.");
		
		r.clear();
		if (r.isEmpty())
			System.out.println("clear() is working.");
		else
			System.out.println("error: clear() is not working.");
		
		try{
			r.goLeftChild();
		} catch (ContainerEmpty280Exception e){System.out.println("Caught empty exception");}
		
		for (int i=1;i<=20;i++)
			r.insert(i);
		
		
		
		r.removeItem();
		if (r.item() == 20)
			System.out.println("remove() works on the last array value");
		else
			System.out.println("error: remove() does not work on the last array value");

		if (r.itemExists())
			System.out.println("itemExists() works.");
		else
			System.out.println("error: itemExists() does not work.");
		
		
				
		try{
			r.curs = 1;
			r.parent();
		} catch (InvalidState280Exception e){System.out.println("Caught cursor root exception");}
		
		r.clear();
		r.insert(1);
		r.insert(2);
		r.curs = 2;
		try{
			r.goSibling();
		}catch (InvalidState280Exception e){System.out.println("Caught sibling exception");	}
		
		r.curs = 15;
		try{
			r.removeItem();
		} catch (InvalidState280Exception e){System.out.println("Caught no item at cursor exception");}
		
		
	}


}
