package domain;
import java.util.Random;

public class Jumper extends Square implements SpecialSquare{
    public Jumper(int numSquare, GameBoard board){
        super(numSquare, board);
    }

	@Override
	public int useTrap() {
		return getNumSquare() + randomN();
	}
	private int randomN() {
		Random random = new Random();
		return random.nextInt(1,7);
	}
}
