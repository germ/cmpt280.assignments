package lib280.tree;

public class KDTree280<I extends Comparable<? super I>> extends LinkedSimpleTree280<I> {
	
	/** The dimension of the tree */
	private int k;
	
	/** The KD Tree */
	KDTree280<I> kdTree;
	
	/** Root of kdTree */
	KDNode280<I> rootNode;
	
	/** Constructor for kdTree */
	public KDTree280(KDNode280<I>[] pointArray,int left,int right, int depth){
		this.rootNode = kdtree(pointArray, left, right, depth);
	}
	
	/** The constructor for the kdTree
	 * @param pointArray an array of KDNode280s
	 * @param left the left most offset of the array
	 * @param right the right most offset of the array
	 * @param depth the current depth in the partially built tree
	 * @param dimension the dimension of the tree
	 * @return node, the newly constructed node */
	public KDNode280<I> kdtree(KDNode280<I>[] pointArray,int left,int right, int depth){
		this.k = pointArray[0].item.dim();
		if ((right-left) <= -1){
			return null;
		}
		else{
			int d = depth % this.k;
			int medianOffset = (left+right)/2;
			
			jSmallest(pointArray, left, right, d, medianOffset);
			
			KDNode280<I> node = new KDNode280<I>(this.k);
			node.item = pointArray[medianOffset].item;
			node.leftChild = kdtree(pointArray, left, medianOffset-1, depth+1);
			node.rightChild = kdtree(pointArray, medianOffset+1, right, depth+1);
			return node;
		}
	}
	
	/** Finds the smallest item
	 * @param list, an array of comparable elements
	 * @param left offset of start of subarray for which we want median element
	 * @param right offset of end of subarray for which we want median element
	 * @param dimension the dimension of the node
	 * @param j we want to find the element that belongs at array index j */
	private void jSmallest(KDNode280<I>[] list, int left, int right, int depth, int j){
		int pivotIndex;
		int dimension;
		int k = list[0].item.dim();
		dimension = depth % k;
		if (right > left){
			pivotIndex = partition(list, left, right, dimension);
			if (j < pivotIndex)
				jSmallest(list, left, pivotIndex-1, depth, j);
			else if (j > pivotIndex)
				jSmallest(list, pivotIndex+1, right, depth, j);
		}
		
	}
	
	/** A subarray using its last element as a pivot
	 * @param list array of comparable elements
	 * @param left lower limit on subarray to be partitioned
	 * @param right upper limit on subarray to be partitioned
	 * @precond all elements in list are unique
	 * @postcond all elements smaller than the pivot appear in the left
	 * 			most part of the subarray, then the pivot element
	 * 			followed by the elements larger than the pivot. There
	 * 			is no guarantee about hte ordering of the elements 
	 * 			before and after the pivot 
	 * @return the offset at which hte pivot element ended up*/
	@SuppressWarnings({ "rawtypes"})
	public int partition(KDNode280[] list, int left, int right, int dimension) {
		KDNode280 pivot;
		KDNode280 temp;
		pivot = list[right];		
		int swapOffset = left;
		for (int i = left; i <= right-1; i++){
			if (list[i].item.compareByDim(dimension, pivot.item) < 0){
				temp = list[i];
				list[i] = list[swapOffset];
				list[swapOffset] = temp;
				swapOffset++;
			}
		}
		temp = list[right];
		list[right] = list[swapOffset];
		list[swapOffset] = temp;
		return swapOffset;
	}
	
