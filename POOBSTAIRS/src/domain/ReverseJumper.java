package domain;

import java.util.concurrent.ThreadLocalRandom;

public class ReverseJumper extends Square implements SpecialSquare{
    public ReverseJumper(int numSquare){
        super(numSquare);
    }
    @Override
	public int useTrap() {
		return getNumSquare() + ThreadLocalRandom.current().nextInt(-6, 0);
	}
}
