package com.example.jbilliard.view;

import java.util.Observable;
import java.util.Observer;
import com.example.jbilliard.model.EndScreenStatus;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * @AUTHOR = Kimia
 * @VERSION = 1.0
 *
 *  View class for EndScreen.
 */

public class EndScreenStatusView implements Observer {

	@FXML
	private Text gameOverText;

	private EndScreenStatus endScreenStatus;
	    
    public void setObservable(EndScreenStatus endScreenStatus) {
    	this.endScreenStatus = endScreenStatus;
    	update(endScreenStatus, "initial update");
    }

	@FXML
	public void update(Observable o, Object arg) {
		if (endScreenStatus == o) {
			gameOverText.setText(endScreenStatus.getGameOverMessage());
		}
	}
}
