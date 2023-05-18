package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import javax.naming.AuthenticationException;

public class GameBoard {
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
		obstacleSquares = new ArrayList();
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
		int aux = (int) Math.round((totalSquares - 2) * pSpecial);
		if (numSnakes * 2 + numStairs * 2 + aux > (totalSquares - 2 - 4))
			throw new POOBSTAIRSException(POOBSTAIRSException.NUM_OBSTACLE);
		randomSquareObstacle(numSnakes, false, true);
		randomSquareObstacle(numStairs, true, false);
		randomSquareObstacle(aux, false, false);
		obstacleSquares.sort(null);
		if (player[1] instanceof MachineLearner) {
			MachineLearner bot = (MachineLearner) player[1];
			bot.setBoard(this);
		}
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
			addSpecialSquare(obstacle);
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
		int random_int;
		for (int i = 0; i < obstacleSS.length; i++) {
			for (int j = 0; j < squares.length; j++) {
				if (obstacleSS[i] < rangeRow) {
					while (true) {
						random_int = ThreadLocalRandom.current().nextInt(rangeRow + 1, totalSquares - 1);
						// System.out.println(obstacleSS[i]+","+random_int+" Range"+rangeRow);
						if (!obstacleSquares.contains(random_int)) {
							obstacleSquares.add(random_int);
							addTheObstacle(obstacleSS[i], random_int, obstacle);
							break;
						}
						if (rangeRow > squares[0].length) {
							random_int = ThreadLocalRandom.current().nextInt(1, rangeRow - squares[0].length + 1);
							// System.out.println(obstacleSS[i]+","+random_int+" Range"+rangeRow);
							if (!obstacleSquares.contains(random_int)) {
								obstacleSquares.add(random_int);
								addTheObstacle(obstacleSS[i], random_int, obstacle);
								break;
							}
						}
					}
					break;
				}
				rangeRow += squares[0].length;
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
		// System.out.println(start+","+finish);
		if (type.equals("stair")) {
			squaresInLine[start].addObstacle(new NormalObstacle(head, tail, type));
			squaresInLine[finish].addObstacle(new NormalObstacle(head, tail, type));
		} else {
			squaresInLine[start].addObstacle(new NormalObstacle(tail, head, type));
			squaresInLine[finish].addObstacle(new NormalObstacle(tail, head, type));
		}
	}

	public void addSpecialSquare(int[] specialSquare) {
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
	 * Reposiciona una pieza dentro del tablero
	 * 
	 * @param positions, numero de casillas que va a recorrer la pieza
	 * @param piece,     Pieza que va a reubicada dentro del tablero
	 * @return La nueva casilla de la pieza
	 * @throws POOBSTAIRSException NO_MORE_SQUARES si, al hacer el primermovimiento,
	 *                             la ficha se
	 *                             sale de los limites del tablero
	 * 
	 *                             public Square changePiece(int positions, Piece
	 *                             piece) throws POOBSTAIRSException {
	 * 
	 *                             int firstPos = piece.getIntPosition();
	 *                             int secondPos = firstPos + positions;
	 *                             if (secondPos >= totalSquares || secondPos <
	 *                             0)throw new
	 *                             POOBSTAIRSException(POOBSTAIRSException.NO_MORE_SQUARES);
	 *                             int lastPos = secondPos;
	 *                             int numStairs = 0;
	 *                             int numSnakes = 0;
	 *                             int numSpecialSquares = 0;
	 *                             // En caso de ser una casilla especial se usa
	 *                             if (squaresInLine[secondPos] instanceof
	 *                             SpecialSquare) {
	 *                             lastPos = ((SpecialSquare)
	 *                             squaresInLine[secondPos]).useTrap();
	 *                             if ((squaresInLine[secondPos] instanceof Jumper
	 *                             || squaresInLine[secondPos] instanceof
	 *                             ReverseJumper)
	 *                             && (lastPos >= totalSquares || lastPos < 0))
	 *                             lastPos = secondPos;
	 *                             numSpecialSquares++;
	 *                             }
	 * 
	 *                             try {
	 *                             // Se trata de utilizar la trampa de la nueva
	 *                             casilla
	 *                             lastPos = squaresInLine[lastPos].useObstacle();
	 *                             if
	 *                             (squaresInLine[lastPos].typeObstacle().equals("stair")
	 *                             &&
	 *                             squaresInLine[lastPos].containsObstacleToUse()) {
	 *                             numStairs++;
	 *                             } else if
	 *                             (squaresInLine[lastPos].containsObstacleToUse())
	 *                             {
	 *                             numSnakes++;
	 *                             }
	 *                             if (lastPos != firstPos) {
	 *                             changePieceBoard(firstPos, lastPos, piece);
	 *                             }
	 *                             } catch (POOBSTAIRSException e) {
	 *                             if (firstPos != lastPos) {
	 *                             changePieceBoard(firstPos, lastPos, piece);
	 *                             }
	 *                             }
	 *                             piece.changeStats(numStairs, numSnakes,
	 *                             numSpecialSquares, lastPos);
	 *                             setActualSquare();
	 *                             if ((squaresInLine[lastPos] instanceof
	 *                             SpecialSquare ||
	 *                             squaresInLine[lastPos].containsObstacleToUse())
	 *                             && firstPos != lastPos)
	 *                             return changePiece(0, piece);
	 *                             return squaresInLine[lastPos];
	 *                             }
	 */
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
			}
		} catch (POOBSTAIRSException e) {
			// En caso de ser una casilla especial se usa
			if (destination instanceof SpecialSquare) {
				int newDestination = ((SpecialSquare) destination).useTrap();
				if (squaresInLine[newDestination] != destination) {
					destination = chooseFinalDestination(newDestination, piece);
					numSpecialSquares++;
				}
			}
		}
		piece.changeStats(numStairs, numSnakes, numSpecialSquares, destination.getNumSquare());
		return destination;
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
	 * @param firstPos, posición inicial de la pieza en juego
	 * @param finalPos, posición final de la pieza
	 * @param piece,    pieza en juego
	 */
	private void changePieceBoard(Square firstPos, Square finalPos, Piece piece) {
		finalPos.receivePiece(piece);
		firstPos.removePiece(piece);
	}


	public int simulateChangePiece(Integer positions, Piece piece) {
		int firstPos = piece.getIntPosition();
		int secondPos = firstPos + positions;
		int lastPos = secondPos;
		if (secondPos >= totalSquares || secondPos < 0) {
			return squaresInLine[firstPos].getNumSquare();
		}
		if (squaresInLine[secondPos] instanceof SpecialSquare) {
			lastPos = ((SpecialSquare) squaresInLine[secondPos]).useTrap();
			if ((squaresInLine[secondPos] instanceof Jumper || squaresInLine[secondPos] instanceof ReverseJumper)
					&& (lastPos >= totalSquares || lastPos < 0))
				lastPos = secondPos;
		}
		try {
			lastPos = squaresInLine[lastPos].useObstacle();
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
			return simulateChangePiece(0, piece);
		return squaresInLine[lastPos].getNumSquare();
	}
}
