package sos_game_V4;

public class GameManager {
	private TurnManager Turn = new TurnManager();
	private Player PlayerOne;
	private Player PlayerTwo;
	private Barricade barricade;
	private long playerPoints = 0;
	private int gridSizeInput;
	private Grid gameGrid = new Grid();
	private int row;
	private int column;
	private char move;
	private char[][] gridSpots;
	private Player currentPlayer;
	private CheckSOS sosMatchChecker = new CheckSOS();
	private Winner winner = new Winner();
	private String playerName;
	private Player WinningPlayer;
	private int gridFull;
	private int sosMatches = 0;

	public GameManager() {
	};

	public GameManager(Barricade bar) {
		barricade = bar;
	}

	public void GamePlay() {
		resetValues();
		getMove();
		makeMove();
		sosMatchChecker.checkSOS(gridSpots, row, column, move, gridSizeInput);
		playerPoints = sosMatchChecker.getPoints();
		sosMatches = sosMatchChecker.getPoints();
		if (playerPoints != 0) {
			currentPlayer().addPoints(playerPoints);
			playerPoints = currentPlayer().getPlayerPoints();
			setPlayerOnePoints();
			setPlayerTwoPoints();
		} else {
			Turn.changeTurn(PlayerOne, PlayerTwo);
			currentPlayer();
		}
		gameGrid.updateGameGridDisplay(gridSizeInput);
		if (isGridFull() == (gridSizeInput * gridSizeInput)) {
			winningPlayer();
			hasWinner();
		}
	}

	public void ExtremeGamePlay() {
		resetValues();
		getMove();
		makeMove();
		sosMatchChecker.checkSOS(gridSpots, row, column, move, gridSizeInput);
		playerPoints = sosMatchChecker.getPoints();
		sosMatches = sosMatchChecker.getPoints();
		if (playerPoints != 0) {
			currentPlayer().addMatches(sosMatches);
			sosMatches = currentPlayer().getPlayerMatches();
			playerPoints = (int) Math.pow(2, sosMatches - 1);
			currentPlayer().setPlayerPoints(playerPoints);
			sosMatches = sosMatchChecker.getPoints();
			setPlayerOnePoints();
			setPlayerTwoPoints();
		} else {
			Turn.changeTurn(PlayerOne, PlayerTwo);
			currentPlayer();
		}
		gameGrid.updateGameGridDisplay(gridSizeInput);
		if (isGridFull() == (gridSizeInput * gridSizeInput)) {
			winningPlayer();
			hasWinner();
		}
	}

	public boolean isPlayerOneTurn() {
		return PlayerOne.getTurn() == true;
	}

	public void setPlayerTurn(boolean isPlayerOneTurn) {
		getPlayerOne().setTurn(isPlayerOneTurn);
		getPlayerTwo().setTurn(!isPlayerOneTurn);

		System.out.println("current turn is " + currentPlayer());
	}

	private void resetValues() {
		playerPoints = 0;
		sosMatches = 0;
	}

	private void makeMove() {
		gameGrid.updateGridMoves(row, column, move);
		gameGrid.setGridSpots(gridSpots);
	}

	public void resetPlayerPoints() {
		PlayerOne.setPlayerPoints(0);
		PlayerTwo.setPlayerPoints(0);
		setPlayerOnePoints();
		setPlayerTwoPoints();

	}

	public Barricade getBarricade() {
		return barricade;
	}

	private void getMove() {
		gridSpots = gameGrid.getGridSpots();
		move = barricade.getMove();
		row = barricade.getMoveRow();
		column = barricade.getMoveColumn();
	}

	public char[][] getGridSpots() {
		return gridSpots;
	}

	public int isGridFull() {
		gridFull = 0;
		for (int i = 0; i < gridSizeInput; i++) {
			for (int j = 0; j < gridSizeInput; j++) {
				if (gridSpots[i][j] == 'S' | gridSpots[i][j] == 'O') {
					gridFull++;
				}
			}
		}
		return gridFull;
	}

	public boolean hasWinner() {
		if (isGridFull() == (gridSizeInput * gridSizeInput)) {
			return true;
		} else {
			return false;
		}
	}

	public int getMatches() {
		return sosMatches;
	}

	public void randomFirstTurn() {
		currentPlayer = Turn.randomizedTurn(PlayerOne, PlayerTwo);
	}

	public Player currentPlayer() {
		currentPlayer = Turn.getCurrentPlayer(PlayerOne, PlayerTwo);
		return currentPlayer;
	}

	public Player winningPlayer() {
		WinningPlayer = winner.determineWinner(Turn, PlayerOne, PlayerTwo);
		return WinningPlayer;
	}

	public String printWinningPlayer(Player winningPlayer) {
		playerName = winningPlayer.getPlayerName().toUpperCase();
		return playerName;
	}

	public String printCurrentPlayerName(Player currentPlayer) {
		playerName = currentPlayer.getPlayerName().toUpperCase();
		return playerName;
	}

	public void setPlayerOnePoints() {
		barricade.setPlayerOnePoints(PlayerOne.getPlayerPoints());
	}

	public void setPlayerTwoPoints() {
		barricade.setPlayerTwoPoints(PlayerTwo.getPlayerPoints());
	}

	public String getPlayerOneName() {
		playerName = PlayerOne.getPlayerName().toUpperCase();
		return playerName;
	}

	public String getPlayerTwoName() {
		playerName = PlayerTwo.getPlayerName().toUpperCase();
		return playerName;
	}

	public Player getPlayerOne() {
		return PlayerOne;
	}

	public Player getPlayerTwo() {
		return PlayerTwo;
	}

	public void initializeGrid() {
		gridSizeInput = barricade.getGridSize();
		gameGrid.setGridSize(gridSizeInput);
		gridSpots = new char[gridSizeInput][gridSizeInput];
		gameGrid.setGridSpots(gridSpots);
		gameGrid.updateGameGridDisplay(gridSizeInput);
	}

	public void initializePlayers() {
		PlayerOne = new Player(barricade.getPlayerOneName(), playerPoints, false, sosMatches);
		PlayerTwo = new Player(barricade.getPlayerTwoName(), playerPoints, false, sosMatches);
	}

	public String quit() {
		Turn.changeTurn(PlayerOne, PlayerTwo);
		WinningPlayer = Turn.getCurrentPlayer(PlayerOne, PlayerTwo);
		playerName = printWinningPlayer(WinningPlayer);
		return playerName;
	}

	public CheckSOS getSOSChecker() {
		return sosMatchChecker;
	}

}
