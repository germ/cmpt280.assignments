package lib280.tree;

import lib280.base.Pair280;
import lib280.exception.InvalidState280Exception;


public class LeafTwoThreeNode280<K, I> extends TwoThreeNode280<K,I>{
	/**
	 * The data stored in the leaf node.
	 */
	Pair280<K, I> data;

	
	/**
	 * Create a new leaf node containing a key-value pair for a 2-3 tree.
	 * @param data The key-value pair to store in this leaf node.
	 */
	public LeafTwoThreeNode280(Pair280<K,I> data) {
		this.data = data;
	}
	
	
	@Override
	public boolean isInternal() {
		return false;
	}

	/**
	 * Obtain the data in this node.
	 * @return The key-value pair for this node.
	 */
	public Pair280<K, I> getData() {
		return data;
	}

	/** 
	 * Change the data in this node.
	 * @param data The new key-value pair for this node.
	 */
	public void setData(Pair280<K, I> data) {
		this.data = data;
	}


	@Override
	public K getKey1() {
		return data.firstItem();
	}


	@Override
	public K getKey2() {
		throw new InvalidState280Exception("This method cannot be called on a leaf node.");
	}


	@Override
	public TwoThreeNode280<K,I> getLeftSubtree() {
		throw new InvalidState280Exception("This method cannot be called on a leaf node.");	
	}

	@Override
	public TwoThreeNode280<K,I> getMiddleSubtree() {
		throw new InvalidState280Exception("This method cannot be called on a leaf node.");	
}

	@Override
	public TwoThreeNode280<K,I> getRightSubtree() {
		throw new InvalidState280Exception("This method cannot be called on a leaf node.");	 
	}


	@Override
	public void setLeftSubtree(TwoThreeNode280<K,I> leftSubtree) {
		throw new InvalidState280Exception("This method cannot be called on a leaf node.");	 
		
	}

	@Override
	public void setMiddleSubtree(TwoThreeNode280<K,I> middleSubtree) {
		throw new InvalidState280Exception("This method cannot be called on a leaf node.");	 
		
	}

	@Override
	public void setRightSubtree(TwoThreeNode280<K,I> rightSubtree) {
		throw new InvalidState280Exception("This method cannot be called on a leaf node.");	 		
	}


	@Override
	public boolean isRightChild() {
		return false;
	}


	@Override
	public void setKey1(K key1) {
		throw new InvalidState280Exception("This method cannot be called on a leaf node.");	 				
	}


	@Override
	public void setKey2(K key2) {
		throw new InvalidState280Exception("This method cannot be called on a leaf node.");	 		
	}
		
}
