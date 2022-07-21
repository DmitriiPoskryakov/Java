package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	
	}
		
	public void draw(PGraphics pg, float x, float y) {
		// For starter code just drawMaker(...)
		if (!hidden) {
			drawMarker(pg, x, y);
			if (selected) {
				showTitle(pg, x, y);
			}
		}
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
				
			pg.fill(200, 0, 200);
			pg.ellipse(x, y, 5, 5);				
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		 // show rectangle with title
		String s = getProperty("name")+", "+ getProperty("city") +", "+ getProperty("country")+", "+ getProperty("code");
		int sizeText = 12;
		pg.textSize(sizeText);
		float w = pg.textWidth(s);
		pg.fill(200);
		pg.rect(x, y-sizeText, w+5, sizeText+3);
		pg.fill(0);
		pg.text(s, x+3, y);
		
				
	}
	
}
