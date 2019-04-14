package GameStates;

import Managers.GameStateManager;
import java.awt.*;
import java.awt.image.BufferedImage;


import GraphicalElements.Background;
import Managers.Content;

import Managers.JukeBox;
import Managers.Keys;


public class MenuState extends GameState {
	
	private Background bg ;

	
	private int currentChoice = 0;
	private String[] options = {
		"START",
		"QUIT"
	};
	
	public Color titleColor ;
	public Font titleFont ;
	
	private Font font ;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		
		try {
			bg = new Background("/Backgrounds/cyberpunk-street3.png", 1);
			bg.setVector(-1, 0);
			bg.setAcceleration(0, 0);
			
			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Century Gothic", Font.PLAIN, 28);
			font = new Font("Arial", Font.PLAIN, 12);
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	public void init() {

		JukeBox.load("/Music/menusong.wav", "menusong");
		JukeBox.play("menusong");
	}
	
	public void update() {
		handleInput() ;
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		
		//draw bg 
		bg.draw(g);
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Paradise City"
				+ "",75, 70);
		
		//draw menu options 
		g.setFont(font);
		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				g.setColor(Color.pink);
			}
			else {
				g.setColor(Color.black);
			}
			g.drawString(options[i], 140, 100 + i * 15 );
		}
	}
	
	private void select() {
		if(currentChoice == 0) {
			JukeBox.stop("menusong");
			gsm.setState(GameStateManager.PLAY1);
		}
		if(currentChoice == 1) {
			System.exit(0);
		}
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.DOWN)) {
			if (currentChoice < options.length - 1) {
				// JukeBox.play("menuoption");
				currentChoice++;
			}
			else currentChoice = 0;
		}
		if(Keys.isPressed(Keys.UP)) {
			if (currentChoice > 0) {
				//JukeBox.play("menuoption");
				currentChoice--;
			}
			else currentChoice = options.length - 1;
		}
			
		if(Keys.isPressed(Keys.ENTER)) {
			//JukeBox.play("collect");
			select();
		}
	}
}