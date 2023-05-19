package domain;

public class FaceException extends Exception {
	public static final String SUP_POWERS = "La cara no puede recibir más poderes";
	public static final String NO_POWERS = "La cara no tiene poderes";
	

	public FaceException(String message) {
		super(message);
	}
}
