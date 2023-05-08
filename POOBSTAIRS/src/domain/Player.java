package domain;

import java.awt.Color;

public class Player {
	private String name;
	private int numStairs;
	private int numSnakes;
	private int numSpecialSquares;
	private int maxPosition;
	private int modifiers;

	private Piece piece;
	
	public Player(String name, Color color) {
		this.name = name;
		piece = new Piece(color,this);
		numStairs = 0;
		numSnakes = 0;
		numSpecialSquares = 0;
		maxPosition = 0;
		modifiers = 0;
	}
	
	public int getPiecePosition() {
		return piece.getPosition().getNumSquare();
	}
	
	public void movePiece(Square position) {
		piece.changePositionTo(position);
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public String getName() {
		return name;
	}
	
	
	
	
	public Square getPieceSquare() {
		return piece.getPosition();
	}
	
	public int getNumSnakes() {
		return numSnakes;
	}
	
	public int getNumStairs() {
		return numStairs;
	}
	
	public int numSpecials() {
		return numSpecialSquares;
	}
	
	public int getMax() {
		return maxPosition;
	}
	
	public int getNumModifiers() {
		return modifiers;
	}
}