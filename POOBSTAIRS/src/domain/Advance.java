package domain;

import java.io.Serializable;

public class Advance extends Square implements SpecialSquare, Serializable{
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
	


