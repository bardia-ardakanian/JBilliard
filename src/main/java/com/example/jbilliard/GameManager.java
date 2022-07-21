package com.example.jbilliard;

import java.io.IOException;
import com.example.jbilliard.model.EndScreenStatus;
import com.example.jbilliard.model.PoolBoard;
import com.example.jbilliard.model.PoolGame;
import com.example.jbilliard.model.PoolGameStatus;
import com.example.jbilliard.view.EndScreenStatusView;
import com.example.jbilliard.view.PoolBoardView;
import com.example.jbilliard.view.PoolGameStatusView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * @AUTHOR = Kimia
 * @VERSION = 1.0
 *
 *  The "manager" that is in charge of changing scenes within the program.
 */
public class GameManager {
	private final Scene scene;
	private final BorderPane rootLayout;
	private PoolGame poolGame;

	private static GameManager instance = null;
	
	public static GameManager getInstance(Scene scene) {
		if(instance == null) {
			instance = new GameManager(scene);
		}
		
		return instance;
	}
	
	public static GameManager getInstance() {
		return instance;
	}
	
	private GameManager(Scene scene){
	    this.scene = scene;

	    rootLayout = new BorderPane();

	    scene.setRoot(rootLayout);
	}

	public void initStartScreen(Scene scene){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				GameManager.class.getResource("MainMenuScreen.fxml"));
		try {
			Parent mainMenuScreen = loader.load();
			rootLayout.setCenter(mainMenuScreen);
			scene.setRoot(rootLayout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initPoolScreen(){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				GameManager.class.getResource("PoolScreen.fxml"));
		try {
			Parent poolScreen = loader.load();
			PoolGameStatusView poolGameStatusView = loader.getController();
			
			poolGame = new PoolGame();
			PoolBoard poolBoard = poolGame.getPoolBoard();
			PoolBoardView poolBoardView = new PoolBoardView(poolBoard);
			poolBoard.addObserver(poolBoardView);
			
			PoolGameStatus poolGameStatus = poolGame.getPoolGameStatus();
			poolGameStatusView.setObservable(poolGameStatus);
			poolGameStatus.addObserver(poolGameStatusView);
			
			rootLayout.setCenter(poolScreen);
			Pane pane = (Pane) poolScreen.getChildrenUnmodifiable().get(1);
			
			
			pane.getChildren().add(poolBoardView.getPane());		

			scene.setRoot(rootLayout);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initEndScreen() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				GameManager.class.getResource("EndScreen.fxml"));
		try {
			Parent endScreen = (Parent) loader.load();
			EndScreenStatusView endScreenStatusView = loader.getController();
			EndScreenStatus endScreenStatus = poolGame.getEndScreenStatus();
			
			endScreenStatusView.setObservable(endScreenStatus);
			endScreenStatus.addObserver(endScreenStatusView);
			
			rootLayout.setCenter(endScreen);
			scene.setRoot(rootLayout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
