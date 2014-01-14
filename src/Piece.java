
public abstract class Piece {
	
	private String name;
	private int type;
	private Position position;
	private boolean dead;
	private boolean white;
	
	// 0 - none
	// 1 - pawn
	// 2 - bishop
	// 3 - knight
	// 4 - rook
	// 5 - queen
	// 6 - king
	
	public Piece(String name, Position position){
		this.name = name;
		setType(name);
		this.position = position;
	}
	
	public Piece(int type, Position position){
		this.type = type;
		setName(type);
		this.position = position;
	}
	
	abstract void move(int x, int y);
	
	abstract boolean[][] getPossibleMoves(int[][] occupiedPositions);
	
	abstract int getSide();
	
	public int getType(){
		return type;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public Position getPosition(){
		if (!dead){
			return position;
		} else {
			return null;
		}
	}
	
	public int getX(){
		return position.getX();
	}
	
	public int getY(){
		return position.getY();
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	public void setType(String name){
		switch(name) {
			case "pawn":
				type = 1;
				break;
			case "bishop":
				type = 2;
				break;
			case "knight":
				type = 3;
				break;
			case "rook":
				type = 4;
				break;
			case "queen":
				type = 5;
				break;
			case "king":
				type = 6;
				break;
			default:
				type = 0;
				break;
		}
	}
	
	public void setName(int type){
		switch(type) {
			case 1:
				name = "pawn";
				break;
			case 2:
				name = "bishop";
				break;
			case 3:
				name = "knight";
				break;
			case 4:
				name = "rook";
				break;
			case 5:
				name = "queen";
				break;
			case 6:
				name = "king";
				break;
			default:
				name = "none";
				break;
		}
	}

	public String toString(){
		return name;
	}
}
