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

	public Player(String name) {
		this.name = name;
		numStairs = 0;
		numSnakes = 0;
		numSpecialSquares = 0;
		maxPosition = 0;
		modifiers = 0;
	}

	public int getPiecePosition() {
		return piece.getPosition().getNumSquare();
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
		return maxPosition + 1;
	}

	public int getNumModifiers() {
		return modifiers;
	}

	public void changePositionPiece(Square position) {
		piece.changePositionTo(position);
	}

	public void sumStairs(int numStairs) {
		this.numStairs += numStairs;
	}

	public void sumSnakes(int numSnakes) {
		this.numSnakes += numSnakes;
	}

	public void sumSpecialSquares(int numSpecialSquares) {
		this.numSpecialSquares += numSpecialSquares;
	}

	public void maxPositionSquare(int actualSquare) {
		if (maxPosition < actualSquare) {
			maxPosition = actualSquare;
		}
	}

	public void sumModifiers() {
		this.modifiers++;
	}

	public void setPiece(String color, String shape) {
		piece = new Piece(color, shape, this);
	}

	public void changeStats(int numStairs,int numSnakes,int numSpecialSquares,int actualSquare) {
		sumStairs(numStairs);
		sumSnakes(numSnakes);
		sumSpecialSquares(numSpecialSquares);
		maxPositionSquare(actualSquare);
	}
}