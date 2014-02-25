package lib280.tree;


import lib280.exception.ContainerEmpty280Exception;

public class AVLTree280<I extends Comparable<? super I>> implements Cloneable{

	/**	Root node of the tree. */
	protected AVLNode280<I> rootNode;
	
	/**	Create an empty tree.*/
	public AVLTree280() {
		rootNode = null;
	}
	
	/**	Create a tree from a root and two subtrees. <
	Analysis: Time = O(1) 
	@param lt tree to initialize as the left subtree.  If null, the left subtree is empty.
	@param r item to initialize as the root item
	@param rt tree to initialize as the right subtree.  If null, the right subtree is empty. *
	public AVLTree280(AVLTree280<I> lt, I r, AVLTree280<I> rt) 
	{
		rootNode = createNewNode(r);
		setRootLeftSubtree(lt);
		setRootRightSubtree(rt);
	}*/
	
	
	/**	Create a new node that is appropriate to this tree.
	Analysis: Time = O(1) 
	@param item    The item to be placed in the new node */
	protected AVLNode280<I> createNewNode(I item, AVLNode280<I> parent)
	{
		return new AVLNode280<I>(item, parent);
	}
	

	/**	Is the tree empty?. 
	Analysis: Time = O(1)  */
	private boolean isEmpty() {
		return rootNode == null;
	}
	
	/**	Return the root node. 
	Analysis: Time = O(1) */
	protected AVLNode280<I> rootNode()
	{
		return rootNode;
	}
	
	/**	Set root node to new node.
	Analysis: Time = O(1) 
	@param newNode node to become the new root node */
	protected void setRootNode(AVLNode280<I> newNode)
	{
		rootNode = newNode;
	}
	
	/**	Contents of the root item. 
	Analysis: Time = O(1) 
	@precond !isEmpty()
	 */
	private I rootItem() {
		if (isEmpty()) 
			throw new ContainerEmpty280Exception("Cannot access the root of an empty tree.");
		
		return rootNode.item();
	}
	
	/** returns the root node
	 * @return 
	 * @return root node*/
	private AVLNode280<I> getRootNode(){
		return this.rootNode;
	}
	
	
	/**	Set contents of the root to x. 
	Analysis: Time = O(1) 
	@precond !isEmpty() 
	@param x item to become the new root item 
	  */
	public void setRootItem(I x) throws ContainerEmpty280Exception
	{
		if (isEmpty())
			throw new ContainerEmpty280Exception("Cannot set the root of an empty tree.");
		
		rootNode.setItem(x);
	}
	
	/**	Left subtree of the root. 
	Analysis: Time = O(1) 
	@precond !isEmpty() 
	 */
	private AVLTree280<I> rootLeftSubtree() {
		if (isEmpty())
			throw new ContainerEmpty280Exception("Cannot return a subtree of an empty tree.");
		
		AVLTree280<I> result = this.clone();
		result.clear();
		result.setRootNode((AVLNode280<I>) rootNode.leftNode());
		return result;
	}
	
	/**	Right subtree of the root. 
	Analysis: Time = O(1) 
	@precond !isEmpty() 
	 */
	private AVLTree280<I> rootRightSubtree() {
		if (isEmpty())
			throw new ContainerEmpty280Exception("Cannot return a subtree of an empty tree.");
		
		AVLTree280<I> result = this.clone();
		result.clear();
		result.setRootNode((AVLNode280<I>) rootNode.rightNode());
		return result;
	}
	
	/**	Set the left subtree to t (set isEmpty if t == null). 
	Analysis: Time = O(1) 
	@precond !isEmpty() 
	@param t tree to become the rootLeftSubtree() 
	 */
	public void setRootLeftSubtree(AVLTree280<I> t) throws ContainerEmpty280Exception
	{
		if (isEmpty())
			throw new ContainerEmpty280Exception("Cannot set subtree of an empty tree.");
		
		if (t != null)
			rootNode.setLeftNode(t.rootNode);
		else
			rootNode.setLeftNode(null);
	}

