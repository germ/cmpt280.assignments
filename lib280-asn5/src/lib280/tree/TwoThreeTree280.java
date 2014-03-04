package lib280.tree;

import lib280.base.Pair280;

/**  2-3 Tree */
public class TwoThreeTree280<K extends Comparable<? super K>,I extends Comparable<? super I>> {
	
	/** Root node */
	TwoThreeNode280 rootNode;
	
	
	/** Constructs an empty TwoThreeTree */
	public TwoThreeTree280(){
		this.rootNode = null;
	}
	
	/** determines if the tree is empty or not
	 * @return true if empty false otherwise*/
	public boolean isEmpty() {
		if (rootNode == null)
			return true;
		else
			return false;
	}
	
	/** determines the height of the tree */
	public int getHeight(){
		int height = 0;
		// TODO, tarverse down left while in internal nodes blahblah
		
		
		return height;
	}
	
	/** Searches for an item in node n
	 * @precond not empty tree
	 * @param k, key to element we're looking for
	 * @param n, the root node of tree we're searching in
	 * @return either return the item, or return null if not found */ 
	// TODO probably needs some tweaking
	@SuppressWarnings("unchecked")
	private I search (K k, @SuppressWarnings("rawtypes") TwoThreeNode280 n){
		if (n.isInternal()){
			if (k.compareTo((K) n.getKey1()) < 0)
				return search(k, n.getLeftSubtree());
			else if (n.getMiddleSubtree() != null || k.compareTo((K) n.getKey2()) < 0)
				return search(k, n.getMiddleSubtree());
			else
				return search(k, n.getRightSubtree());
		}
		else{
			if (k == n.getKey1())
				return (I) n.getData().secondItem();
			else
				return null;
		}
	}
	
	/** Creates a leaf node
	 * @param k the key of the new leaf
	 * @param i the item of the new leaf
	 * @return returns the newly created leaf */
	public LeafTwoThreeNode280<K, I> createLeaf(K k, I i){
		Pair280<K, I> data = new Pair280<K,I>(k,i);
		LeafTwoThreeNode280<K, I> leaf = new LeafTwoThreeNode280<K, I>(data);
		return leaf;
	}
	
	/** Creates a new InternalTwoThreeNode280 out of 2 or more 2-3 nodes
	 * @precond 1st and 3rd parameter must not be null
	 * @param one is a 2-3Node
	 * @param two is either a 2-3 node or null
	 * @param three is a 2-3Node
	 * @return newInternal, the new internal node which either points to 2 or 3 nodes */
	public InternalTwoThreeNode280<K,I> createInternal(TwoThreeNode280<K,I> one,
													   TwoThreeNode280<K,I> two, 
													   TwoThreeNode280<K,I> three){
		TwoThreeNode280<K,I> smallest;
		TwoThreeNode280<K,I> largest;
		TwoThreeNode280<K,I> middle = null;
		InternalTwoThreeNode280<K,I> newInternal;
		if (one.getKey1().compareTo(three.getKey1()) < 0){
			smallest = one;
			largest = three;
		}
		else{
			smallest = three;
			largest = one;
		}
		if (two != null){
			if (two.getKey1().compareTo(smallest.getKey1()) < 0){
				middle = smallest;
				smallest = two;
			}
			else if (largest.getKey1().compareTo(two.getKey1()) < 0){
				middle = largest;
				largest = two;
			}
			else{
				middle = two;
			}
			
		}
		if (middle == null){
			newInternal = new InternalTwoThreeNode280<K,I>(smallest, largest.getKey1(), largest, null, null);
		}
		else{
			newInternal = new InternalTwoThreeNode280<K,I>(smallest, middle.getKey1(), middle, largest.getKey1(), largest);
		}
		return newInternal;
		//TwoThreeNode280<K,I> leftSubtree, K key1, TwoThreeNode280<K,I>middleSubtree,  K key2,  TwoThreeNode280<K,I> rightSubtree
	}
	
	

