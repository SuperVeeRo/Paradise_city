package GameStates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import Entity.Bed;
import Entity.Fridge;
import Entity.Player;
import HUD.Hud;
import Main.GamePanel;
import Managers.GameStateManager;
import Managers.JukeBox;
import Managers.Keys;
import TileMap.TileMap;

public class PlayState2 extends GameState{
	
	// player
		private Player[] players;
		private int activePlayer ;
		private int numPlayableCharacters ;
		
		// tilemap
		private TileMap tileMap;
		
		// camera position
		private int xsector;
		private int ysector;
		private int sectorSizeX; 
		private int sectorSizeY;
		
		// hud
		private static Hud hud;
		
		// events
		private boolean eventStart;

		private int eventTick;
		
		private ArrayList<Bed> beds;
		private ArrayList<Fridge> fridges;
		
		// transition box
		private ArrayList<Rectangle> boxes;
		
		public PlayState2(GameStateManager gsm) {
			super(gsm);
		}
		
		public void init() {
			
			beds = new ArrayList<Bed>();
			fridges = new ArrayList<Fridge>();
			
			// load map
			tileMap = new TileMap(32);
			tileMap.loadTiles("/Tilesets/dark-wood.png");
			tileMap.loadMap("/Maps/testmap.map");
			
			// create the players
			activePlayer = 0;
			numPlayableCharacters = 2;
			players = new Player[numPlayableCharacters];
			players[0] = new Player(tileMap, Player.LIDIA);
			players[1] = new Player(tileMap, Player.CLAUDIUS);
			
			
			populateBeds();
			populateFridges();
			
			
			// initialize players
			players[0].setTilePosition(11, 11);
			players[1].setTilePosition(5, 5) ;
			
			// set up camera position
			sectorSizeX = GamePanel.WIDTH;
			sectorSizeY = GamePanel.HEIGHT;
			xsector = players[activePlayer].getx() ;
			ysector = players[activePlayer].gety() ;
			tileMap.setPositionImmediately(-xsector + sectorSizeX / 2, -ysector + sectorSizeY / 2);
			// load hud
			hud = new Hud(players[activePlayer]);
			
			// load music
			JukeBox.load("/Music/playsong.mp3", "background");
			JukeBox.setVolume("background", -10);
			JukeBox.play("background");
			
			
			// start event
			boxes = new ArrayList<Rectangle>();
			eventStart = true;
			eventStart();
				
		}
		
		private void populateBeds() {
			
			Bed d;
			
			d = new Bed(tileMap);
			d.setTilePosition(7, 7);
			beds.add(d);
			
	}
		private void populateFridges() {
			
			Fridge d;
			
			d = new Fridge(tileMap);
			d.setTilePosition(3, 3);
			fridges.add(d);
			
		}
		
		public void update() {
			
			// check keys
			handleInput();
			
			// check events
			if(eventStart) eventStart();
			
			// update camera and check for map borders; the camera can not be centred if the player gets too close to the edge
			int xMap ;
			int yMap ;
			
			if ((players[activePlayer].getx() + GamePanel.WIDTH/2)>= tileMap.getWidth()) xMap = -tileMap.getWidth() + GamePanel.WIDTH ;
			else if ((players[activePlayer].getx()<GamePanel.WIDTH/2)) xMap = 0 ;
			else xMap = -players[activePlayer].getx() + GamePanel.WIDTH/2 ;
		
			if ((players[activePlayer].gety() + GamePanel.HEIGHT/2>= tileMap.getHeight())) yMap = -tileMap.getHeight() + GamePanel.HEIGHT ;
			else if ((players[activePlayer].gety()<GamePanel.HEIGHT/2)) yMap = 0 ;
			else yMap = -players[activePlayer].gety() + GamePanel.HEIGHT/2 ;
			
			tileMap.setPosition(xMap, yMap);
			tileMap.update();
			
			if(tileMap.isMoving()) return;
			
			// update player
			for (int i=0; i<numPlayableCharacters; i++) {
				players[i].update();
			}
			
			for(int i = 0; i < beds.size(); i++) {
				
				Bed d = beds.get(i);
				d.update();
				d.getRectangle().setSize(16, 32);
				if(players[activePlayer].intersects(d)) {
					//hud.setEnergy(100);
					hud.suggestBed(d.getx(),d.gety(), tileMap.getx(), tileMap.gety());
				}
				else {
					hud.notBed();
				}
			}
			for(int i = 0; i < fridges.size(); i++) {
				
				Fridge d = fridges.get(i);
				d.update();
				if(players[activePlayer].intersects(d)) {
					//hud.setEnergy(100);
					hud.suggestFridge(d.getx(),d.gety(), tileMap.getx(), tileMap.gety());
				}
				else {
					hud.notFridge();
				}
			}


			
		}
		
		public void draw(Graphics2D g) {
			
			// draw tilemap
			tileMap.draw(g);
			
			// draw players
			for (int i=0; i< numPlayableCharacters; i++) {
				players[i].draw(g);;
			}
			for(Fridge d : fridges) {
				d.draw(g);
			}
			for(Bed d : beds) {
				d.draw(g);
			}
			
			// active player gets a red dot on it precise coordinates
			g.setColor(Color.red);
			g.fillOval(players[activePlayer].getx()+tileMap.getx(), players[activePlayer].gety()+tileMap.gety(), 4, 4 );
			
			// draw transition boxes
			g.setColor(java.awt.Color.BLACK);
			for(int i = 0; i < boxes.size(); i++) {
				g.fill(boxes.get(i));
			}
			
			//Status bar 
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, GamePanel.HEIGHT, GamePanel.WIDTH, -GamePanel.HEIGHT+GamePanel.HEIGHT2);
			
			// draw hud
			hud.draw(g, activePlayer);

			
		}
		
		public void handleInput() {

			if(Keys.isDown(Keys.LEFT)) players[activePlayer].setLeft();
			if(Keys.isDown(Keys.RIGHT)) players[activePlayer].setRight();
			if(Keys.isDown(Keys.UP)) players[activePlayer].setUp();
			if(Keys.isDown(Keys.DOWN)) players[activePlayer].setDown();
			if(Keys.isDown(Keys.SHIFT)) players[activePlayer].speedUp(true);
			if(!Keys.isDown(Keys.SHIFT)) players[activePlayer].speedUp(false);
			if(Keys.isPressed(Keys.SPACE)) players[activePlayer].setAction();
			if(Keys.isPressed(Keys.C)) {
				activePlayer++ ;
				System.out.println("Changing the active player");
				if (activePlayer==numPlayableCharacters) activePlayer = 0;
				
			}
		}
		
		public static void mouseEvent(int x, int y) {
			hud.pressButton(x,y);
		}
		
		public static void releaseEvent() {
			hud.setRelease(true);
		}
		
		//===============================================
		
		private void eventStart() {
			eventTick++;
			if(eventTick == 1) {
				boxes.clear();
				for(int i = 0; i < 9; i++) {
					boxes.add(new Rectangle(0, i * 16, GamePanel.WIDTH, 16));
				}
			}
			if(eventTick > 1 && eventTick < 32) {
				for(int i = 0; i < boxes.size(); i++) {
					Rectangle r = boxes.get(i);
					if(i % 2 == 0) {
						r.x -= 4;
					}
					else {
						r.x += 4;
					}
				}
			}
			if(eventTick == 33) {
				boxes.clear();
				eventStart = false;
				eventTick = 0;
			}
		}

}
