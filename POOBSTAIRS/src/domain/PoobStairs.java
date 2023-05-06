package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;



public class PoobStairs {
	private boolean playerOnTurn;
	private Player[] players;
	private GameBoard board;
	//private Square[] boardLine;
	private Die dice;

	/**
	 * Constructor de la clase PoobStairs
	 * 
	 * @param rows     , numero de filas deseadas.
	 * @param columns, numero de columnas deseadas.
	 * @throws POOBSTAIRSException Se lanza la excepcion de RANGE si se llega a
	 *                             tener un numero
	 *                             invalido de columnas o de filas.
	 */
	public PoobStairs(int rows, int columns) throws POOBSTAIRSException {
		playerOnTurn = true;
		players = new Player[2];
		board = new GameBoard(rows, columns);
	}

	/**
	 * Este metodo generara todo el tablero y el dado. Para el tablero se generan el
	 * numero deseado
	 * de obstaculos mas las casillas especiales. Para el dado se va configurar el
	 * porcentaje
	 * de modificador.
	 * 
	 * @param numSnakes , numero de escaleras a construir
	 * @param numStairs , numero de serpientes a construir
	 * @param pModifier , porcentaje de modificador de moviemineto para el dado
	 * @param pSpecial  , porcentaje de casillas especiales a construir
	 * @throws POOBSTAIRSException NUM_OBSTACLE, si Se lanza un excepcion si se llega a tener un
	 *                             numero excesivo
	 *                             de casillas especiales y obstaculos.
	 */
	public void setGame(int numSnakes, int numStairs, float pModifier, float pSpecial) throws POOBSTAIRSException {
		board.setArea(numSnakes, numStairs, pModifier, pSpecial);
	}
	
	public Square[][] board(){
		return board.getSquares();
	}

	public boolean movePlayer(byte positions) {
		return false;
	}

	
	
}
