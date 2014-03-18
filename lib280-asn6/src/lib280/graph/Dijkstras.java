package lib280.graph;

import lib280.tree.ArrayedMinHeap280;

public class Dijkstras {
	// TODO everything.
	
	public Dijkstras(WeightedGraphAdjListRep280<Vertex280> G, Vertex280 s){
		Vertex280[] V = new Vertex280[G.numVertices()];
		Double tentativeDistance[] = new Double[G.numVertices()];
		Boolean visited[] = new Boolean[G.numVertices()];
		Vertex280 predecessor[] = new Vertex280[G.numVertices()];
		G.goFirst();
		for (int i = 0; i<G.numVertices();i++){
			V[i] = G.item();
			tentativeDistance[i] = 0.0;
			visited[i] = false;
			predecessor[i] = null;
			G.goForth();
			 
			//tests
			System.out.println(V[i].index);
		}
		
		int numVisited = 0;
		Vertex280 cur;
		for (int i = G.numVertices(); i>=0;i--){
			if (visited[i] == false)
				cur = V[i];
		}		
	}
	
	
	/**
	 * test function
	 */
	public static void main(String[] args) {
		WeightedGraphAdjListRep280<Vertex280> G = new WeightedGraphAdjListRep280<Vertex280>(10, false, "lib280.graph.Vertex280", "lib280.graph.WeightedEdge280");
		G.initGraphFromFile("src/lib280/graph/weightedtestgraph.gra");
		G.goFirst();
		
		Dijkstras d = new Dijkstras(G, G.item());
		
		
		System.out.println(G.toString());
		System.out.println("dfggs");
	}
}
