package domain;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MachineBegginer extends Machine{
    private GameBoard board;
    public MachineBegginer(String name, GameBoard board){
        super(name);
        this.board=board;
    }
    public void setBoard(GameBoard board) {
        this.board = board;
    }
    public int[] play(int value) {
        Integer[] analizeSpecialsSquares = board.analizeSpecials(value, this);
        int randomChosse=ThreadLocalRandom.current().nextInt(0, analizeSpecialsSquares.length+1);
        int inicial = getPiecePosition();
        int[] change;
        if(randomChosse==(analizeSpecialsSquares.length)){
            change=board.simulateChangePiece(value, getPiece(),0,0,0);
        }else{
            change=board.simulateChangePiece(analizeSpecialsSquares[randomChosse]-inicial, getPiece(),0,0,0);
        }
        return change;
    }
}
