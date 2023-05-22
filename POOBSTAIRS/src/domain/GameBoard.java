package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import javax.naming.AuthenticationException;

public class GameBoard implements Serializable {
	private int totalSquares;
	private ArrayList<Integer> obstacleSquares;
	private Square[][] squares;
	private Square[] squaresInLine;

	/**
	 * Contructor de la clase GameBoard
	 * 
	 * @param rows,    numero de filas del tablero
	 * @param columns, numero de columnas del tablero
	 * @throws POOBSTAIRSException POOBSTAIRSException Se lanza la excepcion de
	 *                             RANGE si se llega a
	 *                             tener un numero
	 *                             invalido de columnas o de filas.
	 */
	public GameBoard(int rows, int columns) throws POOBSTAIRSException {
		if (rows < 3 || columns < 3)
			throw new POOBSTAIRSException(POOBSTAIRSException.RANGE);
		squares = new Square[rows][columns];
		totalSquares = rows * columns;
		obstacleSquares = new ArrayList<>();
		squaresInLine = new Square[totalSquares];
		setSquares();
	}

	/**
	 * Este metodo generara todo el tablero. Para el tablero se generan el
	 * numero deseado
	 * de obstaculos mas las casillas especiales. Para el dado se va configurar el
	 * porcentaje
	 * de modificador.
	 * 
	 * @param numSnakes , numero de escaleras a construir
	 * @param numStairs , numero de serpientes a construir
	 * @param pModifier , porcentaje de modificador de moviemineto para el dado
	 * @param pSpecial  , porcentaje de casillas especiales a construir
	 * @throws POOBSTAIRSException NUM_OBSTACLE, si Se lanza un excepcion si se
	 *                             llega a tener un
	 *                             numero excesivo
	 *                             de casillas especiales y obstaculos.
	 */
	public void setArea(int numSnakes, int numStairs, float pSpecial, Player[] player) throws POOBSTAIRSException {
		int specialSquares = Math.round((totalSquares - 2) * pSpecial);
		if (numSnakes * 2 + numStairs * 2 + specialSquares > (totalSquares - 2 - 4))
			throw new POOBSTAIRSException(POOBSTAIRSException.NUM_OBSTACLE);
		randomSquareObstacle(numSnakes, false, true);
		randomSquareObstacle(numStairs, true, false);
		randomSquareObstacle(specialSquares, false, false);
		obstacleSquares.sort(null);
		if (player[1] instanceof Machine) {
			Machine bot = (Machine) player[1];
			bot.setBoard(this);
		}
		player[0].changePositionPiece(squaresInLine[0]);
		player[1].changePositionPiece(squaresInLine[0]);
		squaresInLine[0].receivePiece(player[0].getPiece());
		squaresInLine[0].receivePiece(player[1].getPiece());
		setActualSquare();
	}

	/**
	 * Ensambla todas las casillas que hacen parte del tablero
	 */
	private void setSquares() {
		int value = 0;
		if (squares.length % 2 == 0) {
			for (int i = squares.length - 1; i >= 0; i--) {
				if (i % 2 == 1) {
					for (int j = 0; j < squares[0].length; j++) {
						if (obstacleSquares.contains(value)) {
							squares[i][j] = squaresInLine[value];
						} else if (!obstacleSquares.contains(value)) {
							squares[i][j] = new Square(value);
							squaresInLine[value] = new Square(value);
						}
						value++;
					}
				} else {
					for (int j = squares[0].length - 1; j >= 0; j--) {
						if (obstacleSquares.contains(value)) {
							squares[i][j] = squaresInLine[value];
						} else if (!obstacleSquares.contains(value)) {
							squares[i][j] = new Square(value);
							squaresInLine[value] = new Square(value);
						}
						value++;
					}
				}
			}
		} else {
			for (int i = squares.length - 1; i >= 0; i--) {
				if (i % 2 == 0) {
					for (int j = 0; j < squares[0].length; j++) {
						if (obstacleSquares.contains(value)) {
							squares[i][j] = squaresInLine[value];
						} else if (!obstacleSquares.contains(value)) {
							squares[i][j] = new Square(value);
							squaresInLine[value] = new Square(value);
						}
						value++;
					}
				} else {
					for (int j = squares[0].length - 1; j >= 0; j--) {
						if (obstacleSquares.contains(value)) {
							squares[i][j] = squaresInLine[value];
						} else if (!obstacleSquares.contains(value)) {
							squares[i][j] = new Square(value);
							squaresInLine[value] = new Square(value);
						}
						value++;
					}
				}
			}
		}

	}

