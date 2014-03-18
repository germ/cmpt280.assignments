package lib280.graph;

/** weighted edge class */
public class WeightedEdge280<V> extends Edge280<Vertex280>{

	/** weight of edge */
	Double weight;
	
	/** constructs a weighted edge
	 * @param v1 the first vertex
	 * @param v2 the second vertex
	 * @param weight the weight of the edge */
	public WeightedEdge280(Vertex280 v1, Vertex280 v2) {
		super(v1, v2);
	}
	
	/** sets the weight
	 * @param weight the weight to set */
	public void setWeight(Double weight){
		this.weight = weight;
	}
	
	/** returns the weight
	 * @return weight the weight of the edge */
	public Double getWeight(){
		return this.weight;
	}



}
