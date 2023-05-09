package domain;

public class Advance extends Square implements SpecialSquare{
	GameBoard board;
    public Advance(int numSquare,GameBoard board){
        super(numSquare);
		this.board=board;
    }

	@Override
	public int useTrap() {
		//System.out.println(board.findCloseStair(numSquare));
		return board.findCloseStair(numSquare);
	}
}
	


