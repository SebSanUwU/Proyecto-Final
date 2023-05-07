package domain;

public class Power {
	public static final String EXTRA_MOVE = "Mover una Casilla Adicional";
	public static final String MINUS_MOVE = "Mover una Casilla Menos";
	public static final String CHANGE = "Change";
	
	public static String[] givePowers() {
		return new String[] {EXTRA_MOVE, MINUS_MOVE, CHANGE};
	}
}
