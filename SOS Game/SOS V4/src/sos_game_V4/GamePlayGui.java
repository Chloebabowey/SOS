package sos_game_V4;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import sos_game_V4.Barricade;
import sos_game_V4.CreateSOSSoundThread;
import sos_game_V4.GameClock;
import sos_game_V4.GameManager;

public class GamePlayGui{
	private Button[][] buttonArray;
	private GridPane gameGrid = new GridPane();
	private Barricade barricade;
	private GameManager game;
	private int steals;
	private int totalSteals;
	private Pane mainPane = new Pane();
	private int gridSize;
	private String currentPlayerName;
	private Player CurrentPlayer;
	private Label currentPlayerLabel; 
	private Label playerOnePoints;
	private Label playerTwoPoints;
	private int playerNumber;
	private Button quitGame = new Button("QUIT");
	private Button restartGame = new Button("RESTART");
	private String playerOneName;
	private String playerTwoName;
	private Button gridSquare;
	private String letterAtCurrentSquare;
	private Button confirmMove = new Button("MAKE MOVE");
	private Button clearMove = new Button("CLEAR MOVE");
	private char letterMove;
	private char[][] gridSpotArray;
	private int [][] playerOccupyingGridSpot;
	private Button lastButtonClicked;
	private Button resetLastButton = new Button();
	private String winnerName;
	private Player winningPlayer;
	private String playerOneCurrentPoints;
	private String playerTwoCurrentPoints;
	private TableView<TurnDetailsNode> turnHistory = new TableView<TurnDetailsNode>();
	private TableColumn<TurnDetailsNode, String> Player = new TableColumn<TurnDetailsNode, String>("Current Player");//.setCellValueFactory(new PropertyValueFactory("currentPlayer"));
	private TableColumn<TurnDetailsNode, String> StartTime = new TableColumn<>("Start Time");
	private TableColumn<TurnDetailsNode, String> EndTime = new TableColumn<>("End Time");
	private TableColumn<TurnDetailsNode, String> PlayerOnePoints = new TableColumn<>("Player One Points");
	private TableColumn<TurnDetailsNode, String> PlayerTwoPoints = new TableColumn<>("Player Two Points");
	private String playerOneColor = "-fx-background-color: #FF5733";
	private String playerTwoColor = "-fx-background-color: #3498DB";
	private String bothPlayersColor = "-fx-background-color: #8E44AD"; 
	private GameClock gameClock = new GameClock();
	private CreateSOSSoundThread createSOSSounds = new CreateSOSSoundThread();
	private Text timeDisplay;
	private int SOSMatches;
	private String StartTimeOfTurn;
	private String EndTimeOfTurn;
	private UpdateGameClockLabel updateGameClock;
	private CheckSteals checkOtherPlayerMatches = new CheckSteals();
	private ObservableList<TurnDetailsNode> turnDetailData = FXCollections.observableArrayList();
	private int moveRow;
	private int moveColumn;

	GamePlayGui(Barricade bar){
		barricade = bar;
	}

	public GameManager getGame() {
		return game;
	}

	public void setBarricade(Barricade barricade) {
		this.barricade = barricade;
	}

	public void clearMove() {
		reverseDisabledButtons();
		lastButtonClicked.setText(" ");
	}

	public void quitGame(Stage primaryStage) {
		winnerName = game.quit();
		barricade.setWinnerName(winnerName);
		gameClock = null;
		WinnerWindow GUI = new WinnerWindow(barricade);
		GUI.start(primaryStage);
	}

//	public void restart() {
//		gameClock = null;
//	}

