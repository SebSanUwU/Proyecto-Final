package domain;

import java.util.Random;

public class ReverseJumper extends Square implements SpecialSquare{
    public ReverseJumper(int numSquare, GameBoard board){
        super(numSquare, board);
    }
    @Override
	public int useTrap() {
		return getNumSquare() - randomN();
	}
    private int randomN() {
		Random random = new Random();
		return random.nextInt(1,7);
	}
}
