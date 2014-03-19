package lib280.graph;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import lib280.base.CursorPosition280;

/** WeightedGraph class*/
public class WeightedGraphAdjListRep280<V> extends GraphAdjListRep280<Vertex280, WeightedEdge280<V>>{

	/** directed boolean value */
	boolean d;
	
	/** constructs a weighted graph*/
	public WeightedGraphAdjListRep280(int cap, boolean d, String vertexTypeName, String edgeTypeName) {
		super(cap, d, vertexTypeName, edgeTypeName);
		this.d = d;
	}




	/**
	 * Replaces the current graph with a graph read from a data file.
	 * 
	 * File format is a sequence of integers. The first integer is the total
	 * number of nodes which will be numbered between 1 and n.
	 * 
	 * Remaining integers are treated as ordered pairs of (source, destination)
	 * indicies defining graph edges.
	 * 
	 * @param fileName
	 *            Name of the file from which to read the graph.
	 * @throws RuntimeException
	 *             if the file format is incorrect, or an edge appears more than
	 *             once in the input.
	 */
	@SuppressWarnings({ "resource" })
	public void initGraphFromFile(String fileName) {

		// Erase the current graph.
		this.clear();

		// Try to open the data file.
		Scanner inFile = null;
		try {
			inFile = new Scanner(new File(fileName));
		} catch (IOException e) {
			throw new RuntimeException("Error opening the file with name "
					+ fileName);
		}

		if (!inFile.hasNextInt())
			throw new RuntimeException(
					"Did not find number of nodes, illegal file format or unexpected end of file.");

		// Get the number of nodes in the graph.
		int numV = inFile.nextInt();

		// Add more capacity if needed.
		if (numV > this.capacity()) {
			this.vertexArray = (Vertex280[]) new Vertex280[numV];
			this.createEdgeDataStructure();
		}

		// Make sure all needed vertices exist. (this also updates
		// this.numVertices because it adds each vertex with this.addVertex())
		this.ensureVertices(numV);

		// Read the adjacency list until there are no more tokens available.
		while (inFile.hasNext()) {

			// Get source vertex. If there is a next token and it's not an
			// integer, issue error.
			if (!inFile.hasNextInt())
				throw new RuntimeException(
						"Illegal file format or unexpected end of file.");
			int srcIdx = inFile.nextInt();

			// Get destination vertex. If there is a next token and it's not an
			// integer, issue error.
			if (!inFile.hasNextInt())
				throw new RuntimeException(
						"Illegal file format or unexpected end of file.");
			int dstIdx = inFile.nextInt();
			
			// Get weight.
			if (!inFile.hasNextDouble())
				throw new RuntimeException(
						"Illegal file format or unexpected enf of file.");
			Double wghtIdx = inFile.nextDouble();

			if (this.isAdjacent(srcIdx, dstIdx))
				throw new RuntimeException("Edge (" + srcIdx + ", " + dstIdx
						+ ") appears multiple times in the data file.");
			
			if (wghtIdx < 0)
				throw new RuntimeException(
						"Illegal weight, can not be negative.");

			this.addEdge(srcIdx, dstIdx);
			this.setWeight(srcIdx, dstIdx, wghtIdx);
			if (this.d == false){ // if undirected do backwards
				this.setWeight(dstIdx, srcIdx, wghtIdx);
			}

		}
	}
	
	/** set the weight via integer vertex indices 
	 * @param v1, first vertex
	 * @param v2, 2nd vertex
	 * @param wghtIdx weight */
	public void setWeight(int v1, int v2, double wghtIdx){
		this.eSearch(this.vertex(v1), this.vertex(v2));
		this.eItem().setWeight(wghtIdx);
	}
	
	/** get the weight via integer vertex indices
	 * @param v1, int of first vertex
	 * @param v2, int of 2nd vertex
	 * @return weight of the edge*/
	public Double getWeight(int v1, int v2){
		this.eSearch(this.vertex(v1), this.vertex(v2));
		return this.eItem().getWeight();
	}
	
	/** set the weight via objects 
	 * @param v1 the first vertex object
	 * @param v2 the 2nd vertex object
	 * @param wghtIdx the weight of the edge */
	public void setWeight(Vertex280 v1, Vertex280 v2, double wghtIdx){
		this.eSearch(v1, v2);
		this.eItem().setWeight(wghtIdx);
	}
	
	/**  get the weight via objects
	 * @param v1 the  first vertex object
	 * @param v2 the 2nd vertex object
	 * @return the weight of the edge*/
	public Double getWeight(Vertex280 v1, Vertex280 v2){
		this.eSearch(v1, v2);
		return this.eItem().getWeight();
	}
	

	/**
	 * String representation of the graph for output.
	 * 
	 * @timing = O(max(numVertices, numEdges))
	 */
	public String toString() {
		CursorPosition280 position = this.currentPosition();
		StringBuffer result = new StringBuffer();
		result.append(this.numVertices);
		if (directed)
			result.append("   directed");
		else
			result.append("   undirected");
		
		this.goFirst();
		if( !itemExists() ) {
			// Graph contains no nodes.
			result.append("\nThis graph contains zero nodes.");
			return new String(result);
		}
		
		while (!this.after()) {
			result.append("\n" + item() + " : ");
			this.eGoFirst(this.item());
			while (!this.eAfter()) {
				result.append(" " + this.eItem().toStringGraphIO(item())+ ":" + this.eItem().getWeight());
				this.eGoForth();
			}
			// result.append(" 0");
			this.goForth();
		}
		this.goPosition(position);
		return new String(result);
	}

	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WeightedGraphAdjListRep280<Vertex280> G = new WeightedGraphAdjListRep280<Vertex280>(10, false, "lib280.graph.Vertex280", "lib280.graph.WeightedEdge280");

		G.initGraphFromFile("src/lib280/graph/weightedtestgraph.gra");
		System.out.println(G.toString());
		
		System.out.println("\n\nNow printing a directed graph.\n\n");
		
		WeightedGraphAdjListRep280<Vertex280> H = new WeightedGraphAdjListRep280<Vertex280>(10, true, "lib280.graph.Vertex280", "lib280.graph.WeightedEdge280");
		H.initGraphFromFile("src/lib280/graph/weightedtestgraph.gra");
		System.out.println(H.toString());
	}

}
