package com.example.jbilliard.view;

import java.util.Observable;
import java.util.Observer;
import com.example.jbilliard.GameConstants;
import com.example.jbilliard.controller.CueBallController;
import com.example.jbilliard.controller.CueStickController;
import com.example.jbilliard.model.Ball;
import com.example.jbilliard.model.CueStick;
import com.example.jbilliard.model.Pocket;
import com.example.jbilliard.model.PoolBoard;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @AUTHOR = Kimia
 * @VERSION = 1.0
 *
 *  View class for a pool board and its elements (balls, cue stick, and pockets)
 */

public class PoolBoardView implements Observer {
	private final Pane view;
	double length;
	double width;
	private final Rectangle cueStickRectangle;
	private final Rectangle scratchRectangle;
	private final PoolBoard poolBoard;
	private BallView[] ballViews;
	private CueStickView cueStickView;

	public PoolBoardView(PoolBoard poolBoard) {
		this.poolBoard = poolBoard;
		view = new Pane();

		double xMargin = 50;
		double yMargin = 50;
		this.length = GameConstants.POOL_TABLE_LENGTH*GameConstants.IN_TO_PIXEL;
		this.width = GameConstants.POOL_TABLE_WIDTH*GameConstants.IN_TO_PIXEL;

		Color brown = Color.web("0x3D362D");
		Rectangle bigRectangle = new Rectangle(xMargin, yMargin, this.length, this.width);

		double x_scale_multiplier = 1.09;
		double y_scale_multiplier = 1.157;

		bigRectangle.setScaleX(x_scale_multiplier);
		bigRectangle.setScaleY(y_scale_multiplier);
		bigRectangle.setFill(brown);
		view.getChildren().add(bigRectangle);

		Color green = Color.web("0x27AE60");
		Rectangle rectangle = new Rectangle(xMargin, yMargin, this.length, this.width);
		rectangle.setFill(green);
		view.getChildren().add(rectangle);

		rectangle.setX(GameConstants.POOL_BOARD_X);
		rectangle.setY(GameConstants.POOL_BOARD_Y);

		bigRectangle.setX(GameConstants.POOL_BOARD_X);
		bigRectangle.setY(GameConstants.POOL_BOARD_Y);

		double addLength = 2000;
		double addWidth = 2000;

		cueStickRectangle = new Rectangle(0, 0, this.length + addLength, this.width + addWidth);
		cueStickRectangle.setFill(Color.TRANSPARENT);
		view.getChildren().add(cueStickRectangle);

		double ballRadius = GameConstants.BALL_RADIUS * GameConstants.IN_TO_PIXEL;
		scratchRectangle = new Rectangle(xMargin, yMargin, this.length / 4.0, this.width - 2 * ballRadius);
		scratchRectangle.setX(GameConstants.POOL_BOARD_X + ballRadius);
		scratchRectangle.setY(GameConstants.POOL_BOARD_Y + ballRadius);
		scratchRectangle.setFill(Color.TRANSPARENT);
		view.getChildren().add(scratchRectangle);
		scratchRectangle.toBack();

		initElements();
		bringElementsToFront();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o == poolBoard) {
			if (poolBoard.getResetCue()) {
				view.getChildren().add(ballViews[15].getCircle());
				initCueBallController();
			}
		}
	}
	
	public void initElements() {
		Ball[] balls = poolBoard.getBalls();
		ballViews = new BallView[16];

		for (int i = 0; i < 16; i ++) {
			ballViews[i] = new BallView(balls[i]);
			balls[i].addObserver(ballViews[i]);
			this.getPane().getChildren().add(ballViews[i].getCircle());
		}

		Pocket[] pockets = poolBoard.getPockets();
		PocketView[] pocketViews = new PocketView[6];
		for (int i = 0; i < 6; i ++) {
			pocketViews[i] = new PocketView(pockets[i]);
			this.getPane().getChildren().add(pocketViews[i].getCircle());
		}

		CueStick cueStick = poolBoard.getCueStick();
		cueStickView = new CueStickView(cueStick);
		setCueStickHandlers();
		cueStick.addObserver(cueStickView);
		this.getPane().getChildren().add(cueStickView.getLine());
	}
	
	public void bringElementsToFront(){
		for (BallView bv: ballViews){
			bv.getCircle().toFront();
		}
		cueStickView.getLine().toFront();
	}
	
	public void initCueBallController(){
		CueBallController cueBallController = new CueBallController();
		cueBallController.addMouseHoverEventHandler(this, 
				poolBoard.getBalls()[15]);
		cueBallController.addMousePressedEventHandler(this, 
				poolBoard.getBalls()[15]);
	}

	public void setCueStickHandlers() {
		CueStickController cueStickController = 
				new CueStickController(cueStickView);
		cueStickController.addMouseHoverEH(this);
		cueStickController.addMouseDraggedEH(this);
		cueStickController.addMousePressedEH(this);
		cueStickController.addMouseReleasedEH(this);
	}
	
	public void removeCueStickHandlers() {
		getCueStickRectangle().setOnMouseMoved(null);
		getCueStickRectangle().setOnMouseDragged(null);
		cueStickView.getLine().setOnMouseDragged(null);
		cueStickView.getLine().setOnMouseClicked(null);
	}
	
	public Pane getPane() { return view; }

	public Rectangle getCueStickRectangle() { return cueStickRectangle; }
	
	public Rectangle getScratchRectangle() { return scratchRectangle; }

	public BallView[] getBallViews(){ return ballViews; }
	
	public CueStickView getCueStickView(){ return cueStickView; }
	
	public PoolBoard getPoolBoard(){ return poolBoard; }
}
