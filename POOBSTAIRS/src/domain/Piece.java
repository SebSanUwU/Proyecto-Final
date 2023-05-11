package domain;

import java.awt.Color;

public class Piece {
	private Color color;
	private Square position;
	private Player owner;

	
	public Piece(Color color, Player owner) {
		this.color = color;
		this.owner = owner;
	}
	
	protected void assignStart(Square start) {
		position = start;
	}
	public Square getPosition() {
		return position;
	}
	
	
	
	public void changePositionTo(Square newPosition) {
		
		position = newPosition;
		
	}
	
	protected void setColor(Color newColor) {
		color = newColor;
	}
	
	public Color getColor() {
		return color;
	}
	
	
	
	
}
