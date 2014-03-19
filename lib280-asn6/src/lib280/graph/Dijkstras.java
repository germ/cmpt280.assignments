package lib280.graph;

import lib280.exception.InvalidArgument280Exception;
import lib280.exception.InvalidState280Exception;


public class Dijkstras {
	// TODO everything.
	
	/** place holder for infinity */
	 private static final Double INF = Double.MAX_VALUE;

	
	public Dijkstras(WeightedGraphAdjListRep280<Vertex280> G, int s){
		Double tentativeDistance[] = new Double[G.numVertices()];
		Boolean visited[] = new Boolean[G.numVertices()];
		Vertex280 predecessor[] = new Vertex280[G.numVertices()];
		G.goFirst();
		for (int i = 0; i<G.numVertices();i++){
			tentativeDistance[i] = INF;
			visited[i] = false;
			predecessor[i] = null;			
		}
		tentativeDistance[s] = 0.0;
		int cur = 0;
		int numVisited = 0;
		while (numVisited != G.numVertices()){
			cur = this.getSTD(tentativeDistance, visited);
			numVisited++;
			
			if (cur == -1){
				System.out.println("Unreachable");
			}
			else{
			
				visited[cur] = true;
				
				 for(G.eGoFirst(G.vertex(cur)); G.eItemExists(); G.eGoForth()){
					 if (visited[G.eItemAdjacentIndex()] == false && tentativeDistance[G.eItemAdjacentIndex()] >
					 									(tentativeDistance[cur] + G.getWeight(cur, G.eItemAdjacentIndex()))){
						tentativeDistance[G.eItemAdjacentIndex()] = tentativeDistance[cur] + G.getWeight(cur, G.eItemAdjacentIndex());
						
						//
						System.out.println("Weight: " + tentativeDistance[G.itemIndex()]);
						//
						
						predecessor[G.itemIndex()] = G.vertex(cur);			
					}
				}
			}
		}
		
		
		
		// tests
		for (int i = 0; i<tentativeDistance.length;i++){
			System.out.println(tentativeDistance[i]);
		}
		
		
	}		
	
	/** get the smallest tentative distance of unvisited
	 * @param arr an array of tentative distances 
	 * td the array of tentativeDistances
	 * visited the boolean array of whether something is visited or not
	 * @return the int value of the STD which is unvisited*/
	private int getSTD(Double[] td, Boolean[] visited){
		int val = -1;
		Double smallest = INF;
		for (int i = 0; i<td.length; i++){
			if (visited[i] == false){
				if (td[i] < smallest){
					val = i;
					smallest = td[i];
				}
			}
			
		}
		return val;
	}
	
	/**
	 * test function
	 */
	public static void main(String[] args) {
		WeightedGraphAdjListRep280<Vertex280> G = new WeightedGraphAdjListRep280<Vertex280>(10, false, "lib280.graph.Vertex280", "lib280.graph.WeightedEdge280");
		G.initGraphFromFile("src/lib280/graph/weightedtestgraph.gra");
		G.goFirst();
		System.out.println(G.toString());
		
		@SuppressWarnings("unused")
		Dijkstras d = new Dijkstras(G, 1);
		
		
		System.out.println(G.toString());
		System.out.println("dfggs");
	}
}
