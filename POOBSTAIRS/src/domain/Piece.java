package domain;

public class Piece {
	private String color;
	private Square position;
	private Player owner;
	
	public Piece(String color, Player owner) {
		this.color = color;
		this.owner = owner;
	}
	
	protected void assignStart(Square start) {
		position = start;
	}
	protected int getPosition() {
		return position.getNumSquare();
	}
	
	public void changePositionTo(Square newPosition) {
		if(position != null)position.removePiece(this);
		position = newPosition;
	}
	
}
