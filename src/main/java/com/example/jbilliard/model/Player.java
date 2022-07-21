package com.example.jbilliard.model;

/**
 * @AUTHOR = Kimia
 * @VERSION = 1.0
 *
 *  Model class for a player. Stores the player's ball type and points.
 */

public class Player {
	 
	int ballType;
	int points = 0;
	public Player() {
	    ballType = -1;
	}
	public boolean canPocketEightBall() { 
		return (points == 7);
	}
	public void addPoint() { 
		points += 1; 
	}
	public void setBallType(int ballType) { this.ballType = ballType; }
	public int getBallType() { return ballType; }
	public int getPoints() { return points; }
	
}