	/**
	 * Metodos no utilizados
	 */
	private void setActualSquare() {
		int value = 0;
		if (squares.length % 2 == 0) {
			for (int i = squares.length - 1; i >= 0; i--) {
				if (i % 2 == 1) {
					for (int j = 0; j < squares[0].length; j++) {
						squares[i][j] = squaresInLine[value];
						value++;
					}
				} else {
					for (int j = squares[0].length - 1; j >= 0; j--) {
						squares[i][j] = squaresInLine[value];
						value++;
					}
				}
			}
		} else {
			for (int i = squares.length - 1; i >= 0; i--) {
				if (i % 2 == 0) {
					for (int j = 0; j < squares[0].length; j++) {
						squares[i][j] = squaresInLine[value];
						value++;
					}
				} else {
					for (int j = squares[0].length - 1; j >= 0; j--) {
						squares[i][j] = squaresInLine[value];
						value++;
					}
				}
			}
		}

	}

	/**
	 * Función que indica el estado actual del tablero
	 * 
	 * @return squares
	 */
	public Square[][] getSquares() {
		return squares;
	}

	/**
	 * Genera aleatoriamente los obstaculos para el tablero. Busca una posicion sin
	 * ningun
	 * obstaculo o casilla especial,e inicializa la casilla con su respectivo
	 * obstaculo. Para
	 * el caso de que sea un obstaculo serpiente o escalera, la conecta con otra
	 * casilla vacia.
	 * 
	 * @param numObstacle , numero de obstaculos
	 * @param isStair
	 * @param isSnake
	 */
	private void randomSquareObstacle(int numObstacle, boolean isStair, boolean isSnake) {
		int random_int;
		int[] obstacle = new int[numObstacle];
		int i = numObstacle;
		while (i > 0) {
			if (isStair || isSnake) {
				random_int = ThreadLocalRandom.current().nextInt(1, totalSquares - squares[0].length);
			} else {
				random_int = ThreadLocalRandom.current().nextInt(1, totalSquares - 1);
			}
			if (!obstacleSquares.contains(random_int)) {
				obstacleSquares.add(random_int);
				obstacle[i - 1] = random_int;
				i--;
			}
		}
		Arrays.sort(obstacle);
		if (isStair) {
			connectObstacle(obstacle, "stair");
		} else if (isSnake) {
			connectObstacle(obstacle, "snake");
		} else {
			addSpecialSquares(obstacle);
		}
	}

	/**
	 * Conecta ya sea la escalera o la serpiente a una casilla vacia y la
	 * inicializa.
	 * 
	 * @param obstacleSS , arreglo con una de las casillas donde ira el obstaculo
	 *                   serpiente o escalera.
	 * @param obstacle   , el tipo de obstaculo(serpiente o escalera)
	 */
	private void connectObstacle(int[] obstacleSS, String obstacle) {
		int rangeRow = squares[0].length;
		for (int i = 0; i < obstacleSS.length; i++) {
			for (int j = 0; j < squares.length; j++) {
				if (obstacleSS[i] < rangeRow) {
					findRandomSquare(rangeRow, obstacleSS[i], obstacle);
					break;
				}
				rangeRow += squares[0].length;
			}
		}
	}

	private void findRandomSquare(int rangeRow, int obstacleInit, String type) {
		int random_int;
		while (true) {
			random_int = ThreadLocalRandom.current().nextInt(rangeRow + 1, totalSquares - 1);
			// System.out.println(1+obstacleInit+","+(random_int+1)+" Range+"+rangeRow);
			if (!obstacleSquares.contains(random_int)) {
				obstacleSquares.add(random_int);
				addTheObstacle(obstacleInit, random_int, type);
				break;
			}
			if (rangeRow > squares[0].length && (rangeRow - squares[0].length) > squares[0].length) {
				// System.out.println(1+obstacleInit+","+(random_int+1)+" Range-"+(rangeRow -
				// squares[0].length));
				random_int = ThreadLocalRandom.current().nextInt(1, rangeRow - squares[0].length);
				if (!obstacleSquares.contains(random_int)) {
					obstacleSquares.add(random_int);
					addTheObstacle(obstacleInit, random_int, type);
					break;
				}
			}
		}
	}

