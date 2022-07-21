package roadgraph;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import geography.GeographicPoint;


public class Vertex implements Comparable<Vertex> {
	
	private Map<Vertex, Edge> neighbors;	
	private GeographicPoint from;
	private double lenRoad;
	
	
	public Vertex () {
				
	}
	
	public Vertex (GeographicPoint from) {
		neighbors = new HashMap<Vertex,Edge>();
		this.from = from;
		lenRoad = 1.0/0.0;
		
	}
	
	public boolean addNeighbors(GeographicPoint from, Vertex to, String roadName,String roadType, double length) {
		Edge a = new Edge(from, to, roadName, roadType,length);
		
		neighbors.put(to, a);
		
		return true;
	}
	
	public Set<Vertex> getNeighbors(){
		
		return neighbors.keySet();
	}
   
	public Edge getRoad(Vertex to){
		
		return neighbors.get(to);
	}
    
    public Collection<Edge> getAllRoad(){
		
		return neighbors.values();
	}
    
    public double getLenRoad(){
		
		return lenRoad;
	}
    
    public void setLenRoad(double x){
		
		lenRoad = x;
	}

	public GeographicPoint getFrom() {
		return from;
	}
	@Override
	public int compareTo(Vertex v) {
		// TODO Auto-generated method stub
		int a = 0;
		if (this.getLenRoad() - v.getLenRoad()< 0) a = -1;
		else if (this.getLenRoad() - v.getLenRoad()> 0)a = 1;
		else a = 0;
		return a;
	}

	public String toString(){
		String s = this.getFrom().x +"; " + this.getFrom().y;
		return s;
	}
	
}
