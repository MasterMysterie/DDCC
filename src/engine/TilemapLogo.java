package engine;

import org.json.simple.JSONObject;

import processing.core.PVector;

public class TilemapLogo extends SelectedLogo{
	
	int map_type;
	
	public TilemapLogo(PVector position, float width, float height, int type, String name, Tilemap map, Main applet) {
		super(position, width, height, map, applet);
		this.map_type = type;
		this.name = name;
		
		JSONObject tile = (JSONObject) map.tilemap.get(0);
		
		texture = applet.loadImage((String) tile.get("texture"));
		
		// TODO Auto-generated constructor stub
	}

}
