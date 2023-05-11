package domain;

public class POOBSTAIRSException extends Exception{
	public static final String RANGE="Rango Invalido";
	public static final String NUM_OBSTACLE="Numero de invalido de obstaculos";
	public static final String SUP_POWERS = "La cara no puede recibir más poderes";
	public static final String NO_POWERS = "La cara no tiene poderes";
	public static final String NO_OBSTACLE = "La casilla no tiene obstaculos";
	public static final String NO_MORE_SQUARES = "El numero de pasos supera al numero de casillas";
	public static final String INACCEPTED_PERCENTAGE = "Los porcentajes no pueden ser mayor a 1";
	public static final String NO_PIECES= "No ahí piezas en esta casilla";
	public static final String NO_MORE_PIECES= "La casilla no puede haceptar más piezas";
	public static final String NO_SUPER_POWER= "No es un super poder";
	public static final String NO_SPECIALS= "No existen casillas especiales en el rango";
	public static final String NO_MOVEMENTS= "No se realiza ningun movimiento";
	public POOBSTAIRSException(String message) {
		super(message);
	}
}
