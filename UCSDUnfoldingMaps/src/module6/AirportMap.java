package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	private SimpleLinesMarker lastroute;
	
	public void setup() {
		// setting up PAppler
		size(1980, 1000, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 10, 10, width*980/1000, height*990/1000, new Microsoft.HybridProvider());
		map.zoomToLevel(3);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
			m.setId(feature.getId());
			m.setRadius(10);
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
		
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
			sl.setColor(255);
						
			
			routeList.add(sl);
			
			
		}
	
		for(Marker marker : routeList) {
			marker.setHidden(true);}
		
		
		
		map.addMarkers(routeList);
		
		map.addMarkers(airportList);
		
	}
	
	public void draw() {
		background(0);
		map.draw();
		
	}
	
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(airportList);
		
	}
	
	// If there is a marker selected 
	private void selectMarkerIfHover(List<Marker> markers)
	{
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}
		
		for (Marker m : markers) 
		{
			if(!m.isHidden()) {
				CommonMarker marker = (CommonMarker)m;
				if (marker.isInside(map,  mouseX, mouseY)) {
					lastSelected = marker;
					marker.setSelected(true);
					return;
				}
			}
		}
	}
	
	
	@Override
	public void mouseClicked()
	{
		if (lastClicked != null) {
			unhideMarkers();
			lastClicked = null;
		}
		else if (lastClicked == null) 
		{
			checkAirportMarkerForClick();
			
		}
	}
	
	// Helper method that will check if a Airport marker was clicked on
	// and respond appropriately
	private void checkAirportMarkerForClick()
	{ 
		if (lastClicked != null) return;
		// Loop over the Airport markers to see if one of them is selected
		for (Marker marker : airportList) {
			if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {
				lastClicked = (CommonMarker)marker;				
				}
		}
				
		// Hide all the other Airports and hide
		for (Marker mhide : airportList) {
			if (mhide != lastClicked) {
				mhide.setHidden(true);
			}
		}
				
		for (Marker m :routeList) {
			SimpleLinesMarker mark = (SimpleLinesMarker)m;			
			if (Integer.parseInt(mark.getProperty("source").toString()) == Integer.parseInt(lastClicked.getId())) {
				lastroute = mark;				
				
				for (Marker mh : routeList) {
					if (mh == lastroute) {
						mh.setHidden(false);										
				 		for (Marker mk : airportList) {						
				 			if (Integer.parseInt(mh.getProperty("destination").toString()) == Integer.parseInt(mk.getId())) {
							mk.setHidden(false);
				 			}
				 		}	
					}
				}
			}
		}				
		return;			
	}		
	
	
	// Helper method that will check if an earthquake marker was clicked on
	// and respond appropriately
	/*private void checkrouteListForClick()
	{
		if (lastClicked != null) return;
		// Loop over the earthquake markers to see if one of them is selected
		for (Marker m : quakeMarkers) {
			EarthquakeMarker marker = (EarthquakeMarker)m;
			if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {
				lastClicked = marker;
				// Hide all the other earthquakes and hide
				for (Marker mhide : quakeMarkers) {
					if (mhide != lastClicked) {
						mhide.setHidden(true);
					}
				}
				for (Marker mhide : cityMarkers) {
					if (mhide.getDistanceTo(marker.getLocation()) 
							> marker.threatCircle()) {
						mhide.setHidden(true);
					}
				}
				return;
			}
		}
	}*/

	private void unhideMarkers() {
		for(Marker marker : airportList) {
			marker.setHidden(false);
		}
			
		for(Marker marker : routeList) {
			marker.setHidden(true);
		}
	}

}

