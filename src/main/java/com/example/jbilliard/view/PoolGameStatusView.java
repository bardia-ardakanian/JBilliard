package com.example.jbilliard.view;

import java.util.Observable;
import java.util.Observer;

import com.example.jbilliard.model.PoolGameStatus;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * @AUTHOR = Bardia
 * @VERSION = 1.0
 *
 *  View class for PoolGameStatus. Updates the text on side panel of poolScreen.fxml
 */

public class PoolGameStatusView implements Observer {
    @FXML
    private Text player1PointsText;

    @FXML
    private Text player2PointsText;
    
    @FXML
    private Text player1BallColorText;
    
    @FXML
    private Text player2BallColorText;
    
    @FXML
    private Text turnText;
    
    @FXML
    private Text lastTurnText;
    
    @FXML
    private Text cueBallText;
    
    private PoolGameStatus poolGameStatus;
    
    public void setObservable(PoolGameStatus poolGameStatus) {
    	this.poolGameStatus = poolGameStatus;
    	update(poolGameStatus, "initial update");
    }

	@FXML
	public void update(Observable o, Object arg) {
		if (poolGameStatus == o) {
			player1PointsText.setText(poolGameStatus.getPlayer1PointsStatus());
			player2PointsText.setText(poolGameStatus.getPlayer2PointsStatus());
			player1BallColorText.setText(poolGameStatus.getPlayer1BallColorStatus());
			player2BallColorText.setText(poolGameStatus.getPlayer2BallColorStatus());
			
			turnText.setText(poolGameStatus.getTurnStatus());
			lastTurnText.setText(poolGameStatus.getLastTurnStatus());
			cueBallText.setText(poolGameStatus.getCueBallStatus());
		}
		
	}	
}
