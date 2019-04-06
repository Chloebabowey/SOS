package sos_game_V4;

public class CheckSOS {
	private int points; 
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

	public int getPoints() {
		return points;
	}
	
	public void setPoints(int Points) {
		points = Points;
	}
	
	public void checkSOS(char[][] gridSpots, int row, int column, char move, int gridSize) {
		resetValues();
		if(move == 'O') {
			if(((row == 0) && (column == 0)) || ((row == gridSize-1) && (column == 0)) || ((column == gridSize-1) && (row == 0)) || ((column == gridSize-1) && (row == gridSize-1))) {
				setPoints(points);
			}
			else if((column == 0) || (column == gridSize-1)) {
				checkVerticalMatchForO(gridSpots, row, column);
			}
			else if((row == 0) || (row == gridSize-1)) {
				checkHorizontalMatchForO(gridSpots, row, column);
			}
			else {
				checkHorizontalMatchForO(gridSpots, row, column);
				checkVerticalMatchForO(gridSpots, row, column);
				checkDiagonalDownMatchForO(gridSpots, row, column);
				checkDiagonalUpMatchForO(gridSpots, row, column);	
				setPoints(points);
			}
		}
		else if(move == 'S') {
			if(((row == gridSize-2) && (row == 1)) && ((column == gridSize-1) && (column == 1))){
				setPoints(points);
			}
			else if((row > 1) & (row < gridSize-2) & (column > 1) & (column < gridSize-2)) {
				checkDiagonalDownLeftMatchForS(gridSpots, row, column);
				checkDiagonalDownRightMatchForS(gridSpots, row, column);
				checkDiagonalUpRightMatchForS(gridSpots, row, column);
				checkDiagonalUpLeftMatchForS(gridSpots, row, column);
				checkHorizontalLeftMatchForS(gridSpots, row, column);
				checkHorizontalRightMatchForS(gridSpots, row, column);
				checkVerticalDownMatchForS(gridSpots, row, column);
				checkVerticalUpMatchForS(gridSpots, row, column);
			}
			else if((row <= 1) & (column < gridSize-2) & (column > 1)) {
				checkHorizontalRightMatchForS(gridSpots, row, column);
				checkHorizontalLeftMatchForS(gridSpots, row, column);
				checkVerticalDownMatchForS(gridSpots, row, column);
				checkDiagonalDownRightMatchForS(gridSpots, row, column);
				checkDiagonalDownLeftMatchForS(gridSpots, row, column);	
				setPoints(points);
			}
			else if((row > 1) &  (row <= gridSize-2) & (column >= gridSize-2)) {
				checkHorizontalLeftMatchForS(gridSpots, row, column);
				checkVerticalUpMatchForS(gridSpots, row, column);
				checkVerticalDownMatchForS(gridSpots, row, column);
				checkDiagonalDownLeftMatchForS(gridSpots, row, column);
				checkDiagonalUpLeftMatchForS(gridSpots, row, column);
				setPoints(points);
			}
			else if((row >= gridSize-2) & (column > 1) & (column < gridSize-2)) {
				checkVerticalUpMatchForS(gridSpots, row, column);
				checkHorizontalRightMatchForS(gridSpots, row, column);
				checkHorizontalLeftMatchForS(gridSpots, row, column);
				checkDiagonalUpRightMatchForS(gridSpots, row, column);
				checkDiagonalUpLeftMatchForS(gridSpots, row, column);		
				setPoints(points);
			}
			else if((row > 1) & (row < gridSize-2) & (column <= 1)) {
				checkVerticalUpMatchForS(gridSpots, row, column);
				checkVerticalDownMatchForS(gridSpots, row, column);
				checkDiagonalUpRightMatchForS(gridSpots, row, column); 
				checkDiagonalDownRightMatchForS(gridSpots, row, column);
				checkHorizontalRightMatchForS(gridSpots, row, column);
				setPoints(points);
			}
			else if((row <= 1) & (column >= gridSize-2)) {
				checkHorizontalLeftMatchForS(gridSpots, row, column);
				checkDiagonalDownLeftMatchForS(gridSpots, row, column); 
				checkVerticalDownMatchForS(gridSpots, row, column);
				setPoints(points);
			}
			else if((row >= gridSize-2) & (column >= gridSize-2)) {
				checkVerticalUpMatchForS(gridSpots, row, column);
				checkHorizontalLeftMatchForS(gridSpots, row, column);
				checkDiagonalUpLeftMatchForS(gridSpots, row, column);
				setPoints(points);
			}
			else if((row == gridSize-1) & (column == gridSize-2) & (column == 1)) {
				checkVerticalUpMatchForS(gridSpots, row, column);
				setPoints(points);
			}
			else if((row == 1) & (row == gridSize-2) & (column == gridSize-1)) {
				checkHorizontalLeftMatchForS(gridSpots, row, column);
				setPoints(points);
			}
			else if((row == 1) & (row == gridSize-2) & (column == 0)) {
				checkHorizontalRightMatchForS(gridSpots, row, column);
				setPoints(points);
			}
			else if((row == 0) & (column == 1) & (column == gridSize-2)) {
				checkVerticalDownMatchForS(gridSpots, row, column);
				setPoints(points);
			}
			else if((row <= 1) & (column <= 1)) {
				checkDiagonalDownRightMatchForS(gridSpots, row, column);
				checkHorizontalRightMatchForS(gridSpots, row, column);
				checkVerticalDownMatchForS(gridSpots, row, column);
				setPoints(points);
			}
			else if((row >= gridSize-2) & (column <= 1)) {
				checkVerticalUpMatchForS(gridSpots, row, column);
				checkDiagonalUpRightMatchForS(gridSpots, row, column);
				checkHorizontalRightMatchForS(gridSpots, row, column);
				setPoints(points);
			}
		}
	}

