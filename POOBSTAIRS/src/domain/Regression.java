package domain;

public class Regression extends Square implements SpecialSquare{
	GameBoard board;
    public Regression(int numSquare,GameBoard board){
        super(numSquare);
		this.board=board;
    }

	@Override
	public int useTrap() {
		//System.out.println(board.findCloseSnake(numSquare));
		return board.findCloseSnake(numSquare);
	}
}
