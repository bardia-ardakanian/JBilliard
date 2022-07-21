package com.example.jbilliard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @AUTHOR = Kimia
 * @VERSION = 1.0
 *
 *  Game luncher
 */

public class MainApp extends Application {

	@Override
	public void start(Stage primaryStage){
		Pane root = new Pane();
		
		double width = com.example.jbilliard.GameConstants.SCREEN_WIDTH;
		double length = com.example.jbilliard.GameConstants.SCREEN_LENGTH;

		Scene scene = new Scene(root, width, length);
		
		primaryStage.setTitle("Cool Pool");
		primaryStage.setWidth(width);
		primaryStage.setHeight(length);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		com.example.jbilliard.GameManager poolGame = com.example.jbilliard.GameManager.getInstance(scene);

		poolGame.initStartScreen(scene);
	}

	public static void main(String[] args){
		launch(args);
	}
}
