package domain;

public class ReverseJumper extends Square implements SpecialSquare{
    public ReverseJumper(int numSquare, GameBoard board){
        super(numSquare, board);
    }
    @Override
	public int useTrap() {
		return getNumSquare() - 5;
	}
}
