package Managers;

//The GameStateManager does exactly what its
//name says. It contains a list of GameStates.
//It decides which GameState to update() and
//draw() and handles switching between different
//GameStates.


import java.awt.Graphics2D;

import GameStates.GameState;
import GameStates.MenuState;
import GameStates.PlayState;

public class GameStateManager {
	
	// private boolean paused;
	// private PauseState pauseState;
	
	private GameState[] gameStates;
	private int currentState;
	private int previousState;
	
	public static final int NUM_STATES = 3;
	// public static final int INTRO = 0;
	public static final int MENU = 0;
	public static final int PLAY1 = 1;
	public static final int PLAY2 = 2;
	// public static final int GAMEOVER = 3;
	
	public GameStateManager() {
		
		JukeBox.init();
		
		// paused = false;
		// pauseState = new PauseState(this);
		
		gameStates = new GameState[NUM_STATES];
		
		setState(MENU);
		
	}
	public void loadState(int state) {
		if (gameStates[state] ==  null) {
			setState(state) ;
		}
		else { currentState = state; }
	}
		
	public int getState() { return currentState; }
	
	public void setState(int state) {
		//previousState = currentState;
		//unloadState(previousState);
		currentState = state; 

		if (state == MENU) {
			gameStates[state] = new MenuState(this);
			gameStates[state].init();
		}
		else if (state == PLAY1) {
			gameStates[state] = new PlayState(this);
			gameStates[state].init();
		}

	}
	
	public void unloadState(int state) {
		gameStates[state] = null;
	} 
	
	/* public void setPaused(boolean b) {
		paused = b;
	} */
	
	public void update() {
		//if(paused) {
		//	pauseState.update();
		//}
		if(gameStates[currentState] != null) {
			gameStates[currentState].update();
		}
	}
	
	public void draw(Graphics2D g) {
		//if(paused) {
		//	pauseState.draw(g);
		//}
		if(gameStates[currentState] != null) {
			gameStates[currentState].draw(g);
		}
	}
	
}