	/**
	 * Agrega la casilla al tablero y le asigna un obstaculo asociado.
	 * 
	 * @param start   , casilla inicial
	 * @param finish, casilla final
	 * @param type,   tipo de obstaculo(escalera o serpiente)
	 */
	public void addTheObstacle(int start, int finish, String type) {
		Square head;
		Square tail;
		squaresInLine[start] = new Square(start);
		squaresInLine[finish] = new Square(finish);
		if (start > finish) {
			head = squaresInLine[finish];
			tail = squaresInLine[start];
		} else {
			head = squaresInLine[start];
			tail = squaresInLine[finish];
		}

		if (type.equals("stair")) {
			squaresInLine[start].addObstacle(new Obstacle(head, tail, type));
			squaresInLine[finish].addObstacle(new Obstacle(head, tail, type));
		} else {
			squaresInLine[start].addObstacle(new Obstacle(tail, head, type));
			squaresInLine[finish].addObstacle(new Obstacle(tail, head, type));
		}
	}

	public void addTheSpecialSquare(int start, String type) {
		if (squaresInLine[start] instanceof Square) {
			if (type.equals("Regression")) {
				squaresInLine[start] = new Regression(start, this);
			} else if (type.equals("QA")) {
				squaresInLine[start] = new QA(start);
			} else if (type.equals("Jumper")) {
				squaresInLine[start] = new Jumper(start);
			} else if (type.equals("ReverseJumper")) {
				squaresInLine[start] = new ReverseJumper(start);
			} else if (type.equals("Mortal")) {
				squaresInLine[start] = new Mortal(start);
			} else {
				squaresInLine[start] = new Advance(start, this);
			}
		}
	}

	public void addSpecialSquares(int[] specialSquare) {
		int random_int;
		for (int i = 0; i < specialSquare.length; i++) {
			random_int = ThreadLocalRandom.current().nextInt(0, 6);
			// System.out.println(specialSquare[i]);
			switch (random_int) {
				case 0:
					squaresInLine[specialSquare[i]] = new Regression(specialSquare[i], this);
					break;
				case 1:
					squaresInLine[specialSquare[i]] = new QA(specialSquare[i]);
					break;
				case 2:
					squaresInLine[specialSquare[i]] = new Jumper(specialSquare[i]);
					break;
				case 3:
					squaresInLine[specialSquare[i]] = new ReverseJumper(specialSquare[i]);
					break;
				case 4:
					squaresInLine[specialSquare[i]] = new Mortal(specialSquare[i]);
					break;
				default:
					squaresInLine[specialSquare[i]] = new Advance(specialSquare[i], this);
					break;
			}
		}
	}

	/**
	 * Se encarga de analizar que casillas especiales se encuentran en el rango de
	 * movimiento
	 * la pieza en turno
	 * 
	 * @param movements, numero de casillas que va a recorrer la pieza
	 * @param player,    jugador en turno
	 * @return un arreglo de enteros indicando las casillas especiales dentro del
	 *         rango entre
	 *         la posicion actual del usuario la casilla final de este mismo
	 */
	public Integer[] analizeSpecials(int movements, Player player) {
		ArrayList<Integer> inRange = new ArrayList<Integer>();
		int limInf = player.getPiecePosition();
		int limSup = limInf + movements;

		for (int i = limInf + 1; i < limSup; i++) {
			if (i < totalSquares && squaresInLine[i] instanceof SpecialSquare)
				inRange.add(i);
			if (i >= totalSquares)
				break;
		}
		Integer[] inRangeArray = new Integer[inRange.size()];
		return inRange.toArray(inRangeArray);
	}

	/**
	 * Mueve la pieza en turno dentro del tablero
	 * 
	 * @param positions, numero de posiciones que se va a mover la pieza
	 * @param piece,     pieza que se va a mover
	 * @return
	 * @throws POOBSTAIRSException- NO_MORE_SQUARES si las casillas a moverse
	 *                              superan el numero de casillas
	 *                              dentro del tablero.
	 */
	public Square changePiece(int positions, Piece piece) throws POOBSTAIRSException {
		int firstPos = piece.getIntPosition();
		if (firstPos + positions >= totalSquares || firstPos + positions < 0)
			throw new POOBSTAIRSException(POOBSTAIRSException.NO_MORE_SQUARES);
		Square destination = chooseFinalDestination(firstPos + positions, piece);
		changePieceBoard(squaresInLine[firstPos], destination, piece);
		setActualSquare();
		return destination;
	}

