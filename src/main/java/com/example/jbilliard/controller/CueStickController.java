package com.example.jbilliard.controller;

import com.example.jbilliard.GameConstants;
import com.example.jbilliard.model.CueStick;
import com.example.jbilliard.view.CueStickView;
import com.example.jbilliard.view.PoolBoardView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * @AUTHOR = Bardia
 * @VERSION = 1.0
 *
 *  This class is the controller for the CueStick.
 */

public class CueStickController {

	private double mouseX;
	private double mouseY;
	private boolean isMousePressed = false;
	private boolean hasJustDragged = false;
	private final CueStickView cueStickView;
	private final CueStick cueStick;
	
	public CueStickController(CueStickView cueStickView){
		this.cueStickView = cueStickView;
		this.cueStick = cueStickView.getCueStick();
	}

	public void addMouseHoverEH(PoolBoardView pbv) {
		Rectangle r = pbv.getCueStickRectangle();
		r.setOnMouseMoved(lambda -> {
			if (!isMousePressed && cueStick.canMove()) {
				mouseX = lambda.getX()*GameConstants.PIXEL_TO_IN;
				mouseY = lambda.getY()*GameConstants.PIXEL_TO_IN;
				cueStick.hover(mouseX, mouseY);
			}
		});
	}
	
	public void addMousePressedEH(PoolBoardView pbv) {
		Line l = (Line) cueStickView.getLine();
		l.setOnMousePressed(lambda -> {
			if (cueStick.canMove()) {
				isMousePressed = true;
			}
		});
		Rectangle r = pbv.getCueStickRectangle();
		r.setOnMousePressed(lambda -> {
			if (cueStick.canMove()) {
				isMousePressed = true;
			}
		});
	}
	
	public void addMouseDraggedEH(PoolBoardView pbv) {
	    Line l = (Line) cueStickView.getLine();
		l.setOnMouseDragged(this::mouseDragged);
		Rectangle r = pbv.getCueStickRectangle();
		r.setOnMouseDragged(this::mouseDragged);
	}

	private void mouseDragged(MouseEvent lambda) {
		if (cueStick.canMove()) {
			double initMouseX = mouseX;
			double initMouseY = mouseY;
			cueStick.initValues(initMouseX, initMouseY);

			double endMouseX = lambda.getX() * GameConstants.PIXEL_TO_IN;
			double endMouseY = lambda.getY() * GameConstants.PIXEL_TO_IN;
			cueStick.drag(endMouseX, endMouseY);

			hasJustDragged = true;
		}
	}

	public void addMouseReleasedEH(PoolBoardView pbv) {
	  Line l = (Line) cueStickView.getLine();
	  l.setOnMouseReleased(this::mouseReleased);

	  Rectangle r = pbv.getCueStickRectangle();
	  r.setOnMouseReleased(this::mouseReleased);
	}

	private void mouseReleased(MouseEvent lambda) {
		if (hasJustDragged) {
			double finalMouseX = lambda.getX()* GameConstants.PIXEL_TO_IN;
			double finalMouseY = lambda.getY()*GameConstants.PIXEL_TO_IN;

			cueStick.hit();
			cueStick.update(finalMouseX, finalMouseY);

			cueStick.setCanMove(false);
			hasJustDragged = false;
		}
		isMousePressed = false;
	}
}
