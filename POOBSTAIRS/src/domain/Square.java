package domain;

import java.util.HashSet;

public  class Square {
    protected int numSquare;
    private Obstacle obstacle;
    private HashSet<Piece> pieces;

    public Square(int numSquare){
        this.numSquare=numSquare;
        pieces = new HashSet<Piece>();
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
    	pieces.add(piece);
    }
    
    protected void removePiece(Piece piece) throws POOBSTAIRSException{
    	//if(pieces.isEmpty()) throw new POOBSTAIRSException(POOBSTAIRSException.NO_PIECES);
    	pieces.remove(piece);
    }
    
   
    
    public Piece[] getPieces() {
    	Piece[] pieceS = new Piece[pieces.size()];
    	pieceS = pieces.toArray(pieceS);
		return pieceS;
    }
    
    public boolean contains(Piece piece) {
    	return pieces.contains(piece);
    }
    
    public int useObstacle() throws POOBSTAIRSException {
    	return getObstacle().getTail().getNumSquare();
    }
    
   

}
