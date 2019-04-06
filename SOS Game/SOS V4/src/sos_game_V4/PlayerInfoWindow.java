package sos_game_V4;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class PlayerInfoWindow extends Application {
	private Pane mainPane = new Pane();
	private String gridSizeInputString;
	private Barricade barricade;
	private Label PlayerOne = new Label("Player One:");
	private Label PlayerTwo = new Label("Player Two:");
	private TextField playerOneTextBox = new TextField();
	private TextField playerTwoTextBox = new TextField();
	private Label gridSizeLabel = new Label("Grid Size:");
	private TextField gridSizeInputBox = new TextField();
	private Button classicModeButton = new Button("Classic Mode");
	private Label onlinePort = new Label("Online Port");
	private TextField onlinePortTextBox = new TextField();
	private Label emptyTextField = new Label("MISSING INFORMATION");
	private Label samePlayerName = new Label("PLAYER NAMES MUST BE DIFFERENT");
	private Label invalidGridSizeInput = new Label("GRID SIZE MUST BE AN INTEGER (3-10)");
	private Label characterLimitReached = new Label("PLAYER NAMES CANNOT BE LONGER THAN 15 CHARACTERS");
	private Label ipAddress = new Label("IP Address");
	private TextField ipAddressTextBox = new TextField();
	private boolean fieldsEmpty;
	private boolean gridSizeEmpty;
	private boolean gridSizeValid;
	private boolean playerNamesDifferent;
	private Button combatModeButton = new Button("Combat Mode");
	private Button extremeModeButton = new Button("Extreme Mode");

	public PlayerInfoWindow(Barricade bar) {
		barricade = bar;
	}
	

	@Override
	public void start(Stage primaryStage) {	
		initializeLabels();	
		initializePlayerOneFields();
		initializePlayerTwoFields();
		initializeGridSizeFields();
		initializeButtons();
		initializeDisplay();

		Scene scene = new Scene(mainPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setMaximized(true);

		classicModeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				removeLabels();
				areStartRequirementsMet();
				if(safeToProceed()==true) {
					ClassicMode GUI = new ClassicMode(barricade);
					GUI.start(primaryStage);
				}
			}
		});

		extremeModeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				mainPane.getChildren().removeAll(emptyTextField, samePlayerName, invalidGridSizeInput, 
						characterLimitReached);

				areStartRequirementsMet();
				if(safeToProceed()==true) {				
					ExtremeMode GUI = new ExtremeMode(barricade);
					GUI.start(primaryStage);
				}
			}
		});

		combatModeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				mainPane.getChildren().removeAll(emptyTextField, samePlayerName, invalidGridSizeInput, 
						characterLimitReached);

				areStartRequirementsMet();
				if(safeToProceed()==true) {				
					CombatMode GUI = new CombatMode(barricade);
					GUI.start(primaryStage);
				}
			}
		});

	}
	
	public Barricade getBarricade() {
		return barricade;
	}

	void areJoinStartRequirementsMet() {
		areJoinFieldsEmpty();
		if(!isGridSizeEmpty()) {
			isGridSizeValid();
		}
	}

	void areHostStartRequirementsMet() {
		areHostFieldsEmpty();
		if(!isGridSizeEmpty()) {
			isGridSizeValid();
		}
	}

	void areStartRequirementsMet() {
		areFieldsEmpty();
		if(!isGridSizeEmpty()) {
			isGridSizeValid();
		}
		arePlayerNamesDifferent();
	}

	void removeLabels() {
		mainPane.getChildren().removeAll(emptyTextField, samePlayerName, invalidGridSizeInput, characterLimitReached);
	}
	
	public Button getClassicModeButton() {
		return classicModeButton;
	}

	public Button getCombatModeButton() {
		return combatModeButton;
	}

	public Button getExtremeModeButton() {
		return extremeModeButton;
	}
	
	void initializeIPFields() {
		ipAddress.setFont(Font.font ("Arial", 50));
		ipAddress.setLayoutX(389);
		ipAddress.setLayoutY(410);
		
		ipAddressTextBox.setFont(Font.font ("Arial", 30));
		ipAddressTextBox.setPrefSize(300, 55);
		ipAddressTextBox.setLayoutX(650);
		ipAddressTextBox.setLayoutY(410);
		
		mainPane.getChildren().addAll(ipAddress, ipAddressTextBox);	
	}
	
	void intializePortFields() {
		onlinePort.setFont(Font.font ("Arial", 60));
		onlinePort.setLayoutX(300);
		onlinePort.setLayoutY(300);
		
		onlinePortTextBox.setFont(Font.font ("Arial", 40));
		onlinePortTextBox.setPrefSize(300, 70);
		onlinePortTextBox.setLayoutX(650);
		onlinePortTextBox.setLayoutY(300);
		
		mainPane.getChildren().addAll(onlinePort, onlinePortTextBox);
	}

	boolean safeToProceed() {
		if((fieldsEmpty == false) && (gridSizeEmpty == false) && (gridSizeValid == true) /*&& (playerNamesDifferent == true)*/) {
			return true;
		}
		else {
			return false;
		}
	}

	void initializeDisplay() {
		mainPane.setStyle("-fx-background-color: #FFFFFF");
		mainPane.setMinHeight(700);
		mainPane.setMinWidth(1500);

	}

	void initializeButtons() {
		classicModeButton.setFont(Font.font ("Arial", 30));
		classicModeButton.setLayoutX(300);
		classicModeButton.setLayoutY(500);

		combatModeButton.setFont(Font.font ("Arial", 30));
		combatModeButton.setLayoutX(600);
		combatModeButton.setLayoutY(500);

		extremeModeButton.setFont(Font.font ("Arial", 30));
		extremeModeButton.setLayoutX(900);
		extremeModeButton.setLayoutY(500);
		mainPane.getChildren().addAll(classicModeButton, combatModeButton, extremeModeButton);
	}
	
	void initializePlayerOneFields() {
		PlayerOne.setFont(Font.font ("Arial", 60));
		PlayerOne.setLayoutX(300);
		PlayerOne.setLayoutY(175);
		
		playerOneTextBox.setFont(Font.font ("Arial", 40));
		playerOneTextBox.setPrefSize(300, 70);
		playerOneTextBox.setLayoutX(650);
		playerOneTextBox.setLayoutY(175);
		
		mainPane.getChildren().addAll(PlayerOne, playerOneTextBox);
	}
	void initializePlayerTwoFieldsJoin() {
		PlayerTwo.setFont(Font.font ("Arial", 60));
		PlayerTwo.setLayoutX(300);
		PlayerTwo.setLayoutY(175);
		
		playerTwoTextBox.setFont(Font.font ("Arial", 40));
		playerTwoTextBox.setPrefSize(300, 70);
		playerTwoTextBox.setLayoutX(650);
		playerTwoTextBox.setLayoutY(175);
		
		mainPane.getChildren().addAll(PlayerTwo, playerTwoTextBox);
	}

	void initializePlayerTwoFields() {
		PlayerTwo.setFont(Font.font ("Arial", 60));
		PlayerTwo.setLayoutX(300);
		PlayerTwo.setLayoutY(300);
		
		playerTwoTextBox.setFont(Font.font ("Arial", 40));
		playerTwoTextBox.setPrefSize(300, 70);
		playerTwoTextBox.setLayoutX(650);
		playerTwoTextBox.setLayoutY(300);
		
		mainPane.getChildren().addAll(PlayerTwo, playerTwoTextBox);
	}

	void initializeGridSizeFields() {
		gridSizeLabel.setFont(Font.font ("Arial", 50));
		gridSizeLabel.setLayoutX(389);
		gridSizeLabel.setLayoutY(410);
		
		gridSizeInputBox.setFont(Font.font ("Arial", 30));
		gridSizeInputBox.setPrefSize(300, 55);
		gridSizeInputBox.setLayoutX(650);
		gridSizeInputBox.setLayoutY(410);
		
		mainPane.getChildren().addAll(gridSizeLabel, gridSizeInputBox);
	}
	
	void initializeLabels() {
		emptyTextField.setFont(Font.font ("Arial", 30));
		emptyTextField.setTextFill(Color.RED);
		emptyTextField.setLayoutX(500);
		emptyTextField.setLayoutY(90);

		samePlayerName.setFont(Font.font ("Arial", 30));
		samePlayerName.setTextFill(Color.RED);
		samePlayerName.setLayoutX(400);
		samePlayerName.setLayoutY(10);

		invalidGridSizeInput.setFont(Font.font ("Arial", 30));
		invalidGridSizeInput.setTextFill(Color.RED);
		invalidGridSizeInput.setLayoutX(390);
		invalidGridSizeInput.setLayoutY(50);

		characterLimitReached.setFont(Font.font ("Arial", 30));
		characterLimitReached.setTextFill(Color.RED);
		characterLimitReached.setLayoutX(240);
		characterLimitReached.setLayoutY(130);
	}

	public boolean arePlayerNamesDifferent() {
		if(playerOneTextBox.getText().equals(playerTwoTextBox.getText()) && !playerOneTextBox.getText().isEmpty()) {
			mainPane.getChildren().add(samePlayerName);
			playerNamesDifferent = false;
			return playerNamesDifferent;
		}
		else {
			playerNamesDifferent = true;
			barricade.setPlayerNames(playerOneTextBox.getText(), playerTwoTextBox.getText());
			return playerNamesDifferent;
		}
	}
	
	public void assignPort() {
		barricade.setPort(Integer.parseInt(onlinePortTextBox.getText()));
		System.out.println("assigned port to " + barricade.getPort());
	}
	
	public void assignIP() {
		barricade.setIP(ipAddressTextBox.getText());
	}

	public boolean isGridSizeValid() {
		if(barricade.isValidGridSize(gridSizeInputString) == false) {
			mainPane.getChildren().add(invalidGridSizeInput);
			gridSizeValid = false;
			return gridSizeValid;
		}
		else {
			gridSizeValid = true;
			return gridSizeValid;
		}
	}

	public boolean isGridSizeEmpty() {
		if(!gridSizeInputBox.getText().isEmpty()) {
			gridSizeInputString = gridSizeInputBox.getText();
			gridSizeEmpty = false;
			return gridSizeEmpty;
		}
		else {
			gridSizeEmpty = true;
			return gridSizeEmpty;
		}
	}
	
	public boolean areJoinFieldsEmpty() {
		if(playerTwoTextBox.getText().isEmpty() || ipAddressTextBox.getText().isEmpty() || onlinePortTextBox.getText().isEmpty()) {
			mainPane.getChildren().add(emptyTextField);
			fieldsEmpty = true;
			return fieldsEmpty;
		}
		else {
			barricade.setPlayerTwoName(playerTwoTextBox.getText());
			fieldsEmpty = false;
			return fieldsEmpty;
		}
	} 

	public boolean areHostFieldsEmpty() {
		if(playerOneTextBox.getText().isEmpty() || gridSizeInputBox.getText().isEmpty() || onlinePortTextBox.getText().isEmpty()) {
			mainPane.getChildren().add(emptyTextField);
			fieldsEmpty = true;
			return fieldsEmpty;
		}
		else {
			barricade.setPlayerOneName(playerOneTextBox.getText());
			fieldsEmpty = false;
			return fieldsEmpty;
		}
	}

	
	public boolean areFieldsEmpty() {
		if(playerOneTextBox.getText().isEmpty() || playerTwoTextBox.getText().isEmpty() 
				|| gridSizeInputBox.getText().isEmpty()) {
			mainPane.getChildren().add(emptyTextField);
			fieldsEmpty = true;
			return fieldsEmpty;
		}
		else {
			fieldsEmpty = false;
			return fieldsEmpty;
		}
	}

	public Pane getMainPane() {
		return mainPane;
	}
}
