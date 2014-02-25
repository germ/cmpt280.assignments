package lib280.tree;

import lib280.exception.ContainerEmpty280Exception;

public class AVLTree280<I extends Comparable<I>> {

	/**	Root node of the tree. */
	protected AVLNode280<I> rootNode;
	
	/**	Create an empty tree.*/
	public AVLTree280() {
		rootNode = null;
	}
	

	private I rootItem() {
		if (isEmpty()) 
			throw new ContainerEmpty280Exception("Cannot access the root of an empty tree.");
		
		return rootNode.item();
	}
	
	/**	Set root node to new node.
	Analysis: Time = O(1) 
	@param newNode node to become the new root node */
	protected void setRootNode(AVLNode280<I> newNode)
	{
		rootNode = newNode;
	}

	/**	Remove all items from the tree. */
	public void clear()
	{
		setRootNode(null);
	}
	
	/**	Right subtree of the root. 
	Analysis: Time = O(1) 
	@precond !isEmpty() 
	 */
	private AVLTree280<I> rootRightSubtree() {
		if (isEmpty())
			throw new ContainerEmpty280Exception("Cannot return a subtree of an empty tree.");
		
		AVLTree280<I> result = (AVLTree280<I>) this.clone();
		result.clear();
		result.setRootNode((AVLNode280<I>) rootNode.rightNode());
		return result;
	}

	/**	Left subtree of the root. 
	Analysis: Time = O(1) 
	@precond !isEmpty() 
  */
	private AVLTree280<I> rootLeftSubtree() {
		if (isEmpty())
			throw new ContainerEmpty280Exception("Cannot return a subtree of an empty tree.");
		
		AVLTree280<I> result = (AVLTree280<I>) this.clone();
		result.clear();
		result.setRootNode((AVLNode280<I>) rootNode.leftNode());
		return result;
	}

	/**	Is the tree empty?. 
	Analysis: Time = O(1)  */
	private boolean isEmpty() {
		return rootNode == null;
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
	
	/**	Create a new node that is appropriate to this tree.
	Analysis: Time = O(1) 
	@param item    The item to be placed in the new node */
	protected AVLNode280<I> createNewNode(I item, int height)
	{
		return new AVLNode280<I>(item, height);
	}

	/**	Form a string representation that includes level numbers. 
	Analysis: Time = O(n), where n = number of items in the (sub)tree  
	@param i the level for the root of this (sub)tree  
	 */
	protected String toStringByLevel(int i) 
	{
		StringBuffer blanks = new StringBuffer((i - 1) * 5);
		for (int j = 0; j < i - 1; j++)
			blanks.append("     ");
	  
		String result = new String();
		if (!isEmpty() && (!rootLeftSubtree().isEmpty() || !rootRightSubtree().isEmpty()))
			result += rootRightSubtree().toStringByLevel(i+1);
		
		result += "\n" + blanks + i + ": " ;
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
	
	public void insert(I data, AVLTree280<I> R){
		
		if (R.isEmpty()){
			R.setRootNode(R.createNewNode(data, 1));
		}
		
		if (data <= R.rootItem()){
			
		}
	}








	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
