
public class King extends Piece{

	private int side;
	private boolean canCastle;
	
	public King(Position p, int side){
		super("king", p);
		this.side = side;
		canCastle = true;
	}

	@Override
	void move(int x, int y) {
		canCastle = false;
		getPosition().setPosition(x, y);
		
	}

	@Override
	boolean[][] getPossibleMoves(int[][] occupiedPositions) {
		int x = getX();
		int y = getY();
		
		boolean[][] possibleMoves = new boolean[8][8];
		
		if (x + 1 < 8){
			possibleMoves[x+1][y] = (occupiedPositions[x+1][y] == 0 || (side == 0 && occupiedPositions[x+1][y] > 6|| side == 1 && occupiedPositions[x+1][y] < 7));
			if (y + 1 < 8){
				possibleMoves[x+1][y+1] = (occupiedPositions[x+1][y+1] == 0 || (side == 0 && occupiedPositions[x+1][y+1] > 6|| side == 1 && occupiedPositions[x+1][y+1] < 7));
			}
			if (y - 1 > -1){
				possibleMoves[x+1][y-1] = (occupiedPositions[x+1][y-1] == 0 || (side == 0 && occupiedPositions[x+1][y-1] > 6|| side == 1 && occupiedPositions[x+1][y-1] < 7));
			}
		}
		if (x - 1 > -1){
			possibleMoves[x-1][y] = (occupiedPositions[x-1][y] == 0 || (side == 0 && occupiedPositions[x-1][y] > 6|| side == 1 && occupiedPositions[x-1][y] < 7));
			if (y + 1 < 8){
				possibleMoves[x-1][y+1] = (occupiedPositions[x-1][y+1] == 0 || (side == 0 && occupiedPositions[x-1][y+1] > 6|| side == 1 && occupiedPositions[x-1][y+1] < 7));
			}
			if (y - 1 > -1){
				possibleMoves[x-1][y-1] = (occupiedPositions[x-1][y-1] == 0 || (side == 0 && occupiedPositions[x-1][y-1] > 6|| side == 1 && occupiedPositions[x-1][y-1] < 7));
			}
		}
		if (y + 1 < 8){
			possibleMoves[x][y+1] = (occupiedPositions[x][y+1] == 0 || (side == 0 && occupiedPositions[x][y+1] > 6|| side == 1 && occupiedPositions[x][y+1] < 7));
		}
		if (y - 1 > -1){
			possibleMoves[x][y-1] = (occupiedPositions[x][y-1] == 0 || (side == 0 && occupiedPositions[x][y-1] > 6|| side == 1 && occupiedPositions[x][y-1] < 7));
		}
		
		return possibleMoves;

	}

	@Override
	int getSide() {
		return side;
	}

}
