package sos_game_V4;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartScreenWindow extends Application {
	private Pane mainPane = new Pane();
	private double sosGraphicWidth = 584;
	private double sosGraphicHeight = 304;
	private	double buttonWidth = 400;
	private double buttonHeight = 100;
	private Barricade barricade = new Barricade();
	private ImageView sosGraphic = new ImageView("graphics/startScreenSOSImage.png");
	private Button playLocalButton = new Button("LOCAL");
	private Button multiplayerButton = new Button("MULTIPLAYER");
	private Button hostGameButton = new Button("HOST GAME");
	private Button joinGameButton = new Button("JOIN GAME");
	private Text ipAddress = new Text();
	private IPAddress ip = new IPAddress();
	private String localHost;

	
	public static void main (String [] args) {	
		launch(args);
	}
		
	@Override
	public void start(Stage primaryStage) {
		initializeIPAddress();
		initializeDisplay(primaryStage);
		
		Scene scene = new Scene(mainPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setMaximized(true);
		
		playLocalButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				barricade.setMultiplayer(false);
				PlayerInfoWindow GUI = new PlayerInfoWindow(barricade);
				GUI.start(primaryStage);
			}
		});
		
		multiplayerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				barricade.setMultiplayer(true);
				mainPane.getChildren().removeAll(playLocalButton, multiplayerButton);
				mainPane.getChildren().addAll(ipAddress, hostGameButton, joinGameButton);
			}
		});
		
		hostGameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				HostWindow GUI = new HostWindow(barricade);
				GUI.start(primaryStage);
			}
		});
		
		
		joinGameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				JoinWindow GUI = new JoinWindow(barricade);
				GUI.start(primaryStage);
			}
		});
		
	}

	private void initializeIPAddress() {
			localHost = ip.getIPAddress();
	}

	private void initializeDisplay(Stage primaryStage) {
		primaryStage.setHeight(700);
		primaryStage.setWidth(1500);
		mainPane.setStyle("-fx-background-color: #FFFFFF");

		sosGraphic.setLayoutX((primaryStage.getWidth()/2) - (sosGraphicWidth/2) - 50);
		sosGraphic.setLayoutY((primaryStage.getHeight()/2) - (sosGraphicHeight));
		
		ipAddress.setFont(Font.font ("Arial", 25));
		ipAddress.setText("YOUR IP ADDRESS: " + localHost);
		ipAddress.setLayoutX((primaryStage.getWidth()/2) - (buttonWidth/2) - 45);
		ipAddress.setLayoutY(primaryStage.getHeight() - 55);
		
		playLocalButton.setFont(Font.font ("Arial", 40));
		playLocalButton.setMinSize(buttonWidth, buttonHeight);
		playLocalButton.setLayoutX((primaryStage.getWidth()/2) - (buttonWidth/2) - 50);
		playLocalButton.setLayoutY(primaryStage.getHeight() - 320);
		
		multiplayerButton.setFont(Font.font ("Arial", 40));
		multiplayerButton.setMinSize(buttonWidth, buttonHeight);
		multiplayerButton.setLayoutX((primaryStage.getWidth()/2) - (buttonWidth/2) - 50);
		multiplayerButton.setLayoutY(primaryStage.getHeight() - 195);
		
		hostGameButton.setFont(Font.font ("Arial", 40));
		hostGameButton.setMinSize(buttonWidth, buttonHeight);
		hostGameButton.setLayoutX((primaryStage.getWidth()/2) - (buttonWidth/2) - 50);
		hostGameButton.setLayoutY(primaryStage.getHeight() - 320);

		joinGameButton.setFont(Font.font ("Arial", 40));
		joinGameButton.setMinSize(buttonWidth, buttonHeight);
		joinGameButton.setLayoutX((primaryStage.getWidth()/2) - (buttonWidth/2) - 50);
		joinGameButton.setLayoutY(primaryStage.getHeight() - 195);

		mainPane.getChildren().addAll(sosGraphic, playLocalButton, multiplayerButton);
	}
}