	/**
	 * Escoge la casilla final a la cual se va a mover la pieza en juego
	 * 
	 * @param position, posible posiciòn final de la pieza
	 * @param piece,    pieza en juego
	 * @return casilla a la cual se va a mover la pieza despuès de hacer todo el
	 *         analisis
	 */
	private Square chooseFinalDestination(int position, Piece piece) {
		Square destination = squaresInLine[position];
		int numStairs = 0;
		int numSnakes = 0;
		int numSpecialSquares = 0;
		try {
			// Se trata de utilizar la trampa de la nueva casilla
			if (destination.getObstacle().getHead() == destination) {
				destination = chooseFinalDestination(squaresInLine[position].useObstacle(), piece);
				if (destination.typeObstacle().equals("stair"))
					numStairs++;
				else
					numSnakes++;
				changeObstacleToUse(destination.getObstacle());
			}
		} catch (POOBSTAIRSException e) {
			// En caso de ser una casilla especial se usa
			if (destination instanceof SpecialSquare) {
				int newDestination = ((SpecialSquare) destination).useTrap();
				if (!(newDestination >= totalSquares || newDestination < 0)
						&& squaresInLine[newDestination] != destination) {
					destination = chooseFinalDestination(newDestination, piece);
					numSpecialSquares++;
				}
			}
		}
		piece.changeStats(numStairs, numSnakes, numSpecialSquares, destination.getNumSquare());
		return destination;
	}

	public void changeObstacleToUse(Obstacle obstacle) {
		Obstacle newObstacle = Obstacle.change(obstacle, this);
		changeObstacle(newObstacle);
	}

	/**
	 * Metodo que encuentra la posicion mas cercana a la escalera siguiente con
	 * respecto a
	 * la posicion de la pieza en las casillas.
	 * 
	 * @param actualPos ,posicion del jugador en el tablero
	 * @return posicion de la escalera si la encuentra o la posicion acutla si no la
	 *         encontro
	 */
	public int findCloseStair(int actualPos) {
		for (int i = actualPos + 1; i < squaresInLine.length; i++) {
			try {
				Obstacle obstacle = squaresInLine[i].getObstacle();
				if (squaresInLine[i].containsObstacleToUse() && obstacle.getType().equals("stair")) {
					return squaresInLine[i].getNumSquare();
				}
			} catch (Exception e) {
				// System.out.println(e.getMessage()+" Board actualizar plis");
			}
		}
		return actualPos;
	}

	/**
	 * Metodo que encuentra la posicion mas cercana de la serpiente anterior
	 * con respecto a la posicion de la pieza en las casillas.
	 * 
	 * @param actualPos , posicion del jugador en el tablero
	 * @return posicion de la serpiente si la encuentra o la posicion actual si no
	 *         la encontro
	 */
	public int findCloseSnake(int actualPos) {
		for (int i = actualPos; i > 0; i--) {
			try {
				Obstacle obstacle = squaresInLine[i].getObstacle();
				if (squaresInLine[i].containsObstacleToUse() && obstacle.getType().equals("snake")) {
					return squaresInLine[i].getNumSquare();
				}
			} catch (Exception e) {

			}
		}
		return actualPos;
	}

	public ArrayList<Integer> getObstacleSquares() {
		return obstacleSquares;
	}

	protected Square[] getInLine() {
		return squaresInLine;
	}

	/**
	 * Metodo que cambia una pieza de casillas,
	 * desde su casilla actual a la casilla final.
	 * 
	 * @param actualPos , la casilla donde se sabe que esta la ficha
	 * @param finalPos  , la casilla donde se quiere posiciona
	 * @param piece     , pieza a la que se quiere hacer el cambio
	 */
	public void changePieceBoard(int actualPos, int finalPos, Piece piece) {
		squaresInLine[finalPos].receivePiece(piece);
		squaresInLine[actualPos].removePiece(piece);
		piece.changePositionTo(squaresInLine[finalPos]);
	}

	/**
	 * Metodo que cambia una pieza de casillas,desde su casilla inicial a la casilla
	 * final.
	 * 
	 * @param firstPos , posición inicial de la pieza en juego
	 * @param finalPos , posición final de la pieza
	 * @param piece    , pieza en juego
	 */
	private void changePieceBoard(Square firstPos, Square finalPos, Piece piece) {
		finalPos.receivePiece(piece);
		firstPos.removePiece(piece);
	}

