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

    public int getNumSquare(){
        return numSquare;
    }

}
