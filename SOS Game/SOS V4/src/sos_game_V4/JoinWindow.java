package sos_game_V4;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class JoinWindow extends Application{
	private PlayerInfoWindow playerInfoWindow;
	private Pane mainPane = new Pane();
	private Button joinButton = new Button("Join Game");

	public JoinWindow(Barricade barricade) {
		playerInfoWindow = new PlayerInfoWindow(barricade);
	}

	@Override
	public void start(Stage primaryStage) {
		mainPane = playerInfoWindow.getMainPane();
		playerInfoWindow.initializeLabels();	
		playerInfoWindow.initializePlayerTwoFieldsJoin();
		playerInfoWindow.intializePortFields();
		playerInfoWindow.initializeIPFields();
		playerInfoWindow.initializeDisplay();

		initializeJoinButton();

		Scene scene = new Scene(mainPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setMaximized(true);

		joinButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				playerInfoWindow.assignIP();
				playerInfoWindow.assignPort();
				playerInfoWindow.areJoinStartRequirementsMet();
				Barricade barricade = playerInfoWindow.getBarricade();
				Client client = new Client(primaryStage, barricade);
			}
		});
	}

	private void initializeJoinButton() {
		joinButton.setFont(Font.font ("Arial", 30));
		joinButton.setLayoutX(600);
		joinButton.setLayoutY(500);
		mainPane.getChildren().add(joinButton);
	}
}
