package com.example.jbilliard.model;

import java.util.Observable;

/**
 * @AUTHOR = Bardia
 * @VERSION = 1.0
 *
 *  Model class the sets the status for the EndScreen to pass on to the observing view.
 */

public class EndScreenStatus extends Observable{

	private String gameOverMessage;
	
	public void gameOverSuccess(int currPlayerInd) {
    	int playerNum = currPlayerInd + 1;
		gameOverMessage = "Player " + playerNum + " successfully"
				+ "pocketed the eight ball. Congratulations,"
				+ "you win!";

    	setChanged();
    	notifyObservers(); 
	}
	
	public void gameOverFail(int currPlayerInd) {
    	int playerNum = currPlayerInd + 1;
    	int otherPlayerNum = ((playerNum%2)+1);
		gameOverMessage = "Oops! Player " + playerNum + 
				" prematurely pocketed the eight ball. "
				+ "Congratulations, Player " + otherPlayerNum +", you win!";

    	setChanged();
    	notifyObservers();
	}
	
	public String getGameOverMessage() { return gameOverMessage; }
}
