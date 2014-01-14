
public class Bishop extends Piece{

	private int side;
	
	public Bishop(Position p, int side){
		super("bishop", p);
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
		
		int i, j;
		
		i = x+1;
		j = y+1;
		while (i < 8 && j < 8){
			if (occupiedPositions[i][j] == 0){
				possibleMoves[i][j] = true;
			} else {
				if (side == 0 && occupiedPositions[i][j] > 6 || side == 1 && occupiedPositions[i][j] < 7){
					possibleMoves[i][j] = true;
				}
				break;
			}
			i++;
			j++;
		}
		i = x+1;
		j = y-1;
		while (i < 8 && j > -1){
			if (occupiedPositions[i][j] == 0){
				possibleMoves[i][j] = true;
			} else {
				if (side == 0 && occupiedPositions[i][j] > 6 || side == 1 && occupiedPositions[i][j] < 7){
					possibleMoves[i][j] = true;
				}
				break;
			}
			i++;
			j--;
		}
		i = x-1;
		j = y+1;
		while (i > -1 && j < 8){
			if (occupiedPositions[i][j] == 0){
				possibleMoves[i][j] = true;
			} else {
				if (side == 0 && occupiedPositions[i][j] > 6 || side == 1 && occupiedPositions[i][j] < 7){
					possibleMoves[i][j] = true;
				}
				break;
			}
			i--;
			j++;
		}
		i = x-1;
		j = y-1;
		while (i > -1 && j > -1){
			if (occupiedPositions[i][j] == 0){
				possibleMoves[i][j] = true;
			} else {
				if (side == 0 && occupiedPositions[i][j] > 6 || side == 1 && occupiedPositions[i][j] < 7){
					possibleMoves[i][j] = true;
				}
				break;
			}
			i--;
			j--;
		}
		
		return possibleMoves;
	}

	@Override
	int getSide() {
		return side;
	}

}
