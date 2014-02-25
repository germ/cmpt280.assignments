package lib280.tree;

public class AVLNode280<I extends Comparable<? super I>> extends BinaryNode280<I> {
	
	/** Height of the node */
	protected int height;
	
	/** Constructs an AVLNode
	 * @param x the item inserted into node
	 * @param height the current height of this node **/
	public AVLNode280(I x, int height) {
		super(x);
		this.height = height;
	}
	
	/** increase nodes height by 1 */
	public void increaseHeight(){
		this.height = this.height++;
	}
	
	/** decrease nodes height by 1 */
	public void decreaseHeight(){
		this.height = this.height--;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
