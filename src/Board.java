import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.beans.*;


public class Board extends JPanel implements MouseListener{

	private final int X_OFFSET = 40;
	private final int Y_OFFSET = 40;
	private final int BOX = 50;
	
	private final int N = 0;
	private final int P = 1;
	private final int B = 2;
	private final int H = 3;
	private final int R = 4;
	private final int Q = 5;
	private final int K = 6;
	private final int WHITE = 0;
	private final int BLACK = 1;
	private final int[][] INIT_BOARD =  {{R, P, N, N, N, N, P+6, R+6},
										 {H, P, N, N, N, N, P+6, H+6},
										 {B, P, N, N, N, N, P+6, B+6},
										 {K, P, N, N, N, N, P+6, K+6},
										 {Q, P, N, N, N, N, P+6, Q+6},
										 {B, P, N, N, N, N, P+6, B+6},
										 {H, P, N, N, N, N, P+6, H+6},
										 {R, P, N, N, N, N, P+6, R+6}};

	private Image imageWhiteKing, imageWhiteQueen, imageWhiteRook, imageWhiteKnight, imageWhiteBishop, imageWhitePawn;
	private Image imageBlackKing, imageBlackQueen, imageBlackRook, imageBlackKnight, imageBlackBishop, imageBlackPawn;
	private Image imageBoxDark, imageBoxLight, imageBoxSelect;
	private final int TRANSPARENT_COLOR = (new Color(200, 191, 231)).getRGB();
	
	private ArrayList<Piece> allPieces;
	private ArrayList<Piece> whitePieces;
	private ArrayList<Piece> blackPieces;
	private ArrayList<Piece> deadPieces;
	private Piece selectedPiece;
	private int side;
	private int[][] occupiedPositions;
	private boolean[][] possibleMoves;
	private int moveStage; // 0 = initial, 1 = pick square, 2 = waiting for other
	
	private PropertyChangeSupport pcs;
	public String sendString;
	
