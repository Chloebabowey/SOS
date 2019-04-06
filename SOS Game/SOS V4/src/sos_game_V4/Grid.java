package sos_game_V4;

public class Grid {
	private char move;
	private int gridSize;
	private char[][] gridSpots = new char[gridSize][gridSize];

	public Grid() {}

	public Grid(int row, int column) {}

	public void setGridSize(int gridSizeInput) {
		gridSize = gridSizeInput;
	}

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSpots(char[][] GridSpots) {
		gridSpots = GridSpots;
	}

	public char[][] getGridSpots() {
		return gridSpots;
	}

	public void setMove(char Move) {
		move = Move;
	}

	public char getMove() {
		return move;
	}

	public boolean checkGridSizeValidity(String gridSizeInput) {
		if(gridSizeInput.equals("3")) {
			gridSize = 3;
			setGridSize(gridSize);
			System.out.println("GRIDSIZE IN GRID CLASS = " + getGridSize());
			return true;
		}
		else if(gridSizeInput.equals("4")) {
			gridSize = 4;
			setGridSize(gridSize);
			return true;
		}
		else if(gridSizeInput.equals("5")) {
			gridSize = 5;
			setGridSize(gridSize);
			return true;
		}
		else if(gridSizeInput.equals("6")) {
			gridSize = 6;
			setGridSize(gridSize);
			return true;
		}
		else if(gridSizeInput.equals("7")) {
			gridSize = 7;
			setGridSize(gridSize);
			return true;
		}
		else if(gridSizeInput.equals("8")){
			gridSize = 8;
			setGridSize(gridSize);
			return true;
		}
		else if(gridSizeInput.equals("9")) {
			gridSize = 9;
			setGridSize(gridSize);
			return true;
		}
		else if(gridSizeInput.equals("10")) {
			gridSize = 10;
			setGridSize(gridSize);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void updateGridMoves(int row, int column, char move) {
		gridSpots [row][column] = move;
	}

	public void updateGameGridDisplay(int gridSize) {
		for(int i = 0; i < gridSize; i++) {
			System.out.print("   "+ i);
		}

		for(int j = 0; j < gridSize; j++) {
			System.out.print("\n" + j + "  ");
			for(int k = 0; k < gridSize; k++) {
				System.out.print(gridSpots[j][k] + " | ");		
			}
		}
	}

}


