package lib280.tree;

import lib280.base.NDPoint280;

public class KDNode280<I extends Comparable<? super I>> {

	
	/** Contents of the node. */
	protected NDPoint280 item;
	
	/** pointer to left child */
	protected KDNode280<I> leftChild = null;
	
	/** pointer to right child */
	protected KDNode280<I> rightChild = null;
	
	/**	Is the tree empty?. 
	Analysis: Time = O(1)  */
	public boolean isEmpty()
	{
		if (item == null)
			return true;
		else
			return false;
	}
	
	/**	Form a string representation that includes level numbers. 
	Analysis: Time = O(n), where n = number of items in the (sub)node 
	@param i the level for the root of this (sub)node  
	 */
	protected String toStringByLevel(int i) 
	{
		StringBuffer blanks = new StringBuffer((i - 1) * 5);
		for (int j = 0; j < i - 1; j++)
			blanks.append("         ");
	  
		String result = new String();
		if (!isEmpty() && (leftChild != null || rightChild != null))
			result += rightChild.toStringByLevel(i+1);
		
		
		result += "\n" + blanks + i + ": " ;
		if (isEmpty())
			result += "-";
		else 
		{
			result += item;
			if (leftChild != null || rightChild != null){
				if (leftChild != null)
					result += leftChild.toStringByLevel(i+1);
			}
		}
		return result;
	}
	
	
	/**	String representation of the tree, level by level. <br>
		Analysis: Time = O(n), where n = number of items in the node 
	  */
	public String toStringByLevel()
	{
		return toStringByLevel(1);
	}
		
	
	/** constructs the node
	 * @param dimension the dimension of the node*/
	public KDNode280(int dimension){
		NDPoint280 p = new NDPoint280(dimension);
		this.item = p;
	}

}
