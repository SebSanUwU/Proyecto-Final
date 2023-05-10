package domain;



import java.util.HashMap;
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
	private HashMap<String,Player> playersHash;
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
		this.playersHash= new HashMap<String,Player>();
		this.players=players;
		for(int i = 0;i<players.length;i++){
			this.playersHash.put(players[i].getName(),players[i]);
		}
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
		board.setArea(numSnakes, numStairs,pSpecial,players);
		die = new Die((byte)6,pModifier);
		int rows = board().length;
		players[0].changePositionPiece(board.getInLine()[0]);
		players[1].changePositionPiece(board.getInLine()[0]);
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
	 * 
	 */
	
	public Integer[] analize(int numberPositions) throws POOBSTAIRSException{
		if(board.analizeSpecials(numberPositions, getTurn()).length == 0) throw new POOBSTAIRSException(POOBSTAIRSException.NO_SPECIALS);
		return board.analizeSpecials(numberPositions, getTurn());
	}
	
	/**
	 * Se ecnarga de mover la pieza en juego n posiciones
	 * @param positions, numero de casillas que el jugador  en turno va a mover su pieza
	 * @return si algun jugador ha ganado
	 */
	public boolean movePiece(int positions) {
		Piece piece = getTurn().getPiece();
		try {
			Square newPosition = board.changePiece(positions, piece);
			getTurn().changePositionPiece(newPosition);
			if(playerOnTurn == 0) playerOnTurn = 1;
			else playerOnTurn = 0;
			
		} catch (POOBSTAIRSException e) {
			if(playerOnTurn == 0) playerOnTurn = 1;
			else playerOnTurn = 0;
		}
		return false;
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
	 * @throws POOBSTAIRSException 
	 */
	
	public void usePower() throws POOBSTAIRSException {
		
		Player nextP;
		String power = die.getCurrentFace().indicatePowers()[0];
		if(power.equals(Power.CHANGE)) {
			if(playerOnTurn == 0) nextP = players[1];
			else nextP = players[0];
			int[] changes = Power.giveSuperPower(power, getTurn(), nextP);
			movePiece(changes[0]);
			movePiece(changes[1]);
			movePiece(die.getCurrentFace().getValue());
		}else {
				//advancePlayer(Power.usePower(power) + die.getCurrentFace().getValue());
			movePiece(Power.givePower(power) + die.getCurrentFace().getValue());
		}
		
		
	}	
	
	
	public Square[] getInLine() {
		return board.getInLine();
	}
}
