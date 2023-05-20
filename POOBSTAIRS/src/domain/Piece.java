package domain;

import java.awt.Color;
import java.io.Serializable;

public class Piece implements Serializable{
	private String color;
	private Square position;
	private Player owner;
	private String representation;

	
	public Piece(String color, String representation, Player owner) {
		this.color = color;
		this.owner = owner;
		this.representation = representation;
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
		owner.changeStats(numStairs, numSnakes, numSpecialSquares, actualSquare);
	}
}
