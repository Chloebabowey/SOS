package sos_game_V4;

public class CheckSteals {
	private int steals; 
	private boolean horizontalLeftS;
	private boolean horizontalRightS;
	private boolean verticalDownS;
	private boolean verticalUpS;
	private boolean diagonalUpRightS;
	private boolean diagonalUpLeftS;
	private boolean diagonalDownLeftS;
	private boolean diagonalDownRightS;
	private boolean diagonalUpO;
	private boolean diagonalDownO;
	private boolean verticalO;
	private boolean horizontalO;

	public void checkHowManySteals(int [][] playerOccupyingGridSpot, int row, int column, String letterMove, int playerNumber, int gridSize) {
		resetValues();
		if(letterMove == "O") {
			if(((row == 0) && (column == 0)) || ((row == gridSize-1) && (column == 0)) || ((column == gridSize-1) && (row == 0)) || ((column == gridSize-1) && (row == gridSize-1))) {
				setSteals(steals); 
			}
			else if((column == 0) || (column == gridSize-1)) {
				checkVerticalMatchForO(playerOccupyingGridSpot, row, column, playerNumber);
				setSteals(steals);
			}
			else if((row == 0) || (row == gridSize-1)) {
				checkHorizontalMatchForO(playerOccupyingGridSpot, row, column, playerNumber);
				setSteals(steals);
			}
			else {
				checkHorizontalMatchForO(playerOccupyingGridSpot, row, column, playerNumber);
				checkVerticalMatchForO(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalDownMatchForO(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalUpMatchForO(playerOccupyingGridSpot, row, column, playerNumber);	
				setSteals(steals);
			}
		}
		else if(letterMove == "S") {
			if(((row == gridSize-2) && (row == 1)) && ((column == gridSize-1) && (column == 1))){
				setSteals(steals);
			}
			else if((row > 1) & (row < gridSize-2) & (column > 1) & (column < gridSize-2)) {
				checkDiagonalDownLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalDownRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalUpRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalUpLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkHorizontalLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkHorizontalRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkVerticalDownMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkVerticalUpMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				setSteals(steals);
			}
			else if((row <= 1) & (column < gridSize-2) & (column > 1)) {
				checkHorizontalRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkHorizontalLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkVerticalDownMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalDownRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalDownLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber);	
				setSteals(steals);
			}
			else if((row > 1) &  (row <= gridSize-2) & (column >= gridSize-2)) {
				checkHorizontalLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkVerticalUpMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkVerticalDownMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalDownLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalUpLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				setSteals(steals);
			}
			else if((row >= gridSize-2) & (column > 1) & (column < gridSize-2)) {
				checkVerticalUpMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkHorizontalRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkHorizontalLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalUpRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalUpLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber);		
				setSteals(steals);
			}
			else if((row > 1) & (row < gridSize-2) & (column <= 1)) {
				checkVerticalUpMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkVerticalDownMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalUpRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber); 
				checkDiagonalDownRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkHorizontalRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				setSteals(steals);
			}
			else if((row <= 1) & (column >= gridSize-2)) {
				checkHorizontalLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalDownLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber); 
				checkVerticalDownMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				setSteals(steals);
			}
			else if((row >= gridSize-2) & (column >= gridSize-2)) {
				checkVerticalUpMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkHorizontalLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalUpLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				setSteals(steals);
			}
			else if((row == gridSize-1) & (column == gridSize-2) & (column == 1)) {
				checkVerticalUpMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				setSteals(steals);
			}
			else if((row == 1) & (row == gridSize-2) & (column == gridSize-1)) {
				checkHorizontalLeftMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				setSteals(steals);
			}
			else if((row == 1) & (row == gridSize-2) & (column == 0)) {
				checkHorizontalRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				setSteals(steals);
			}
			else if((row == 0) & (column == 1) & (column == gridSize-2)) {
				checkVerticalDownMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				setSteals(steals);
			}
			else if((row <= 1) & (column <= 1)) {
				checkDiagonalDownRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkHorizontalRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkVerticalDownMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				setSteals(steals);
			}
			else if((row >= gridSize-2) & (column <= 1)) {
				checkVerticalUpMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkDiagonalUpRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				checkHorizontalRightMatchForS(playerOccupyingGridSpot, row, column, playerNumber);
				setSteals(steals);
			}
		}
	}

	private void resetValues() {
		steals = 0;
		horizontalLeftS = false;
		horizontalRightS = false;
		verticalDownS = false;
		verticalUpS = false;
		diagonalUpRightS = false;
		diagonalUpLeftS = false;
		diagonalDownLeftS = false;
		diagonalDownRightS = false;
		diagonalUpO = false;
		diagonalDownO = false;
		verticalO = false;
		horizontalO = false;
	}

	public boolean checkHorizontalMatchForO(int [][] playerOccupyingGridSpot, int row, int column, int playerNumber) {
		if((playerOccupyingGridSpot[row][column+1] == playerNumber) && (playerOccupyingGridSpot[row][column-1] == playerNumber)) {
			steals++;
			horizontalO = true;
			return true;
		}	
		else {
			return false;
		}
	}

	public boolean checkHorizontalRightMatchForS(int [][] playerOccupyingGridSpot, int row, int column, int playerNumber) {
		if((playerOccupyingGridSpot[row][column+1] == playerNumber) && (playerOccupyingGridSpot[row][column+2] == playerNumber)) {
			steals++;
			horizontalRightS = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkHorizontalLeftMatchForS(int [][] playerOccupyingGridSpot, int row, int column, int playerNumber) {
		if((playerOccupyingGridSpot[row][column-1] == playerNumber) && (playerOccupyingGridSpot[row][column-2] == playerNumber)) {
			steals++;
			horizontalLeftS = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkVerticalMatchForO(int [][] playerOccupyingGridSpot, int row, int column, int playerNumber) {
		if((playerOccupyingGridSpot[row-1][column] == playerNumber) && (playerOccupyingGridSpot[row+1][column] == playerNumber)) {
			steals++;
			verticalO = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkVerticalUpMatchForS(int [][] playerOccupyingGridSpot, int row, int column, int playerNumber) {
		if((playerOccupyingGridSpot[row-1][column] == playerNumber) && (playerOccupyingGridSpot[row-2][column] == playerNumber)) {
			steals++;
			verticalUpS = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkVerticalDownMatchForS(int [][] playerOccupyingGridSpot, int row, int column, int playerNumber) {
		if((playerOccupyingGridSpot[row+1][column] == playerNumber) && (playerOccupyingGridSpot[row+2][column] == playerNumber)) {
			steals++;
			verticalDownS = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkDiagonalUpMatchForO(int [][] playerOccupyingGridSpot, int row, int column, int playerNumber) {
		if((playerOccupyingGridSpot[row+1][column-1] == playerNumber) && (playerOccupyingGridSpot[row-1][column+1] == playerNumber)) {
			steals++;
			diagonalUpO = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkDiagonalDownMatchForO(int [][] playerOccupyingGridSpot, int row, int column, int playerNumber) {
		if((playerOccupyingGridSpot[row-1][column-1] == playerNumber) && (playerOccupyingGridSpot[row+1][column+1] == playerNumber)) {
			steals++;
			diagonalDownO = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkDiagonalUpLeftMatchForS(int [][] playerOccupyingGridSpot, int row, int column, int playerNumber) {
		if((playerOccupyingGridSpot[row-1][column-1] == playerNumber) && (playerOccupyingGridSpot[row-2][column-2] == playerNumber)) {
			steals++;
			diagonalUpLeftS = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkDiagonalUpRightMatchForS(int [][] playerOccupyingGridSpot, int row, int column, int playerNumber) {
		if((playerOccupyingGridSpot[row-1][column+1] == playerNumber) && (playerOccupyingGridSpot[row-2][column+2] == playerNumber)) {
			steals++;
			diagonalUpRightS = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkDiagonalDownLeftMatchForS(int [][] playerOccupyingGridSpot, int row, int column, int playerNumber) {
		if((playerOccupyingGridSpot[row+1][column-1] == playerNumber) && (playerOccupyingGridSpot[row+2][column-2] == playerNumber)) {
			steals++;
			diagonalDownLeftS = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkDiagonalDownRightMatchForS(int [][] playerOccupyingGridSpot, int row, int column, int playerNumber) {
		if((playerOccupyingGridSpot[row+1][column+1] == playerNumber) && (playerOccupyingGridSpot[row+2][column+2] == playerNumber)) {
			steals++;
			diagonalDownRightS = true;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setSteals(int Steals) {
		steals = Steals;
	}
	
	public int getSteals() {
		return steals;
	}
	
	public boolean getHorizontalO() {
		return horizontalO;
	}
	
	public boolean getVerticalO() {
		return verticalO;
	}
	
	public boolean getDiagonalDownO() {
		return diagonalDownO;
	}
	
	public boolean getDiagonalUpO() {
		return diagonalUpO;
	}
	
	public boolean getDiagonalDownRightS() {
		return diagonalDownRightS;
	}
	
	public boolean getDiagonalDownLeftS() {
		return diagonalDownLeftS;
	}
	
	public boolean getDiagonalUpLeftS() {
		return diagonalUpLeftS;
	}
	
	public boolean getDiagonalUpRightS() {
		return diagonalUpRightS;
	}
	
	public boolean getVerticalUpS() {
		return verticalUpS;
	}
	
	public boolean getVerticalDownS() {
		return verticalDownS;
	}
	
	public boolean getHorizontalRightS() {
		return horizontalRightS;
	}
	
	public boolean getHorizontalLeftS() {
		return horizontalLeftS;
	}

}
