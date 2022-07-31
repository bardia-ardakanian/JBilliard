package com.example.jbilliard.model;

import javafx.scene.paint.Color;
import java.io.Serializable;
import java.util.Observable;

/**
 * @AUTHOR = Bardia
 * @VERSION = 1.0
 *
 * Model class for a pool ball.
 *
 * colors: {
 *     COLOR,
 *     WHITE,
 *     BLACK,
 * }
 */
public class Ball extends Observable implements Serializable {
	private final int id;
	private double centerX;
	private double centerY;
	private double xVelocity;
	private double yVelocity;
	private boolean isPocketed;
	private final Color color;
	private final int point;

	public Ball(int id, Color color, int point) {
		this.centerX = 0;
		this.centerY = 0;
		this.xVelocity = 0;
		this.yVelocity = 0;
		this.id = id;
		this.isPocketed = false;
		this.color = color;
		this.point = point;
	}

	public Ball(int id, double centerX, double centerY, Color color, int point) {
		this.id = id;
		this.centerX = centerX;
		this.centerY = centerY;
		this.xVelocity = 0;
		this.yVelocity = 0;
		this.isPocketed = false;
		this.color = color;
		this.point = point;
	}
	
	public void unPocket() {
		isPocketed = false;
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;

		setChanged();
		notifyObservers();
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;

		setChanged();
		notifyObservers();
	}

	public void setCenter(double centerX, double centerY) {
		this.centerX = centerX;
		this.centerY = centerY;

		setChanged();
		notifyObservers();
	}
	
	public void setXVelocity(double xVel) { 
		xVelocity = xVel;

		setChanged();
		notifyObservers();
	}
	
	public void setYVelocity(double yVel) { 
		yVelocity = yVel;

		setChanged();
		notifyObservers();
	}
	
	// Pockets a ball.
	public void setPocketed() {
		isPocketed = true;
		xVelocity = 0;
		yVelocity = 0;
		centerX = 0;
		centerY = 0;

		setChanged();
		notifyObservers();
	}
	
	public int getId() { return id;}
	public double getCenterX() { return centerX; }
	public double getCenterY() { return centerY; }
	public double getXVelocity() { return xVelocity; }
	public double getYVelocity() { return yVelocity; }
	public boolean isPocketed() { return isPocketed; }
	public Color getColor() {
		return color;
	}
	public int getPoint() {
		return point;
	}
}
