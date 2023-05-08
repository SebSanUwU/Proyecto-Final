package domain;

public class Regression extends Square implements SpecialSquare{
    public Regression(int numSquare, GameBoard board){
        super(numSquare, board);
    }

	@Override
	public int useTrap() {
		int square = getNumSquare();
		for(int i = board.getObstacleSquares().indexOf(square) - 1; i >= 0; i--) {
			try {
				if(board.getObstacleSquares().get(i) < square && board.find(board.getObstacleSquares().get(i)).getObstacle().getType().equals("snake") ) {
					square = board.find(board.getObstacleSquares().get(i)).useObstacle();
					break;
				}
			} catch (POOBSTAIRSException e) {
				e.printStackTrace();
			}
		}
		return square;
	}
}
