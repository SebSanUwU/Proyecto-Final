package domain;

import java.util.ArrayList;

public  class Square {
    protected int numSquare;
    private Obstacle obstacle;
    private ArrayList<Piece> pieces;

    public Square(int numSquare){
        this.numSquare=numSquare;
        pieces = new ArrayList<Piece>();
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

    public String typeObstacle(){
        try {
            return getObstacle().getType();
        } catch (Exception e) {
            return "None";
        }
    }

    public boolean containsObstacleToUse(){
        if(obstacle!=null && this.equals(obstacle.getHead())){
            return true;
        }
        return false;
    }
    
    public int useObstacle() throws POOBSTAIRSException {
    	return getObstacle().use();
    }
}
