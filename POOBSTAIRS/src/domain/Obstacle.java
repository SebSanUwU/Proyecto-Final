package domain;

import java.io.Serializable;

public   class Obstacle implements Serializable{
    private Square head;
    private Square tail;
    private String type;
    /*Por aplicar */
    private boolean change;

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
}