	public void addToWindow(Stage primaryStage, Button quitGame, Button restartGame, Button confirmMove, Button clearMove) {
		mainPane.getChildren().addAll(gameGrid, currentPlayerLabel, playerOnePoints, playerTwoPoints, timeDisplay,
				quitGame, restartGame, confirmMove, clearMove);
		Scene scene = new Scene(mainPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setMaximized(true);
	}

	public void startGame(Button quitGame, Button restartGame, Button confirmMove, Button clearMove, Barricade Barricade) {
		Thread gameClockThread = new Thread(gameClock);
		gameClockThread.setDaemon(true);
		gameClockThread.start();
		initializeWindow();
		barricade = Barricade;
		game = new GameManager(barricade);
		gridSize = barricade.getGridSize();
		initializeBackEnd();
		game.resetPlayerPoints();
		playerOccupyingGridSpot = new int [gridSize][gridSize];
		CurrentPlayer = game.currentPlayer();
		currentPlayerName = game.printCurrentPlayerName(CurrentPlayer);
		initializeTurnTableDetails();
		final EventHandler<ActionEvent> gridSquareButtonHandler = initializeButtonBehavior();
		initializeButtonArray(gridSquareButtonHandler);
		initializeLabels();
		initializeButtons(quitGame, restartGame, confirmMove, clearMove);
		initializeGameGrid();
	}

	public char getLetterMove() {
		return letterMove;
	}

	public int getMoveRow() {
		return moveRow;
	}

	public int getMoveColumn() {
		return moveColumn;
	}

	private void initializeGameGrid() {
		gameGrid.setLayoutX(150);
		gameGrid.setLayoutY(100);
	}

	private void initializeWindow() {
		mainPane.setStyle("-fx-background-color: #FFFFFF");
		mainPane.setMinHeight(700);
		mainPane.setMinWidth(1500);
	}

	private void initializeBackEnd() {
		game.initializePlayers();
		game.initializeGrid();
		game.randomFirstTurn();
	}



	private EventHandler<ActionEvent> initializeButtonBehavior() {
		final EventHandler<ActionEvent> gridSquareButtonHandler = new EventHandler<ActionEvent>(){
			@Override public void handle(final ActionEvent event) {
				Button gridSquare = (Button) event.getSource();
				if(gridSquare.getText().equals(" ")) {
					gridSquare.setText("S");
					gridSquare.setFont(Font.font ("Arial", 25));
				}
				else if(gridSquare.getText().equals("S")) {
					gridSquare.setText("O");
					gridSquare.setFont(Font.font ("Arial", 25));
				}
				else if(gridSquare.getText().equals("O")) {
					gridSquare.setText(" ");
					gridSquare.setFont(Font.font ("Arial", 25));
				}
				for(int row = 0; row < gridSize; row++) {
					for(int column = 0; column < gridSize; column++) {	
						if (gridSquare.equals(buttonArray[row][column])) {
							buttonArray[row][column].setText(gridSquare.getText());
						}
						else {
							buttonArray[row][column].setDisable(true);						
						}
					}
				}	
				lastButtonClicked = gridSquare;
			}
		};
		return gridSquareButtonHandler;
	}

	public void updateMoveFromGUI(){
		if(lastButtonClicked.getText() == "S") {
			letterMove = 'S';
		} else {
			letterMove = 'O';
		}

		barricade.setMove(letterMove);

		for(int row = 0; row < gridSize; row++) {
			for(int column = 0; column < gridSize; column++) {	
				if (lastButtonClicked.equals(buttonArray[row][column])) {
					moveRow = row;
					moveColumn = column;
					break;
				}
			}
		}
	}

	private void updateButtonGraphic(int row, int column, char letter) {
		buttonArray[row][column].setDisable(true);
		buttonArray[row][column].setText(String.valueOf(letter));
		buttonArray[row][column].setFont(Font.font ("Arial", 25));
	}


	private void initializeLabels() {
		currentPlayerLabel = new Label(currentPlayerName + "'S TURN");
		currentPlayerLabel.setFont(Font.font ("Arial", 40));
		currentPlayerLabel.setLayoutX(450);
		currentPlayerLabel.setLayoutY(30);

		timeDisplay = new Text();
		updateGameClock = new UpdateGameClockLabel(gameClock, timeDisplay);
		Thread updateTime = new Thread(updateGameClock);
		updateTime.setDaemon(true);
		updateTime.start();
		System.out.println("time = " + gameClock.getTime());
		timeDisplay.setFont(Font.font ("Arial", 20));
		timeDisplay.setLayoutX(960);
		timeDisplay.setLayoutY(350);

		playerOneName = game.getPlayerOneName();
		playerOneCurrentPoints = Long.toString(barricade.getPlayerOnePoints());
		playerOnePoints = new Label(playerOneName + "'S POINTS: " + playerOneCurrentPoints);
		playerOnePoints.setFont(Font.font ("Arial", 20));
		playerOnePoints.setLayoutX(800);
		playerOnePoints.setLayoutY(250);
		playerOnePoints.setTextFill(Color.RED);

		playerTwoName = game.getPlayerTwoName();
		playerTwoCurrentPoints = Long.toString(barricade.getPlayerTwoPoints());
		playerTwoPoints = new Label(playerTwoName + "'S POINTS: " + playerTwoCurrentPoints);
		playerTwoPoints.setFont(Font.font ("Arial", 20));
		playerTwoPoints.setLayoutX(800);
		playerTwoPoints.setLayoutY(300);
		playerTwoPoints.setTextFill(Color.CORNFLOWERBLUE);
	}

	public void classicModeMove(Stage primaryStage, int row, int column, char letter) {
		gameGrid.getChildren().remove(buttonArray[row][column]);
		updateButtonGraphic(row, column, letter);
		setMoveSpot(row, column);
		game.GamePlay();
		playSOSSound();
		resetTurnValues();
		updatePlayerPoints();
		assignTurnDetails();
		updateDisplayAfterMoveMade(row, column);
		if(checkIfWinner() == true) {
			WinnerWindow GUI = new WinnerWindow(barricade);
			GUI.start(primaryStage);
		}
	}

	public void extremeModeMove(Stage primaryStage, int row, int column, char letter) {
		gameGrid.getChildren().remove(buttonArray[row][column]);
		updateButtonGraphic(row, column, letter);
		setMoveSpot(row, column);
		game.ExtremeGamePlay();
		playSOSSound();
		resetTurnValues();
		updatePlayerPoints();
		assignTurnDetails();
		updateDisplayAfterMoveMade(row, column);
		if(checkIfWinner() == true) {
			WinnerWindow GUI = new WinnerWindow(barricade);
			GUI.start(primaryStage);
		}
	}

	public void combatModeMove(Stage primaryStage, int row, int column, char letter) {
		gameGrid.getChildren().remove(buttonArray[row][column]);
		updateButtonGraphic(row, column, letter);
		setMoveSpot(row, column);
		game.GamePlay();
		changeButtonColorsIfMatchCombat(row, column);
		game.currentPlayer().addPoints(totalSteals);
		subtractPointsFromOtherPlayer();
		updatePlayerPoints();
		playSOSSound();
		assignTurnDetails();
		resetTurnValues();
		updateDisplayAfterMoveMade(row, column);
		if(checkIfWinner() == true) {
			WinnerWindow GUI = new WinnerWindow(barricade);
			GUI.start(primaryStage);
		}
	}

	private void initializeButtons(Button quitGame, Button restartGame, Button confirmMove, Button clearMove) {
		quitGame.setFont(Font.font ("Arial", 20));
		quitGame.setLayoutX(25);
		quitGame.setLayoutY(20);

		restartGame.setFont(Font.font ("Arial", 20));
		restartGame.setLayoutX(125);
		restartGame.setLayoutY(20);

		confirmMove.setFont(Font.font ("Arial", 20));
		confirmMove.setLayoutX(800);
		confirmMove.setLayoutY(170);

		clearMove.setFont(Font.font ("Arial", 20));
		clearMove.setLayoutX(800);
		clearMove.setLayoutY(100);
	}

	private void initializeButtonArray(final EventHandler<ActionEvent> gridSquareButtonHandler) {
		buttonArray = new Button[gridSize][gridSize];
		for(int row = 0; row < gridSize; row++) {
			for(int column = 0; column < gridSize; column++) {
				playerOccupyingGridSpot[row][column] = 0;				
				buttonArray[row][column] = new Button();
				buttonArray[row][column].setOnAction(gridSquareButtonHandler);
				buttonArray[row][column].setText(" ");

				buttonArray[row][column].setStyle("-fx-background-color: #FFF8DC");
				buttonArray[row][column].setStyle("-fx-border-color: #000000");
				buttonArray[row][column].setPrefSize(60, 60);
				gameGrid.add(buttonArray[row][column], column, row);					
			}
		}
	}

	private void resetTurnValues() {
		SOSMatches = 0;
		totalSteals = 0;
		steals = 0;
		lastButtonClicked = resetLastButton;
	}

	private void setMoveSpot(int row, int column) {
		barricade.setMoveRow(row);
		barricade.setMoveColumn(column);
	}

	public void updatePlayerPoints() {
		playerOneCurrentPoints = Long.toString(barricade.getPlayerOnePoints());
		playerTwoCurrentPoints = Long.toString(barricade.getPlayerTwoPoints());
	}

	private void subtractPointsFromOtherPlayer() {
		if(CurrentPlayer.equals(game.getPlayerOne())) {
			game.getPlayerTwo().subtractPoints(totalSteals);
		}
		else {
			game.getPlayerOne().subtractPoints(totalSteals);
		}
		game.setPlayerOnePoints();
		game.setPlayerTwoPoints();
	}

	public void updateDisplayAfterMoveMade(int row, int column) {
		gameGrid.getChildren().remove(buttonArray[row][column]);
		gameGrid.add(buttonArray[row][column], column, row);
		mainPane.getChildren().removeAll(currentPlayerLabel, playerOnePoints, playerTwoPoints);
		updateCurrentPlayerLabel();
		playerOnePoints.setText(playerOneName + "'S POINTS: " + playerOneCurrentPoints);
		playerTwoPoints.setText(playerTwoName + "'S POINTS: " + playerTwoCurrentPoints);
		mainPane.getChildren().addAll(currentPlayerLabel, playerOnePoints, playerTwoPoints);
		reverseDisabledButtons();
		changeButtonColorsIfMatch(row, column);
	}

	public void updateCurrentPlayerLabel() {
		CurrentPlayer = game.currentPlayer();
		currentPlayerName = game.printCurrentPlayerName(CurrentPlayer);
		currentPlayerLabel.setText(currentPlayerName + "'S TURN");
	}

	public void changeButtonColorsIfMatch(int row, int column) {
		if(game.getSOSChecker().getDiagonalDownLeftS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row+2][column-2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column] = 1;
				}
				if(checkIfAlreadyColored(row+1, column-1) == false) {
					buttonArray[row+1][column-1].setStyle(playerOneColor);
					playerOccupyingGridSpot[row+1][column-1] = 1;
				}
				if(checkIfAlreadyColored(row+2, column-2) == false) {
					buttonArray[row+2][column-2].setStyle(playerOneColor);
					playerOccupyingGridSpot[row+2][column-2] = 1;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row+2][column-2]);
			}
			else {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column] = 2;
				}
				if(checkIfAlreadyColored(row+1, column-1) == false) {
					buttonArray[row+1][column-1].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row+1][column-1] = 2;
				}
				if(checkIfAlreadyColored(row+2, column-2) == false) {
					buttonArray[row+2][column-2].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row+2][column-2] = 2;
				}	
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row+2][column-2]);
			}
		}		
		if(game.getSOSChecker().getDiagonalDownRightS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row+1][column+1], buttonArray[row+2][column+2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {		
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column] = 1;
				}
				if(checkIfAlreadyColored(row+1, column+1) == false) {
					buttonArray[row+1][column+1].setStyle(playerOneColor);
					playerOccupyingGridSpot[row+1][column+1] = 1;
				}
				if(checkIfAlreadyColored(row+2, column+2) == false) {
					buttonArray[row+2][column+2].setStyle(playerOneColor);
					playerOccupyingGridSpot[row+2][column+2] = 1;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column+1], buttonArray[row+2][column+2]);
			}
			else {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column] = 2;
				}
				if(checkIfAlreadyColored(row+1, column+1) == false) {
					buttonArray[row+1][column+1].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row+1][column+1] = 2;
				}
				if(checkIfAlreadyColored(row+2, column+2) == false) {
					buttonArray[row+2][column+2].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row+2][column+2] = 2;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column+1], buttonArray[row+2][column+2]);
			}
		}		
		if(game.getSOSChecker().getDiagonalUpRightS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column+1], buttonArray[row-2][column+2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {			
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column] = 1;
				}
				if(checkIfAlreadyColored(row-1, column+1) == false) {
					buttonArray[row-1][column+1].setStyle(playerOneColor);
					playerOccupyingGridSpot[row-1][column+1] = 1;
				}
				if(checkIfAlreadyColored(row-2, column+2) == false) {
					buttonArray[row-2][column+2].setStyle(playerOneColor);
					playerOccupyingGridSpot[row-2][column+2] = 1;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column+1], buttonArray[row-2][column+2]);
			}
			else {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column] = 2;
				}
				if(checkIfAlreadyColored(row-1, column+1) == false) {
					buttonArray[row-1][column+1].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row-1][column+1] = 2;
				}
				if(checkIfAlreadyColored(row-2, column+2) == false) {
					buttonArray[row-2][column+2].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row-2][column+2] = 2;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column+1], buttonArray[row-2][column+2]);
			}
		}	
		if(game.getSOSChecker().getDiagonalUpLeftS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row-2][column-2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column] = 1;
				}
				if(checkIfAlreadyColored(row-1, column-1) == false) {
					buttonArray[row-1][column-1].setStyle(playerOneColor);
					playerOccupyingGridSpot[row-1][column-1] = 1;
				}
				if(checkIfAlreadyColored(row-2, column-2) == false) {
					buttonArray[row-2][column-2].setStyle(playerOneColor);
					playerOccupyingGridSpot[row-2][column-2] = 1;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row-2][column-2]);
			}
			else {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column] = 2;
				}
				if(checkIfAlreadyColored(row-1, column-1) == false) {
					buttonArray[row-1][column-1].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row-1][column-1] = 2;
				}
				if(checkIfAlreadyColored(row-2, column-2) == false) {
					buttonArray[row-2][column-2].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row-2][column-2] = 2;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row-2][column-2]);
			}
		}	
		if(game.getSOSChecker().getVerticalUpS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row-2][column]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {			
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column] = 1;
				}
				if(checkIfAlreadyColored(row-1, column) == false) {
					buttonArray[row-1][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row-1][column] = 1;
				}
				if(checkIfAlreadyColored(row-2, column) == false) {
					buttonArray[row-2][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row-2][column] = 1;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row-2][column]);
			}
			else {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column] = 2;
				}
				if(checkIfAlreadyColored(row-1, column) == false) {
					buttonArray[row-1][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row-1][column] = 2;
				}
				if(checkIfAlreadyColored(row-2, column) == false) {
					buttonArray[row-2][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row-2][column] = 2;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row-2][column]);
			}
		}	
		if(game.getSOSChecker().getVerticalDownS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row+1][column], buttonArray[row+2][column]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column] = 1;
				}
				if(checkIfAlreadyColored(row+1, column) == false) {
					buttonArray[row+1][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row+1][column] = 1;
				}
				if(checkIfAlreadyColored(row+2, column) == false) {
					buttonArray[row+2][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row+2][column] = 1;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column], buttonArray[row+2][column]);
			}
			else {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column] = 2;
				}
				if(checkIfAlreadyColored(row+1, column) == false) {
					buttonArray[row+1][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row+1][column] = 2;
				}
				if(checkIfAlreadyColored(row+2, column) == false) {
					buttonArray[row+2][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row+2][column] = 2;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column], buttonArray[row+2][column]);
			}
		}	
		if(game.getSOSChecker().getHorizontalLeftS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column-2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column] = 1;
				}
				if(checkIfAlreadyColored(row, column-1) == false) {
					buttonArray[row][column-1].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column-1] = 1;
				}
				if(checkIfAlreadyColored(row, column-2) == false) {
					buttonArray[row][column-2].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column-2] = 1;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column-2]);
			}
			else {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column] = 2;
				}
				if(checkIfAlreadyColored(row, column-1) == false) {
					buttonArray[row][column-1].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column-1] = 2;
				}
				if(checkIfAlreadyColored(row, column-2) == false) {
					buttonArray[row][column-2].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column-2] = 2;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column-2]);
			}
		}		
		if(game.getSOSChecker().getHorizontalRightS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row][column+1], buttonArray[row][column+2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column] = 1;
				}
				if(checkIfAlreadyColored(row, column+1) == false) {
					buttonArray[row][column+1].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column+1] = 1;
				}
				if(checkIfAlreadyColored(row, column+2) == false) {
					buttonArray[row][column+2].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column+2] = 1;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column+1], buttonArray[row][column+2]);
			}
			else {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column] = 2;
				}
				if(checkIfAlreadyColored(row, column+1) == false) {
					buttonArray[row][column+1].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column+1] = 2;
				}
				if(checkIfAlreadyColored(row, column+2) == false) {
					buttonArray[row][column+2].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column+2] = 2;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column+1], buttonArray[row][column+2]);
			}
		}
		if(game.getSOSChecker().getDiagonalDownO() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row+1][column+1]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column] = 1;
				}
				if(checkIfAlreadyColored(row-1, column-1) == false) {
					buttonArray[row-1][column-1].setStyle(playerOneColor);
					playerOccupyingGridSpot[row-1][column-1] = 1;
				}
				if(checkIfAlreadyColored(row+1, column+1) == false) {
					buttonArray[row+1][column+1].setStyle(playerOneColor);
					playerOccupyingGridSpot[row+1][column+1] = 1;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row+1][column+1]);
			}
			else {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column] = 2;
				}
				if(checkIfAlreadyColored(row-1, column-1) == false) {
					buttonArray[row-1][column-1].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row-1][column-1] = 2;
				}
				if(checkIfAlreadyColored(row+1, column+1) == false) {
					buttonArray[row+1][column+1].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row+1][column+1] = 2;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row+1][column+1]);
			}
		}
		if(game.getSOSChecker().getDiagonalUpO() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row-1][column+1]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column] = 1;
				}
				if(checkIfAlreadyColored(row+1, column-1) == false) {
					buttonArray[row+1][column-1].setStyle(playerOneColor);
					playerOccupyingGridSpot[row+1][column-1] = 1;
				}
				if(checkIfAlreadyColored(row-1, column+1) == false) {
					buttonArray[row-1][column+1].setStyle(playerOneColor);
					playerOccupyingGridSpot[row-1][column+1] = 1;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row-1][column+1]);
			}
			else {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column] = 2;
				}
				if(checkIfAlreadyColored(row+1, column-1) == false) {
					buttonArray[row+1][column-1].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row+1][column-1] = 2;
				}
				if(checkIfAlreadyColored(row-1, column+1) == false) {
					buttonArray[row-1][column+1].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row-1][column+1] = 2;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row-1][column+1]);
			}
		}
		if(game.getSOSChecker().getHorizontalO() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column+1]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column] = 1;
				}
				if(checkIfAlreadyColored(row, column-1) == false) {
					buttonArray[row][column-1].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column-1] = 1;
				}
				if(checkIfAlreadyColored(row, column+1) == false) {
					buttonArray[row][column+1].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column+1] = 1;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column+1]);
			}
			else {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column] = 2;
				}
				if(checkIfAlreadyColored(row, column-1) == false) {
					buttonArray[row][column-1].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column-1] = 2;
				}
				if(checkIfAlreadyColored(row, column+1) == false) {
					buttonArray[row][column+1].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column+1] = 2;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column+1]);
			}
		}
		if(game.getSOSChecker().getVerticalO() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row+1][column]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row][column] = 1;
				}
				if(checkIfAlreadyColored(row-1, column) == false) {
					buttonArray[row-1][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row-1][column] = 1;
				}
				if(checkIfAlreadyColored(row+1, column) == false) {
					buttonArray[row+1][column].setStyle(playerOneColor);
					playerOccupyingGridSpot[row+1][column] = 1;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row+1][column]);
			}
			else {
				if(checkIfAlreadyColored(row, column) == false) {
					buttonArray[row][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row][column] = 2;
				}
				if(checkIfAlreadyColored(row-1, column) == false) {
					buttonArray[row-1][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row-1][column] = 2;
				}
				if(checkIfAlreadyColored(row+1, column) == false) {
					buttonArray[row+1][column].setStyle(playerTwoColor);
					playerOccupyingGridSpot[row+1][column] = 2;
				}
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row+1][column]);
			}
		}
	}

	public boolean checkIfWinner() {
		if(game.hasWinner() == true) {
			winningPlayer = game.winningPlayer();
			winnerName = game.printWinningPlayer(winningPlayer);
			barricade.setWinnerName(winnerName);
			return true;
		}
		return false;
	}

	public void reverseDisabledButtons() {
		for(int row = 0; row < gridSize; row++) {
			for(int column = 0; column < gridSize; column++) {	
				gridSpotArray = game.getGridSpots();
				if(gridSpotArray[row][column] != 'S' && gridSpotArray[row][column] != 'O') {
					buttonArray[row][column].setDisable(false);	
				}
			}
		}
	}

	public boolean checkIfAlreadyColored(int row, int column) {
		if((CurrentPlayer.equals(game.getPlayerOne())) & (playerOccupyingGridSpot[row][column] == 2)) {
			buttonArray[row][column].setStyle(bothPlayersColor);
			return true;
		}
		else if((CurrentPlayer.equals(game.getPlayerTwo())) & (playerOccupyingGridSpot[row][column] == 1)) {
			buttonArray[row][column].setStyle(bothPlayersColor);
			return true;
		}
		else {
			return false;
		}
	}

	public void updateGameGrid() {
		for(int row = 0; row < gridSize; row++) {
			for(int column = 0; column < gridSize; column++) {
				gameGrid.getChildren().remove(row, column);
				gameGrid.add(buttonArray[row][column], row, column);		
			}
		}
	}

	public void playSOSSound() {
		SOSMatches = game.getMatches() + totalSteals;
		createSOSSounds.createSOSThread(SOSMatches);
	}

	public void initializeTurnTableDetails() {
		Player.setCellValueFactory(new PropertyValueFactory<TurnDetailsNode, String>("currentPlayer"));
		StartTime.setCellValueFactory(new PropertyValueFactory<TurnDetailsNode, String>("startTimeOfTurn"));
		EndTime.setCellValueFactory(new PropertyValueFactory<TurnDetailsNode, String>("endTimeOfTurn"));
		PlayerOnePoints.setCellValueFactory(new PropertyValueFactory<TurnDetailsNode, String>("playerOnePoints"));
		PlayerTwoPoints.setCellValueFactory(new PropertyValueFactory<TurnDetailsNode, String>("playerTwoPoints"));
		Player.setPrefWidth(100);
		PlayerOnePoints.setPrefWidth(125);
		PlayerTwoPoints.setPrefWidth(125);
		turnHistory.getColumns().setAll(Player, StartTime, EndTime, PlayerOnePoints, PlayerTwoPoints);
		turnHistory.setItems(turnDetailData);
		turnHistory.setPrefWidth(550);
		turnHistory.setPrefHeight(300);
		turnHistory.setLayoutX(800);
		turnHistory.setLayoutY(370);
		mainPane.getChildren().add(turnHistory);
		StartTimeOfTurn = gameClock.getTime();
	}

	public void assignTurnDetails() {
		EndTimeOfTurn = gameClock.getTime();
		turnDetailData.add(new TurnDetailsNode(currentPlayerName, StartTimeOfTurn, EndTimeOfTurn, playerOneCurrentPoints, playerTwoCurrentPoints));
		turnHistory.setItems(turnDetailData);
		StartTimeOfTurn = EndTimeOfTurn;
	}

	public Pane getMainPane() {
		return mainPane;
	}	

	public void changeButtonColorsIfMatchCombat(int row, int column) {
		if(game.getSOSChecker().getDiagonalDownLeftS() == true) {
			checkIfAlreadyOccupied(row, column);
			checkIfAlreadyOccupied(row+1, column-1);
			checkIfAlreadyOccupied(row+2, column-2);
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row+2][column-2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row+1][column-1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+1][column-1] = 1;
				buttonArray[row+2][column-2].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+2][column-2] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row+2][column-2]);					
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row+1][column-1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+1][column-1] = 2;
				buttonArray[row+2][column-2].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+2][column-2] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row+2][column-2]);
			}
		}		
		if(game.getSOSChecker().getDiagonalDownRightS() == true) {
			checkIfAlreadyOccupied(row, column);
			checkIfAlreadyOccupied(row+1, column+1);
			checkIfAlreadyOccupied(row+2, column+2);
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row+1][column+1], buttonArray[row+2][column+2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {		
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row+1][column+1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+1][column+1] = 1;
				buttonArray[row+2][column+2].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+2][column+2] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column+1], buttonArray[row+2][column+2]);

			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row+1][column+1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+1][column+1] = 2;
				buttonArray[row+2][column+2].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+2][column+2] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column+1], buttonArray[row+2][column+2]);

			}
		}		
		if(game.getSOSChecker().getDiagonalUpRightS() == true) {
			checkIfAlreadyOccupied(row, column);
			checkIfAlreadyOccupied(row-1, column+1);
			checkIfAlreadyOccupied(row-2, column+2);
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column+1], buttonArray[row-2][column+2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {			
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row-1][column+1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-1][column+1] = 1;
				buttonArray[row-2][column+2].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-2][column+2] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column+1], buttonArray[row-2][column+2]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row-1][column+1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-1][column+1] = 2;
				buttonArray[row-2][column+2].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-2][column+2] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column+1], buttonArray[row-2][column+2]);
			}
		}	
		if(game.getSOSChecker().getDiagonalUpLeftS() == true) {
			checkIfAlreadyOccupied(row, column);
			checkIfAlreadyOccupied(row-1, column-1);
			checkIfAlreadyOccupied(row-2, column-2);
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row-2][column-2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row-1][column-1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-1][column-1] = 1;
				buttonArray[row-2][column-2].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-2][column-2] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row-2][column-2]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row-1][column-1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-1][column-1] = 2;
				buttonArray[row-2][column-2].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-2][column-2] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row-2][column-2]);
			}
		}	
		if(game.getSOSChecker().getVerticalUpS() == true) {
			checkIfAlreadyOccupied(row, column);
			checkIfAlreadyOccupied(row-1, column);
			checkIfAlreadyOccupied(row-2, column);
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row-2][column]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {			
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row-1][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-1][column] = 1;
				buttonArray[row-2][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-2][column] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row-2][column]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row-1][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-1][column] = 2;
				buttonArray[row-2][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-2][column] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row-2][column]);
			}
		}	
		if(game.getSOSChecker().getVerticalDownS() == true) {
			checkIfAlreadyOccupied(row, column);
			checkIfAlreadyOccupied(row+1, column);
			checkIfAlreadyOccupied(row+2, column);
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row+1][column], buttonArray[row+2][column]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row+1][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+1][column] = 1;
				buttonArray[row+2][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+2][column] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column], buttonArray[row+2][column]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row+1][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+1][column] = 2;
				buttonArray[row+2][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+2][column] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column], buttonArray[row+2][column]);
			}
		}	
		if(game.getSOSChecker().getHorizontalLeftS() == true) {
			checkIfAlreadyOccupied(row, column);
			checkIfAlreadyOccupied(row, column-1);
			checkIfAlreadyOccupied(row, column-2);
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column-2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row][column-1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column-1] = 1;
				buttonArray[row][column-2].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column-2] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column-2]);
			}
			else {
				gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column-2]);
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row][column-1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column-1] = 2;
				buttonArray[row][column-2].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column-2] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column-2]);
			}

		}		
		if(game.getSOSChecker().getHorizontalRightS() == true) {
			checkIfAlreadyOccupied(row, column);
			checkIfAlreadyOccupied(row, column+1);
			checkIfAlreadyOccupied(row, column+2);
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row][column+1], buttonArray[row][column+2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row][column+1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column+1] = 1;
				buttonArray[row][column+2].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column+2] = 1;

				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column+1], buttonArray[row][column+2]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row][column+1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column+1] = 2;
				buttonArray[row][column+2].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column+2] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column+1], buttonArray[row][column+2]);
			}
		}
		if(game.getSOSChecker().getDiagonalDownO() == true) {
			checkIfAlreadyOccupied(row, column);
			checkIfAlreadyOccupied(row-1, column-1);
			checkIfAlreadyOccupied(row+1, column+1);
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row+1][column+1]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row-1][column-1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-1][column-1] = 1;
				buttonArray[row+1][column+1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+1][column+1] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row+1][column+1]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row-1][column-1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-1][column-1] = 2;
				buttonArray[row+1][column+1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+1][column+1] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row+1][column+1]);
			}
		}
		if(game.getSOSChecker().getDiagonalUpO() == true) {
			checkIfAlreadyOccupied(row, column);
			checkIfAlreadyOccupied(row+1, column-1);
			checkIfAlreadyOccupied(row-1, column+1);
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row-1][column+1]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row+1][column-1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+1][column-1] = 1;
				buttonArray[row-1][column+1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-1][column+1] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row-1][column+1]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row+1][column-1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+1][column-1] = 2;
				buttonArray[row-1][column+1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-1][column+1] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row-1][column+1]);
			}
		}
		if(game.getSOSChecker().getHorizontalO() == true) {
			checkIfAlreadyOccupied(row, column);
			checkIfAlreadyOccupied(row, column-1);
			checkIfAlreadyOccupied(row, column+1);
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column+1]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row][column-1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column-1] = 1;
				buttonArray[row][column+1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column+1] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column+1]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row][column-1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column-1] = 2;
				buttonArray[row][column+1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column+1] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column+1]);
			}
		}
		if(game.getSOSChecker().getVerticalO() == true) {
			checkIfAlreadyOccupied(row, column);
			checkIfAlreadyOccupied(row-1, column);
			checkIfAlreadyOccupied(row+1, column);
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row+1][column]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row-1][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-1][column] = 1;
				buttonArray[row+1][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+1][column] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row+1][column]);
			}
			else {
				gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row+1][column]);
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row-1][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-1][column] = 2;
				buttonArray[row+1][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+1][column] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row+1][column]);
			}
		}
	}

	public void changeButtonColorsForSteals(int row, int column) {
		if(checkOtherPlayerMatches.getDiagonalDownLeftS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row+2][column-2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;	
				buttonArray[row+1][column-1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+1][column-1] = 1;
				buttonArray[row+2][column-2].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+2][column-2] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row+2][column-2]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row+1][column-1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+1][column-1] = 2;
				buttonArray[row+2][column-2].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+2][column-2] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row+2][column-2]);
			}
		}		
		if(checkOtherPlayerMatches.getDiagonalDownRightS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row+1][column+1], buttonArray[row+2][column+2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {		
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row+1][column+1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+1][column+1] = 1;				
				buttonArray[row+2][column+2].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+2][column+2] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column+1], buttonArray[row+2][column+2]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;				
				buttonArray[row+1][column+1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+1][column+1] = 2;				
				buttonArray[row+2][column+2].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+2][column+2] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column+1], buttonArray[row+2][column+2]);
			}
		}		
		if(checkOtherPlayerMatches.getDiagonalUpRightS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column+1], buttonArray[row-2][column+2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {			
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row-1][column+1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-1][column+1] = 1;
				buttonArray[row-2][column+2].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-2][column+2] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column+1], buttonArray[row-2][column+2]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row-1][column+1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-1][column+1] = 2;
				buttonArray[row-2][column+2].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-2][column+2] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column+1], buttonArray[row-2][column+2]);
			}
		}	
		if(checkOtherPlayerMatches.getDiagonalUpLeftS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row-2][column-2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row-1][column-1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-1][column-1] = 1;
				buttonArray[row-2][column-2].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-2][column-2] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row-2][column-2]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row-1][column-1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-1][column-1] = 2;
				buttonArray[row-2][column-2].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-2][column-2] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row-2][column-2]);
			}
		}	
		if(checkOtherPlayerMatches.getVerticalUpS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row-2][column]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {			
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row-1][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-1][column] = 1;
				buttonArray[row-2][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-2][column] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row-2][column]);
			}
			else { 
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row-1][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-1][column] = 2;
				buttonArray[row-2][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-2][column] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row-2][column]);
			}
		}	
		if(checkOtherPlayerMatches.getVerticalDownS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row+1][column], buttonArray[row+2][column]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row+1][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+1][column] = 1;
				buttonArray[row+2][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+2][column] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column], buttonArray[row+2][column]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row+1][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+1][column] = 2;
				buttonArray[row+2][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+2][column] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column], buttonArray[row+2][column]);
			}
		}	
		if(checkOtherPlayerMatches.getHorizontalLeftS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column-2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row][column-1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column-1] = 1;
				buttonArray[row][column-2].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column-2] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column-2]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row][column-1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column-1] = 2;
				buttonArray[row][column-2].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column-2] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column-2]);
			}
		}		
		if(checkOtherPlayerMatches.getHorizontalRightS() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row][column+1], buttonArray[row][column+2]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row][column+1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column+1] = 1;
				buttonArray[row][column+2].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column+2] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column+1], buttonArray[row][column+2]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row][column+1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column+1] = 2;
				buttonArray[row][column+2].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column+2] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column+1], buttonArray[row][column+2]);
			}
		}
		if(checkOtherPlayerMatches.getDiagonalDownO() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row+1][column+1]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row-1][column-1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-1][column-1] = 1;
				buttonArray[row+1][column+1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+1][column+1] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row+1][column+1]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row-1][column-1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-1][column-1] = 2;
				buttonArray[row+1][column+1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+1][column+1] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column-1], buttonArray[row+1][column+1]);
			}
		}
		if(checkOtherPlayerMatches.getDiagonalUpO() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row-1][column+1]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row+1][column-1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+1][column-1] = 1;
				buttonArray[row-1][column+1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-1][column+1] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row-1][column+1]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row+1][column-1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+1][column-1] = 2;
				buttonArray[row-1][column+1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-1][column+1] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row+1][column-1], buttonArray[row-1][column+1]);
			}
		}
		if(checkOtherPlayerMatches.getHorizontalO() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column+1]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row][column-1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column-1] = 1;
				buttonArray[row][column+1].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column+1] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column+1]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row][column-1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column-1] = 2;
				buttonArray[row][column+1].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column+1] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row][column-1], buttonArray[row][column+1]);
			}
		}
		if(checkOtherPlayerMatches.getVerticalO() == true) {
			gameGrid.getChildren().removeAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row+1][column]);
			if(CurrentPlayer.equals(game.getPlayerOne())) {
				buttonArray[row][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row][column] = 1;
				buttonArray[row-1][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row-1][column] = 1;
				buttonArray[row+1][column].setStyle(playerOneColor);
				playerOccupyingGridSpot[row+1][column] = 1;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row+1][column]);
			}
			else {
				buttonArray[row][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row][column] = 2;
				buttonArray[row-1][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row-1][column] = 2;
				buttonArray[row+1][column].setStyle(playerTwoColor);
				playerOccupyingGridSpot[row+1][column] = 2;
				gameGrid.getChildren().addAll(buttonArray[row][column], buttonArray[row-1][column], buttonArray[row+1][column]);
			}
		}
	}

	public void checkIfAlreadyOccupied(int row, int column) {
		if((CurrentPlayer.equals(game.getPlayerOne()))) {
			playerNumber = 2;
			letterAtCurrentSquare = buttonArray[row][column].getText();
			checkOtherPlayerMatches.checkHowManySteals(playerOccupyingGridSpot, row, column, letterAtCurrentSquare, playerNumber, gridSize);
			steals = checkOtherPlayerMatches.getSteals();
			System.out.println("steals = " + steals);
			totalSteals = totalSteals + steals;
			System.out.println("total steals = " +totalSteals);
			changeButtonColorsForSteals(row, column);	
		}
		else if((CurrentPlayer.equals(game.getPlayerTwo()))) {
			playerNumber = 1;
			letterAtCurrentSquare = buttonArray[row][column].getText();
			checkOtherPlayerMatches.checkHowManySteals(playerOccupyingGridSpot, row, column, letterAtCurrentSquare, playerNumber, gridSize);
			steals = checkOtherPlayerMatches.getSteals();
			System.out.println("steals = " + steals);
			totalSteals = totalSteals + steals;
			System.out.println("total steals = " +totalSteals);
			changeButtonColorsForSteals(row, column);	
		}
	}

}
