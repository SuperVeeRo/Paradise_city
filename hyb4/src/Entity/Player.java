package Entity;

//The only subclass the fully utilizes the
//Entity superclass (no other class requires
//movement in a tile based map).
//Contains all the gameplay associated with
//the Player.


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Managers.Content;
import Managers.JukeBox;
import TileMap.TileMap;

public class Player extends Entity {
	
	// sprites
	private BufferedImage[] downSprites;
	private BufferedImage[] leftSprites;
	private BufferedImage[] rightSprites;
	private BufferedImage[] upSprites;

	// animation
	private final int DOWN = 0;
	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
	private int animationDelay = 5;
	
	// player tileset
	public static final int LIDIA = 0 ;
	public static final int SHURA = 1 ;
	public static final int CLAUDIUS = 2 ;
	// gameplay
	private long ticks;
	
	public Player(TileMap tm, int player) {
		
		super(tm);
		
		width = 32;
		height = 64;
		cwidth = 32;
		cheight = 32;	
		moveSpeed = 1;
		
		if (player == LIDIA) {
			upSprites = Content.PLAYER_LIDIA[0];
			leftSprites = Content.PLAYER_LIDIA[1];
		    downSprites = Content.PLAYER_LIDIA[2] ;
			rightSprites = Content.PLAYER_LIDIA[3] ;
		}
		
		else if (player == SHURA) {		
			downSprites = Content.PLAYER_DEFAULT[0];
			leftSprites = Content.PLAYER_DEFAULT[1];
			rightSprites = Content.PLAYER_DEFAULT[2];
			upSprites = Content.PLAYER_DEFAULT[3];

		}
		
		else if (player == CLAUDIUS) {
			downSprites = Content.PLAYER_CLAUDIUS[0];
			upSprites = Content.PLAYER_CLAUDIUS[1];
			leftSprites = Content.PLAYER_CLAUDIUS[2];
			rightSprites = Content.PLAYER_CLAUDIUS[3];
		}
			
		animation.setFrames(downSprites);
		animation.setDelay(animationDelay);
		
	}
	
	private void setAnimation(int i, BufferedImage[] bi, int d) {
		currentAnimation = i;
		animation.setFrames(bi);
		animation.setDelay(d);
		
	}
	
	// Used to update time.
	public long getTicks() { return ticks; }
	
	// Keyboard input. Moves the player.
	public void setDown() {
		super.setDown();
	}
	public void setLeft() {
		super.setLeft();
	}
	public void setRight() {
		super.setRight();
	}
	public void setUp() {
		super.setUp();
	}
	
	// Keyboard input.
	// If Player has axe, dead trees in front
	// of the Player will be chopped down.
	public void setAction() {
		System.out.println(getx() + "/ "+ gety());

		if(currentAnimation == UP && tileMap.getIndex(rowTile - 1, colTile) == 30) {
			System.out.println("Changing level");
		}
		if(currentAnimation == DOWN && tileMap.getIndex(rowTile + 1, colTile) == 30) {
			System.out.println("Changing level");

		}
		if(currentAnimation == LEFT && tileMap.getIndex(rowTile, colTile - 1) == 30) {
			System.out.println("Changing level");

		}
		if(currentAnimation == RIGHT && tileMap.getIndex(rowTile, colTile + 1) == 30) {
			System.out.println("Changing level");

		}

	}
	
	public void speedUp(boolean b) {
		if (b) { moveSpeed = 2 ;
		         animationDelay = 4;
		         animation.setDelay(animationDelay);
		}
		else {
			moveSpeed = 1 ;
			animationDelay = 10;
			animation.setDelay(animationDelay);
		}
	}
	
	public void update() {
		
		ticks++;
		
		
		// set animation
		if(down) {
			if(currentAnimation != DOWN) {
				setAnimation(DOWN, downSprites, animationDelay);
			}
		}
		if(left) {
			if(currentAnimation != LEFT) {
				setAnimation(LEFT, leftSprites, animationDelay);
			}
		}
		if(right) {
			if(currentAnimation != RIGHT) {
				setAnimation(RIGHT, rightSprites, animationDelay);
			}
		}
		if(up) {
			if(currentAnimation != UP) {
				setAnimation(UP, upSprites, animationDelay);
			}
		}
		
		// update position
		super.update();
		
	}
	
	// Draw Player.
	public void draw(Graphics2D g) {
		setMapPosition();
		g.drawImage(
			animation.getImage(),
			x + xmap - width / 2,
			y + ymap - height*9/12,
			null
		);
	}
	
}