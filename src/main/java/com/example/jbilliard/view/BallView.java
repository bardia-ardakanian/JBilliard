package com.example.jbilliard.view;

import java.util.Observable;
import java.util.Observer;
import com.example.jbilliard.GameConstants;
import com.example.jbilliard.model.Ball;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 * @AUTHOR = Bardia
 * @VERSION = 1.0
 *
 *  View class for the ball.
 */

public class BallView implements Observer {
	private final Circle circle;
	private double centerX;
	private double centerY;

	private final Ball ball;

	public BallView(Ball ball) {
		int id = ball.getId();
		this.ball = ball;

		double centerX_inches = ball.getCenterX();
		double centerY_inches = ball.getCenterY();
		double radius_inches = GameConstants.BALL_RADIUS;

		this.centerX = centerX_inches * GameConstants.IN_TO_PIXEL;
		this.centerY = centerY_inches * GameConstants.IN_TO_PIXEL;
		double radius = radius_inches * GameConstants.IN_TO_PIXEL;

		circle = new Circle(centerX, centerY, radius);
		circle.setFill(ball.getColor());
	}

	public void setCenterX() { 
		this.centerX = ball.getCenterX() * GameConstants.IN_TO_PIXEL; 
		circle.setCenterX(centerX);
	}

	public void setCenterY() {
		this.centerY = ball.getCenterY() * GameConstants.IN_TO_PIXEL;
		circle.setCenterY(centerY);
	}
	
	public void remove() {
		Pane parentNode = (Pane) circle.getParent();
		parentNode.getChildren().remove(circle);
	}

	public Shape getCircle() {return circle; }

	public void update(Observable o, Object arg) {
		if (ball == o) {
			setCenterX();
			setCenterY();
			if (ball.isPocketed()) {
				this.remove();
			}
		}

	}
}
