package lib280.graph;

import lib280.tree.ArrayedMinHeap280;

public class MinSpanningTreePrim  {
	
	/** A Large global */
	private final int INFINITY = 1000000;
	
	/** Min heap H */
	ArrayedMinHeap280<VertexProperties> H;
	
	/** The MinSpanningTreePrim tree itself */
	WeightedGraphAdjListRep280<Vertex280> tree;

	
	MinSpanningTreePrim(String file, int size){
		if (size >50)
			throw new RuntimeException("Error: too many nodes");
		WeightedGraphAdjListRep280<Vertex280> initialTree = new WeightedGraphAdjListRep280<Vertex280>(size, false);
		this.H = new ArrayedMinHeap280<VertexProperties>(size);
		initialTree.initGraphFromFile(file);
		this.tree = PrimSuggested(initialTree);
	}
	
	/** returns the minspanning tree
	 * @return this.tree, the MinSpanningTreePrim */
	WeightedGraphAdjListRep280<Vertex280> getMinSpanningTree(){
		return this.tree;
	}
	
	WeightedGraphAdjListRep280<Vertex280> PrimSuggested(WeightedGraphAdjListRep280<Vertex280> G){
		VertexProperties[] vertexArray;
		vertexArray = new VertexProperties[G.capacity()+1];
		for (int i = 0; i<G.capacity();i++){
			vertexArray[i] = new VertexProperties(); 
			vertexArray[i].vertex = i;
			vertexArray[i].heaped = true;
			vertexArray[i].minDistance = INFINITY;
			vertexArray[i].parent = -1;
		}
		vertexArray[0].minDistance = 0;
		
		for (int i = 1; i<G.capacity();i++){
			this.H.insert(vertexArray[i]); 
		}
		
		WeightedGraphAdjListRep280<Vertex280> MinG = new WeightedGraphAdjListRep280<Vertex280>(G.capacity(), false);
		while (!H.isEmpty()){
			VertexProperties h = H.item();
			H.removeItem();
			h.heaped = false;
			// add edge (h.vertex, h.parent) to MinG
			if (h.parent  >= 1){
				MinG.addEdge(G.vertex(h.vertex), G.vertex(h.parent));
			}
			for (G.eGoFirst(G.vertex(h.vertex)); G.eItemExists();G.eGoForth()){
				int v = G.eItem().secondItem().index();
				if (vertexArray[v].heaped == true &&  G.getEdgeWeight(G.vertex(h.vertex), G.vertex(v)) < vertexArray[v].minDistance){
					vertexArray[v].parent = h.vertex;
					vertexArray[v].minDistance = (int) G.getEdgeWeight(G.vertex(h.vertex), G.vertex(v));
					H.siftUp(vertexArray[v]); 
				}
			}
		}
		//System.out.println(MinG.toString());
		return MinG;
	}
	
	
	
	public static void main(String args[]) {
		String filename;

		if( args.length == 0)
			filename = "src/lib280/graph/weightedtestgraph.gra";
		else 
			filename =args[0];
		MinSpanningTreePrim newTree = new MinSpanningTreePrim(filename, 10);	
		System.out.print(newTree.getMinSpanningTree());
	}
}
