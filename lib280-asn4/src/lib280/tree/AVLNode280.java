package lib280.tree;

public class AVLNode280<I extends Comparable<? super I>> extends BinaryNode280<I> {
	
	/** Height of the nodes */
	private int heightLeft = 0;
	private int heightRight = 0;
	private AVLNode280<I> parent;
	
	/** Constructs an AVLNode
	 * @param x the item inserted into node **/
	public AVLNode280(I x, AVLNode280<I> parent) {
		super(x);
		this.parent = parent;
	}
	
	/** sets left node height */
	public void setHeightLeft(int H){
		this.heightLeft = H;
	}
	
	/** sets left node height */
	public void setHeightRight(int H){
		this.heightRight = H;
	}
	
	/** get's the left node height 
	 * @return H the height of the left node**/
	public int getHeightLeft(){
		return this.heightLeft;
	}
	
	/** get's the right node height 
	 * @return H the height of the right node**/
	public int getHeightRight(){
		return this.heightRight;
	}
	
	/** returns the height of the left and right subtrees
	 * @return the heights of the left and right subtrees */
	public String getHeights(){
		return "(" + heightLeft + "," + heightRight + ")";
	}
	
	/** get's parent 
	 * @return the parent of the node*/
	public AVLNode280<I> getParent(){
		return this.parent;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
