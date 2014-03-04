package lib280.tree;

import lib280.base.Pair280;
import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.InvalidState280Exception;

/**  2-3 Tree */
public class TwoThreeTree280<K extends Comparable<? super K>,I extends Comparable<? super I>> {
	
	/** Root node */
	private TwoThreeNode280<K,I> rootNode;
	
	
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
			else if (n.getRightSubtree() == null || k.compareTo((K) n.getKey2()) < 0 )
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

	/** Sorts up to 4 nodes, returning an array with them in order
	 * @precond one and two must not be null
	 * @param one first node
	 * @param two 2nd node
	 * @param three 3rd node
	 * @param four 4th node
	 * @return sorted an array containing the nodes in order */
	@SuppressWarnings("unchecked")
	public TwoThreeNode280<K,I>[] sortNodes(TwoThreeNode280<K,I> one,
											TwoThreeNode280<K,I> two, 
											TwoThreeNode280<K,I> three,
											TwoThreeNode280<K,I> four){
		
		if (one == null || two == null)
			throw new InvalidState280Exception("Error, param one or two is null in sortNodes.");
		TwoThreeNode280<K, I>[] sorted = new TwoThreeNode280[4];
		for (int i = 0; i<4;i++)
			sorted[i] = null;
		if (three == null && four == null){ // only nodes in 1&2
			if (one.getKey1().compareTo(two.getKey1()) < 0){
				sorted[0] = one;
				sorted[1] = two;
			}
			else{
				sorted[0] = two;
				sorted[1] = one;
			}
		}
		else if (four == null){ // nodes in 1,2 and 3
			if (one.getKey1().compareTo(two.getKey1()) < 0){
				sorted[0] = one;
				sorted[1] = two;
			}
			else{
				sorted[0] = two;
				sorted[1] = one;
			}
			if (three.getKey1().compareTo(sorted[0].getKey1()) < 0){ //3 < 1
				sorted[2] = sorted[1];
				sorted[1] = sorted[0];
				sorted[0] = three;
			}
			else if (three.getKey1().compareTo(sorted[1].getKey1()) < 0){ // 1 < 3 < 2
				sorted[2] = sorted[1];
				sorted[1] = three;
			}
			else{ // 3 placed at end
				sorted[2] = three;
			}
			return sorted;
		}
		else{ // nodes in 1,2,3, and 4
			if (one.getKey1().compareTo(two.getKey1()) < 0){
				sorted[0] = one;
				sorted[1] = two;
			}
			else{
				sorted[0] = two;
				sorted[1] = one;
			}
			if (three.getKey1().compareTo(sorted[0].getKey1()) < 0){ //3 < 1
				sorted[2] = sorted[1];
				sorted[1] = sorted[0];
				sorted[0] = three;
			}
			else if (three.getKey1().compareTo(sorted[1].getKey1()) < 0){ // 1 < 3 < 2
				sorted[2] = sorted[1];
				sorted[1] = three;
			}
			else{ // 3 placed at end
				sorted[2] = three;
			}
			if (four.getKey1().compareTo(sorted[0].getKey1()) < 0){ // 4 < 1
				sorted[3] = sorted[2];
				sorted[2] = sorted[1];
				sorted[1] = sorted[0];
				sorted[0] = four;				
			}
			else if (four.getKey1().compareTo(sorted[1].getKey1()) < 0){ // 1 < 4 < 2
				sorted[3] = sorted[2];
				sorted[2] = sorted[1];
				sorted[1] = four;
			}
			else if (four.getKey1().compareTo(sorted[2].getKey1()) < 0){ // 2< 4 < 3
				sorted[3] = sorted[2];
				sorted[2] = four;				
			}
			else{
				sorted[3] = four;
			}
		}
		return sorted;
	}
	
	

	/** Creates a new InternalTwoThreeNode280 out of 2 or more 2-3 nodes
	 * @precond 1st and 2nd parameters must not be null
	 * @param one is a 2-3Node
	 * @param two is a 2-3 node
	 * @param three is a 2-3Node or null
	 * @return newInternal, the new internal node which either points to 2 or 3 nodes */
	public InternalTwoThreeNode280<K,I> createInternal(TwoThreeNode280<K,I> one,
													   TwoThreeNode280<K,I> two, 
													   TwoThreeNode280<K,I> three){
		InternalTwoThreeNode280<K,I> newInternal;
		if (three == null){
			newInternal = new InternalTwoThreeNode280<K,I>(one, two.getKey1(), two, null, null);
		}
		else{
			newInternal = new InternalTwoThreeNode280<K,I>(one, two.getKey1(), two, three.getKey1(), three);
		}
		return newInternal;
		//TwoThreeNode280<K,I> leftSubtree, K key1, TwoThreeNode280<K,I>middleSubtree,  K key2,  TwoThreeNode280<K,I> rightSubtree
	}
	
	

