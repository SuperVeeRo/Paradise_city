package Entity;

import java.awt.Color;
import java.awt.Graphics2D;

import TileMap.TileMap;

public class Door extends Entity{
	
	
	public Door(TileMap tileMap) {
		super(tileMap) ;
		
		width = tileSize ;
		height = tileSize ;
		cwidth = tileSize ;
		cheight = tileSize ;
	}
	
	public void draw(Graphics2D g) {

		g.setColor(Color.red);
		g.fillRect(x + tileMap.getx() - width/2, y + tileMap.gety() - height/2, cwidth, cheight); }

}

