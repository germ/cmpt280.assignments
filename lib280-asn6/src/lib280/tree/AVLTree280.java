package lib280.tree;

import lib280.base.Searchable280;
import lib280.exception.*;


public class AVLTree280<I extends Comparable<? super I>> extends LinkedSimpleTree280<I>
	implements Searchable280<I>
{
	/**	The current node as set by search. */
	protected BinaryNode280<I> cur;

	/**	The parent node of the current node as set by search. */
	protected BinaryNode280<I> parent;

	/**	Do searches continue?. */
	protected boolean searchesContinue = false;

	/**	Are equality comparisons done using object reference comparisons?. */
	protected boolean objectReferenceComparison = false;


	public AVLTree280() {
		this.setRootNode(null);
	}

	protected AVLTreeNode280<I> createNewNode(I item) {
		return new AVLTreeNode280<I>(item);		
	}




	@Override
	public boolean isEmpty() {
		return this.rootNode() == null;
	}



	@Override
	public boolean isFull() {
		return false;
	}



	@Override
	public I item() throws NoCurrentItem280Exception {
		if( !itemExists() ) {
			throw new NoCurrentItem280Exception("Error retrieving current item: There is no current item.");
		}
		return cur.item();
	}



	@Override
	public boolean itemExists() {
		return cur != null;
	}

	public AVLTree280<I> rootLeftSubtree() throws ContainerEmpty280Exception {		
		
		return (AVLTree280<I>) super.rootLeftSubtree();
	}

	public AVLTree280<I> rootRightSubtree() throws ContainerEmpty280Exception {		
		return (AVLTree280<I>) super.rootRightSubtree();	
	}

	/**	Is the current position below the bottom of the tree?. <br>
	Analysis: Time = O(1) */
	protected boolean below()
	{
		return (cur == null) && (parent != null || isEmpty());
	}

	/**	Is the current position above the root of the tree?. <br>
	Analysis: Time = O(1) */
	protected boolean above()
	{
		return (parent == null) && (cur == null);
	}

	/**	Go to item x, if it is in the tree.  If searchesContinue, continue in the right subtree. <br>
	Analysis : Time = O(h) worst case, where h = height of the tree */
	public void search(I x)
	{
		boolean found = false;
		if (!searchesContinue || above())
		{
			parent = null;
			cur = rootNode;
		}
		else if (!below())
		{
			parent = cur;
			cur = cur.rightNode();
		}
		while (!found && itemExists())
		{
			if (x.compareTo(item()) < 0)
			{
				parent = cur;
				cur = parent.leftNode();
			}
			else if (x.compareTo(item()) > 0)
			{
				parent = cur;
				cur = parent.rightNode();
			}
			else
				found = true;
		}
	}

	/**	Does the tree contain x?. <br>
	Analysis : Time = O(h) worst case, where h = height of the tree */
	public boolean has(I x)
	{
		BinaryNode280<I> saveParent = parent;
		BinaryNode280<I> saveCur = cur;
		search(x);
		boolean result = itemExists();
		parent = saveParent;
		cur = saveCur;
		return result;
	}

	/**	Restart searches each time search is called. <br>
	Analysis: Time = O(1) */
	public void restartSearches()
	{
		searchesContinue = false;
	}

	/**	Resume searches after each call to search. <br>
	Analysis: Time = (1) */
	public void resumeSearches()
	{
		searchesContinue = true;
	}

	/**	Test whether x equals y using the current comparison mode. <br>
	Analysis: Time = O(1) */
	public boolean membershipEquals(I x, I y)
	{
		if (objectReferenceComparison)
			return x == y;
		else if ((x instanceof Comparable) && (y instanceof Comparable))
			return 0 == x.compareTo(y);
		else if (x.equals(y))
			return true;
		else 
			return false;
	}

	/**	Set comparison operations to use ==. <br>
	Analysis: Time = O(1) */
	public void compareObjectReferences()
	{
		objectReferenceComparison = true;
	}

	/**	Set comparison operations to use equal() or compareTo().  <br>
	Analysis: Time = O(1) */
	public void compareContents()
	{
		objectReferenceComparison = false;
	}


	/** 
	 * Insert an item into the tree.
	 * 
	 * @param item The item to insert.
	 */
	public void insert(I item) {
		this.insert(item, null);
	}


	/**
	 * Insert the item in the tree with knowledge of the parent of this tree.
	 * @param item The item to insert in this tree.
	 * @param parent The parent node of the root of this tree.
	 */
	protected void insert(I item, AVLTreeNode280<I> parent) {
		// If the tree is empty, then just make the item the root of the tree.
		if( this.isEmpty() ) {
			this.setRootNode(this.createNewNode(item));
			return;
		}

		// Recursively find the insertion point (normal ordered binary tree insertion)
		AVLTreeNode280<I> v = this.rootNode();
		if( item.compareTo(v.item()) < 0) {
			// go left
			if( v.leftNode() == null ) {
				v.setLeftNode(new AVLTreeNode280<I>(item));
			}
			else {
				this.rootLeftSubtree().insert(item, this.rootNode());
			}
			v.setLtHeight(
					Math.max(v.leftNode().getLtHeight(), v.leftNode().getRtHeight()) + 1
					);

		}
		else { // Observe: duplicate elements are inserted as the rightmost of all their copies.
			// go right 
			if( v.rightNode() == null ) {
				v.setRightNode(new AVLTreeNode280<I>(item));
			}
			else {
				this.rootRightSubtree().insert(item, this.rootNode());			
			}
			v.setRtHeight(
					Math.max(v.rightNode().getLtHeight(), v.rightNode().getRtHeight()) + 1
					);

		}

		// As the recursion unwinds, check if the current node is critical
		// if it is, then we have to do rotations.
		this.restoreAVLProperty(parent);
	}

	public AVLTreeNode280<I> rootNode() {
		return  (AVLTreeNode280<I>) super.rootNode();
	}


	/**
	 * Determines the imbalance of this tree.  
	 * @return The imbalance of the root of this tree.  If the result is negative, 
	 */
	protected int signedImbalance(AVLTreeNode280<I> N) {
		return N.getLtHeight() - N.getRtHeight();
	}


	/**
	 * Perform a double left rotation at the root of the tree.
	 * 
	 * @param parent The parent node of the root of this tree.
	 */
	protected void doubleLeftRotation(AVLTreeNode280<I> parent) {
		this.rootRightSubtree().rightRotation(this.rootNode());
		this.leftRotation(parent);
	}

	/**
	 * Perform a double right rotation at the root of the tree.
	 * 
	 * @param parent The parent of the root of this tree.
	 */
	protected void doubleRightRotation(AVLTreeNode280<I> parent) {
		this.rootLeftSubtree().leftRotation(this.rootNode());
		this.rightRotation(parent);

	}


	/**
	 * Perform a left rotation at the root of this tree.
	 * 
	 * @param parent The parent node of the root of this tree.
	 */
	protected void leftRotation(AVLTreeNode280<I> parent) {		
		// Obtain the subtrees at positions A, B, C (see slide 8 in the Specialized Tree notes)
		AVLTree280<I> posA = this;
		AVLTree280<I> posC = this.rootRightSubtree();
		AVLTree280<I> posD = posC.rootLeftSubtree();


		// The parent of this.root needs to now point to posC!!!
		if(parent != null) {
			if( parent.leftNode() == this.rootNode() ) parent.setLeftNode(posC.rootNode());
			else parent.setRightNode(posC.rootNode());
		}


		// Update anticipated subtree heights before we move nodes around.
		if( posD.isEmpty() ) {
			posA.rootNode().setRtHeight(0);
		}
		else {
			posA.rootNode().setRtHeight( Math.max(posD.rootNode().getLtHeight(), posD.rootNode().getRtHeight() + 1)	);
		}

		if( posA.isEmpty() ) {
			posC.rootNode().setLtHeight(0);
		}
		else {
			posC.rootNode().setLtHeight(Math.max(posA.rootNode().getLtHeight(), posA.rootNode().getRtHeight() + 1));
		}

		// Either way, we need to do a left rotation of posA.
		posA.rootNode().setRightNode(posD.rootNode());
		posC.rootNode().setLeftNode(posA.rootNode());
		this.setRootNode(posC.rootNode());

	}


	/**
	 * Perform a right rotation at the root of this tree.
	 * 
	 * @param parent The parent node of the root of this tree.
	 */
	protected void rightRotation(AVLTreeNode280<I> parent) {

		// Obtain the subtrees at positions B, C, D (see slide 9 of the Specialized Tree notes)
		AVLTree280<I> posA = this;
		AVLTree280<I> posB = this.rootLeftSubtree();
		AVLTree280<I> posE = posB.rootRightSubtree();

		// The parent of this.root needs to now point to posB!!!
		if(parent != null) {
			if( parent.leftNode() == this.rootNode() ) parent.setLeftNode(posB.rootNode());
			else parent.setRightNode(posB.rootNode());
		}



		// Update anticipated subtree heights before we move nodes around.
		if(posE.isEmpty()) {
			posA.rootNode().setLtHeight(0); 
		}
		else {
			posA.rootNode().setLtHeight( Math.max( posE.rootNode().getLtHeight(), posE.rootNode().getRtHeight() + 1 ));
		}
		if(posA.isEmpty()) {
			posB.rootNode().setRtHeight(0);
		}
		else {
			posB.rootNode().setRtHeight(Math.max( posA.rootNode().getLtHeight(), posA.rootNode().getRtHeight() + 1 ));
		}

		// Either way we do a right rotation of posD.
		posA.rootNode().setLeftNode(posE.rootNode());
		posB.rootNode().setRightNode(posA.rootNode());
		this.setRootNode(posB.rootNode());

	}

	protected void restoreAVLProperty(AVLTreeNode280<I> parent) {
		int imbalanceR = this.signedImbalance(this.rootNode());

		// If the imbalance is <= 1, then N is not a critical node and no rotations are necessary.
		if( Math.abs(imbalanceR) <= 1) return;

		if( imbalanceR == 2 )  { // If root is left heavy...
			if( this.signedImbalance(this.rootNode().leftNode()) >= 0 ) {  // If root.leftNode is left-heavy...
				// Then we need a right rotation.
				this.rightRotation(parent);
			}
			else { 
				// We need a double-right rotation.
				this.doubleRightRotation(parent);
			}
		}
		else {  // Root is right heavy
			if( this.signedImbalance(this.rootNode().rightNode()) <= 0 ) {  // If root.rightNode is right-heavy
				// We need a left rotation.
				this.leftRotation(parent);
			}
			else {
				// We need a double left rotation.
				this.doubleLeftRotation(parent);
			}
		}
	}


	/**	Form a string representation that includes level numbers. 
	Analysis: Time = O(n), where n = number of items in the (sub)tree  
	@param i the level for the root of this (sub)tree  
	 */
	protected String toStringByLevel(int i) 
	{
		StringBuffer blanks = new StringBuffer((i - 1) * 5);
		for (int j = 0; j < i - 1; j++)
			blanks.append("            ");

		String result = new String();
		if (!isEmpty() && (!rootLeftSubtree().isEmpty() || !rootRightSubtree().isEmpty()))
			result += rootRightSubtree().toStringByLevel(i+1);

		result += "\n" + blanks + i + "/";
		if( !isEmpty() ) result += this.rootNode().getLtHeight() + "/" + this.rootNode().getRtHeight();
		result += ": ";
		if (isEmpty())
			result += "-";
		else 
		{
			result += rootItem();
			if (!rootLeftSubtree().isEmpty() || !rootRightSubtree().isEmpty())
				result += rootLeftSubtree().toStringByLevel(i+1);
		}
		return result;
	}

	public String toString() {
		return this.toStringByLevel(1);
	}
	
	public AVLTree280<I> clone()
	{
		return (AVLTree280<I>) super.clone();
	}

	public static void main(String[] args) {
		AVLTree280<Integer> T = new AVLTree280<Integer>();

		/* This is a very feeble unit test.  I would expect something a little more
		 * robust from the students. I just didn't have time for a better one.  Sorry.  
		 */

		// Test left rotations
		T.insert(12);
		T.insert(14);
		T.insert(22);
		T.insert(35);
		T.insert(36);
		T.insert(43);
		T.insert(55);
		T.insert(63);
		T.insert(73);
		T.insert(99);
		T.insert(40);		
		System.out.println(T);

		// test right rotations
		T.clear();
		T.insert(99);
		T.insert(88);
		T.insert(77);
		T.insert(66);
		T.insert(55);
		T.insert(44);
		T.insert(33);
		T.insert(22);
		T.insert(11);
		T.insert(10);
		T.insert(9);		

		System.out.println(T);


		if( !T.has(9) ) System.out.println("Error: T reports that it does not contain 9 but it does.");
		if( !T.has(77) ) System.out.println("Error: T reports that it does not contain 77 but it does.");
		if( !T.has(44) ) System.out.println("Error: T reports that it does not contain 44 but it does.");
		if( T.has(15) ) System.out.println("Error: T reports that it has 15, but it doesn't.");
		
	}









}
