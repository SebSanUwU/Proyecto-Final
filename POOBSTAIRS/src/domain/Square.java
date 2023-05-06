package domain;

public abstract class Square {
    protected int numSquare;
    private Obstacle obstacle;
    private Piece[] pieces;

    public Square(int numSquare){
        this.numSquare=numSquare;
        pieces = new Piece[2];
    }

    public void addObstacle(Obstacle obstacle){
        this.obstacle=obstacle;
    }

    public int getNumSquare(){
        return numSquare;
    }
    
    public Obstacle getObstacle() throws POOBSTAIRSException{
    	if(obstacle == null) throw new POOBSTAIRSException(POOBSTAIRSException.NO_OBSTACLE);
    	return obstacle;
    }
    
    protected void receivePiece(Piece piece) {
    	if(pieces[0] == null) pieces[0] = piece;
    	else pieces[1] = piece;
    }
    
    protected void removePiece(Piece piece) {
    	if(pieces[0] != null) pieces[0] = null;
    	else pieces[1] = null;
    }
    
    public int useObstacle() throws POOBSTAIRSException{
    	return getObstacle().use();
    }

}
