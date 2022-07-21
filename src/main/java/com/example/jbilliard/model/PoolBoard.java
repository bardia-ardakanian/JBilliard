package com.example.jbilliard.model;

import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import com.example.jbilliard.GameConstants;

/**
 * @AUTHOR = Bardia
 * @VERSION = 1.0
 *
 * Model class for a pool board, including interactions between the pool balls, cues tick, and pockets.
 */

public class PoolBoard extends Observable {

	private final Ball[] balls = new Ball[16];
	private ArrayList<Ball> pocketedBalls = new ArrayList<>();
	private final ArrayList<Ball> remainingBalls = new ArrayList<>();
	private final Pocket[] pockets = new Pocket[6];
	private final CueStick cueStick;
	private final double boardX = GameConstants.POOL_BOARD_X
			* GameConstants.PIXEL_TO_IN;
	private final double boardY = GameConstants.POOL_BOARD_Y
			* GameConstants.PIXEL_TO_IN;
	private final double length = GameConstants.POOL_TABLE_LENGTH;
	private final double width = GameConstants.POOL_TABLE_WIDTH;
	private int collisionCount;
	private boolean resetCue;

	public PoolBoard() {
		for (int i = 0; i < pockets.length; i++) {
			pockets[i] = new Pocket(i, boardX, boardY);
		}

		setUpBalls();
		cueStick = new CueStick(balls[15]);

		this.collisionCount = 0;
	}

	private void setUpBalls() {
		for (int k = 0; k < balls.length; k ++) {
			if (k < 7) { balls[k] = new Ball(0); }
			else if (k < 14) { balls[k] = new Ball(1); }
			else if (k == 14) { balls[k] = new Ball(3); }
			else { balls[k] = new Ball(2); }
		}

		rackBalls(balls);
		Collections.addAll(remainingBalls, balls);
	}

	public void rackBalls(Ball[] balls) {
		double centerY = width / 2 + boardY;
		double incrementX = 2.25 * Math.cos(30) * GameConstants.IN_TO_PIXEL;
		double radius = 1.125;
		double threeQuartersLength = 0.75 * length + boardX;

		balls[0].setCenter(threeQuartersLength, centerY);
		balls[1].setCenter(threeQuartersLength + 1 * incrementX, centerY + radius);
		balls[2].setCenter(threeQuartersLength + 2 * incrementX, centerY - 2 * radius);
		balls[3].setCenter(threeQuartersLength + 3 * incrementX, centerY + 3 * radius);
		balls[4].setCenter(threeQuartersLength + 3 * incrementX, centerY - radius);
		balls[5].setCenter(threeQuartersLength + 4 * incrementX, centerY + 2 * radius);
		balls[6].setCenter(threeQuartersLength + 4 * incrementX, centerY - 4 * radius);
		balls[7].setCenter(threeQuartersLength + 1 * incrementX, centerY - radius);
		balls[8].setCenter(threeQuartersLength + 2 * incrementX, centerY + 2 * radius);
		balls[9].setCenter(threeQuartersLength + 3 * incrementX, centerY + radius);
		balls[10].setCenter(threeQuartersLength + 3 * incrementX, centerY - 3 * radius);
		balls[11].setCenter(threeQuartersLength + 4 * incrementX, centerY + 4 * radius);
		balls[12].setCenter(threeQuartersLength + 4 * incrementX, centerY);
		balls[13].setCenter(threeQuartersLength + 4 * incrementX, centerY - 2 * radius);
		balls[14].setCenter(threeQuartersLength + 2 * incrementX, centerY);

		balls[15].setCenter(length * 1/4 + boardX, width / 2 + boardY);
		collisionCount = 0;
	}

	public void update() {
		double elapsedSeconds = 0.01;
		for (Ball b : remainingBalls) {
			b.setCenterX(b.getCenterX() + elapsedSeconds * b.getXVelocity());
			b.setCenterY(b.getCenterY() + elapsedSeconds * b.getYVelocity());
		}

		checkPockets();
		checkCollisions();
		decelerateBalls();
	}

	public boolean stable() {
		boolean isStable = true;
		for (Ball ball: remainingBalls) {
			if (ball.getXVelocity() != 0 || ball.getYVelocity() != 0) {
				isStable = false;
				break;
			}
		}
		return isStable;
	}

	public void resetCueBall() {
		pocketedBalls.remove(balls[15]);
		remainingBalls.add(balls[15]);
		balls[15].unPocket();
		balls[15].setCenter(length * 0.25 + boardX, width / 2 + boardY);
		resetCue = true;

		setChanged();
		notifyObservers();

		resetCue = false;
	}

