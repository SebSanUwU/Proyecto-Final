package domain;

public class Mortal extends Square implements SpecialSquare{
    public Mortal(int numSquare){
        super(numSquare);
    }

	@Override
	public int useTrap() {
		return 0;
	}
}