	/**
	 * Simula el movimineto de la pieza a una casilla guardando sus estadisticas del
	 * recorrido. Sobre el recorrido pueden haber obstaculos y casillas especiales.
	 * 
	 * @param positions         , posicion de la casila a la que va a moverse la
	 *                          pieza.
	 * @param piece             , pieza del jugador que se va a mover.
	 * @param numStairs         , numero de escaleras usadas en el recorrido.
	 * @param numSnakes         , numero de serpientes usadas en el recorrido.
	 * @param numSpecialSquares , numero de casillas especiales usadas en el
	 *                          recoorido.
	 * @return Un arreglo de toda la inforamcion del recorrido (posicion
	 *         llegada,escaleras usadas, serpientes usadas, casillas usadas)
	 */
	public int[] simulateChangePiece(Integer positions, Piece piece, int numStairs, int numSnakes,
			int numSpecialSquares) {
		int firstPos = piece.getIntPosition();
		int secondPos = firstPos + positions;
		int lastPos = secondPos;
		if (secondPos >= totalSquares - 1 || secondPos < 0)
			return new int[] { squaresInLine[firstPos].getNumSquare(), numStairs, numSnakes, numSpecialSquares };
		if (squaresInLine[secondPos] instanceof SpecialSquare) {
			lastPos = ((SpecialSquare) squaresInLine[secondPos]).useTrap();
			if ((squaresInLine[secondPos] instanceof Jumper || squaresInLine[secondPos] instanceof ReverseJumper)
					&& (lastPos >= totalSquares || lastPos < 0))
				lastPos = secondPos;
			numSpecialSquares++;
		}
		try {
			if (squaresInLine[lastPos].typeObstacle().equals("stair")
					&& squaresInLine[lastPos].containsObstacleToUse()) {
				lastPos = squaresInLine[lastPos].useObstacle();
				numStairs++;
			} else if (squaresInLine[lastPos].typeObstacle().equals("snake")
					&& squaresInLine[lastPos].containsObstacleToUse()) {
				lastPos = squaresInLine[lastPos].useObstacle();
				numSnakes++;
			}
			if (lastPos != firstPos) {
				changePieceBoard(firstPos, lastPos, piece);
			}
		} catch (POOBSTAIRSException e) {
			if (firstPos != lastPos) {
				changePieceBoard(firstPos, lastPos, piece);
			}
		}
		setActualSquare();
		if ((squaresInLine[lastPos] instanceof SpecialSquare || squaresInLine[lastPos].containsObstacleToUse())
				&& firstPos != lastPos)
			return simulateChangePiece(0, piece, numStairs, numSnakes, numSpecialSquares);
		return new int[] { squaresInLine[lastPos].getNumSquare(), numStairs, numSnakes, numSpecialSquares };
	}

	/**
	 * Identifica en que fila y columna de la matriz se encuentra cierta casilla del
	 * tablero
	 * 
	 * @param square, casilla a encontrar
	 * @return arreglo de enteros que indica la fila y columna en la que se
	 *         encuentra la casilla
	 */
	public int[] findRC(Square square) {
		int row;
		int column;
		boolean found = false;
		int[] info = new int[2];
		for (row = 0; row < squares.length; row++) {
			for (column = 0; column < squares[0].length; column++) {
				if (squares[row][column] == square) {
					found = true;
					info[0] = row;
					info[1] = column;
					break;
				}
				if (found)
					break;
			}
		}
		return info;
	}

	/**
	 * Metodo que identifica el camino que debe recorrer la pieza de una casilla a
	 * otra
	 * 
	 * @param start,       casilla en la que inicia la pieza
	 * @param destination, casilla final de la pieza
	 * @return arreglo de enteros que indica cuantas filas y columnas debe recorrer
	 *         la pieza
	 * @throws POOBSTAIRSException - SAME_LINE si ambas casillas se encuentran en la
	 *                             misma fila, NO_MOR_SQUARES si
	 *                             alguno de los resultados se salen del tablero
	 */
	public int[] findPath(Square start, Square destination) {
		int[] startLocation = findRC(start);
		int[] destinationLocation = findRC(destination);
		int[] path = { Math.abs(destinationLocation[0] - startLocation[0]),
				Math.abs(destinationLocation[1] - startLocation[1]) };
		return path;
	}

	protected Square findSquare(int row, int column) {
		return squares[row][column];
	}

	protected void changeObstacleTail(Square previous, Square newTail, Obstacle obstacle) {
		previous.removeObstacle();
		newTail.addObstacle(obstacle);
	}

	private void changeObstacle(Obstacle newObstacle) {
		newObstacle.getHead().addObstacle(newObstacle);
		newObstacle.getTail().addObstacle(newObstacle);
	}
}
