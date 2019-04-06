package sos_game_V4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class WinnerWindow extends Application {
	private Barricade barricade;
	private Pane mainPane = new Pane();
	private Label winningPlayer;
	private String winnerName;
	
	public static void main (String [] args) {	
		launch(args);
	}

	public WinnerWindow(Barricade bar){
		barricade = bar;
	}
	@Override
	public void start(Stage primaryStage) {
		mainPane.setStyle("-fx-background-color: #FFFFFF");
		mainPane.setMinHeight(700);
		mainPane.setMinWidth(1500);
		winnerName = barricade.getWinningPlayerName();
	
		if(winnerName != "IT'S A TIE") {
			winningPlayer = new Label(winnerName + " IS THE WINNER");
		}
		else {
			winningPlayer = new Label("IT'S A TIE. NO WINNER.");
		}
		
		initializeLabel();
		
		mainPane.getChildren().addAll(winningPlayer);
		Scene scene = new Scene(mainPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setMaximized(true);		
	}
	
	private void initializeLabel() {
		winningPlayer.setFont(Font.font ("Arial", 60));
		winningPlayer.setLayoutX(350);
		winningPlayer.setLayoutY(250);
	}
}
