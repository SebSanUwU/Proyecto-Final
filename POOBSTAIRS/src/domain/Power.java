package domain;

public class Power {
	public static final String EXTRA_MOVE = "Mover una Casilla Adicional";
	public static final String MINUS_MOVE = "Mover una Casilla Menos";
	public static final String CHANGE = "Change";
	
	public static String[] givePowers() {
		return new String[] {EXTRA_MOVE, MINUS_MOVE, CHANGE};
	}
	
	public static int givePower(String power) {
		if(power.equals(EXTRA_MOVE)) return 1;
		else return -1;
		
	}
	
	public static int[] giveSuperPower(String power, Player player1, Player player2){
		int[] positions = {player2.getPiecePosition() - player1.getPiecePosition(), 
		player1.getPiecePosition() - player2.getPiecePosition()};
		return positions;
		
	}
	
}
