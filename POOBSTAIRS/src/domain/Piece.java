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
	protected int getPosition() {
		return position.getNumSquare();
	}
	
	public void changePositionTo(Square newPosition) {
		try {
			position.removePiece(this);
		} catch (POOBSTAIRSException e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
			e.printStackTrace();
		}
		position = newPosition;
	}
	
	protected void setColor(Color newColor) {
		color = newColor;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void useObstacle() throws POOBSTAIRSException {
		if(position.getObstacle().getType().equals("snake")) {
			changePositionTo(position.getObstacle().getHead());
		}else {
			changePositionTo(position.getObstacle().getTail());
		}
	}
	
}
