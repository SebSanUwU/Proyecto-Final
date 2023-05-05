package domain;

public abstract class Square {
    private int numSquare;
    public Obstacle obstacle;

    public Square(int numSquare){
        this.numSquare=numSquare;
    }

    public void addObstacle(Obstacle obstacle){
        this.obstacle=obstacle;
    }

    public int getNumSquareBoardGUI(){
        return numSquare+1;
    }

    public int getNumSquare(){
        return numSquare;
    }
    
    public Obstacle getObstacle() throws POOBSTAIRSException{
    	if(obstacle == null) throw new POOBSTAIRSException(POOBSTAIRSException.NO_OBSTACLE);
    	return obstacle;
    }

}
