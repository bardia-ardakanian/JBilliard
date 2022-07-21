package com.example.jbilliard.controller;

import com.example.jbilliard.GameConstants;
import com.example.jbilliard.model.Ball;
import com.example.jbilliard.view.PoolBoardView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * @AUTHOR = Kimia
 * @VERSION = 1.0
 *
 *  This class is the controller for the CueBall.
 */

public class CueBallController {
	private final boolean isMousePressed = false;
	private double mouseX;
	private double mouseY;
	public void addMousePressedEventHandler(PoolBoardView pbv, Ball cueBall) {
		Rectangle r = pbv.getScratchRectangle();
		Circle c = (Circle) pbv.getBallViews()[15].getCircle();
		c.setOnMouseClicked(lambda -> {
			boolean isNotOverlapping = true;

			for (Ball b: pbv.getPoolBoard().getBalls()){
				double radiusSum = GameConstants.BALL_RADIUS + GameConstants.BALL_RADIUS;
				double deltaX = cueBall.getCenterX() - b.getCenterX();
				double deltaY = cueBall.getCenterY() - b.getCenterY();

				if (b != cueBall && (deltaX * deltaX +
						deltaY * deltaY < radiusSum * radiusSum)) {
					isNotOverlapping = false;
					break;
				}
			}

			if (isNotOverlapping){
				cueBall.setCenterX(mouseX);
				cueBall.setCenterY(mouseY);
				r.setOnMouseMoved(null);
				c.setOnMousePressed(null);
				r.toBack();
				pbv.getCueStickView().getLine().setVisible(true);
				pbv.setCueStickHandlers();
			}
		});
	}

	public void addMouseHoverEventHandler(PoolBoardView pbv, Ball cueBall) {
		pbv.getScratchRectangle().toFront();
		Rectangle r = pbv.getScratchRectangle();
		r.setFill(Color.ORANGE);
		pbv.bringElementsToFront();
		pbv.removeCueStickHandlers();
		pbv.getCueStickView().getLine().setVisible(false);
		
		r.setOnMouseMoved(lambda -> {
			if (!isMousePressed) {
				mouseX = lambda.getX()*GameConstants.PIXEL_TO_IN;
				mouseY = lambda.getY()*GameConstants.PIXEL_TO_IN;
				cueBall.setCenterX(mouseX);
				cueBall.setCenterY(mouseY);
			}
		});
	}
}
