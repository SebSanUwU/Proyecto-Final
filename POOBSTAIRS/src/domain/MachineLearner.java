package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class MachineLearner extends Machine{
    private GameBoard board;

    public MachineLearner(String name, GameBoard board) {
        super(name);
        this.board = board;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    public int[] play(int value) {
        Integer[] analizeSpecialsSquares = board.analizeSpecials(value, this);
        ArrayList<int[]> simulatePlays = new ArrayList<int[]>();
        int inicial = getPiecePosition();
        int[] change;
        
        for (int i = 0; i < analizeSpecialsSquares.length; i++) {
            change=board.simulateChangePiece(analizeSpecialsSquares[i]-inicial, getPiece(),0,0,0);
            simulatePlays.add(change);
            if(inicial!=change[0]){
                board.changePieceBoard(change[0], inicial, getPiece());
            }
        }
        int maxPlay = -1;
        int[] maxSquare=new int[5];

        for (int i = 0; i < simulatePlays.size(); i++) {
            //System.out.println((simulatePlays.get(i)[0] + 1) + " <- simulacion de " + (analizeSpecialsSquares[i] + 1));
            if (simulatePlays.get(i)[0] > maxSquare[0]) {
                maxSquare = simulatePlays.get(i);
                maxPlay = analizeSpecialsSquares[i];
            }
        }
        
        change = board.simulateChangePiece(value, getPiece(),0,0,0);
        if(inicial!=change[0]){
            board.changePieceBoard(change[0], inicial, getPiece());
        }
        System.out.println((change[0] + 1) + " <- simulacion de " + (value+inicial+1));
        if (maxSquare[0]<change[0]) {
            maxSquare=change;
            maxPlay=value+inicial;
        }
        //System.out.println(1 + maxSquare[0] + " casilla de llegada, " + (inicial + 1) + " casilla  de inicial");
        //System.out.println((maxPlay+1)+" casilla jugada");
        return maxSquare;
    }
}