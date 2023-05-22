package domain;

public class Dual extends Obstacle {

	public Dual(Square head, Square tail, String type) {
		super(head, tail, type);
		if(this.getType().equals("snake")) this.setType("stair");
		else this.setType("snake");
		this.setTail(head);
		this.setHead(tail);
	}

}
