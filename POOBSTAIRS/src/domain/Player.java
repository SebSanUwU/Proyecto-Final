package domain;

import java.awt.Color;

public class Player {
	private String name;
	private int numStairs;
	private int numSnakes;
	private int numSpecialSquares;
	private int maxPosition;
	private Piece piece;
	
	public Player(String name, Color color) {
		this.name = name;
		piece = new Piece(color,this);
		numStairs = 0;
		numSnakes = 0;
		numSpecialSquares = 0;
		maxPosition = 0;
	}
	
	public int getPiecePosition() {
		return piece.getPosition();
	}
	
	public int movePiece(int positions) {
		return getPiecePosition() + positions;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public String getName() {
		return name;
	}
	
	protected void usePower(String power, GameBoard board, int extraMoves) throws POOBSTAIRSException {
		if(power.equals(Power.EXTRA_MOVE)) {
			board.advancePlayer(extraMoves + 1, piece);
		}else {
			board.advancePlayer(extraMoves - 1, piece);
		}
	}
	
}