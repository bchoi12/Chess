
public class Rook extends Piece{

	private int side;
	
	public Rook(Position p, int side){
		super("rook", p);
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
		
		return possibleMoves;
	}

	@Override
	int getSide() {
		return side;
	}

}
