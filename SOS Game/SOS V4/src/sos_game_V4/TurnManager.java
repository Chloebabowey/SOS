package sos_game_V4;

import java.util.Random;

public class TurnManager {
	private boolean isTurn = true;
	private boolean notTurn = false;

	public TurnManager() {}

	public Player randomizedTurn(Player PlayerOne, Player PlayerTwo){
		Random randSeed = new Random();
		int number = randSeed.nextInt(2) + 1;
		if(number == 1) {
			PlayerOne.setTurn(true);
			return PlayerOne;
		}
		else {
			PlayerTwo.setTurn(true);
			return PlayerTwo;
		}
	}

	public Player getCurrentPlayer(Player PlayerOne, Player PlayerTwo) {
		if(PlayerOne.getTurn() == true) {
			return PlayerOne;
		}
		else {
			return PlayerTwo;
		}
	}

	public void changeTurn(Player PlayerOne, Player PlayerTwo) {		
		if(PlayerOne.getTurn() == true) {
			PlayerTwo.setTurn(isTurn);
			PlayerOne.setTurn(notTurn);
		}
		else {
			PlayerOne.setTurn(isTurn);
			PlayerTwo.setTurn(notTurn);
		}
	}

}
