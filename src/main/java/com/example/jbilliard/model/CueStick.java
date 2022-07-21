package com.example.jbilliard.model;

import java.util.Observable;

/**
 * @AUTHOR = Bardia
 * @VERSION = 1.0
 *
 *  Model class for a cue stick.
 */

public class CueStick extends Observable {
	
	private double startX;
	private double startY;
	private double endX;
	private double endY;
	private double initStartX;
	private double initStartY;
	private double initEndX;
	private double initEndY;
	private double initMouseX;
	private double initMouseY;
	private double distanceInit;
	private static double mouseDist;
	private boolean canReset = true;
	private boolean canMove = true;
	private boolean isDragging = false;
	private boolean hasHit = false;
	private final double cueBallDist = 3.0;
	private final double cueLen = 37.0;
	private final Ball cueBall;
	
	public CueStick(Ball cueBall) {
		this.cueBall = cueBall;
		this.startX = cueBall.getCenterX() - this.cueBallDist;
		this.startY = cueBall.getCenterY();
		this.endX = this.startX - this.cueLen;
		this.endY = this.startY;
	}

	public void update(double finalMouseX, double finalMouseY) {
		
		double amplifier = 3;
		dist(finalMouseX, finalMouseY);
		
		double dirX = -initStartX + cueBall.getCenterX();
		double dirY = -initStartY + cueBall.getCenterY();
		
		double xVel = amplifier* mouseDist *dirX;
		double yVel = amplifier* mouseDist *dirY;
		
		cueBall.setXVelocity(xVel);
		cueBall.setYVelocity(yVel);
		
		mouseDist = 0;
		
		hasHit = true;
		setChanged();
		notifyObservers();
		hasHit = false;
	}
	
	private double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}

	private boolean isCorrect(double mouseX, double mouseY) {
		double cueBallX = cueBall.getCenterX();
		double cueBallY = cueBall.getCenterY();
		
		boolean correctDir = true;
		if (cueBallX > initMouseX) {
			if ((cueBallY > initMouseY) && (mouseX >= initMouseX && mouseY >= initMouseY)) {
				correctDir = false;
			}
			else if ((cueBallY < initMouseY) && (mouseX >= initMouseX && mouseY <= initMouseY)) {
				correctDir = false;
			}
		}
		else { 
			if ((cueBallY > initMouseY) && (mouseX <= initMouseX && mouseY >= initMouseY)) {
				correctDir = false;
			}
			else if ((cueBallY < initMouseY) && (mouseX <= initMouseX && mouseY <= initMouseY)) {
				correctDir = false;
			}
		}

		return correctDir;
	 }
	
	public void initValues(double initMouseX, double initMouseY) {
		if (canReset) {
			initStartX = this.getStartX();
			initStartY = this.getStartY();
			initEndX = this.getEndX();
			initEndY = this.getEndY();
			this.initMouseX =  initMouseX;
			this.initMouseY = initMouseY;
			
			distanceInit = Math.abs(
					(1 / cueLen) * (
							(initMouseX - initStartX) *
							(initEndX - initStartX) +
							(initMouseY - initStartY) *
							(initEndY - initStartY)
					));
		}
		canReset = false;
	}
	
	private void dist(double mouseX, double mouseY) {
		double dist = Math.abs((1 / cueLen)*((mouseX -
				initStartX)*(initEndX - initStartX) + (mouseY - initStartY)
				*(initEndY - initStartY)));
		
		double distInit = dist - distanceInit;
		if (!isCorrect(mouseX, mouseY)) {
			distInit = 0;
		}
		
		double stretchLimit = 17.0;
		if (distInit > stretchLimit) {
 			distInit = stretchLimit;
 		}
 		
		CueStick.mouseDist = Math.abs(distInit);
		
		setChanged();
		notifyObservers();
	}

	private void location(double distanceTipFromCueBall, double x, double y) {
		double cueBallX = cueBall.getCenterX();
		double cueBallY = cueBall.getCenterY();
		
		double ballToMouseDist = getDistance(cueBallX, cueBallY, x, y);
		double CueBallDist = distanceTipFromCueBall + cueLen;
		
		startX = (distanceTipFromCueBall / ballToMouseDist) * (x -
				cueBallX) + cueBallX;
		startY = (distanceTipFromCueBall / ballToMouseDist) * (y -
				cueBallY) + cueBallY;
		endX = (CueBallDist / ballToMouseDist) * (x -
				cueBallX) + cueBallX;
		endY = (CueBallDist / ballToMouseDist) * (y -
				cueBallY) + cueBallY;
		
		this.setStartX(startX);
		this.setStartY(startY);
		this.setEndX(endX);
		this.setEndY(endY);
		
		setChanged();
		notifyObservers();
	}
	
	public void hover(double mouseX, double mouseY) {
		location(cueBallDist, mouseX, mouseY);
	}
	
	public void drag(double mouseX, double mouseY) {
		isDragging = true;
		dist(mouseX, mouseY);

 		double newDistanceTipFromCueBall = cueBallDist +
				mouseDist;
 		location(newDistanceTipFromCueBall, initStartX,
				initStartY);
	}
			
	public void hit() {
		isDragging = false;
		location(cueBallDist, initStartX, initStartY);
	}
	
	public double getStartX() { return startX; }
	
	public double getStartY() { return startY; }
	
	public double getEndX() { return endX; }
	
	public double getEndY() { return endY; }

	public double getDistanceInitToMouse() { return mouseDist; }

	public boolean hasHit(){ return hasHit; }
	
	public boolean canMove() {	return canMove; }

	public boolean isDragging(){ return isDragging; }

	public void setStartX(double startX) { this.startX = startX; }
	
	public void setStartY(double startY) { this.startY = startY; }
	
	public void setEndX(double endX) { this.endX = endX; }
	
	public void setEndY(double endY) { this.endY = endY; }
	
	public void setCanMove(boolean canMove) { this.canMove = canMove; }

	public void setCanReset(boolean canReset) { this.canReset = canReset; }
	
}