	/**	Set the right subtree to t (set isEmpty if t == null). 
		Analysis: Time = O(1) 
		@precond !isEmpty() 
		@param t tree to become the rootRightSubtree() 
	  */
	public void setRootRightSubtree(AVLTree280<I> t) throws ContainerEmpty280Exception
	{
		if (isEmpty())
			throw new ContainerEmpty280Exception("Cannot set subtree of an empty tree.");
		
		if (t != null)
			rootNode.setRightNode(t.rootNode);
		else
			rootNode.setRightNode(null);
	}
	

	/**	Remove all items from the tree. */
	public void clear()
	{
		setRootNode(null);
	}
	

	/**	Form a string representation that includes level numbers. 
	Analysis: Time = O(n), where n = number of items in the (sub)tree  
	@param i the level for the root of this (sub)tree  
	 */
	protected String toStringByLevel(int i) 
	{
		StringBuffer blanks = new StringBuffer((i - 1) * 5);
		for (int j = 0; j < i - 1; j++)
			blanks.append("           ");
	  
		String result = new String();
		if (!isEmpty() && (!rootLeftSubtree().isEmpty() || !rootRightSubtree().isEmpty()))
			result += rootRightSubtree().toStringByLevel(i+1);
		
		result += "\n" + blanks + i + ": ";
		//if (rootNode() != null)
		//	result += rootNode().getHeights(); Testing for heights
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
	
	/**	String representation of the tree, level by level. <br>
	Analysis: Time = O(n), where n = number of items in the tree 
	 */
	public String toStringByLevel()
	{
		return toStringByLevel(1);
	}
	
	
	/**	String containing an inorder list of the items of the tree. 
	Analysis: Time = O(n), where n = number of items in the tree 
	 */
	public String toString()
	{
		if (isEmpty())
			return "";
		else
			return rootNode().toString();
	}

	
	
	/**	A shallow clone of this tree. */
	@SuppressWarnings("unchecked")
	public AVLTree280<I> clone()
	{
		try
		{
			return (AVLTree280<I>) super.clone();
		} catch(CloneNotSupportedException e)
		{
			/*	Should not occur: SimpleList280 extends Cloneable and Object implements clone(). */
			e.printStackTrace();
			return null;
		}
	}

	/** returns the height of the node R
	 * @param R an AVLNode280
	 * @return the height of the node R */
	public int height(AVLNode280<I> R){
		if (R == null)
			return 0;
		else{
			int leftHeight = height((AVLNode280<I>) R.leftNode());
			int rightHeight = height((AVLNode280<I>) R.rightNode());
			return 1 + Math.max(leftHeight, rightHeight);
		}
	}

	/** Recursively inserts data into the tree rooted at R
	 * @param data the data we are inserting
	 * @param R the root of tree we are inserting data into */
	public void insert(I data, AVLNode280<I> R){
		// if empty 
		if (R == null){
			this.setRootNode(createNewNode(data, null));
			R=this.rootNode;
		}
		// if non empty 
		else if (data.compareTo(R.item()) <=0){
			if (R.leftNode() == null){
				R.setLeftNode(createNewNode(data, R));
			}
			else
				insert(data,(AVLNode280<I>) R.leftNode());
			R.setHeightLeft(height(R)-1);
		}
		else{
			if (R.rightNode() == null){
				R.setRightNode(createNewNode(data, R));
			}
			else
				insert(data,(AVLNode280<I>) R.rightNode());
			R.setHeightRight(height(R)-1);
		}
		restoreAVLProperty(rootNode());
	}

	/** restores the AVLProperty to a node R
	 * @Precond R is a AVLNode280 that may not have the AVL Property */
	private void restoreAVLProperty(AVLNode280<I> R) {
		int imbalanceR = signed_imbalance(R);
		if (Math.abs(imbalanceR) <= 1)
			return;
		
		if (imbalanceR == 2){
			if (signed_imbalance((AVLNode280<I>) R.leftNode()) >= 0)
				RightRotate(R);
			else{
				LeftRotate((AVLNode280<I>) R.leftNode());
				RightRotate(R);
			}
		}
		else{
			if (signed_imbalance((AVLNode280<I>) R.rightNode())<=0)
				LeftRotate(R);
			else{
				RightRotate((AVLNode280<I>) R.rightNode());
				LeftRotate(R);
			}
		}
		
	}
	/** intiates a LeftRotation for the tree */
	private void LeftRotate(AVLNode280<I> R) {
		boolean rootChange = false;
		if (R == this.getRootNode())
			rootChange = true;

		AVLNode280<I> temp = (AVLNode280<I>)R.rightNode();
		R.setRightNode(temp.leftNode()); 
		temp.setLeftNode(R);
		if (rootChange)
			this.setRootNode(temp); // need else parent = temp
		else
			R.getParent().setLeftNode(temp);		
	}
	
	/** intiates a RightRotation for the tree */
	private void RightRotate(AVLNode280<I> R) {
		boolean rootChange = false;
		if (R == this.getRootNode())
			rootChange = true;
		
		AVLNode280<I> temp = (AVLNode280<I>)R.leftNode();
		//if (!temp.rightNode().equals(null));
		R.setLeftNode(temp.rightNode()); 
		temp.setRightNode(R);
		if (rootChange)
			this.setRootNode(temp);
		else
			R.getParent().setRightNode(temp);
	}

	/** Determines if there is an imbalance in the tree
	 * @return returns the difference of the left and right subtrees */
	private int signed_imbalance(AVLNode280<I> n){
		return n.getHeightLeft()-n.getHeightRight();
	}

	/**
	 * Demonstrates working rotations for all 4 types 
	 * (Left, Right, Double Left and Double Right)
	 */
	public static void main(String[] args) {
		AVLTree280<Integer> v = new AVLTree280<Integer>();
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("Left Rotation\nInitial:");
		v.insert(50, v.rootNode);
		v.insert(25, v.rootNode);
		v.insert(75, v.rootNode);
		v.insert(70, v.rootNode);
		v.insert(80, v.rootNode);

		System.out.println(v.toStringByLevel());
		System.out.println("=================================");
		System.out.println("Left Rotation\nFinal(added 90):");
		v.insert(90, v.rootNode);
		System.out.println(v.toStringByLevel());
		
		
		AVLTree280<Integer> t = new AVLTree280<Integer>();
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("Right Rotation\nInitial:");
		t.insert(50, t.rootNode);
		t.insert(25, t.rootNode);
		t.insert(75, t.rootNode);
		t.insert(20, t.rootNode);
		t.insert(30, t.rootNode);
		System.out.println(t.toStringByLevel());
		System.out.println("=================================");
		System.out.println("Right Rotation\nFinal(added 15):");
		t.insert(15, t.rootNode);
		System.out.println(t.toStringByLevel());
		
		
		AVLTree280<Integer> u = new AVLTree280<Integer>();
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("Double Right Rotation(LR)\nInitial:");
		u.insert(50, u.rootNode);
		u.insert(25, u.rootNode);
		u.insert(75, u.rootNode);
		u.insert(20, u.rootNode);
		u.insert(30, u.rootNode);
		System.out.println(u.toStringByLevel());
		System.out.println("=================================");
		System.out.println("Double Right Rotation\nFinal(expanded 30, added 29 and 31):");
		u.insert(29, u.rootNode);
		u.insert(31, u.rootNode);
		System.out.println(u.toStringByLevel());
		
		
		AVLTree280<Integer> x = new AVLTree280<Integer>();
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("Double Left Rotation(RL)\nInitial:");
		x.insert(50, x.rootNode);
		x.insert(25, x.rootNode);
		x.insert(75, x.rootNode);
		x.insert(70, x.rootNode);
		x.insert(80, x.rootNode);
		System.out.println(x.toStringByLevel());
		System.out.println("=================================");
		System.out.println("Double Left Rotation\nFinal(expanded 70, added 69 and 71):");
		x.insert(69, x.rootNode);
		x.insert(71, x.rootNode);
		System.out.println(x.toStringByLevel());
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	}
}
