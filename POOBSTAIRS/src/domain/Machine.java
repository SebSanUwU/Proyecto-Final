package domain;

import java.awt.Color;
import java.util.Random;
import java.util.random.*;

public abstract class Machine extends Player{

	public Machine(Color personColor) {
		super("Machine", Color.RED);
		getPiece().setColor(selectColor(personColor));
	}
	
	private Color giveColor(String color) {
		if(color.equals("RED")) return Color.RED;
		else if(color.equals("BLUE")) return Color.BLUE;
		else if(color.equals("GREEN")) return Color.GREEN;
		else return Color.yellow;
	}
	
	private  Color selectColor(Color wichNot) {
		Random random = new Random();
		String[] colorOptions = { "RED", "BLUE", "YELLOW", "GREEN" };
		Color chose = giveColor(colorOptions[random.nextInt(4)]);
		if(chose.equals(wichNot)) chose = selectColor(wichNot);
		return chose;
		
	}
	
	protected abstract short makeAMove(String power, int positions, GameBoard toAnalize);

}
