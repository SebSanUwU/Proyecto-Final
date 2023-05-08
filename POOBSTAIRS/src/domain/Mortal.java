package domain;

public class Mortal extends Square implements SpecialSquare{
    public Mortal(int numSquare, GameBoard board){
        super(numSquare, board);
    }

	@Override
	public int useTrap() {
		return 0;
	}
}
