package domain;

import java.util.ArrayList;

public class MachineLearner extends Machine {
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
        Square inicialSquare = getPieceSquare();
        for (int i = 0; i < analizeSpecialsSquares.length; i++) {
            getPiece().changePositionTo(inicialSquare);
            board.addPieceBoard(inicial, getPiece());
            simulatePlays.add(board.simulateChangePiece(analizeSpecialsSquares[i]-inicial, getPiece()));
            board.removePieceBoard(simulatePlays.get(i), getPiece());
        }
        int maxPlay = -1;
        int maxSquare = -1;
        System.out.println(dieFace.getValue());

        for (int i = 0; i < simulatePlays.size(); i++) {
            System.out.println((simulatePlays.get(i) + 1) + " <- simulacion de " + (analizeSpecialsSquares[i] + 1));
            if (simulatePlays.get(i) > maxSquare) {
                maxSquare = simulatePlays.get(i);
                maxPlay = analizeSpecialsSquares[i]-inicial;
            }

        }
        int aux;
        getPiece().changePositionTo(inicialSquare);
        board.addPieceBoard(inicial, getPiece());
        aux = board.simulateChangePiece(dieFace.getValue(), getPiece());
        board.removePieceBoard(aux, getPiece());
        System.out.println((aux + 1) + " <- simulacion de " + (dieFace.getValue()+inicial+1));
        if (maxSquare<aux) {
            maxSquare=aux;
            maxPlay=aux-inicial;
        }
        getPiece().changePositionTo(inicialSquare);
        board.addPieceBoard(inicial, getPiece());
        if (inicial != maxSquare) {
            board.changePieceBoard(inicial, maxSquare, getPiece());
        }
        System.out.println(1 + maxSquare + " casilla jugada " + (inicial + 1) + " casilla inicial");
        System.out.println(maxPlay);

        return 0;
        // return analizeSpecialsSquares[maxPlay];
    }
}