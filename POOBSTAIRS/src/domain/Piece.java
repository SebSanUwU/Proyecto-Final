package domain;

import java.awt.Color;

public class Piece {
	private String color;
	private Square position;
	private Player owner;
	private String representation;

	
	public Piece(String color, String representation, Player owner) {
		this.color = color;
		this.owner = owner;
		this.representation = representation;
	}
	
	protected void assignStart(Square start) {
		position = start;
	}
	public Square getPosition() {
		return position;
	}

	public int getIntPosition(){
		return position.getNumSquare();
	}
	
	public void changePositionTo(Square newPosition) {
		position = newPosition;
	}
	
	protected void setColor(String newColor) {
		color = newColor;
	}
	
	public String getColor() {
		return color;
	}
	
	public String getRepresentation() {
		return representation;
	}

	public void changeStats(int numStairs,int numSnakes,int numSpecialSquares,int actualSquare){
		owner.sumStairs(numStairs);
		owner.sumSnakes(numSnakes);
		owner.sumSpecialSquares(numSpecialSquares);
		owner.maxPositionSquare(actualSquare);
	}
	
	
	
	
}
