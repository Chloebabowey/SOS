package sos_game_V4;

public class Winner {
	private Player noWinner = new Player("IT'S A TIE", 0, false, 0);
	public Winner(){}

	public Player determineWinner(TurnManager Turn, Player PlayerOne, Player PlayerTwo) {
		if(playerWithMostPoints(PlayerOne, PlayerTwo) == null){
			return null;
		}
		else {
			return playerWithMostPoints(PlayerOne, PlayerTwo);		
		}
	}

	public Player playerWithMostPoints(Player PlayerOne, Player PlayerTwo) {
		if(PlayerOne.getPlayerPoints() > PlayerTwo.getPlayerPoints()) {
			return PlayerOne;
		}
		else if(PlayerOne.getPlayerPoints() < PlayerTwo.getPlayerPoints()) {
			return PlayerTwo;
		}
		else {
			return noWinner;
		}
	}
}

