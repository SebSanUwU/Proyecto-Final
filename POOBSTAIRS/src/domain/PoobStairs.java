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
	private Player[] players;
	private GameBoard board;
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
		this.players=players;
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
	 * throws POOBSTAIRSException -  NO_SPECIALS si el dentro del rango de movimiento de la pieza
	 * no ahí casillas especiales
	 */
	
	public Integer[] analize(int numberPositions) throws POOBSTAIRSException{
		if(board.analizeSpecials(numberPositions, getTurn()).length == 0) throw new POOBSTAIRSException(POOBSTAIRSException.NO_SPECIALS);
		return board.analizeSpecials(numberPositions, getTurn());
	}
	
	/**
	 * Se ecncarga de mover la pieza en juego n posiciones
	 * @param positions, numero de casillas que el jugador  en turno va a mover su pieza
	 * @return si algun jugador ha ganado
	 */
	public boolean movePiece(int positions) {
		Piece piece = getTurn().getPiece();
		try {
			Square finalDestination = board.changePiece(positions, piece);
			piece.changePositionTo(finalDestination);
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
	/**
	 * Se encarga de usar el poder otorgado por la cara, en caso de que la cara no tenga
	 * poder, se mueven las mismas casillas que son indicadas por el dado.
	 * @return el valor de posiciones que va a mover el jugador luego de usar el poder
	 */
	public int usePower(){
		Player nextP;
		String power;
		int movements = die.getCurrentFace().getValue();
		try {
			power = die.getCurrentFace().indicatePowers();
			getTurn().sumModifiers();
			if(power.equals(Power.CHANGE)) {
				if(playerOnTurn == 0) nextP = players[1];
				else nextP = players[0];
				int[] changes = Power.giveSuperPower(power, getTurn(), nextP);
				movePiece(changes[0]);
				movePiece(changes[1]);
			}else {
				movements = die.usePower();
			}
			return movements;
		} catch (FaceException e) {
			return movements;
		}
	}	
	
	/**
	 * Indica cual es el arreglo de casiilas dentro del juego
	 * @return un arreglo que contiene todas las casillas del tablero
	 */
	public Square[] getInLine() {
		return board.getInLine();
	}

	
	public GameBoard getBoard() {
		return board;
		
	}


	public int playMachine(){
		Machine bot = (Machine) players[playerOnTurn];
		int valorJugado=bot.play(die.getCurrentFace());
		if(playerOnTurn == 0) playerOnTurn = 1;
			else playerOnTurn = 0;
		return valorJugado;
	}

	public GameBoard getGameBoard(){

		return board;
	}
}
