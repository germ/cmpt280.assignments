package lib280.tree;
import lib280.tree.LinkedSimpleTree280;

/**
 * 
 * @author eramian
 *
 * @param <I>
 */
public class RootComparableLinkedSimpleTree280<I extends Comparable<? super I>> extends LinkedSimpleTree280<I> implements Comparable<RootComparableLinkedSimpleTree280<I>> {

	RootComparableLinkedSimpleTree280() {
		super();
	}
	
	RootComparableLinkedSimpleTree280(RootComparableLinkedSimpleTree280<I> leftSubTree, I root, RootComparableLinkedSimpleTree280<I> rightSubTree) {
		super(leftSubTree, root, rightSubTree);
	}
	

	@Override
	public int compareTo(RootComparableLinkedSimpleTree280<I> o) {
		return this.rootNode.compareTo(o.rootNode());
	}

}
