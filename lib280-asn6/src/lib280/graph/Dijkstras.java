package lib280.graph;

import java.util.Scanner;



public class Dijkstras {
	// TODO everything.
	
	/** place holder for infinity */
	 private static final Double INF = Double.MAX_VALUE;

	/** prints out the shortest path in graph g from s
	 * @param G is a weighted graph with non neg weights (checked in other class)
	 * @param s is hte start vertex
	 * @postcondition: tentativeDistance contains the length of
	 * 					the shortest path from s to some vertex
	 * 					predecessor contains the nodes appearing before
	 * 					some node v on the shortest path from s */
	public Dijkstras(WeightedGraphAdjListRep280<Vertex280> G, int s){
		Double tentativeDistance[] = new Double[G.capacity()];
		Boolean visited[] = new Boolean[G.capacity()];
		Vertex280 predecessor[] = new Vertex280[G.capacity()];
		G.goFirst();
		for (int i = 0; i<G.capacity();i++){
			tentativeDistance[i] = INF;
			visited[i] = false;
			predecessor[i] = null;			
		}
		tentativeDistance[s] = 0.0;
		int cur = 0;
		int numVisited = 0;
		while (numVisited != G.capacity()){
			cur = this.getSTD(tentativeDistance, visited);
			numVisited++;
			if (cur == -1){
				System.out.println("");
			}
			else{
				visited[cur] = true;
				 for(G.eGoFirst(G.vertex(cur)); G.eItemExists(); G.eGoForth()){
					 if (visited[G.eItemAdjacentIndex()] == false && tentativeDistance[G.eItemAdjacentIndex()] >
					 									(tentativeDistance[cur] + G.getWeight(cur, G.eItemAdjacentIndex()))){
						tentativeDistance[G.eItemAdjacentIndex()] = tentativeDistance[cur] + G.getWeight(cur, G.eItemAdjacentIndex());
						predecessor[G.eItemAdjacentIndex()] = G.vertex(cur);			
					}
				}
			}
		}
		// Prints out the function
		for (int i = 1; i<tentativeDistance.length;i++){
			System.out.println("The length of the shortest path from vertex " + s + " "
					+ "to vertex " + i + " is:" + tentativeDistance[i]);
			if (tentativeDistance[i] == 0)
				System.out.println("Not reachable.");
			else{
				System.out.print("The path to " + i + " is: " );

				// THIS BUILDS THE STRING OF THE PATH
				String path="";
				int j = i;
				while (predecessor[j].index() != s){
					path+= predecessor[j] + " ,";
					j = predecessor[j].index();
				}
				path+=predecessor[j];
				String reversepath = new StringBuilder(path).reverse().toString();
				reversepath+= ", " + i;
				System.out.print(reversepath);
				System.out.println();
			}
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
		for (int i = 1; i<td.length; i++){
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
		//System.out.println(G.toString());
		
		/** 
		 * There is a check in WeightedGraphAdjListRep280 that checks for negative weights
		 * and throws an exception if any are found.*/
		
		//Dijkstras d = new Dijkstras(G, 1);
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the number of the start vertex :");
		int n = in.nextInt();
		@SuppressWarnings("unused")
		Dijkstras a;
		while (n > 0 && n < G.capacity()){
			a = new Dijkstras(G, n);
			System.out.println("Enter the number of the start vertex :");
			n = in.nextInt();
		}
		System.out.println(n + " is not a valid number. Goodbye.");
		
		
		//System.out.println(G.toString());
	}
}
