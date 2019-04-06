package sos_game_V4;

public class TurnDetails {
	TurnDetailsNode head;
	TurnDetailsNode tail;
	int size = 0;
	
	public TurnDetails() 
	{
		head = tail = null;
	}
	
//	public void addToTail(Player CurrentPlayer, GameClock StartTimeOfTurn, GameClock EndTimeOfTurn, 
//			int PlayerOnePoints, int PlayerTwoPoints)
//	{
//		if (head == null) {
//			head = tail = new TurnDetailsNode(CurrentPlayer, StartTimeOfTurn, EndTimeOfTurn, PlayerOnePoints, PlayerTwoPoints);
//		size++;
//		}else {
//			tail = tail.next = new TurnDetailsNode(CurrentPlayer, StartTimeOfTurn, EndTimeOfTurn, PlayerOnePoints, PlayerTwoPoints);
//		size++;
//		}
//	}
//	
}
