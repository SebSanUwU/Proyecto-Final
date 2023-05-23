package domain;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Machine extends Player{
	protected GameBoard board;

	public Machine(String name) {
		super(name);
	}

	public abstract int[] play(int value);
	
	public void setBoard(GameBoard board) {
        this.board = board;
    }
	
	public void setColorPiece(Player humanPlayer){
		String color = humanPlayer.getPiece().getColor();
		int random;
		while(true){
			random = ThreadLocalRandom.current().nextInt(0, 4);
			if(random==0 && !"RED".equals(color)){
				setPiece("RED", humanPlayer.getPiece().getRepresentation());
				break;
			}else if(random==1 && !"BLACK".equals(color)){
				setPiece("BLACK", humanPlayer.getPiece().getRepresentation());
				break;
			}else if(random==2 && !"BLUE".equals(color)){
				setPiece("BLUE", humanPlayer.getPiece().getRepresentation());
				break;	
			}else if(random==3 && !"GREEN".equals(color)){
				setPiece("GREEN", humanPlayer.getPiece().getRepresentation());
				break;
			}
		}
	}
}
