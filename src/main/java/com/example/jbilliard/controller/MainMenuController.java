package com.example.jbilliard.controller;

import com.example.jbilliard.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @AUTHOR = Bardia
 * @VERSION = 1.0
 *
 *  This class is the controller for the MainMenu.
 */
public class MainMenuController {
	@FXML Button btn_Go;
	
	@FXML
	public void handleButtonAction() {
		GameManager gm = GameManager.getInstance((btn_Go.getScene()));
		gm.initPoolScreen();
	}
}
