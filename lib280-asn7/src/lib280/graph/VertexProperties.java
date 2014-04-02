package lib280.graph;

public class VertexProperties implements Comparable<VertexProperties> {
	
	
	
	public VertexProperties(){
		
	}

	/** the index of the vertex that we are storing information about */
	protected int vertex;
	
	/** the index of the parent of this vertex in the spanning tree */
	protected int parent;
	
	/** the cost of the cheapest edge between this vertex and
	 *  a vertex that is already in the spanning tree */
	protected float minDistance;
	
	/** a boolean variable heaped to denote whether
	 *  this VertexProperties object is still in
	 *  the heap used by Prim’s algorithm. */
	protected boolean heaped;

	@Override
	public int compareTo(VertexProperties o) {
		return Float.compare(minDistance, o.minDistance);
	}
}
