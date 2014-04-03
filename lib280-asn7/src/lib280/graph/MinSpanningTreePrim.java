package lib280.graph;

import lib280.tree.ArrayedMinHeap280;

public class MinSpanningTreePrim  {
	
	/** A Large global */
	private final double INFINITY = 1000000;
	
	/** Min heap H */
	ArrayedMinHeap280<VertexProperties> H;
	
	/** The MinSpanningTreePrim tree itself */
	WeightedGraphAdjListRep280<Vertex280> tree;

	/** Constructs the minspanning tree
	 * @param file the name of the file to build
	 * @param size the size of the tree */
	MinSpanningTreePrim(String file, int size){
		if (size >50)
			throw new RuntimeException("Error: too many nodes");
		WeightedGraphAdjListRep280<Vertex280> initialTree = new WeightedGraphAdjListRep280<Vertex280>(size, false);
		this.H = new ArrayedMinHeap280<VertexProperties>(size);
		initialTree.initGraphFromFile(file);
		System.out.println("Input Graph:\n" +initialTree.toString()); // for test output
		this.tree = PrimSuggested(initialTree);
	}
	
	/** returns the minspanning tree
	 * @return this.tree, the MinSpanningTreePrim */
	WeightedGraphAdjListRep280<Vertex280> getMinSpanningTree(){
		return this.tree;
	}
	
	/** builds the minspanning tree
	 * @param G the weighted graph to find the minspanningtree
	 * @return MinG the newly created minspanning tree */
	WeightedGraphAdjListRep280<Vertex280> PrimSuggested(WeightedGraphAdjListRep280<Vertex280> G){
		VertexProperties[] vertexArray;
		vertexArray = new VertexProperties[G.capacity()+1+1];
		for (int i = 0; i<G.capacity()+1;i++){
			vertexArray[i] = new VertexProperties(); 
			vertexArray[i].vertex = i;
			vertexArray[i].heaped = true;
			vertexArray[i].minDistance = INFINITY;
			vertexArray[i].parent = -1;
		}
		vertexArray[1].minDistance = 0.0;
		
		for (int i = 1; i<G.capacity()+1;i++){
			this.H.insert(vertexArray[i]); 
		}
		
		WeightedGraphAdjListRep280<Vertex280> MinG = new WeightedGraphAdjListRep280<Vertex280>(G.capacity()+1, false);
		MinG.ensureVertices(G.capacity()+1);
		while (!H.isEmpty()){
			VertexProperties h = H.item();
			H.removeItem();
			h.heaped = false;
			// add edge (h.vertex, h.parent) to MinG
			if (h.parent  >= 1){
				MinG.addEdge(h.vertex, h.parent);
				MinG.setEdgeWeight(h.vertex, h.parent, h.minDistance);
			}
			for (G.eGoFirst(G.vertex(h.vertex)); G.eItemExists();G.eGoForth()){
				int v = G.eItem().secondItem().index();
				if (vertexArray[v].heaped == true &&  G.getEdgeWeight(G.vertex(h.vertex), G.vertex(v)) < vertexArray[v].minDistance){
					vertexArray[v].parent = h.vertex;
					vertexArray[v].minDistance = G.getEdgeWeight(G.vertex(h.vertex), G.vertex(v));
					H.siftUp(vertexArray[v]); 
				}
			}
		}
		return MinG;
	}
	
	
	/** a main function to test and display that this works. */
	public static void main(String args[]) {
		String filename;

		if( args.length == 0)
			filename = "src/lib280/graph/weightedtestgraph.gra";
		else 
			filename =args[0];
		MinSpanningTreePrim newTree = new MinSpanningTreePrim(filename, 9);
		System.out.println("Minimum spanning tree:\n" + newTree.getMinSpanningTree().toString());
	}
}