	/** Inserts an item i with key k into the tree
	 * @precond k is not already a key in the tree
	 * @param k, the key
	 * @param i, the item
	 */
	public void insert(K k, I i){
		if (this.isEmpty()) // empty tree, insert 1st leaf
			this.rootNode = createLeaf(k, i);
		else if (!this.rootNode.isInternal()){ //only 1 leaf in tree, insert another.
			TwoThreeNode280<K, I>[] sorted = sortNodes(this.rootNode,createLeaf(k,i),null,null);
			this.rootNode = createInternal(sorted[0], sorted[1], null);			
		}
		else{
			Pair280<K, InternalTwoThreeNode280<K,I>> ksq = auxinsert(this.rootNode, k, i);
			if (ksq != null){
				//System.out.println("XXXXXXXXXXXXXXXXX\n\n" + toStringByLevel() + "\n\nXXXXXXXXXXXX");
				this.rootNode = createInternal(this.rootNode, ksq.secondItem(), null);
				this.rootNode.setKey1(ksq.firstItem());
				// if root has 3 children, then just slot them into two groups
				// of two
			}
		}
	}
	
	/** Auxiliary insert, recursive algorithm called by previous insert.
	 * @param p is the root of tree in which to insert
	 * @param k is the key of element i
	 * @parma i is the element to be inserted */
	private Pair280<K, InternalTwoThreeNode280<K,I>> auxinsert(TwoThreeNode280<K, I> p, K k, I i) {
		// TODO Auto-generated method stub
		// base case, children of p are leaf nodes
		if (!p.getLeftSubtree().isInternal()){
			LeafTwoThreeNode280<K,I> c = createLeaf(k,i);
			InternalTwoThreeNode280<K,I> q = null;
			K ks = null;
			if (p.getRightSubtree()==null){ // has exactly 2 children, 3rd child would be null
				TwoThreeNode280<K, I>[] sorted = sortNodes(c,p.getLeftSubtree(),p.getMiddleSubtree(),null);
				p.setLeftSubtree(sorted[0]);
				p.setMiddleSubtree(sorted[1]);
				p.setRightSubtree(sorted[2]);
				p.setKey1(p.getMiddleSubtree().getKey1()); // fix keys
				p.setKey2(p.getRightSubtree().getKey1());
				return null;
				
			}
			else{ // p already has 3 children
				TwoThreeNode280<K, I>[] sorted = sortNodes(c,p.getLeftSubtree(),p.getMiddleSubtree(),p.getRightSubtree());
				p.setLeftSubtree(sorted[0]); //smallest
				p.setMiddleSubtree(sorted[1]); //2nd smallest
				p.setKey2(null);
				p.setRightSubtree(null);
				q = createInternal(sorted[2],sorted[3],null); // 2 largest nodes
				p.setKey1(p.getMiddleSubtree().getKey1()); //set keys to middle children
				q.setKey1(q.getMiddleSubtree().getKey1());
				ks = sorted[2].getKey1();
				Pair280<K, InternalTwoThreeNode280<K,I>> ksq = new Pair280<K, InternalTwoThreeNode280<K,I>>(ks,q);
				return (ksq);

				
			}
		}
		else{ // recursive case
			TwoThreeNode280<K, I> Rs;
			if (k.compareTo(p.getKey1()) < 0)
				Rs = p.getLeftSubtree();
			else if (p.getRightSubtree() == null || k.compareTo(p.getKey2()) < 0)
				Rs = p.getMiddleSubtree();
			else
				Rs = p.getRightSubtree();
			//(n, ks) = insert(Rs, i, k);
			Pair280<K, InternalTwoThreeNode280<K,I>> ksn = auxinsert(Rs, k, i);
			
			if (ksn != null){
				if (p.getRightSubtree() == null){ // p has exactly 2 children
					TwoThreeNode280<K, I>[] sorted = sortNodes(ksn.secondItem(), p.getLeftSubtree(),p.getMiddleSubtree(),null);
					p.setLeftSubtree(sorted[0]);
					p.setMiddleSubtree(sorted[1]);
					p.setRightSubtree(sorted[2]);
					p.setKey1(p.getMiddleSubtree().getLeftSubtree().getKey1());
					p.setKey2(ksn.firstItem());
					return null;
				}
				else{
					/*TwoThreeNode280<K, I>[] sorted = sortNodes(ksn.secondItem(),p.getLeftSubtree(),p.getMiddleSubtree(),p.getRightSubtree());
					p.setLeftSubtree(sorted[0]); //smallest
					p.setMiddleSubtree(sorted[1]); //2nd smallest
					p.setKey2(null);
					p.setRightSubtree(null);
					InternalTwoThreeNode280<K,I> q = createInternal(sorted[2],sorted[3],null); // 2 largest nodes
					p.setKey1(p.getMiddleSubtree().getKey1()); //set keys to middle children
					q.setKey1(q.getMiddleSubtree().getKey1());*/
					Pair280<K, InternalTwoThreeNode280<K,I>> qks = split(p,ksn.secondItem(),Rs,ksn.firstItem());// DO SPLIT
					return qks;
				}
			}
			
			
		}
		return null;
	}
	
