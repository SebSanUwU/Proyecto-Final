package domain;

public class NormalObstacle extends Obstacle{
    public NormalObstacle(Square head,Square tail,String type){
        super(head, tail, type);
    }
    
    public int use() {
    	if(getType().equals("snake")) return getNumTail();
    	else return getNumHead();
    }
}
