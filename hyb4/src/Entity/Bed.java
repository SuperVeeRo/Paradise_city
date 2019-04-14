package Entity;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Bed extends Entity{
	BufferedImage img = null;
	private int yc;
	private int xc;
	public Bed(TileMap tm) {
		super(tm);
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/Sprites/Bed1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cwidth = 96;
		cheight = 128;
		xc = x-32;
		yc = y+96;
		
	}
	
	public Rectangle getRectangle() {
		xc = x- 32;
		yc = y+96;
		return new Rectangle(xc+tileMap.getx(), yc+ tileMap.gety(), cwidth, cheight);
	}
	
	
	public void draw(Graphics2D g) {
		//System.out.println("x: "+x + " xmap: "+xmap);
		g.drawImage(img, x + tileMap.getx() , y + tileMap.gety() , 32, 64, null);//32, 64
		
	}

}
