package sos_game_V4;

public class Player {
	private String playerName;
	private long playerPoints = 0;
	private boolean turn;
	private int maxPlayerNameLength = 15;
	private int sosMatches;

	public Player() {}

	public Player(String PlayerName, long Points, boolean Turn, int SosMatches) {
		playerName = PlayerName;
		playerPoints = Points;
		turn = Turn;
		sosMatches = SosMatches;
	}

	public boolean checkPlayerNameValidity(String PlayerName) {
		if(PlayerName.length() > maxPlayerNameLength) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean getTurn() {
		return turn;
	}

	public void setTurn(boolean Turn) {
		turn = Turn;
	}

	public void addPoints(long PlayerPoints) { 
		playerPoints = playerPoints + PlayerPoints;
		setPlayerPoints(playerPoints);
	}
	
	public void addMatches(int SosMatches) { 
		sosMatches = sosMatches + SosMatches;
		setPlayerMatches(sosMatches);
	}
	
	public int getPlayerMatches() {
		return sosMatches;
	}
	
	public void setPlayerMatches(int SosMatches) {
		sosMatches = SosMatches;
	}
	
	public void subtractPoints(int steals) { 
		playerPoints = playerPoints - steals;
		setPlayerPoints(playerPoints);
	}

	public void setPlayerPoints(long PlayerPoints) {
		playerPoints = PlayerPoints;
	}

	public long getPlayerPoints() {
		return playerPoints;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String PlayerName) {
		playerName = PlayerName;
	}

	@Override
	public String toString() {
		return "Player [playerName=" + playerName + ", playerPoints=" + playerPoints + ", turn=" + turn
				+ ", maxPlayerNameLength=" + maxPlayerNameLength + ", sosMatches=" + sosMatches + "]";
	}
}
