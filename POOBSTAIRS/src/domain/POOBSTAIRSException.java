package domain;

public class POOBSTAIRSException extends Exception{
	public static final String RANGE="Rango Invalido";
	public static final String NUM_OBSTACLE="Numero de invalido de obstaculos";
	public static final String SUP_POWERS = "La cara no puede recibir más poderes";
	public static final String NO_POWERS = "La cara no tiene poderes";
	public POOBSTAIRSException(String message) {
		super(message);
	}
}
