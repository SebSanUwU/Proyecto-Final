package domain;

public class Weak extends Obstacle {
	private GameBoard board;

	public Weak(Square head, Square tail, String type, GameBoard board) {
		super(head, tail, type);
		this.board = board;
		
	}
	
	public int use() {
		int[] routeh = board.findRC(getHead());
		int[] routet = board.findRC(getTail());
		Square newTail = new Jumper(0);
		Square destination = getTail();
		boolean itCanBreak = true;
		while(newTail instanceof SpecialSquare && itCanBreak) {
			int[] path = board.findPath(getHead(), destination);
			if(path[0] == 1 ) {
				itCanBreak = false;
				newTail = getTail();
				break;
			}
			if(routeh[1] >= routet[1]) {
				if(getType().equals("snake")) newTail = board.findSquare(routeh[0] + ((int) path[0]/2), routeh[1] - ((int) path[1]/2));	
				else newTail = board.findSquare(routeh[0] - ((int) path[0]/2), routeh[1] - ((int) path[1]/2));	
			}else {
				if(getType().equals("snake")) newTail = board.findSquare(routeh[0] + ((int) path[0]/2), routeh[1] + ((int) path[1]/2));	
				else newTail = board.findSquare(routeh[0] - ((int) path[0]/2), routeh[1] + ((int) path[1]/2));
			}
			destination = newTail;
		}
		
		
		board.changeObstacleTail(getTail(), newTail, this);
		setTail(newTail);
		
		return newTail.getNumSquare();
	}
	
	
	
}
