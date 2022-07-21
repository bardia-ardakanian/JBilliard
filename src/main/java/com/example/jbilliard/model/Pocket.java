package com.example.jbilliard.model;

import com.example.jbilliard.GameConstants;

/**
 * @AUTHOR = Bardia
 * @VERSION = 1.0
 *
 *  Model class for a pocket.
 */

public class Pocket {
	
	private double xPosition;
	private double yPosition;
	private final double maxX;
	private final double maxY;
	private final double minX;
	private final double minY;

	public Pocket(int id, double boardX, double boardY) {
		minX = boardX;
		minY = boardY;
		maxX = minX + GameConstants.POOL_TABLE_LENGTH;
		maxY = minY +  GameConstants.POOL_TABLE_WIDTH;
		setPocketLocation(id);
	}
	
	private void setPocketLocation(int id) {
		switch (id) {
			case 0 -> {
				xPosition = minX;
				yPosition = minY;
			}
			case 1 -> {
				xPosition = (minX + maxX) / 2;
				yPosition = minY;
			}
			case 2 -> {
				xPosition = maxX;
				yPosition = minY;
			}
			case 3 -> {
				xPosition = minX;
				yPosition = maxY;
			}
			case 4 -> {
				xPosition = (minX + maxX) / 2;
				yPosition = maxY;
			}
			case 5 -> {
				xPosition = maxX;
				yPosition = maxY;
			}
			default -> {
				xPosition = 0;
				yPosition = 0;
			}
		}
	}
	
	public double getXPosition() { return xPosition; }
	
	public double getYPosition() { return yPosition; }
}
