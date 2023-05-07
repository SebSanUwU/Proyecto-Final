package domain;



import java.util.Random;


import domain.Die.Face;



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
			assignPiece(0, piece);
		}
		
	}
	/**
	 * Metodo que le entrega el trablero a PoobStairs
	 * @return board.getSquares()
	 */
	public Square[][] board(){
		return board.getSquares();
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
	 * @throws POOBSTAIRSException - NO_MOR_SQUARES, si el jugador va a avnzar más casillas de la que se encuentran en el tablero.
	 */
	public boolean advancePlayer(int positions) throws POOBSTAIRSException{
		if(players[playerOnTurn].getPiecePosition() + positions > board.getTotalSquares()) throw new POOBSTAIRSException(POOBSTAIRSException.NO_MORE_SQUARES);
		int newPosition = players[playerOnTurn].movePiece(positions);
		assignPiece(newPosition, players[playerOnTurn].getPiece());
		//useSquare(findSquare(players[playerOnTurn].getPiecePosition()));
		if(playerOnTurn == 0) playerOnTurn = 1;
		else playerOnTurn = 0;
		return false;
	}
	
	public void assignPiece(int square, Piece piece) {
		Square found = findSquare(square);
		found.receivePiece(piece);
		piece.changePositionTo(found);
		
		try {
			piece.useObstacle();
			findSquare(piece.getPosition()).receivePiece(piece);
		} catch (POOBSTAIRSException e) {
			if(findSquare(square) instanceof Jumper) {
				piece.changePositionTo(findSquare(square + 5));
				findSquare(piece.getPosition()).receivePiece(piece);

			}
			else if(findSquare(square) instanceof ReverseJumper) {
				piece.changePositionTo(findSquare(square - 5));
				findSquare(piece.getPosition()).receivePiece(piece);

			}
			else if(findSquare(square) instanceof Advance) {
				piece.changePositionTo(findSquare(nextStair(square)));
				findSquare(piece.getPosition()).receivePiece(piece);

			}
			else if(findSquare(square) instanceof Regression) {
				piece.changePositionTo(findSquare(lastSnake(square)));
				findSquare(piece.getPosition()).receivePiece(piece);
			} else if(findSquare(square) instanceof Mortal) {
				piece.changePositionTo(findSquare(0));
				findSquare(piece.getPosition()).receivePiece(piece);
			}
		}
	}
	
	
	private int nextStair(int square) {
		
		for(Integer i: board.getObstacleSquares()) {
			try {
				if(i > square && findSquare(i).getObstacle().getType().equals("stair") && findSquare(i).getObstacle().getHead().getNumSquare() == i) {
					square = i;
					break;
				}
			} catch (POOBSTAIRSException e) {
				e.printStackTrace();
			}
		}
		return square;
	}
	
	private int lastSnake(int square) {
		
		for(Integer i: board.getObstacleSquares()) {
			try {
				if(i < square && findSquare(i).getObstacle().getType().equals("snake") && findSquare(i).getObstacle().getTail().getNumSquare() == i) {
					square = i;
					break;
				}
			} catch (POOBSTAIRSException e) {
				e.printStackTrace();
			}
		}
		return square;
	}
	
	public Square findSquare(int value) {
		return board.find(value);
	}
	
	public String getTurn() {
		return players[playerOnTurn].getName();
	}

	
	
}
