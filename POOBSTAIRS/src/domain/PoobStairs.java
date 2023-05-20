package domain;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JOptionPane;


import domain.Die.Face;
import presentation.POOBSTAIRSGUI;

/**
 * Clase principal del modelo para lograr que el juego POOBSTAIRS funcione
 * correctamente
 * 
 * @author Castaño, Camargo
 *         03/05/2023
 *
 */

public class PoobStairs implements Serializable {
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
		this.players = players;
		board = new GameBoard(rows, columns);
	}

	/**
	 * Este metodo generara todo el tablero y el dado. Para el tablero se generan el
	 * numero deseado
	 * de obstaculos mas las casillas especiales. Para el dado se va configurar el
	 * porcentaje
	 * de modificador. Además agrega las piezas de los jugadores en el tablero
	 * 
	 * @param numSnakes , numero de escaleras a construir
	 * @param numStairs , numero de serpientes a construir
	 * @param pModifier , porcentaje de modificador de moviemineto para el dado
	 * @param pSpecial  , porcentaje de casillas especiales a construir
	 * @throws POOBSTAIRSException NUM_OBSTACLE, si Se lanza un excepcion si se
	 *                             llega a tener un
	 *                             numero excesivo
	 *                             de casillas especiales y obstaculos.
	 *                             INACCEPTED_PERCENTAGE si el porcentaje de
	 *                             modifcadores o casillas especiales supera el
	 *                             limite
	 */
	public void setGame(int numSnakes, int numStairs, float pModifier, float pSpecial) throws POOBSTAIRSException {
		if (pModifier > 1.0 || pSpecial > 0.8)
			throw new POOBSTAIRSException(POOBSTAIRSException.INACCEPTED_PERCENTAGE);
		board.setArea(numSnakes, numStairs, pSpecial, players);
		die = new Die((byte) 6, pModifier);
	}

	/**
	 * Metodo que le entrega el trablero a PoobStairs
	 * 
	 * @return board.getSquares()
	 */
	public Square[][] board() {
		return board.getSquares();
	}

	/**
	 * Función que simula el tiro del dado
	 * 
	 * @return la cara actual del dado
	 */
	public Face rollDice() {
		return die.roll();
	}

	/**
	 * Función que cambia la posición del jugador en turno
	 * 
	 * @param positions, cuantas casillas se va a avanzar
	 * @return true si el jugador en turno ya llego a la ultima casilla
	 *         throws POOBSTAIRSException - NO_SPECIALS si el dentro del rango de
	 *         movimiento de la pieza
	 *         no ahí casillas especiales
	 */

	public Integer[] analize(int numberPositions) throws POOBSTAIRSException {
		if (board.analizeSpecials(numberPositions, getTurn()).length == 0)
			throw new POOBSTAIRSException(POOBSTAIRSException.NO_SPECIALS);
		return board.analizeSpecials(numberPositions, getTurn());
	}

	/**
	 * Se ecncarga de mover la pieza en juego n posiciones
	 * 
	 * @param positions, numero de casillas que el jugador en turno va a mover su
	 *                   pieza
	 * @return si algun jugador ha ganado
	 */
	public boolean movePiece(int positions) {
		Piece piece = getTurn().getPiece();
		try {
			Square finalDestination = board.changePiece(positions, piece);
			piece.changePositionTo(finalDestination);
			if (playerOnTurn == 0)
				playerOnTurn = 1;
			else
				playerOnTurn = 0;
		} catch (POOBSTAIRSException e) {
			if (playerOnTurn == 0)
				playerOnTurn = 1;
			else
				playerOnTurn = 0;
		}
		return false;
	}

	/**
	 * Indica el jugador de a quien le toca mover la ficha
	 * 
	 * @return
	 */
	public Player getTurn() {
		return players[playerOnTurn];
	}

	/**
	 * Se encarga de usar el poder otorgado por la cara, en caso de que la cara no
	 * tenga
	 * poder, se mueven las mismas casillas que son indicadas por el dado.
	 * 
	 * @return el valor de posiciones que va a mover el jugador luego de usar el
	 *         poder
	 */
	public int usePower() {
		Player nextP;
		String power;
		int movements = die.getCurrentFace().getValue();
		try {
			power = die.getCurrentFace().indicatePowers();
			getTurn().sumModifiers();
			if (power.equals(Power.CHANGE)) {
				if (playerOnTurn == 0)
					nextP = players[1];
				else
					nextP = players[0];
				int[] changes = Power.giveSuperPower(power, getTurn(), nextP);
				movePiece(changes[0]);
				movePiece(changes[1]);
			} else {
				movements = die.usePower();
			}
			return movements;
		} catch (FaceException e) {
			return movements;
		}
	}

	public boolean someWinner(){
		return false;
	}

	/**
	 * Metodo que toma la desicion de que casilla jugar depenendiendo de su
	 * comportamiento.
	 * 
	 * @return Aun por definir
	 */
	public int playMachine() {
		Machine bot = (Machine) players[playerOnTurn];
		int extraMovement = usePower();
		int[] movementSimulate1 = bot.play(die.getCurrentFace().getValue());
		int[] movementSimulate2 = new int[] { -1, 0, 0, 0 };
		int[] betterMove;
		// Analizar la simulacion con el poder extra de movimiento
		if (extraMovement != die.getCurrentFace().getValue()) {
			movementSimulate2 = bot.play(extraMovement);
		}
		if (movementSimulate1[0] > movementSimulate2[0]) {
			betterMove = movementSimulate1;
		} else {
			betterMove = movementSimulate2;
		}
		getTurn().changeStats(betterMove[1], betterMove[2], betterMove[3], betterMove[0]);
		if (getTurn().getPiecePosition() != betterMove[0]) {
			board.changePieceBoard(getTurn().getPiecePosition(), betterMove[0], getTurn().getPiece());
		}
		if (playerOnTurn == 0)
			playerOnTurn = 1;
		else
			playerOnTurn = 0;
		return 0;
	}

	/**
	 * Indica cual es el arreglo de casiilas dentro del juego
	 * 
	 * @return un arreglo que contiene todas las casillas del tablero
	 */
	public Square[] getInLine() {
		return board.getInLine();
	}

	/**
	 * Devuelve el objeto board de clase GameBoard de poobstairs.
	 * 
	 * @return Objeto Board de poobstairs.
	 */
	public GameBoard getBoard() {
		return board;

	}

	public static PoobStairs open(String name) throws Exception {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("../partidas/" + name + ".stairs")));
		PoobStairs newPoobStairs = null;
		try {
			while (true) {
				newPoobStairs = (PoobStairs) in.readObject();
			}
		} catch (EOFException e) {
			in.close();
		}

		return newPoobStairs;
	}

	/**
	 * Metodo para salvar un archivo con extension de programa (ObjectOutputStream).
	 * 
	 * @param file , archivo que se desa abrir
	 * @throws IOException
	 * @throws AutomataExeption IN_PROCESS Si el metodo se esta construyendo
	 */
	public void save(String name) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(new File("../partidas/" + name + ".stairs")));
		out.writeObject(this);
		out.close();
	}

	public void addObstacle(int start,int finish,String type){
		board.addTheObstacle(start, finish, type);
	}

	public void addSpecialSquare(int start,String type){
		board.addTheSpecialSquare(start,type);
	}
}
