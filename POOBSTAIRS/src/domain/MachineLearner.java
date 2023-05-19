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

    public int play(Die.Face dieFace) {
        Integer[] analizeSpecialsSquares = board.analizeSpecials(dieFace.getValue(), this);
        ArrayList<Integer> simulatePlays = new ArrayList<Integer>();
        int inicial = getPiecePosition();
        int change;
        
        for (int i = 0; i < analizeSpecialsSquares.length; i++) {
            change=board.simulateChangePiece(analizeSpecialsSquares[i]-inicial, getPiece());
            simulatePlays.add(change);
            if(inicial!=change){
                board.changePieceBoard(change, inicial, getPiece());
            }
        }
        int maxPlay = -1;
        int maxSquare = -1;

        for (int i = 0; i < simulatePlays.size(); i++) {
            System.out.println((simulatePlays.get(i) + 1) + " <- simulacion de " + (analizeSpecialsSquares[i] + 1));
            if (simulatePlays.get(i) > maxSquare) {
                maxSquare = simulatePlays.get(i);
                maxPlay = analizeSpecialsSquares[i];
            }
        }
        
        change = board.simulateChangePiece(dieFace.getValue(), getPiece());
        if(inicial!=change){
            board.changePieceBoard(change, inicial, getPiece());
        }
        System.out.println((change + 1) + " <- simulacion de " + (dieFace.getValue()+inicial+1));
        if (maxSquare<change) {
            maxSquare=change;
            maxPlay=dieFace.getValue();
        }
        
        if (inicial != maxSquare) {
            board.changePieceBoard(inicial, maxSquare, getPiece());
        }
        System.out.println(1 + maxSquare + " casilla de llegada, " + (inicial + 1) + " casilla  de inicial");
        System.out.println((maxPlay+1)+" casilla jugada");

        return 0;
        // return analizeSpecialsSquares[maxPlay];
    }
}