
public class Pawn extends Piece{
	
	private boolean hasMoved;
	private boolean canEnPassantRight;
	private boolean canEnPassantLeft;
	private int	side;	// 0 = white, 1 = black
	
	public Pawn(Position p, int side){
		super("pawn", p);
		this.side = side;
		
		hasMoved = false;
		canEnPassantRight = false;
		canEnPassantLeft = false;
	}

	public void promote(int piece){
		setType(piece);	
	}
	
	@Override
	boolean[][] getPossibleMoves(int[][] occupiedPositions) {
		boolean[][] possibleMoves = new boolean[8][8];
		boolean found = false;
		int x = getPosition().getX();
		int y = getPosition().getY();
		if (getType() == 5){
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
		if (occupiedPositions[x][y-2*side+1] == 0){
			possibleMoves[x][y-2*side+1] = true;
			found = true;
			if (!hasMoved && occupiedPositions[x][y-4*side+2] == 0){
				possibleMoves[x][y-4*side+2] = true;
			}
		}
		if (x < 7){
			if (canEnPassantRight && occupiedPositions[x+1][y-2*side+1] == 0 || (side == 0 && occupiedPositions[x+1][y+1] > 6) || (side == 1 && occupiedPositions[x+1][y-1] < 7 && occupiedPositions[x+1][y-1] != 0)){
				possibleMoves[x+1][y-2*side+1] = true;
				found = true;
			}
		}
		if (x > 0){
			if (canEnPassantLeft && x > 0 && occupiedPositions[x-1][y-2*side+1] == 0 || (side == 0 && occupiedPositions[x-1][y+1] > 6) || (side == 1 && occupiedPositions[x-1][y-1] < 7 && occupiedPositions[x-1][y-1] != 0)){
				possibleMoves[x-1][y-2*side+1] = true;
				found = true;
			}
		}
		
		return found ? possibleMoves : null;
	}

	@Override
	void move(int x, int y) {
	
		hasMoved = true;
		getPosition().setPosition(x, y);
	}

	@Override
	int getSide() {
		return side;
	}
}
