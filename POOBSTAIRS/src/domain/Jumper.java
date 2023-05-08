package domain;

public class Jumper extends Square implements SpecialSquare{
    public Jumper(int numSquare, GameBoard board){
        super(numSquare, board);
    }

	@Override
	public int useTrap() {
		return getNumSquare() + 5;
	}
}
