package domain;

import java.util.concurrent.ThreadLocalRandom;

public class ReverseJumper extends Square implements SpecialSquare{
    public ReverseJumper(int numSquare){
        super(numSquare);
    }
    @Override
	public int useTrap() {
		return getNumSquare() + ThreadLocalRandom.current().nextInt(-7, 0);
	}
    
    /**
    @Override
	public int useTrap() {
		return ThreadLocalRandom.current().nextInt(-7, 0);
	}
	*/
}
