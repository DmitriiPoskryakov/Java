package roadgraph;

import geography.GeographicPoint;

public class Edge {
	private String roadName;
	private String roadType; 
	private double length;
	private GeographicPoint from;
	private GeographicPoint to;
	
	public Edge(GeographicPoint from, Vertex to, String roadName,String roadType, double length){
		
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = from.distance(to.getFrom()) ;
		this.from = from;
		this.to = to.getFrom();
		
	}

	public String getRoadName() {
		return roadName;
	}

	public double getLength() {
		return length;
	}

	
	public String getRoadType() {
		return roadType;
	}

	public GeographicPoint getFrom() {
		return from;
	}

	public GeographicPoint getTo() {
		return to;
	}

	
}
