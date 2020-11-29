package engine;


import processing.core.PApplet;
import processing.core.PVector;


public class Main extends PApplet{

	//variables//
	
	//colours
	int BLACK = color(0);
	int WHITE = color(255);
	int LIGHTBLUE = color(13, 174, 214);
	int DARKBLUE = color(9, 108, 132);
	int GRAY = color(170);
	
	//tilemaps
	Tilemap[] tilemaps = new Tilemap[1];
	int map_width = 8;
	int map_height = 8;
	
	//control-related
	Tile dragging;
	int selected_tilemap = 0;
	boolean grid = true;
	boolean mousedown = false;
	int money = 10000;
	boolean alt = false;
	
	//wo-er m8
	int frame = 0;
	float animation_speed = 5;
	float phase = 0;
	
	
	//james
	boolean james = false;
	
	////////////////////
	
	public void setup() {
		background(LIGHTBLUE);
		textSize(20);
		rectMode(CENTER);

		tilemaps[0] = new Tilemap(1, map_width, map_height, "Ground Tiles", this);
		surface.setResizable(true);
		
		
	}
	
	public void settings() {
		size(1024, 1024);
		noSmooth();
		
		
	}
	
	////////////////////
	
	public void draw() {
		background(LIGHTBLUE);
		
		

		if(selected_tilemap >= tilemaps.length) {
			return;
		}
		Tilemap map = tilemaps[selected_tilemap];
		if(dragging != null) {
			dragging.dragDraw(this, map.scale, new PVector(floor(mouseX / map.scale.x), floor(mouseY / map.scale.y)));
		}	
		
		for(Tilemap mapd : tilemaps) {
			if(mapd != tilemaps[selected_tilemap]) {
				mapd.draw(this);
			}
		}
		tilemaps[selected_tilemap].draw(this);
		
		for(Tilemap mapd : tilemaps) {
			
			mapd.drawLogos(this);
			
		}
		
		fill(0);
		textAlign(LEFT);
		text(money, 0, 20);
		
		if(phase + animation_speed < 255) {
			phase += animation_speed;
		}
		else {
			phase = 0;
			frame++;
		}
		
		if(james) {
			imageMode(CORNER);
			image(loadImage("sprites/jems.png"), 0, 0, width, height);
		}
		
	}
	
	////////////////////
	
	public void keyPressed() {
		
		if(alt) {

			//selecting
			if(containsChar("123456789", key)) {
				selected_tilemap = Character.getNumericValue(key) - 1;
			}
	
		}
		else{
			
			//selecting
			
			if(containsChar("123456789", key)) {
				tilemaps[selected_tilemap].selected_tile = Character.getNumericValue(key);
			}
			
			switch(key) {
			
			case '	':
				if(selected_tilemap != tilemaps.length - 1) {
					selected_tilemap++;
				}
				else {
					selected_tilemap = 0;
				}
			
			}
		}
		
		switch(key) {
			
		//other controls(keys)
		
		case 'g':
			grid = ! grid;
			break;
		
		case 'j':
			james = true;
			break;
			
			
		}
		switch (keyCode) {
		
		//other controls(keyCodes)
		
		case ALT:
			alt = true;
			break;
		
		case SHIFT:
		if(tilemaps[selected_tilemap].selected_tile!= tilemaps[selected_tilemap].tilemap.size()) {
			tilemaps[selected_tilemap].selected_tile++;
		}
		else {
			tilemaps[selected_tilemap].selected_tile= 1;
		}
		
		}	
		
		
	}
	
	public void keyReleased() {
		
		switch (keyCode) {
		
		//other controls(keyCodes)
		case ALT:
			alt = false;
			break;
		
		
		}
		
		switch (key) {
		
		case 'j':
			james = false;
			break;
			
		
		}
		
	}
	
	public void mousePressed() {

		mousedown = true;
		
		if(selected_tilemap >= tilemaps.length) {
			return;
		}
		
		for(Tilemap map : tilemaps) {
			
			map.logo_collision(this, mouseButton);
			
			
		}
		
		Tilemap map = tilemaps[selected_tilemap];
		int index = findIndex(map, mouseX, mouseY);
		
		map.collision(this, index, mouseButton);

	}
	
	public void mouseReleased() {
		
		mousedown = false;
		if(selected_tilemap >= tilemaps.length) {
			return;
		}
		Tilemap map = tilemaps[selected_tilemap];
		int index = findIndex(map, mouseX, mouseY);
		
		map.unCollision(this, index, mouseButton);
		
	}
	
	public void mouseDown() {
		
		
		
		
	}
	
	////////////////////
	
	public int findIndex(Tilemap map, int x, int y) {
		
		return floor(y / map.scale.y) * map.map_width + floor(x / map.scale.x);
		
	}
	
	public boolean containsChar (String input, char secondary)
    {
        boolean contains_result = false;
        for (int i = 0 ; i < input.length () ; i++)
        {
            if (input.charAt (i) == secondary)
            {
                contains_result = true;
                break;
            }
        }
        return contains_result;
    }
	
	////////////////////
	
	public static void main(String[] args) {
		PApplet.main("engine.Main");

	}

}
