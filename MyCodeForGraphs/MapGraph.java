/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph   {
	//TODO: Add your member variables here in WEEK 3
	private int numVertices;
	private int numEdges;	
	private Map<GeographicPoint,Vertex> vertices;
	public double POSITIVE_INFINITY = 1.0/0.0;
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3			
		vertices = new HashMap<GeographicPoint,Vertex>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		
		return vertices.keySet(); //vertices;
	}
	
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		return numEdges;
	}
	
	
	/*public boolean getEdge(GeographicPoint from, GeographicPoint to)
	{
		if(!vertices.get(from).getNeighbors().contains(to)) {					
			System.out.println("not edge");
			return false;			
		}
		
		else {
			System.out.println(from + "->" + to + " Length: " + vertices.get(from).getRoad(to).getLength());
			return true;
		}
	}*/
	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		Vertex a = new Vertex(location);		
		vertices.put(location,  a);
		numVertices++;
		
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 3
		if (!vertices.containsKey(from)|| from == null|| to == null || length < 0)throw new IllegalArgumentException();						 
		vertices.get(from).addNeighbors(from, vertices.get(to), roadName,roadType,length);
		numEdges++;
		
	}
	
	/**
	 * Generate string representation of adjacency list
	 * @return the String
	 */
	private String adjacencyString() {
		String s = "Adjacency list";
		s += " (size " + getNumVertices() + "+" + getNumEdges() + " integers):";

		for (GeographicPoint v : vertices.keySet()) {
			s += "\n\t"+v+":    ";
			for (Vertex w : vertices.get(v).getNeighbors()) {
				s += w+"; ";
			}
		}
		return s;
	}
	
	/** Return a String representation of the graph
	 * @return A string representation of the graph
	 */
	public String toString() {
		String s = "\nGraph with " + numVertices + " vertices and " + numEdges + " edges.\n";		
		if (numVertices <= 20) s += adjacencyString();
		return s;
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		double goalX = goal.x;
		double goalY = goal.y;

		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<GeographicPoint>();
		}
				
		HashSet<Vertex> visited = new HashSet<Vertex>();
		Queue<Vertex> toExplore = new LinkedList<Vertex>();
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		boolean found = false;
		
		toExplore.add(vertices.get(start));		
		
		while (!toExplore.isEmpty()) {
			Vertex curr = toExplore.remove();			
			if (goalX == curr.getFrom().x && goalY == curr.getFrom().y) {								
				found = true;				
				break;
			}
			 									
			Set <Vertex> neighbors = curr.getNeighbors();				
			for (Vertex next: neighbors) {																													
				if (!visited.contains(next)) {
					visited.add(next);
					parentMap.put(next.getFrom(),curr.getFrom());						
					toExplore.add(next);
					nodeSearched.accept(next.getFrom());
				}					
			}
		}
		
		

		if (!found) {			
			return null;
		}
		// reconstruct the path
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint curr = goal;
		while (curr.x != start.x || curr.y != start.y) {
			path.addFirst(curr);
			curr = parentMap.get(curr);
		}
		path.addFirst(start);
		return path;						
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		double goalX = goal.x;
		double goalY = goal.y;		
				
		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<GeographicPoint>();
		}
       
		for(GeographicPoint v: vertices.keySet()) vertices.get(v).setLenRoad(POSITIVE_INFINITY);
		
		HashSet<Vertex> visited = new HashSet<Vertex>();
		Queue<Vertex> toExplore = new PriorityQueue<Vertex>();
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		boolean found = false;
		vertices.get(start).setLenRoad(0);
		toExplore.add(vertices.get(start));		
		
		while (!toExplore.isEmpty()) {
			Vertex curr = toExplore.remove();			
			if (goalX == curr.getFrom().x && goalY == curr.getFrom().y) {								
				found = true;				
				break;
			}
			 
			if (!visited.contains(curr)) 
			{				
				nodeSearched.accept(curr.getFrom());
				visited.add(curr);				
				
				Set <Vertex> neighbors = curr.getNeighbors();
				
				for (Vertex next: neighbors) {																													
					double currentLenRoad = curr.getLenRoad() + curr.getRoad(next).getLength();					
					if (currentLenRoad < next.getLenRoad())parentMap.put(next.getFrom(),curr.getFrom());
					next.setLenRoad(currentLenRoad);
					toExplore.add(next);																			
				}								
			}
		}
				
		//System.out.println("dijkstra: " + visited.size());
		
		if (!found) {			
			return null;
		}
		
		// reconstruct the path
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint curr = goal;
				
		
		while (curr.x != start.x || curr.y != start.y) {
			
			path.addFirst(curr);
			curr = parentMap.get(curr);
		}
		
		path.addFirst(start);
		return path;		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		
	}

	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		double goalX = goal.x;
		double goalY = goal.y;
		double shortDist = start.distance(goal);
				
		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<GeographicPoint>();
		}
        
		for(GeographicPoint v: vertices.keySet()) vertices.get(v).setLenRoad(POSITIVE_INFINITY);
		
        HashSet<Vertex> visited = new HashSet<Vertex>();
		Queue<Vertex> toExplore = new PriorityQueue<Vertex>();
		HashMap<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		boolean found = false;
		vertices.get(start).setLenRoad(0);
		toExplore.add(vertices.get(start));		
		
		while (!toExplore.isEmpty()) {
			Vertex curr = toExplore.remove();			
			if (goalX == curr.getFrom().x && goalY == curr.getFrom().y) {								
				found = true;				
				break;
			}
			 
			if (!visited.contains(curr)) 
			{				
				nodeSearched.accept(curr.getFrom());
				visited.add(curr);				
				
				Set <Vertex> neighbors = curr.getNeighbors();
				shortDist = curr.getFrom().distance(goal);
				for (Vertex next: neighbors) {																													
					double currentLenRoad = curr.getLenRoad() + curr.getRoad(next).getLength();											
					if (currentLenRoad < next.getLenRoad())parentMap.put(next.getFrom(),curr.getFrom());
					next.setLenRoad(currentLenRoad);
					
					if(next.getFrom().distance(goal) < shortDist*1.005) {												
						toExplore.add(next);}														
					
				}								
				
			}
		}
		
		
		//System.out.println("aStarSearch: " + visited.size());
		
		if (!found) {			
			return null;
		}
		
		// reconstruct the path
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint curr = goal;
					
		while (curr.x != start.x || curr.y != start.y) {
			
			path.addFirst(curr);
			curr = parentMap.get(curr);
		}
		
		path.addFirst(start);
		return path;	
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		
	}

	
	
	public static void main(String[] args)
	{
		/*System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		System.out.println(firstMap.getVertices());
		System.out.println(firstMap.getNumVertices());
		System.out.println(firstMap.getNumEdges());
		System.out.println(firstMap);
		System.out.println(firstMap.getVertices());
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		//List<GeographicPoint> testroute3 = simpleTestMap.bfs(testStart,testEnd);
		System.out.println("aStarSearch: " + testroute2);
		//System.out.println(testroute2.size());
		//System.out.println(testroute3);
		//System.out.println(testroute3.size());
		System.out.println("dijkstra: " + testroute);
		//System.out.println(testroute.size());
		System.out.println();
		
		/*System.out.println("Test getLength:");
		testStart = new GeographicPoint(7.0, 3.0);
		testEnd = new GeographicPoint(8.0, -1.0);
		simpleTestMap.getEdge(testStart, testEnd);*/
		
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);				
		//testroute3 = testMap.bfs(testStart,testEnd);						
		System.out.println("aStarSearch: " + testroute2);
		System.out.println("dijkstra: " + testroute);
		//System.out.println(testroute2.size());
		System.out.println();
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		//testroute3 = testMap.bfs(testStart,testEnd);
		//System.out.println(testroute3.size());
		System.out.println("aStarSearch: " + testroute2);
		System.out.println("dijkstra: " + testroute);
		System.out.println();
		
		
		/* Use this code in Week 3 End of Week Quiz */
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);		
		//List<GeographicPoint> route3 = theMap.bfs(start,end);		
		System.out.println("aStarSearch: " + route2);
		System.out.println("dijkstra: " + route);

		
		
	}

	

	
	
}
