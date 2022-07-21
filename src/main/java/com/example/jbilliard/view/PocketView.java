package com.example.jbilliard.view;

import com.example.jbilliard.GameConstants;
import com.example.jbilliard.model.Pocket;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @AUTHOR = Kimia
 * @VERSION = 1.0
 *
 *  View class for a pocket.
 */

public class PocketView {
	private final Circle shape;
	
	public PocketView(Pocket pocket) {
		double x = pocket.getXPosition();
		double y = pocket.getYPosition();
		double radius = GameConstants.POCKET_RADIUS;

		x = x * GameConstants.IN_TO_PIXEL;
		y = y * GameConstants.IN_TO_PIXEL;
		radius = radius * GameConstants.IN_TO_PIXEL;

		shape = new Circle(x, y, radius);
		Color lightBrown = Color.web("0x665847");
		shape.setFill(lightBrown);
	}
	
	public Circle getCircle() { return shape; }
}
