package domain;

public class Power {
	public static final String EXTRA_MOVE = "Mover una Casilla Adicional";
	public static final String MINUS_MOVE = "Mover una Casilla Menos";
	public static final String CHANGE = "Change";
	
	public static String[] givePowers() {
		return new String[] {EXTRA_MOVE, MINUS_MOVE, CHANGE};
	}
	
	public static int usePower(String power) {
		if(power.equals(EXTRA_MOVE)) return 1;
		else return -1;
		
	}
	/**
	public static void usSuperPower(String power, GameBoard board, Player player1, Player player2){

		Square nextS = player2.getPieceSquare();
		try {
			board.replacePiecePosition(player1.getPiecePosition(), player2.getPiece());
			board.replacePiecePosition(nextS.getNumSquare(), player1.getPiece());
		} catch (POOBSTAIRSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	*/
}