	/** Inserts an item i with key k into the tree
	 * @precond k is not already a key in the tree
	 * @param k, the key
	 * @param i, the item
	 */
	@SuppressWarnings("unchecked")
	public void insert(K k, I i){
		if (this.isEmpty()) // empty tree, insert 1st leaf
			this.rootNode = createLeaf(k, i);
		else if (!this.rootNode.isInternal()){ //only 1 leaf in tree, insert another.
			this.rootNode = createInternal(this.rootNode, null, createLeaf(k,i));			
		}
		else
			auxinsert(this.rootNode, k, i);
	}
	
	/** Auxiliary insert, recursive algorithm called by previous insert.
	 * @param p is the root of tree in which to insert
	 * @param k is the key of element i
	 * @parma i is the element to be inserted */
	private void auxinsert(TwoThreeNode280 p, K k, I i) {
		// TODO Auto-generated method stub
		
		
		
	}

	// TODO do this,blah blah blah
	/** Deletes an item from the tree that has key k
	 * @precond item with key k exists in the tree
	 * @param k is the key of the item to delete
	 * @postcond k has been deleted */
	public void delete(K k){

	}
	
	/** determines if there exists an item in the tree with key k 
	 * @param k the key of hte item we are looking for
	 * @return true if item exists, false otherwise*/
	public boolean has(K k){
		if (search(k, this.rootNode) == null)
			return false;
		else
			return true;
	}
	
	public I obtain(K k){
		return search(k, this.rootNode);
	}
	
    /** 
	 * String representation of the tree, level by level.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toStringByLevel() {
		if(this.isEmpty()) return "Empty Tree";
		else return toStringByLevel(this.rootNode, 1); 
	}


    /** 
	 * Helper method for printing of the tree.
	 * @param i Current level of the tree.
	 * @param root Root of the current subtree.
	 * @return A string representation of the tree.
	 */
	protected String toStringByLevel(TwoThreeNode280<K,I> root, int i) 
	{
		// This is the toStringByLevel() method we saw in the lecture notes.
		
		StringBuffer blanks = new StringBuffer((i - 1) * 5);
		for (int j = 0; j < i - 1; j++)
			blanks.append("     ");
	  
		String result = new String();
		
		if(!root.isInternal())
			return result += "\n" + blanks + i + ":" + root.getKey1();
		
		if (root.isRightChild()) {
			result += toStringByLevel(root.getRightSubtree(), i+1);
			result += "\n" + blanks + i + ":K2:" + root.getKey2() ;
		}
		
		result += toStringByLevel(root.getMiddleSubtree(), i+1);
	
		result += "\n" + blanks + i + ":K1:" + root.getKey1() ;

		result += toStringByLevel(root.getLeftSubtree(), i+1);
		
		return result;
	}

	
	
	
	
	public static void main(String[] args) {
		TwoThreeTree280<Integer,String> T = new TwoThreeTree280<Integer,String>();
		
		// First Test
		System.out.println("================================================");
		System.out.println("First test, insert into empty 2-3 Tree");
		int firstTest = 0;
		T.insert(5, "hello");
		System.out.println(T.toStringByLevel());
		
		// Has/Search tests
		if (T.has(5))
			firstTest++;
		if (!T.has(4))
			firstTest++;
		// Obtain tests
		if (T.obtain(5) == "hello")
			firstTest++;
		if (T.obtain(4) == null)
			firstTest++;
		// Pass or Fail
		if (firstTest == 4)
			System.out.println("First test successful, all 4 tests passed.");
		else
			System.out.println("First test unsueccesful, "+ firstTest + "/4 tests passed.");
		
		// Second Test
		System.out.println("================================================");
		System.out.println("Second test, insert item into 2-3 tree that has only 1 leaf.");
		int secondTest = 0;
		T.insert(10, "bonjour");
		System.out.println(T.toStringByLevel());
		// Has/Search tests
		if (T.has(10))
			secondTest++;
		if (!T.has(9))
			secondTest++;
		// Obtain tests
		if (T.obtain(10) == "bonjour")
			secondTest++;
		if (T.obtain(9) == null)
			secondTest++;
		if (secondTest == 4)
			System.out.println("Second test successful, all 4 tests passed.");
		else
			System.out.println("Second test unsueccesful, "+ secondTest + "/4 tests passed.");
		
		// Third Test
		System.out.println("================================================");
	}
	
}