	private void checkPockets() {
		for (Pocket pocket: pockets) {
			for (Ball ball: balls) {
				double distance = Math.sqrt(Math.pow(pocket.getXPosition() - ball.getCenterX(), 2) + Math.pow(pocket.getYPosition() - ball.getCenterY(), 2));
				if(distance <= GameConstants.POCKET_RADIUS && !ball.isPocketed()) {
					ball.setPocketed();
					pocketedBalls.add(ball);
					remainingBalls.remove(ball);
				}
			}
		}
	}

	private void checkCollisions() {
		for (Ball ball: remainingBalls)
		{
			if ((ball.getCenterX() - GameConstants.BALL_RADIUS <= boardX && ball.getXVelocity() < 0)
					|| (ball.getCenterX() + GameConstants.BALL_RADIUS >= length + boardX && ball.getXVelocity() > 0)) {
				ball.setXVelocity(ball.getXVelocity()*(-1));
				collisionCount++;
			}
			if ((ball.getCenterY() - GameConstants.BALL_RADIUS <= boardY && ball.getYVelocity() < 0)
					|| (ball.getCenterY() + GameConstants.BALL_RADIUS >= width + boardY & ball.getYVelocity() > 0)) {
				ball.setYVelocity(ball.getYVelocity()*(-1));
				collisionCount++;
			}
			for (Ball b2: remainingBalls) {
				final double deltaX = b2.getCenterX() - ball.getCenterX() ;
				final double deltaY = b2.getCenterY() - ball.getCenterY() ;

				if (colliding(ball, b2, deltaX, deltaY)) {
					bounce(ball, b2, deltaX, deltaY);
					setChanged();
					notifyObservers();
				}
			}
		}
	}

	private void decelerateBalls() {
		double elapsedSeconds = 0.1;

		for (Ball ball: remainingBalls) {
			double xVel = ball.getXVelocity();
			double yVel = ball.getYVelocity();
			double speed = sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));

			if (xVel != 0 || yVel != 0) {
				double v = xVel - GameConstants.MU_K * elapsedSeconds * xVel / speed;
				double v1 = yVel - GameConstants.MU_K * elapsedSeconds * yVel / speed;

				if (xVel < 0) {
					ball.setXVelocity(Math.min(v, 0));
				}
				if (yVel < 0) {
					ball.setYVelocity(Math.min(v1, 0));
				}
				if (xVel > 0) {
					ball.setXVelocity(Math.max(v, 0));
				}
				if (yVel > 0) {
					ball.setYVelocity(Math.max(v1, 0));
				}
			}
		}
	}

	public boolean colliding(final Ball b1, final Ball b2, final double deltaX, final double deltaY) {
		final double radiusSum = 2*GameConstants.BALL_RADIUS;

		if (deltaX * deltaX + deltaY * deltaY <= radiusSum * radiusSum) {
			return deltaX * (b2.getXVelocity() - b1.getXVelocity())
					+ deltaY * (b2.getYVelocity() - b1.getYVelocity()) < 0;
		}

		return false;
	}

	private void bounce(final Ball b1, final Ball b2, final double deltaX, final double deltaY) {
		double distance = sqrt(deltaX * deltaX + deltaY * deltaY);
		double unitContactX = deltaX / distance;
		double unitContactY = deltaY / distance;
		double b1_i = b1.getXVelocity()*unitContactX + b1.getYVelocity()* unitContactY;
		double b2_i = b2.getXVelocity()*unitContactX + b2.getYVelocity()* unitContactY;

		b1.setXVelocity(b1.getXVelocity() +  unitContactX*(b2_i - b1_i));
		b1.setYVelocity(b1.getYVelocity() +  unitContactY*(b2_i - b1_i));
		b2.setXVelocity(b2.getXVelocity() +  unitContactX*(b1_i - b2_i));
		b2.setYVelocity(b2.getYVelocity() +  unitContactY*(b1_i - b2_i));
	}

	public void resetPocketedBalls() {
		pocketedBalls = new ArrayList<>();
	}
	public boolean getResetCue() { return resetCue; }
	public Ball[] getBalls() { return balls; }
	public ArrayList<Ball> pocketedBalls() { return pocketedBalls; }
	public Pocket[] getPockets() { return pockets; }
	public CueStick getCueStick() { return cueStick; }
	public int getCollisionCount() { return collisionCount; }
	
}
