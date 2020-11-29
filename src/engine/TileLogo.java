package engine;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import processing.core.PVector;

public class TileLogo extends SelectedLogo{
	
	int tile_type;
	
	public TileLogo(PVector position, float logo_width, float logo_height, int type, Tilemap map, Main applet) {
		super(position, logo_width, logo_height, map, applet);
		
		this.tile_type = type;
		
		JSONObject tile = (JSONObject) map.tilemap.get(((TileLogo)this).tile_type);
		animated = (boolean) tile.get("animated");
		if(! animated) {
			texture = applet.loadImage((String) tile.get("texture"));
		}
		else {
			texture = applet.loadImage((String) ((JSONArray) tile.get("texture")).get(0)); 
		
		}
		
		cost = (long) tile.get("cost");
		name = (String) tile.get("name");
		
		// TODO Auto-generated constructor stub
	}

}
