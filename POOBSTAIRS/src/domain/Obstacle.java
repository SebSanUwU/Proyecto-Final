package domain;

public abstract class Obstacle {
    private Square head;
    private Square tail;
    private String type;

    public Obstacle(Square head,Square tail,String type){
        this.head=head;
        this.tail=tail;
        this.type=type;
    }

    public int getNumHead(){
        return head.getNumSquare();
    }

    public int getNumTail(){
        return tail.getNumSquare();
    }
    
    public String getType() {
    	return type;
    }
    

}