	private void resetValues() {
		points = 0;
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

	public boolean checkHorizontalMatchForO(char[][] gridSpots, int row, int column) {
		if((gridSpots[row][column+1] == 'S') && (gridSpots[row][column-1] == 'S')) {
			points++;
			horizontalO = true;
			return true;
		}	
		else {
			return false;
		}
	}

	public boolean checkHorizontalRightMatchForS(char[][] gridSpots, int row, int column) {
		if((gridSpots[row][column+1] == 'O') && (gridSpots[row][column+2] == 'S')) {
			points++;
			horizontalRightS = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkHorizontalLeftMatchForS(char[][] gridSpots, int row, int column) {
		if((gridSpots[row][column-1] == 'O') && (gridSpots[row][column-2] == 'S')) {
			points++;
			horizontalLeftS = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkVerticalMatchForO(char[][] gridSpots, int row, int column) {
		if((gridSpots[row-1][column] == 'S') && (gridSpots[row+1][column] == 'S')) {
			points++;
			verticalO = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkVerticalUpMatchForS(char[][] gridSpots, int row, int column) {
		if((gridSpots[row-1][column] == 'O') && (gridSpots[row-2][column] == 'S')) {
			points++;
			verticalUpS = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkVerticalDownMatchForS(char[][] gridSpots, int row, int column) {
		if((gridSpots[row+1][column] == 'O') && (gridSpots[row+2][column] == 'S')) {
			points++;
			verticalDownS = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkDiagonalUpMatchForO(char[][] gridSpots, int row, int column) {
		if((gridSpots[row+1][column-1] == 'S') && (gridSpots[row-1][column+1] == 'S')) {
			points++;
			diagonalUpO = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkDiagonalDownMatchForO(char[][] gridSpots, int row, int column) {
		if((gridSpots[row-1][column-1] == 'S') && (gridSpots[row+1][column+1] == 'S')) {
			points++;
			diagonalDownO = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkDiagonalUpLeftMatchForS(char[][] gridSpots, int row, int column) {
		if((gridSpots[row-1][column-1] == 'O') && (gridSpots[row-2][column-2] == 'S')) {
			points++;
			diagonalUpLeftS = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkDiagonalUpRightMatchForS(char[][] gridSpots, int row, int column) {
		if((gridSpots[row-1][column+1] == 'O') && (gridSpots[row-2][column+2] == 'S')) {
			points++;
			diagonalUpRightS = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkDiagonalDownLeftMatchForS(char[][] gridSpots, int row, int column) {
		if((gridSpots[row+1][column-1] == 'O') && (gridSpots[row+2][column-2] == 'S')) {
			points++;
			diagonalDownLeftS = true;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkDiagonalDownRightMatchForS(char[][] gridSpots, int row, int column) {
		if((gridSpots[row+1][column+1] == 'O') && (gridSpots[row+2][column+2] == 'S')) {
			points++;
			diagonalDownRightS = true;
			return true;
		}
		else {
			return false;
		}
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

