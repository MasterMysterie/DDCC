package engine;
 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class Tile {
	
	//variables//

	PVector position;
	PImage texture;
	PImage[] animation;
	int type = 0;
	int map_type = 0;
	double cost = 0;
	boolean animated = false;
	
	
	Tilemap map;
	
	
	
	////////////////////
	
	public Tile(int tile_type, int map_type, Main applet, int index, Tilemap map) throws IOException, ParseException {
		this.type = tile_type;
		this.map_type = map_type;
		this.map = map;
		
		JSONArray tilemap = loadTilemap(map);
		if(type != 0) {
			JSONObject tile = (JSONObject) tilemap.get(type - 1);
			animated = (boolean) tile.get("animated");
			if(! animated) {
				texture = applet.loadImage((String) tile.get("texture"));
			}
			else {
				animation = new PImage[((JSONArray)tile.get("texture")).size()];
				for(int i = 0; i < animation.length; i++) {
					
					animation[i] = applet.loadImage((String) ((JSONArray)tile.get("texture")).get(i));
					
				}
				
			}
			cost = (long) tile.get("cost");
			if(applet.money - cost >= 0) {
				applet.money -= cost;
			}
			else {
				type = 0;
				
			}
			
		}
		
		
		
	}
	
	////////////////////
	
	public void draw(Main applet, PVector scale) {
		if(type != 0) {
			applet.tint(255, 255);
			if(applet.selected_tilemap != map_type - 1) {
				applet.tint(255, 126);
			}
			applet.imageMode(PConstants.CORNER);
			//this blurs image
			//texture.resize((int)scale.x, (int)scale.y);
			
			if(animated) {
				PImage f1 = animation[applet.frame % animation.length];
				PImage f2 = animation[(applet.frame + 1) % animation.length];
				
				applet.image(f2, position.x * scale.x, position.y * scale.y, scale.x, scale.y);
				applet.tint(255, applet.phase * -1 + 255);
				applet.image(f1, position.x * scale.x, position.y * scale.y, scale.x, scale.y);
				
				
				
			}
			else {
				applet.image(texture, position.x * scale.x, position.y * scale.y, scale.x, scale.y);
				
			}
			
		}
		
	}
	
	public void dragDraw(Main applet, PVector scale, PVector position) {

		applet.imageMode(PConstants.CORNER);
		//this blurs image
		//texture.resize((int)scale.x, (int)scale.y);
		
		if(animated) {
			PImage f1 = animation[applet.frame % animation.length];
			PImage f2 = animation[(applet.frame + 1) % animation.length];
			
			applet.image(f2, position.x * scale.x, position.y * scale.y, scale.x, scale.y);
			applet.tint(255, applet.phase * -1 + 255);
			applet.image(f1, position.x * scale.x, position.y * scale.y, scale.x, scale.y);
			
			
			
		}
		else {
			applet.image(texture, position.x * scale.x, position.y * scale.y, scale.x, scale.y);
			
		}
		
	}

	////////////////////
	
	public static JSONArray loadTilemap(Tilemap map) {
		
		JSONParser jsonParser = new JSONParser();
		FileReader reader = null;
		try {
			reader = new FileReader("src/resources/Tiletypes.json");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object obj = null;
		try {
			obj = jsonParser.parse(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONArray Tilemaps = (JSONArray) obj;
		
		JSONArray tilemap = (JSONArray) Tilemaps.get(map.map_type - 1);
		return tilemap;
		

		
	}
	
}
