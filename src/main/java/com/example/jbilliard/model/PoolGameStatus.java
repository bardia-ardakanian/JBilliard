package com.example.jbilliard.model;

import java.util.Observable;

/**
 * @AUTHOR = Bardia
 * @VERSION = 1.0
 *
 * Model class that stores the status of the pool game after each turn.
 */
public class PoolGameStatus extends Observable {
	private String player1PointsStatus = "0";
	private String player2PointsStatus = "0";
    private String player1BallColorStatus = "N/A";
    private String player2BallColorStatus = "N/A";
    private String turnStatus;
    private String lastTurnStatus;
    private String cueBallStatus;
    public PoolGameStatus() {
    	turnStatus = "Player 1, your turn!";
    	lastTurnStatus = "Click and drag the cue stick to take a shot"
    			+ " at the cue ball.";
    }

    public void setPoints(int player1Points, int player2Points) {
    	this.player1PointsStatus = player1Points + "";
    	this.player2PointsStatus = player2Points + "";
    	
    	setChanged();
    	notifyObservers();
    }
    
    public void setBallColors(int currPlayerInd, int player1ID) {
    	if ((player1ID + currPlayerInd)%2 == 0 ) {
    		player1BallColorStatus = "Ball color: RED";
    		player2BallColorStatus = "Ball color: BLUE";
    	}
    	else{
    		player1BallColorStatus = "Ball color: BLUE";
    		player2BallColorStatus = "Ball color: RED";
    	}
    	
    	setChanged();
    	notifyObservers();
    }
    
    public void setTurnStatus(int currPlayerInd, boolean streak,
    		boolean canPocketEightBall) {
    	
    	int playerNum = currPlayerInd + 1;
   
    	String streakText = "";
    	if (streak) {
    		streakText = " again";
    	}
    	
    	String eightBallPrompt = "";
    	if (canPocketEightBall) eightBallPrompt = " You may now pocket the "
    			+ "eight ball to win.";
    	
    	turnStatus = "Player " + playerNum + ", "
    			+ "your turn" + streakText + "!" + eightBallPrompt;
    	
    	setChanged();
    	notifyObservers();
    }
    
    public void setLastTurnStatusPlayerFailed(int currPlayerInd) {
    	int playerNum = currPlayerInd + 1;
    	lastTurnStatus = "Player " + playerNum + " did not "
    			+ "pocket a ball.";
    	setChanged();
    	notifyObservers();
    }
    
	public void setLastTurnStatusPlayerIllegalBreak(int currPlayerInd) {
    	int playerNum = currPlayerInd + 1;
    	lastTurnStatus = "Player " + playerNum + " did not "
    			+ "get four bumper collisions "
    			+ "- Illegal break.";
    	
    	setChanged();
    	notifyObservers();
    }

    public void setLastTurnStatusPlayerSucceeded(int currPlayerInd) {
    	int playerNum = currPlayerInd + 1;
    	lastTurnStatus = "Player " + playerNum + " has pocketed "
    			+ "a ball.";
    	
    	setChanged();
    	notifyObservers();
    }
    
    public void setLastTurnStatusPocketedCueBall(int currPlayerInd) {
    	int playerNum = currPlayerInd + 1;
    	lastTurnStatus = "Player " + playerNum + " pocketed "
    			+ "the cue ball. Scratch!";
    	setChanged();
    	notifyObservers();
    }
    
    public void setLastTurnStatusPocketedOther(int currPlayerInd) {
    	int playerNum = currPlayerInd + 1;
    	int otherPlayerNum = (currPlayerInd+1)%2 + 1;
    	lastTurnStatus = "Player " + playerNum + " pocketed "
    			+ "Player " + otherPlayerNum + "'s ball. Lost turn!";
    	setChanged();
    	notifyObservers();
    }
    
    public void setCueBallStatusForScratch(){
    	cueBallStatus = "Place the cue ball anywhere inside the orange region"
    			+ " before hitting.";
    	setChanged();
    	notifyObservers();
    }
    
    public void unsetCueBallStatus() {
    	cueBallStatus = "";
    	setChanged();
    	notifyObservers();
    }
    
    public String getPlayer1PointsStatus() { return player1PointsStatus; }
    
    public String getPlayer2PointsStatus() { return player2PointsStatus; }
    
    public String getPlayer1BallColorStatus() { return player1BallColorStatus; }
    
    public String getPlayer2BallColorStatus() { return player2BallColorStatus; }
    
    public String getTurnStatus() { return turnStatus; }
    
    public String getLastTurnStatus() { return lastTurnStatus; }
    
    public String getCueBallStatus() { return cueBallStatus; }
}
