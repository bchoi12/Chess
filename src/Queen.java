
public class Queen extends Piece{

	private int side;
	
	public Queen(Position p, int side){
		super("queen", p);
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
		
		for(int i=x+1;i<8;i++){
			if (occupiedPositions[i][y] == 0){
				possibleMoves[i][y] = true;
			} else {
				if (side == 0 && occupiedPositions[i][y] > 6 || side == 1 && occupiedPositions[i][y] < 7){
					possibleMoves[i][y] = true;
				}
				break;
			}
		}
		for(int i=x-1;i>-1;i--){
			if (occupiedPositions[i][y] == 0){
				possibleMoves[i][y] = true;
			} else {
				if (side == 0 && occupiedPositions[i][y] > 6 || side == 1 && occupiedPositions[i][y] < 7){
					possibleMoves[i][y] = true;
				}
				break;
			}
		}
		for(int i=y+1;i<8;i++){
			if (occupiedPositions[x][i] == 0){
				possibleMoves[x][i] = true;
			} else {
				if (side == 0 && occupiedPositions[x][i] > 6 || side == 1 && occupiedPositions[x][i] < 7){
					possibleMoves[x][i] = true;
				}
				break;
			}
		}
		for(int i=y-1;i>-1;i--){
			if (occupiedPositions[x][i] == 0){
				possibleMoves[x][i] = true;
			} else {
				if (side == 0 && occupiedPositions[x][i] > 6 || side == 1 && occupiedPositions[x][i] < 7){
					possibleMoves[x][i] = true;
				}
				break;
			}
		}
		
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
