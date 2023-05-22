package domain;
import java.util.Random;
import java.io.Serializable;

public   class Obstacle implements Serializable{
    private Square head;
    private Square tail;
    private String type;
    public static boolean toChange;

    public Obstacle(Square head,Square tail,String type){
        this.head=head;
        this.tail=tail;
        this.type=type;
        
    }

    public Square getHead(){
        return head;
    }

    public Square getTail(){
        return tail;
    }
    
    public String getType() {
    	return type;
    }

    public int use(){
        return tail.getNumSquare();
    }
    
    protected void setTail(Square newTail) {
    	tail = newTail;
    }
    
    protected void setHead(Square newHead) {
    	head = newHead;
    }
    
    protected void setType(String newType) {
    	type = newType;
    }
    
    public static Obstacle change(Obstacle actual, GameBoard board) {
    	Obstacle selectedObstacle = actual;
    	Obstacle[] options = {new Obstacle(actual.getHead(),actual.getTail(), actual.getType()),
    			new Dual(actual.getHead(),actual.getTail(), actual.getType()),
    			new Weak(actual.getHead(),actual.getTail(), actual.getType(),board)};
    	Random random = new Random();
    	if(toChange) {
    		while(selectedObstacle.getClass().equals(actual.getClass())) {
    			selectedObstacle = options[random.nextInt(3)];
    		}
    	}
    	return selectedObstacle;
    }
    
    
    
   
}
