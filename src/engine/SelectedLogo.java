package engine;

import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class SelectedLogo {

	PVector position;
	
	float width;
	float height;
	
	boolean animated;
	PImage texture;
	long cost;
	String name;
	
	public SelectedLogo(PVector position, float width, float height, Tilemap map, Main applet) {
		this.position = position;
		this.width = width;
		this.height = height;

	}
	
	public void draw(Main applet, Tilemap map) {
		
		if(this instanceof TilemapLogo) {
			applet.noFill();
			
			
			if(applet.selected_tilemap == map.map_type - 1) {
				applet.stroke(applet.WHITE);
			}
			else {
				applet.stroke(applet.DARKBLUE);
			}
			
			applet.tint(255, 255);
			
			applet.strokeWeight(2);
			
			//float x = map.map_type * map.scale.x * 0.7f;
			//float y = map.scale.y / 2 + 10;
			
			applet.imageMode(PConstants.CENTER);
			
			//texture.resize((int)map.scale.x / 2 - 1, (int)map.scale.y / 2 - 1);
			applet.image(texture, position.x, position.y, width, height);
			
			applet.rect(position.x, position.y, width, height, 5);
			
			applet.textAlign(PConstants.CENTER, PConstants.BOTTOM);
			applet.text(name, position.x, position.y + height * 0.85f);
			
		}
		
		else if(this instanceof TileLogo) {
			
			if(applet.selected_tilemap == map.map_type - 1) {
				
				
					if(map.selected_tile == ((TileLogo)this).tile_type + 1) {
						applet.stroke(applet.WHITE);
					}
					else {
						applet.stroke(applet.DARKBLUE);
					}
					
					//float x = (i + 1) * map.scale.x * 0.7f;
					//float y =  map.scale.y + 30;
					
					applet.imageMode(PConstants.CENTER);
					applet.noTint();
					
					//texture.resize((int)map.scale.x / 2 - 1, (int)map.scale.y / 2 - 1);
					applet.image(texture, position.x, position.y, width, height);
					
					applet.rect(position.x, position.y, width, height, 5);
					
					
					applet.textAlign(PConstants.CENTER, PConstants.BOTTOM);
					applet.text((int)cost, position.x, position.y + height / 2);
					applet.text((String)name, position.x, position.y + height * 0.85f);
					
					
				
			}
			
			
		}
	
		
	}
	
	
	public boolean collision(Main applet, Tilemap map) {
		
		if(applet.mouseX > position.x - width / 2 && applet.mouseX < position.x + width / 2 && 
				applet.mouseY > position.y - height / 2 && applet.mouseY < position.y + height / 2) {
			
			if(this instanceof TilemapLogo) {
				
				applet.selected_tilemap = ((TilemapLogo) this).map_type - 1;
				
			}
			
			if(this instanceof TileLogo) {
				
				map.selected_tile = ((TileLogo) this).tile_type + 1;
				
				
			}
			
			return true;
		
		}
		
		return false;
		
	}
	
	

}
