package domain;



import java.util.Random;


import domain.Die.Face;

/**
 * Clase principal del modelo para lograr que el juego POOBSTAIRS funcione correctamente
 * @author Castaño, Camargo
 * 03/05/2023
 *
 */

public class PoobStairs {
	private int playerOnTurn;
	private Player[] players;
	private GameBoard board;
	//private Square[] boardLine;
	private Die die;


	/**
	 * Constructor de la clase PoobStairs
	 * 
	 * @param rows     , numero de filas deseadas.
	 * @param columns, numero de columnas deseadas.
	 * @param players, arreglo de participantes en el juego
	 * @throws POOBSTAIRSException Se lanza la excepcion de RANGE si se llega a
	 *                             tener un numero
	 *                             invalido de columnas o de filas.
	 */
	public PoobStairs(int rows, int columns, Player[] players) throws POOBSTAIRSException {
		playerOnTurn = (new Random().nextInt(2));
		this.players = players;
		board = new GameBoard(rows, columns);
	}

	/**
	 * Este metodo generara todo el tablero y el dado. Para el tablero se generan el
	 * numero deseado
	 * de obstaculos mas las casillas especiales. Para el dado se va configurar el
	 * porcentaje
	 * de modificador. Además agrega las piezas de los jugadores en el tablero
	 * @param numSnakes , numero de escaleras a construir
	 * @param numStairs , numero de serpientes a construir
	 * @param pModifier , porcentaje de modificador de moviemineto para el dado
	 * @param pSpecial  , porcentaje de casillas especiales a construir
	 * @throws POOBSTAIRSException NUM_OBSTACLE, si Se lanza un excepcion si se llega a tener un
	 *                             numero excesivo
	 *                             de casillas especiales y obstaculos.
	 */
	public void setGame(int numSnakes, int numStairs, float pModifier, float pSpecial) throws POOBSTAIRSException {
		if(pModifier > 1.0 || pSpecial > 1.0) throw new POOBSTAIRSException(POOBSTAIRSException.INACCEPTED_PERCENTAGE);
		board.setArea(numSnakes, numStairs,pSpecial);
		die = new Die((byte)6,pModifier);
		for(int i = 0; i < players.length; i++) {
			Piece piece = players[i].getPiece();
			board.assignPiece(0, piece);
		}
		
	}
	/**
	 * Metodo que le entrega el trablero a PoobStairs
	 * @return board.getSquares()
	 */
	public GameBoard board(){
		return board;
	}
	/**
	 * Función que simula el tiro del dado
	 * @return la cara actual del dado
	 */
	public Face rollDice() {
		return die.roll();
	}
	/**
	 * Función que cambia la posición del jugador en turno
	 * @param positions, cuantas casillas se va a avanzar
	 * @return true si el jugador en turno ya llego a la ultima casilla
	 * 
	 */
	public boolean advancePlayer(int positions) {
		try {
			board.advancePlayer(positions, players[playerOnTurn].getPiece());
		}catch(POOBSTAIRSException e) {
			e.printStackTrace();
		}
		if(playerOnTurn == 0) playerOnTurn = 1;
		else playerOnTurn = 0;
		
		
		return false;
	}
	
	/**
	 * Metodo que identifica la casilla con cierto valor 
	 * @param value. valor numerico de la casilla
	 * @return la casilla con el numero indicado
	 */
	public Square findSquare(int value) {
		return board.find(value);
	}
	/**
	 * Indica el jugador de a quien le toca mover la ficha
	 * @return
	 */
	public Player getTurn() {
		return players[playerOnTurn];
	}
	/**
	 * Si el jugador lo decide, se usa el poder del dado.
	 */
	public void usePower() {
		int firstPosition = players[playerOnTurn].getPiecePosition();
		try {
			if(die.getCurrentFace().indicatePowers()[0].equals(Power.CHANGE)) {
				players[playerOnTurn].getPiece().changePositionTo(findSquare(players[playerOnTurn + 1].getPiecePosition()));
				findSquare(players[playerOnTurn + 1].getPiecePosition()).receivePiece(players[playerOnTurn].getPiece());
				players[playerOnTurn + 1].getPiece().changePositionTo(findSquare(firstPosition));
				findSquare(firstPosition).receivePiece(players[playerOnTurn + 1].getPiece());
				advancePlayer(die.getCurrentFace().getValue());
			}else {
				players[playerOnTurn].usePower(die.getCurrentFace().indicatePowers()[0], board, die.getCurrentFace().getValue());
				if(playerOnTurn == 0) playerOnTurn = 1;
				else playerOnTurn = 0;
			}
		}catch(IndexOutOfBoundsException e) {
			players[playerOnTurn].getPiece().changePositionTo(findSquare(players[0].getPiecePosition()));
			findSquare(players[0].getPiecePosition()).receivePiece(players[playerOnTurn].getPiece());
			players[0].getPiece().changePositionTo(findSquare(firstPosition));
			findSquare(firstPosition).receivePiece(players[0].getPiece());
			advancePlayer(die.getCurrentFace().getValue());
		}catch (POOBSTAIRSException e) {
			e.printStackTrace();
		}
	}
}