	private Pair280<K, InternalTwoThreeNode280<K,I>> split(TwoThreeNode280<K, I> p,
														   InternalTwoThreeNode280<K,I> n,
														   TwoThreeNode280<K,I> Rs,
														   K ks){
		TwoThreeNode280<K, I>[] sorted = sortNodes(n,p.getLeftSubtree(),p.getMiddleSubtree(),p.getRightSubtree());
		if (Rs == p.getLeftSubtree()){
			
			p.setLeftSubtree(sorted[0]);
			p.setMiddleSubtree(n);
			InternalTwoThreeNode280<K,I> q = createInternal(sorted[1],sorted[2],null);
			q.setKey1(p.getKey2());
			p.setKey2(null);
			Pair280<K, InternalTwoThreeNode280<K,I>> rpo = new Pair280<K, InternalTwoThreeNode280<K,I>>(p.getKey1(),q);
			p.setKey1(ks);
			return rpo;
		}
		else if (Rs == p.getMiddleSubtree()){
			p.setLeftSubtree(sorted[0]);
			p.setMiddleSubtree(sorted[1]);
			InternalTwoThreeNode280<K,I> q = createInternal(n,sorted[2],null);
			q.setKey1(p.getKey2());
			p.setKey2(null);
			Pair280<K, InternalTwoThreeNode280<K,I>> rks = new Pair280<K, InternalTwoThreeNode280<K,I>>(ks,q);
			return rks;
		}
		else{
			p.setLeftSubtree(sorted[0]);
			p.setMiddleSubtree(sorted[1]);
			InternalTwoThreeNode280<K,I> q = createInternal(sorted[2],n,null);
			q.setKey1(ks);
			Pair280<K, InternalTwoThreeNode280<K,I>> rp1 = new Pair280<K, InternalTwoThreeNode280<K,I>>(p.getKey2(),q);
			p.setKey2(null);
			return rp1;
		}
	}

	/** Deletes an item from the tree that has key k
	 * @precond item with key k exists in the tree
	 * @param k is the key of the item to delete
	 * @postcond k has been deleted */
	public void delete(K k){
		if (this.isEmpty())
			throw new ContainerEmpty280Exception("Error, tree empty.");
		if (!this.rootNode.isInternal()){
			this.rootNode = null;
		}
		else
			auxdelete(this.rootNode, k);
	}
	
