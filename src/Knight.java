
public class Knight extends Piece{

	private int side;
	
	public Knight(Position p, int side){
		super("knight", p);
		this.side = side;
	}

	@Override
	void move(int x, int y) {
		getPosition().setPosition(x, y);
		
	}

	@Override
	boolean[][] getPossibleMoves(int[][] occupiedPositions) {
		int x = getX();
		int y = getY();
		
		boolean[][] possibleMoves = new boolean[8][8];
		
		if (x + 2 < 8){
			if (y + 1 < 8 && (occupiedPositions[x+2][y+1] == 0 || ((side == 0 && occupiedPositions[x+2][y+1] > 6) || (side == 1 && occupiedPositions[x+2][y+1] < 7))))		possibleMoves[x+2][y+1] = true;
			if (y - 1 > -1 && (occupiedPositions[x+2][y-1] == 0 || ((side == 0 && occupiedPositions[x+2][y-1] > 6) || (side == 1 && occupiedPositions[x+2][y-1] < 7))))		possibleMoves[x+2][y-1] = true;
		}
		if (x + 1 < 8){
			if (y + 2 < 8 && (occupiedPositions[x+1][y+2] == 0 || ((side == 0 && occupiedPositions[x+1][y+2] > 6) || (side == 1 && occupiedPositions[x+1][y+2] < 7))))		possibleMoves[x+1][y+2] = true;
			if (y - 2 > -1 && (occupiedPositions[x+1][y-2] == 0 || ((side == 0 && occupiedPositions[x+1][y-2] > 6) || (side == 1 && occupiedPositions[x+1][y-2] < 7))))		possibleMoves[x+1][y-2] = true;
		}
		if (x - 2 > -1){
			if (y + 1 < 8 && (occupiedPositions[x-2][y+1] == 0 || ((side == 0 && occupiedPositions[x-2][y+1] > 6) || (side == 1 && occupiedPositions[x-2][y+1] < 7))))		possibleMoves[x-2][y+1] = true;
			if (y - 1 > -1 && (occupiedPositions[x-2][y-1] == 0 || ((side == 0 && occupiedPositions[x-2][y-1] > 6) || (side == 1 && occupiedPositions[x-2][y-1] < 7))))		possibleMoves[x-2][y-1] = true;
		}
		if (x - 1 > -1){
			if (y + 2 < 8 && (occupiedPositions[x-1][y+2] == 0 || ((side == 0 && occupiedPositions[x-1][y+2] > 6) || (side == 1 && occupiedPositions[x-1][y+2] < 7))))		possibleMoves[x-1][y+2] = true;
			if (y - 2 > -1 && (occupiedPositions[x-1][y-2] == 0 || ((side == 0 && occupiedPositions[x-1][y-2] > 6) || (side == 1 && occupiedPositions[x-1][y-2] < 7))))		possibleMoves[x-1][y-2] = true;
		}
		
		return possibleMoves;
	}

	@Override
	int getSide() {
		return side;
	}

}