	/** Search Range
	 * @param T subtree in which to search for elements between a and b inclusive
	 * @param a - lower bound of search range
	 * @param b - midd bound  of search range
	 * @param depth the depth of the tree*/
	public String searchRange(KDNode280<I> T,KDNode280<I> a,KDNode280<I> b, int depth){
		String result = "";
		if (T.leftChild == null || T.rightChild == null){
			if (T.leftChild==null && T.rightChild!= null)
				return result+=T.rightChild.item.toString()+ "\n" + T.item.toString()+ "\n";
			else if (T.leftChild != null && T.rightChild == null)
				return result+=T.leftChild.item.toString() + "\n" + T.item.toString()+ "\n";
			else
				return result+= T.item.toString()+ "\n";
		}
		int dimension = depth % k;
		
		//System.out.println("String so far: " + result);
		
		if (T.leftChild.item.compareByDim(dimension, a.item) <= 0){
			// items less than a, search right
			return searchRange(T.leftChild, a, b, depth+1);
		}
		else if (T.leftChild.item.compareByDim(dimension, b.item) >= 0){
			// items larger than b, search left
			return searchRange(T.rightChild, a, b, depth+1);
		}
		else{
			// could be both trees
			String L = this.searchRange(T.leftChild, a, b, depth +1);
			String R = this.searchRange(T.rightChild, a, b, depth+1);
			if (T.item.compareByDim(dimension, a.item) >= 0 && T.item.compareByDim(dimension, b.item) <= 0){
				return result+= L + R + T.item + "\n";
			}
			else
				return result+=L+R+ "\n";
		}
	}
	

	
	/** a method to test the function */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String args[]) {
			
		System.out.println("Input 2D points:");
		KDNode280[] arr = new KDNode280[8];
		
		Double pt02d[] = {5.0,2.0};
		Double pt12d[] = {9.0,10.0};
		Double pt22d[] = {11.0,1.0};
		Double pt32d[] = {4.0,3.0};
		Double pt42d[] = {2.0,19.0};
		Double pt52d[] = {3.0,7.0};
		Double pt62d[] = {1.0,5.0};
		Double pt72d[] = {0.0,18.0};
		for (int i = 0; i<8;i++)
			arr[i] = new KDNode280(2);
		arr[0].item.setPoint(pt02d);
		arr[1].item.setPoint(pt12d);
		arr[2].item.setPoint(pt22d);
		arr[3].item.setPoint(pt32d);
		arr[4].item.setPoint(pt42d);
		arr[5].item.setPoint(pt52d);
		arr[6].item.setPoint(pt62d);
		arr[7].item.setPoint(pt72d);
		for (int i = 0; i<arr.length;i++)
			System.out.println(arr[i].item.toString());

		KDTree280 newTree = new KDTree280(arr, 0,7,0);
		
		System.out.println(newTree.rootNode.toStringByLevel());
		
		System.out.println("\n\nInput 3D points:");
		KDNode280[] arr2 = new KDNode280[8];
		
		Double p0d[] = {5.0,2.0,6.0};
		Double p1d[] = {6.0,10.0,1.0};
		Double p2d[] = {11.0,1.0,2.0};
		Double p3d[] = {4.0,3.0,0.0};
		Double p4d[] = {2.0,12.0,5.0};
		Double p5d[] = {3.0,7.0,7.0};
		Double p6d[] = {1.0,5.0,8.0};
		Double p7d[] = {0.0,11.0,4.0};
		for (int i = 0; i<8;i++)
			arr2[i] = new KDNode280(3);
		arr2[0].item.setPoint(p0d);
		arr2[1].item.setPoint(p1d);
		arr2[2].item.setPoint(p2d);
		arr2[3].item.setPoint(p3d);
		arr2[4].item.setPoint(p4d);
		arr2[5].item.setPoint(p5d);
		arr2[6].item.setPoint(p6d);
		arr2[7].item.setPoint(p7d);
		
		for (int i = 0; i<arr2.length; i++)
			System.out.println(arr2[i].item.toString());
		KDTree280 newTree2 = new KDTree280(arr2, 0,7,0);
		System.out.println(newTree2.rootNode.toStringByLevel());
		
	
		
		Double a1[] = {1.0,5.0,8.0};
		Double b1[] = {1.0,6.0,9.0};
		
		KDNode280 a = new KDNode280(3);
		a.item.setPoint(a1);
		
		KDNode280 b = new KDNode280(3);
		b.item.setPoint(b1);
		System.out.println("Looking for points between {0.0,4.0,7.0} and {1.0,6.0,9.0}\nFound: ");
		System.out.print(newTree2.searchRange(newTree2.rootNode, a, b, 0));
		
		System.out.println("\n\n");
		
		Double a2[] = {0.0,0.0,0.0};
		Double b2[] = {3.0,14.0,6.0};
		a.item.setPoint(a2);
		b.item.setPoint(b2);
		System.out.println("Looking for points between {0.0,0.0,0.0} and {3.0,14.0,6.0}\nFound: ");
		System.out.print(newTree2.searchRange(newTree2.rootNode, a, b, 0));
		
		System.out.println("\n\n");
		
		Double a3[] = {-5.0,-5.0,-5.0};
		Double b3[] = {20.0,20.0,20.0};
		a.item.setPoint(a3);
		b.item.setPoint(b3);
		System.out.println("Looking for points between {-5.0,-5.0,-5.0} and {20.0,20.0,20.0}\nFound: ");
		System.out.print(newTree2.searchRange(newTree2.rootNode, a, b, 0));
	}
	
}
