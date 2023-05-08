package domain;

public class Advance extends Square implements SpecialSquare{
    public Advance(int numSquare, GameBoard board){
        super(numSquare, board);
    }

	@Override
	public int useTrap() {
		int square = getNumSquare();
		for(Integer i: board.getObstacleSquares()) {
			try {
				if(i > square && board.find(i).getObstacle().getType().equals("stair")){
					square = board.find(i).useObstacle();
					break;
				}
			} catch (POOBSTAIRSException e) {
				e.printStackTrace();
			}
		}
		return square;
	}
}
	


