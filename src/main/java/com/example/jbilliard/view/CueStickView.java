package com.example.jbilliard.view;

import java.util.Observable;
import java.util.Observer;
import com.example.jbilliard.GameConstants;
import com.example.jbilliard.model.CueStick;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 * @AUTHOR = Kimia
 * @VERSION = 1.0
 *
 *  View class for the CueStick.
 */

public class CueStickView implements Observer{
	private final Line line;
	private double startX;
	private double startY;
	private double endX;
	private double endY;
	private final CueStick cueStick;

	public CueStickView(CueStick cueStick) {
		this.cueStick = cueStick;
		this.startX = cueStick.getStartX() * GameConstants.IN_TO_PIXEL;
		this.startY = cueStick.getStartY() *GameConstants.IN_TO_PIXEL;
		this.endX = cueStick.getEndX() *GameConstants.IN_TO_PIXEL;
		this.endY = cueStick.getEndY() *GameConstants.IN_TO_PIXEL;

		line = new Line(startX, startY, endX, endY);
		line.setStrokeWidth(6.0);
		line.setStroke(Color.BROWN);
	}

	public void setStartX(double startX) {
		this.startX = startX*GameConstants.IN_TO_PIXEL;
		line.setStartX(this.startX);
	}

	public void setStartY(double startY) {
		this.startY = startY*GameConstants.IN_TO_PIXEL;
		line.setStartY(this.startY);
	}

	public void setEndX(double endX) {
		this.endX = endX*GameConstants.IN_TO_PIXEL;
		line.setEndX(this.endX);
	}

	public void setEndY(double endY) {
		this.endY = endY * GameConstants.IN_TO_PIXEL;
		line.setEndY(this.endY);
	}
	
	public Shape getLine() {return line; }

	public void update(Observable o, Object arg) {
		if (o == cueStick) {
			setStartX(cueStick.getStartX());
			setEndX(cueStick.getEndX());
			setStartY(cueStick.getStartY());
			setEndY(cueStick.getEndY());

			if (!cueStick.isDragging()) {
				this.getLine().setStroke(Color.BROWN);
			}

			else {
				int changeFactor = 2000;
				int k = (int) (changeFactor/cueStick.getDistanceInitToMouse());
				int maxValue = 255;
				if ( k > maxValue) { k = maxValue; }
				
				this.getLine().setStroke(Color.rgb((int)(140+.45*k), k, 0));
			}
		}
	}
	
	public CueStick getCueStick() {
		return cueStick;
	}
}
