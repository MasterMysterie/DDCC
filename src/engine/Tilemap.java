package engine;


import java.io.IOException;

import org.json.simple.JSONArray;

import org.json.simple.parser.ParseException;

import processing.core.PApplet;

import processing.core.PVector;


public class Tilemap {

	//variables//
	
	int map_length;
	int map_width;
	int map_height;
	int map_type;
	JSONArray tilemap;
	boolean interract = true;
	String name;
	
	TilemapLogo map_logo;
	TileLogo[] tile_logos;
	
	int selected_tile = 1;

	PVector scale = new PVector(0, 0);
	
	Tile[] tile_list;


	////////////////////
	
	public Tilemap(int map_type, int map_width, int map_height, String name, Main applet){
		
		this.map_width = map_width;
		this.map_height = map_height;
		scale.x = applet.width / map_width;
		scale.y = applet.height / map_height;
		this.map_type = map_type;
		this.name = name;
		
		map_length = map_width * map_height;
		
		tile_list = new Tile[map_length];
		
		tilemap = Tile.loadTilemap(this);
		

		for(int i = 0; i < map_length; i++) {
			try {
				tile_list[i] = new Tile(0, map_type, applet, i, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		float logo_width = scale.x / 2 - 1;
		float logo_height = scale.y / 2 - 1;
		
		float x = map_type * scale.x * 0.7f;
		float y = scale.y / 2 + 10;
		
		PVector position = new PVector(x, y);
		
		map_logo = new TilemapLogo(position, logo_width, logo_height, map_type, name, this, applet);
		tile_logos = new TileLogo[tilemap.size()];
		
		
		for(int i = 0; i < tilemap.size(); i++) {
			
			x = (i +1) * scale.x * 0.7f;
			y =  scale.y + 30;
			
			position = new PVector(x, y);
			
			
			tile_logos[i] = new TileLogo(position, logo_width, logo_height, i, this, applet);
			
		}
		
	}
	
	////////////////////
	
	public void draw(Main applet) {
		int index = 0;
		
		scale.x = applet.width / map_width;
		scale.y = applet.height / map_height;
		
		for(Tile tile : tile_list) {
			//if(tile.noise) {
				tile.position = new PVector(index % map_width, PApplet.floor(index / map_width));
				tile.draw(applet, scale);
				
				index++;
			//}
		}
		
		if(applet.grid && applet.selected_tilemap == map_type - 1) {
			applet.strokeWeight(2);
			applet.stroke(applet.DARKBLUE);
			for(int y = 0; y < map_height + 1; y++) {
			
				applet.line(-1, y * scale.y, applet.width + 1, y * scale.y);
			
			}
			
			for(int x = 0; x < map_width + 1; x++) {
				
				applet.line(x * scale.x, -1, x * scale.x, applet.height + 1);
				
				
			}
			
		}
		
		
		
	}
	
	public void drawLogos(Main applet) {
		map_logo.draw(applet, this);
		
		for(TileLogo logo : tile_logos) {
			logo.draw(applet, this);
		}
	}
	
	////////////////////
	
	public void resetTile(int i, Main applet) {
		
		try {
			tile_list[i] = new Tile(0, map_type, applet, i, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void newTile(int i, int selected, Main applet) {
		try {
			tile_list[i] = new Tile(selected, map_type, applet, i, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void logo_collision(Main applet, int mouseButton) {
		
		interract = true;
		
		switch(mouseButton) {
		
		case PApplet.LEFT:
			if(map_logo.collision(applet, this)) {
				interract = false;
			}
		
			if(applet.selected_tilemap == map_type - 1) {
				for(TileLogo logo : tile_logos) {
					if(logo.collision(applet, this)) {
						interract = false;
						
					}
				
				}
			}
			break;
		
		}
	}
	
	public void collision(Main applet, int index, int mouseButton) {
		
		switch(mouseButton) {
		
		case PApplet.LEFT:
			
			if(applet.selected_tilemap == map_type - 1) {
			
				if(interract) {
					if(tile_list[index].type == 0) {
						
						applet.dragging = null;
						newTile(index, selected_tile, applet);
		
					}
					else{
						
						applet.dragging = tile_list[index];
						resetTile(index, applet);
						
					}
					break;
				}
			}
			break;
		case PApplet.RIGHT:
			if(applet.selected_tilemap == map_type - 1) {
				if(tile_list[index].type != 0) {
					applet.money += tile_list[index].cost;
					resetTile(index, applet);
					
				}
				
				break;
			}
			break;
		}
		
		
		
		
	}
	
	public void unCollision(Main applet, int index, int mouseButton) {
		
		if(applet.dragging != null) {
			if(tile_list[index].type != 0) {
				applet.money += tile_list[index].cost;
			}
			tile_list[index] = applet.dragging;
		}
		applet.dragging = null;
		
		
	
	}

}