	public Board(){
	
		addMouseListener(this);
		setBackground(Color.darkGray);
		moveStage = 0;
		
		pcs = new PropertyChangeSupport(this);
		sendString = "";
		
		allPieces = new ArrayList<Piece>();
		whitePieces = new ArrayList<Piece>();
		blackPieces = new ArrayList<Piece>();
		deadPieces = new ArrayList<Piece>();
		selectedPiece = null;

		try {
			getImages();
		} catch (FileNotFoundException e){
			System.out.println("file not found");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setFocusable(true);
		
		side = -1;
		initGame();
	}
	
	public void initGame(){
		resetBoard();
	}
	
	public void getImages() throws IOException {
		ImageIcon ii;
		
		ii = new ImageIcon(this.getClass().getResource("image\\king.png"));
		imageWhiteKing = transparent(ii.getImage());
		ii = new ImageIcon(this.getClass().getResource("image\\queen.png"));
		imageWhiteQueen = transparent(ii.getImage());
		ii = new ImageIcon(this.getClass().getResource("image\\rook.png"));
		imageWhiteRook = transparent(ii.getImage());
		ii = new ImageIcon(this.getClass().getResource("image\\knight.png"));
		imageWhiteKnight = transparent(ii.getImage());
		ii = new ImageIcon(this.getClass().getResource("image\\bishop.png"));
		imageWhiteBishop = transparent(ii.getImage());
		ii = new ImageIcon(this.getClass().getResource("image\\pawn.png"));
		imageWhitePawn = transparent(ii.getImage());
		
		ii = new ImageIcon(this.getClass().getResource("image\\king2.png"));
		imageBlackKing = transparent(ii.getImage());
		ii = new ImageIcon(this.getClass().getResource("image\\queen2.png"));
		imageBlackQueen = transparent(ii.getImage());
		ii = new ImageIcon(this.getClass().getResource("image\\rook2.png"));
		imageBlackRook = transparent(ii.getImage());
		ii = new ImageIcon(this.getClass().getResource("image\\knight2.png"));
		imageBlackKnight = transparent(ii.getImage());
		ii = new ImageIcon(this.getClass().getResource("image\\bishop2.png"));
		imageBlackBishop = transparent(ii.getImage());
		ii = new ImageIcon(this.getClass().getResource("image\\pawn2.png"));
		imageBlackPawn = transparent(ii.getImage());
		
		ii = new ImageIcon(this.getClass().getResource("image\\boxDark.png"));
		imageBoxDark = ii.getImage();
		ii = new ImageIcon(this.getClass().getResource("image\\boxLight.png"));
		imageBoxLight = ii.getImage();
		ii = new ImageIcon(this.getClass().getResource("image\\boxSelect.png"));
		imageBoxSelect = ii.getImage();
	}
	
	public void resetBoard(){
		occupiedPositions = INIT_BOARD;
		for (int i=0;i<8;i++){
			for (int j=0;j<8;j++){
				switch(occupiedPositions[i][j]){
				case P:
					Pawn pawn = new Pawn(new Position(i, j), 0);
					allPieces.add(pawn);
					whitePieces.add(pawn);
					break;
				case H:
					Knight knight = new Knight(new Position(i, j), 0);
					allPieces.add(knight);
					whitePieces.add(knight);
					break;
				case B:
					Bishop bishop = new Bishop(new Position(i, j), 0);
					allPieces.add(bishop);
					whitePieces.add(bishop);
					break;
				case R:
					Rook rook = new Rook(new Position(i, j), 0);
					allPieces.add(rook);
					whitePieces.add(rook);
					break;
				case Q:
					Queen queen = new Queen(new Position(i, j), 0);
					allPieces.add(queen);
					whitePieces.add(queen);
					break;
				case K:
					King king = new King(new Position(i, j), 0);
					allPieces.add(king);
					whitePieces.add(king);
					break;
				case P+6:
					Pawn pawnb = new Pawn(new Position(i, j), 1);
					allPieces.add(pawnb);
					blackPieces.add(pawnb);
					break;
				case H+6:
					Knight knight2 = new Knight(new Position(i, j), 1);
					allPieces.add(knight2);
					blackPieces.add(knight2);
					break;
				case B+6:
					Bishop bishop2 = new Bishop(new Position(i, j), 1);
					allPieces.add(bishop2);
					blackPieces.add(bishop2);
					break;
				case R+6:
					Rook rook2 = new Rook(new Position(i, j), 1);
					allPieces.add(rook2);
					blackPieces.add(rook2);
					break;
				case Q+6:
					Queen queen2 = new Queen(new Position(i, j), 1);
					allPieces.add(queen2);
					blackPieces.add(queen2);
					break;
				case K+6:
					King king2 = new King(new Position(i, j), 1);
					allPieces.add(king2);
					blackPieces.add(king2);
					break;
				}
			}
		}
	}
	
	public void paint(Graphics g){
		super.paint(g);
		
		for(int i=0;i<8;i++){
			g.setColor(Color.WHITE);
			g.drawString((char) (65+i) + "", X_OFFSET/3 + BOX*(i+1), Y_OFFSET/2);
			g.drawString(i+1+"", X_OFFSET/2, Y_OFFSET/2 + BOX*(i+1));
			for(int j=0;j<8;j++){
				if ((i + j) % 2 == 0){
					g.drawImage(imageBoxLight, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
				} else {
					g.drawImage(imageBoxDark, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
				}
				
				if (moveStage == 1 && possibleMoves[i][j]){
					g.drawImage(imageBoxSelect, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
				}
				switch(occupiedPositions[i][j]){
					case P:
						g.drawImage(imageWhitePawn, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
						break;
					case B:
						g.drawImage(imageWhiteBishop, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
						break;
					case H:
						g.drawImage(imageWhiteKnight, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
						break;
					case R:
						g.drawImage(imageWhiteRook, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
						break;
					case Q:
						g.drawImage(imageWhiteQueen, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
						break;
					case K:
						g.drawImage(imageWhiteKing, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
						break;
					case P+6:
						g.drawImage(imageBlackPawn, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
						break;
					case B+6:
						g.drawImage(imageBlackBishop, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
						break;
					case H+6:
						g.drawImage(imageBlackKnight, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
						break;
					case R+6:
						g.drawImage(imageBlackRook, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
						break;
					case Q+6:
						g.drawImage(imageBlackQueen, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
						break;
					case K+6:
						g.drawImage(imageBlackKing, X_OFFSET + BOX*i, Y_OFFSET + BOX*j, this);
						break;
				}
			}
		}
	}
	
//	public void actionPerformed(ActionEvent e){
//		repaint();
//	}
	
	public int[][] getOccupiedPositions(){
		return occupiedPositions;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("mouse enter omg");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("mouse left nooo");
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		e.consume();
		
		if (mouseOnBoard(e) && side != -1){
			int x = (int) ((e.getX()-X_OFFSET)/BOX);
			int y = (int) ((e.getY()-Y_OFFSET)/BOX);
			
			//System.out.println(x + " " + y);
			
			if (moveStage == 0){
				if (occupiedPositions[x][y] != 0 && (side == 0 && occupiedPositions[x][y] < 7 || side == 1 && occupiedPositions[x][y] > 6)){
					for (Piece piece : allPieces){
						if (piece.getPosition().getX() == x && piece.getPosition().getY() == y){
							if ((possibleMoves = piece.getPossibleMoves(occupiedPositions)) == null){
								break;
							}
							selectedPiece = piece;
							moveStage = 1;
							System.out.println("painting new level");
							repaint();
							break;
						}
					}
				}
			} else if (moveStage == 1){
				if (possibleMoves[x][y]){
					Piece temp = null;
					boolean flag = false;
					for (Piece piece : allPieces){
						if (piece.getPosition().getX() == x && piece.getPosition().getY() == y){
							temp = piece;
							flag = true;
						}
					}
					if (flag){
						deadPieces.add(temp);
						allPieces.remove(temp);
						if (temp.getSide() == WHITE){
							whitePieces.remove(temp);
						} else {
							blackPieces.remove(temp);
						}
					}
					
					sendString = selectedPiece.getX() + " " + selectedPiece.getY();
					
					occupiedPositions[selectedPiece.getX()][selectedPiece.getY()] = 0;
					occupiedPositions[x][y] = selectedPiece.getType()+(selectedPiece.getSide()*6);
					selectedPiece.move(x, y);
					
					if (selectedPiece.getType() == P && (selectedPiece.getY() == 0 || selectedPiece.getY() == 7)){
						Pawn pawn = (Pawn) selectedPiece;
						pawn.promote(5);
						occupiedPositions[x][y] = 5 + 6*pawn.getSide();
					}
					
					sendString += " " + selectedPiece.getType() + " " + selectedPiece.getSide() + " " + x + " " + y;
					
					moveStage = 2; 
					pcs.firePropertyChange("moved", false, true);
					
					repaint();

				} else {
					selectedPiece = null;
					moveStage = 0;
					System.out.println("cancelled");
					repaint();
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean mouseOnBoard(MouseEvent e){
		return (e.getX() > X_OFFSET && e.getX() < X_OFFSET + 8*BOX && e.getY() > Y_OFFSET && e.getY() < Y_OFFSET+8*BOX);
	}
	
	private Image transparent(Image image){
	    ImageFilter filter = new RGBImageFilter()
	    {
		    public final int filterRGB(int x, int y, int rgb)
		    {
		    	if (rgb == TRANSPARENT_COLOR){
		    		return 0x00FFFFFF & rgb;
		    	} else {
		    		return rgb;
		    	}
		    }
	    };

	    ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
	    return Toolkit.getDefaultToolkit().createImage(ip);
	}
	
	public void setSide(int side){
		this.side = side;
		if (side == 1)	moveStage = 2;
	}
	
	public void setMoveStage(int stage){
		moveStage = stage;
	}
	
	public PropertyChangeSupport getPCS(){
		return pcs;
	}

	public void movePieces(String out) {
		System.out.println("received move piece order " + sendString);
		if (moveStage == 2){
			String temp[] = out.split(" ");
			occupiedPositions[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])] = 0;
			occupiedPositions[Integer.parseInt(temp[4])][Integer.parseInt(temp[5])] = Integer.parseInt(temp[2])+6*Integer.parseInt(temp[3]);
			moveStage = 0;
			repaint();
		} else {
			System.out.println("something went very wrong");
		}
		
	}
}
