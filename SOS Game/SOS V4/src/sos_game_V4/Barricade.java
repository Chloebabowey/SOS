package sos_game_V4;

public class Barricade {
	private Grid grid = new Grid();
	private Player player;
	private Player currentPlayer;
	private String currentPlayerString;
	private String playerOneName;
	private String playerTwoName;
	private String gridSizeInputString;
	private int gridSize;
	private char move;
	private int moveRow;
	private int moveColumn;
	private String winnerName;
	private long playerOnePoints;
	private long playerTwoPoints;
	private int port;
	private String ip;
	private boolean multiplayer;

	public boolean isMultiplayer() {
		return multiplayer;
	}

	public void setMultiplayer(boolean Multiplayer) {
		multiplayer = Multiplayer;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setPlayerNames(String playerOneNameInput, String playerTwoNameInput) {
		playerOneName = playerOneNameInput;
		playerTwoName = playerTwoNameInput;
	}

	public void setPlayerOnePoints(long playerPoints) {
		playerOnePoints = playerPoints;
	}

	public void setPlayerTwoPoints(long playerPoints) {
		playerTwoPoints = playerPoints;
	}

	public long getPlayerOnePoints() {
		return playerOnePoints;
	}

	public long getPlayerTwoPoints() {
		return playerTwoPoints;
	}

	public void setWinnerName(String winningPlayer) {
		winnerName = winningPlayer;
	}

	public String getWinningPlayerName() {
		return winnerName;
	}
	
	public String getPlayerOneName() {
		return playerOneName;
	}

	public String getPlayerTwoName() {
		return playerTwoName;
	}

	public void setMoveRow(int row) {
		moveRow = row;
	}

	public void setMoveColumn(int column) {
		moveColumn = column;
	}

	public int getMoveRow() {
		return moveRow;
	}

	public int getMoveColumn() {
		return moveColumn;
	}

	public void setMove(char letterMove) {
		move = letterMove;
	}

	public char getMove() {
		return move;
	}
	
	public void resetGrid() {
		
	}

	public void setGridSizeInputString(String gridSizeInputStringFromGui) {
		gridSizeInputString = gridSizeInputStringFromGui;
	}

	public boolean isValidGridSize(String gridSizeInputString) {
		if(grid.checkGridSizeValidity(gridSizeInputString) == true) {
			gridSize = grid.getGridSize();
			return true;
		}
		else {
			return false;
		}
	}

	public int getGridSize() {
		return gridSize;
	}
	
	public void setGridSize(int GridSize) {
		gridSize = GridSize;
	}

	public void setIP(String IP) {
		ip = IP;
	}

	public void setCurrentPlayer(Player CurrentPlayer) {
		currentPlayer = CurrentPlayer;
	}

	public String currentPlayerToString() {
		currentPlayerString = currentPlayer.getPlayerName().toUpperCase();
		return currentPlayerString;
	}

	public void setPlayerOneName(String playerName) {
		playerOneName = playerName;
	}

	public void setPlayerTwoName(String playerName) {
		playerTwoName = playerName;
	}

	public boolean isValidPlayerName(String playerName) {
		if(player.checkPlayerNameValidity(playerName) == true) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setPort(int Port) {
		port = Port;
	}
	
	public int getPort() {
		return port;
	}

}
