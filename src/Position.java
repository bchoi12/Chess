
public class Position {

	private int x, y;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(String p){
		x = p.charAt(0) - 65;
		y = p.charAt(1) - 48;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public String getPosition(){
		return (char) (65 + x) + "" + (char) (49 + y);
	}
	
	public String getXChar(){
		return Character.getName(65 + x);
	}
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
}
