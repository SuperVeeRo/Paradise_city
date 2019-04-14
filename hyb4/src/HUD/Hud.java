package HUD;

//Contains a reference to the Player.
//Draws all relevant information at the
//bottom of the screen.
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


import Entity.Player;
import Main.GamePanel;
import Managers.Content;

public class Hud {
	
	private int xoffset;
	private int yoffset;
	private double loose;
	
	private double energy = 100;
	private double hungry = 100;
	private double toilet = 100;
	private double bath = 100;
	private Rectangle buttons;
	
	private BufferedImage bar;
	private BufferedImage sleep;
	private BufferedImage active_player;

	private Player player;
	
	
	private Font font;
	private Color textColor; 
	private Color backgroundColor; 
	private int newtime;
	private int lasttime;
	
	
	private boolean sleepButton = false;
	private boolean fridgeButton = false;
	private int xB;
	private int yB;
	private boolean sleepisPressed = false;
	private boolean isReleased = false;
	private boolean fridgeisPressed = false;
	private boolean fridgeisReleased = false;
	private int widthS = 75;
	private int heightS = 21;
	private int widthSB = 78;
	private int heightSB = 24;
	
	public Hud(Player p) {
		
		player = p;
		yoffset = GamePanel.HEIGHT;
		xoffset = GamePanel.WIDTH;
		
		bar = Content.BAR[0][0];
		active_player = Content.PLAYER_DEFAULT[0][0];

		font = new Font("Arial", Font.PLAIN, 10);
		textColor = new Color(47, 64, 126);
		backgroundColor = new Color(164, 198, 222);
		
	}
	
	public void setEnergy(double e) {
		this.energy = e;
	}
	
	public void setHungry (double h) {
		this.hungry = h;
	}
	
	public void setToilet(double t) {
		this.toilet = t;
	}
	
	public void setBath(double b) {
		this.bath = b;
	}
	
	public void suggestBed(int x, int y, int xm, int ym) {
		this.xB = xoffset - 400;
		this.yB = yoffset +7;
		this.sleepButton = true;
	}
	
	public void notBed() {
		this.sleepButton = false;
	}
	
	public void suggestFridge(int x, int y, int xm, int ym) {
		this.xB = xoffset - 400;
		this.yB = yoffset +7;
		this.fridgeButton = true;
	}
	
	public void notFridge() {
		this.fridgeButton = false;
	}
	
	public void pressButton(int x, int y) {
		
		System.out.println(x + " "+y);
		if(x >= 720 && x <= 944 && y >= 981 && y <= 1042) {
			if(this.sleepButton) {
				sleepisPressed = true;
			}
			if(this.fridgeButton) {
				fridgeisPressed = true;
			}
		}
	}
	
	public void setRelease(boolean x) {
		if(widthS == 78 && heightS == 24) {
			this.isReleased = x;
		}
	}
	
	public void draw(Graphics2D g, int activePlayer) {
		
		// draw hud
		g.drawImage(bar, 10, yoffset+2, null);
		if(activePlayer == 0) {
			g.drawImage(Content.PLAYER_LIDIA[2][0],15, yoffset+1, 16, 32, null);
		}
		else {
			g.drawImage(Content.PLAYER_CLAUDIUS[0][0],15, yoffset+2, 16, 32, null);
		}
		energy -= loose;
		hungry -= loose;
		toilet -= loose;
		bath -= loose;
		loose = 0;
		// draw diamond bar
		g.setColor(textColor);
		g.fillRect(74, yoffset+6, (int)(54*energy/100), 9);
		g.fillRect(74, yoffset+20, (int)(54*hungry/100), 9);
		g.fillRect(151, yoffset+6, (int)(54*toilet/100), 9);
		g.fillRect(151, yoffset+20, (int)(54*bath/100), 9);
		
		if(sleepButton == true) {
			if (sleepisPressed) {
				setEnergy(100);
				widthS+=3;
				heightS+=3;
				sleepisPressed = false;
			}
			else if(isReleased) {
				widthS-=3;
				heightS-=3;
				isReleased = false;
			}
			g.setColor(Color.black);
			g.fillRect(xB, yB, widthSB, heightSB);
			g.setColor(backgroundColor);
			g.fillRect(xB, yB, widthS, heightS);
			g.setColor(textColor);
			Content.drawString(g, "Dormir", xB, yB+2);
			
		}
		if(fridgeButton == true) {
			if (fridgeisPressed) {
				setHungry(100);
				widthS+=3;
				heightS+=3;
				fridgeisPressed = false;
			}
			else if(isReleased) {
				widthS-=3;
				heightS-=3;
				isReleased = false;
			}
			g.setColor(Color.black);
			g.fillRect(xB, yB, widthSB, heightSB);
			g.setColor(backgroundColor);
			g.fillRect(xB, yB, widthS, heightS);
			g.setColor(textColor);
			Content.drawString(g, "Ouvrir", xB, yB+2);
			
		}
		
		

		
		// draw time
		int minutes = (int) (player.getTicks() / 1800);
		int seconds = (int) ((player.getTicks() / 30) % 60);
		if(minutes < 10) {
			if(seconds < 10) Content.drawString(g, "0" + minutes + ":0" + seconds, 85, 3);
			else Content.drawString(g, "0" + minutes + ":" + seconds, 85, 3);
		}
		else {
			if(seconds < 10) Content.drawString(g, minutes + ":0" + seconds, 85, 3);
			else Content.drawString(g, minutes + ":" + seconds, 85, 3);
		}
		newtime = seconds;
		if(Math.abs(newtime-lasttime) >= 2) {
			loose = 1.85185185 ;
			lasttime = newtime;
			//System.out.println(loose);
		}
		
		
		
	}
	
}