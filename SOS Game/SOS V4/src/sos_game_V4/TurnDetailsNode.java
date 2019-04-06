package sos_game_V4;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TurnDetailsNode {
	
	private String currentPlayer;
	private String startTimeOfTurn;
	private String endTimeOfTurn;
	private String playerOnePoints;
	private String playerTwoPoints;	
	
	public TurnDetailsNode(String CurrentPlayer, String StartTimeOfTurn, String EndTimeOfTurn, 
			String PlayerOnePoints, String PlayerTwoPoints) {
		currentPlayer = CurrentPlayer;
		startTimeOfTurn = StartTimeOfTurn;
		endTimeOfTurn = EndTimeOfTurn;
		playerOnePoints = PlayerOnePoints;
		playerTwoPoints = PlayerTwoPoints;
	}
	
	public String getCurrentPlayer() {
		return currentPlayer;
	}
	
	public String getStartTimeOfTurn() {
		return startTimeOfTurn;
	}
	
	public String getEndTimeOfTurn() {
		return endTimeOfTurn;
	}
	
	public String getPlayerOnePoints() {
		return playerOnePoints;
	}
	
	public String getPlayerTwoPoints() {
		return playerTwoPoints;
	}
	
}
