package sos_game_V4;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HostWindow extends Application{
	private PlayerInfoWindow playerInfoWindow;
	private Pane mainPane = new Pane();
	
	public HostWindow(Barricade barricade) {
		playerInfoWindow = new PlayerInfoWindow(barricade);
	}

	@Override
	public void start(Stage primaryStage) {
		mainPane = playerInfoWindow.getMainPane();
		playerInfoWindow.initializePlayerOneFields();
		playerInfoWindow.initializeLabels();	
		playerInfoWindow.initializeButtons();
		playerInfoWindow.intializePortFields();
		playerInfoWindow.initializeGridSizeFields();
		playerInfoWindow.initializeDisplay();	
		
		Scene scene = new Scene(mainPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setMaximized(true);
		
		
		playerInfoWindow.getClassicModeButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				playerInfoWindow.removeLabels();
				playerInfoWindow.areHostStartRequirementsMet();
				if(playerInfoWindow.safeToProceed() == true) {	
					playerInfoWindow.assignPort();
					Barricade barricade = playerInfoWindow.getBarricade();
					ClassicMode GUI = new ClassicMode(barricade);
					Server server = new Server(primaryStage, GUI);
				}
			}
		});
		
		playerInfoWindow.getExtremeModeButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				playerInfoWindow.removeLabels();
				playerInfoWindow.areHostStartRequirementsMet();
				if(playerInfoWindow.safeToProceed() == true) {	
					playerInfoWindow.assignPort();
					Barricade barricade = playerInfoWindow.getBarricade();
					ExtremeMode GUI = new ExtremeMode(barricade);
					Server server = new Server(primaryStage, GUI);
				}
			}
		});
		
		playerInfoWindow.getCombatModeButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				playerInfoWindow.removeLabels();
				playerInfoWindow.areHostStartRequirementsMet();
				if(playerInfoWindow.safeToProceed() == true) {	
					playerInfoWindow.assignPort();
					Barricade barricade = playerInfoWindow.getBarricade();
					CombatMode GUI = new CombatMode(barricade);
					Server server = new Server(primaryStage, GUI);
					
				}
			}
		});
		
	}


}