	/** Auxiliary delete, recursive algorithm called by previous delete
	 * @param p is the root of tree in which to delete
	 * @param k is the key of element i */
	private void auxdelete(TwoThreeNode280<K, I> p, K k) {
		// TODO Auto-generated method stub
		
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
		
		try{
			T.delete(5);
		}
		catch(ContainerEmpty280Exception e){ System.out.println("Success "
				+ "Caught error for deleting item from empty tree.");};
		
		// First Test
		System.out.println("================================================");
		System.out.println("First test, insert [3] into empty 2-3 Tree");
		int firstTest = 0;
		T.insert(3, "three");
		System.out.println(T.toStringByLevel());
		T.delete(3);
		System.out.println("Deleted item with key 3");
		System.out.println("T printing by level to verify:");
		System.out.println(T.toStringByLevel());
		T.insert(3,  "three");
		System.out.println("T restored to state before deletion for next tests.");
		
		// Has/Search tests
		if (T.has(3))
			firstTest++;
		if (!T.has(4))
			firstTest++;
		// Obtain tests
		if (T.obtain(3) == "three")
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
		System.out.println("Second test, insert [13] item into 2-3 tree that has only 1 leaf.");
		int secondTest = 0;
		T.insert(13, "thirteen");
		System.out.println(T.toStringByLevel());
		// Has/Search tests
		if (T.has(13))
			secondTest++;
		if (!T.has(9))
			secondTest++;
		// Obtain tests
		if (T.obtain(13) == "thirteen")
			secondTest++;
		if (T.obtain(9) == null)
			secondTest++;
		if (secondTest == 4)
			System.out.println("Second test successful, all 4 tests passed.");
		else
			System.out.println("Second test unsueccesful, "+ secondTest + "/4 tests passed.");
		
		// Third Test
		System.out.println("================================================");
		System.out.println("Third test, insert [8] item into node that has 2 children.");
		int thirdTest = 0;
		T.insert(8, "eight");
		System.out.println(T.toStringByLevel());
		// Has/Search tests
				if (T.has(8))
					thirdTest++;
				if (!T.has(0))
					thirdTest++;
				// Obtain tests
				if (T.obtain(8) == "eight")
					thirdTest++;
				if (T.obtain(0) == null)
					thirdTest++;
				if (thirdTest == 4)
					System.out.println("Third test successful, all 4 tests passed.");
				else
					System.out.println("Third test unsueccesful, "+ thirdTest + "/4 tests passed.");
		
		// Fourth Test
		System.out.println("================================================");
		System.out.println("Fourth test, insert [11] item into node that has 3 children.");
		int fourthTest = 0;
		T.insert(11, "eleven");
		System.out.println(T.toStringByLevel());
		// Has/Search tests
				if (T.has(11))
					fourthTest++;
				if (!T.has(-10))
					fourthTest++;
				// Obtain tests
				if (T.obtain(11) == "eleven")
					fourthTest++;
				if (T.obtain(-10) == null)
					fourthTest++;
				if (fourthTest == 4)
					System.out.println("Fourth test successful, all 4 tests passed.");
				else
					System.out.println("Fourth test unsueccesful, "+ fourthTest + "/4 tests passed.");
				
				

		// Fifth Test 
		System.out.println("================================================");
		System.out.println("Fifth test, insert [45] item into internal node that has 2 children.");
		int fifthTest = 0;
		T.insert(45, "fortyfive");
		System.out.println(T.toStringByLevel());
		// Has/Search tests
				if (T.has(45))
					fifthTest++;
				if (!T.has(-100))
					fifthTest++;
				// Obtain tests
				if (T.obtain(45) == "fortyfive")
					fifthTest++;
				if (T.obtain(-100) == null)
					fifthTest++;
				if (fifthTest == 4)
					System.out.println("Fifth test successful, all 4 tests passed.");
				else
					System.out.println("Fifth test unsueccesful, "+ fifthTest + "/4 tests passed.");
	
		// Sixth Test 
		System.out.println("================================================");
		System.out.println("Sixth test, insert [22] item into internal node that has 3 children.");
		int sixthTest = 0;
		T.insert(22, "twentytwo");
		System.out.println(T.toStringByLevel());
		// Has/Search tests
				if (T.has(22))
					sixthTest++;
				if (!T.has(-100)) //working
					sixthTest++;
				// Obtain tests
				if (T.obtain(22) == "twentytwo")
					sixthTest++;
				if (T.obtain(-100) == null) //working
					sixthTest++;
				if (sixthTest == 4)
					System.out.println("Sixth test successful, all 4 tests passed.");
				else
					System.out.println("Sixth test unsueccesful, "+ sixthTest + "/4 tests passed.");
				
		// Seventh Test 
		System.out.println("================================================");
		System.out.println("Seventh test, insert [1] item into internal node that has 1 children.");
		System.out.println("This doesn't really test anything new, but sets up for the 8th test");
		int seventhTest = 0;
		T.insert(1, "one");
		System.out.println(T.toStringByLevel());
		// Has/Search tests
				if (T.has(1))
					seventhTest++;
				if (!T.has(-100)) //working
					seventhTest++;
				// Obtain tests
				if (T.obtain(1) == "one")
					seventhTest++;
				if (T.obtain(-100) == null) //working
					seventhTest++;
				if (seventhTest == 4)
					System.out.println("Seventh test successful, all 4 tests passed.");
				else
					System.out.println("Seventh test unsueccesful, "+ seventhTest + "/4 tests passed.");
				
		// Eigth Test 
		System.out.println("================================================");
		System.out.println("Eigth test, insert [1] item into internal node that has 1 children.");
		System.out.println("This doesn't really test anything new, but sets up for the 8th test");
		int EigthTest = 0;
		T.insert(10, "one"); // TODO This isn't working properly, the tree is gibbled.
		System.out.println(T.toStringByLevel());
		// Has/Search tests
				if (T.has(10))
					EigthTest++;
				if (!T.has(-100)) //working
					EigthTest++;
				// Obtain tests
				if (T.obtain(10) == "one")
					EigthTest++;
				if (T.obtain(-100) == null) //working
					EigthTest++;
				if (EigthTest == 4)
					System.out.println("Eigth test successful, all 4 tests passed.");
				else
					System.out.println("Eigth test unsueccesful, "+ EigthTest + "/4 tests passed.");
	}
	
}

