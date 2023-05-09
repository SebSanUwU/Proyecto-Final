package domain;

import java.util.concurrent.ThreadLocalRandom;

public class Jumper extends Square implements SpecialSquare{
    public Jumper(int numSquare){
        super(numSquare);
    }

	@Override
	public int useTrap() {
		return  ThreadLocalRandom.current().nextInt(1, 7) + getNumSquare();
	}
}
