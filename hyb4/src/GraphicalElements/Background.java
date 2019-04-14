package GraphicalElements;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

import Main.GamePanel;


public class Background {
	
	private BufferedImage image;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	private double ddx;
	private double ddy;
	private int counter;
	
	private double moveScale;
	
	public Background(String s, double ms) {
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
			moveScale = ms ;
			counter = 0;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPosition(double x, double y) {		
		this.x = (x * moveScale) % GamePanel.WIDTH ;  
		this.y = (y * moveScale)% GamePanel.HEIGHT2;
		
	}
	public void setAcceleration(double ddx, double ddy) {
		this.ddx = ddx;
		this.ddy = ddy;
	}
	public void setVector(double dx, double dy) {	
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		x+=dx + ddx*counter;
		y+=dy + ddy*counter;
		counter ++;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, (int) x, (int) y, null) ;
		if (x<0) {
			g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
		}
		
		if (x>0 ) {
			g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
		}
	}
	
	

}