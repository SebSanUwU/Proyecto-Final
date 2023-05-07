package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
public class GameBoard {
	private int totalSquares;
	private ArrayList<Integer> obstacleSquares;
	private Square[][] squares;
	private Square[] squaresInLine;
	
	/**
	 * Contructor de la clase GameBoard
	 * @param rows, numero de filas del tablero
	 * @param columns, numero de columnas del tablero
	 * @throws POOBSTAIRSException POOBSTAIRSException Se lanza la excepcion de RANGE si se llega a
	 *                             tener un numero
	 *                             invalido de columnas o de filas.
	 */
	public GameBoard(int rows, int columns) throws POOBSTAIRSException{
		if (rows < 3 || columns < 3)
			throw new POOBSTAIRSException(POOBSTAIRSException.RANGE);
		squares = new Square[rows][columns];
		totalSquares = rows * columns;
		obstacleSquares = new ArrayList();
		squaresInLine = new Square[totalSquares];
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
	 * @throws POOBSTAIRSException NUM_OBSTACLE, si Se lanza un excepcion si se llega a tener un
	 *                             numero excesivo
	 *                             de casillas especiales y obstaculos.
	 */
	public void setArea(int numSnakes, int numStairs, float pSpecial) throws POOBSTAIRSException {
		int aux = (int) Math.round((totalSquares - 2) * pSpecial);
		if (numSnakes * 2 + numSnakes * 2 + aux > (totalSquares - 2 - 4))
			throw new POOBSTAIRSException(POOBSTAIRSException.NUM_OBSTACLE);
		
		randomSquareObstacle(numSnakes, false, true);
		randomSquareObstacle(numStairs, true, false);
		randomSquareObstacle(aux, false, false);
		obstacleSquares.sort(null);
		setSquares();
	}
	/**
	 * Ensambla todas las casillas que hacen parte del tablero
	 */
	private void setSquares(){
		int value=0;
		for(int i= squares.length-1 ; i>=0 ;i--){
			if(i%2==1){
				
				for(int j = 0;j<squares[0].length;j++){
					
					if(obstacleSquares.contains(value)){
						squares[i][j]=squaresInLine[value];
					}else if(!obstacleSquares.contains(value)){
						squares[i][j]=new Normal(value);
					}
					value++;
				}
			}else{
				for(int j = squares[0].length-1;j>=0;j--){
					if(obstacleSquares.contains(value)){
						squares[i][j]=squaresInLine[value];
					}else if(!obstacleSquares.contains(value)){
						squares[i][j]=new Normal(value);
					}
					value++;
				}
			}
		}
	}
	/**
	 * Función que indica el estado actual del tablero
	 * @return squares
	 */
	public Square[][] getSquares(){
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
				random_int = ThreadLocalRandom.current().nextInt(1, totalSquares-1);
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
		}else if (isSnake) {
			connectObstacle(obstacle, "snake");
		}else{
			addSpecialSquare(obstacle);
		}
	}

	/**
	 * Conecta ya sea la escalera o la serpiente a una casilla vacia y la
	 * inicializa.
	 * 
	 * @param obstacleSS , arreglo con una de las casillas donde ira elobstaculo
	 *                   serpiente o escalera
	 * @param obstacle   , el tipo de obstaculo(serpiente o escalera)
	 */
	private void connectObstacle(int[] obstacleSS, String obstacle) {
		int rangeRow = squares[0].length;
		int random_int;
		for (int i = 0; i < obstacleSS.length; i++) {
			for (int j = 0; j < squares.length; j++) {
				if (obstacleSS[i] < rangeRow) {
					while (true) {
						random_int = ThreadLocalRandom.current().nextInt(rangeRow+1, totalSquares - 1);
						 //System.out.println(obstacleSS[i]+","+random_int+" Range"+rangeRow);
						if (!obstacleSquares.contains(random_int)) {
							obstacleSquares.add(random_int);
							addTheObstacle(obstacleSS[i], random_int, obstacle);
							break;
						}
						if (rangeRow > squares[0].length) {
							random_int = ThreadLocalRandom.current().nextInt(1, rangeRow - squares[0].length + 1);
							//System.out.println(obstacleSS[i]+","+random_int+" Range"+rangeRow);
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
	private void addTheObstacle(int start, int finish, String type) {
		Square head;
		Square tail;
		squaresInLine[start] = new Normal(start);
		squaresInLine[finish] = new Normal(finish);
		if (start > finish) {
			head = squaresInLine[finish];
			tail = squaresInLine[start];
		} else {
			head = squaresInLine[start];
			tail = squaresInLine[finish];
		}
		//System.out.println(start+","+finish);
		if(type.equals("stair")){
			squaresInLine[start].addObstacle(new NormalObstacle(head, tail, type));
			squaresInLine[finish].addObstacle(new NormalObstacle(head, tail, type));
		}else{
			squaresInLine[start].addObstacle(new NormalObstacle(tail, head, type));
			squaresInLine[finish].addObstacle(new NormalObstacle(tail, head, type));
		}
	}

	private void addSpecialSquare(int[] specialSquare){
		int random_int;
		for(int i= 0; i <specialSquare.length;i++){
			random_int = ThreadLocalRandom.current().nextInt(0,6);
			//System.out.println(specialSquare[i]);
			switch (random_int) {
				case 0:
					squaresInLine[specialSquare[i]]=new Regression(specialSquare[i]);
					break;
				case 1:
					squaresInLine[specialSquare[i]]=new QA(specialSquare[i]);
					break;
				case 2:
					squaresInLine[specialSquare[i]]=new Jumper(specialSquare[i]);	
					break;
				case 3:
					squaresInLine[specialSquare[i]]=new Mortal(specialSquare[i]);
					break;
				case 4:
					squaresInLine[specialSquare[i]]=new ReverseJumper(specialSquare[i]);
					break;
				default:
					squaresInLine[specialSquare[i]]=new Advance(specialSquare[i]);
					break;
			} 
		}
	}
	
	
	
	/**
	 * Metodo que identifica la casilla con cierto valor 
	 * @param value. valor numerico de la casilla
	 * @return la casilla con el numero indicado
	 */
	protected Square find(int num) {
		Square found = null;
		for(int i = squares.length-1; i >= 0; i--) {
			for(int j = 0; j < squares[0].length; j++) {
				if(squares[i][j].getNumSquare() == num) {
					found = squares[i][j];
					break;
				}
			}
		}
		return found;
	}
	/**
	 * Se encarga de mover cierta ficha atravez del tablero
	 * @param positions, n casillas que se va a avanzar
	 * @param piece, pieza que se va a mover
	 * @throws POOBSTAIRSException NO_SQUARES si el numero de casillas a avanzar super el numero de casillas totales
	 */
	public void advancePlayer(int positions, Piece piece) throws POOBSTAIRSException{
		if(piece.getPosition() + positions > totalSquares) throw new POOBSTAIRSException(POOBSTAIRSException.NO_MORE_SQUARES);
		assignPiece(piece.getPosition() + positions, piece);
	}
	/**
	 * Se encarga de posicionar cierta pieza dentro de cierta casilla
	 * @param square, numero de la casilla a la cual se va a posicionar al jugador
	 * @param piece, pieza que se va a mover
	 */
	public void assignPiece(int square, Piece piece) {
		Square found = find(square);
		found.receivePiece(piece);
		piece.changePositionTo(found);
		try {
			if(find(square) instanceof Jumper) {
				advancePlayer(5,piece);
			}
			else if(find(square) instanceof ReverseJumper) {
				negativeMove(5, piece);
			}
			else if(find(square) instanceof Advance) {
				piece.changePositionTo(find(nextStair(square)));
				find(piece.getPosition()).receivePiece(piece);
				piece.useObstacle();
				find(piece.getPosition()).receivePiece(piece);
			}
			else if(find(square) instanceof Regression) {
				piece.changePositionTo(find(lastSnake(square)));
				find(piece.getPosition()).receivePiece(piece);
				piece.useObstacle();
				find(piece.getPosition()).receivePiece(piece);
			} else if(find(square) instanceof Mortal) {
				piece.changePositionTo(find(0));
				find(piece.getPosition()).receivePiece(piece);
			}else {
				piece.useObstacle();
				find(piece.getPosition()).receivePiece(piece);
			}
		} catch (POOBSTAIRSException e) {
			e.printStackTrace();
		}
		
	}
	
	private void negativeMove(int squares, Piece piece) throws POOBSTAIRSException{
		if(piece.getPosition() - squares  < 0) throw new POOBSTAIRSException(POOBSTAIRSException.NO_MORE_SQUARES);
		assignPiece(piece.getPosition() - squares, piece);
	}
	
	/**
	 * Función que busca la proxima escalera a la posición actual de una ficha
	 * @param square, posición actual de la ficha en cuestion
	 * @return la posición en la cual se encuentra el inicio de la proxima escalera.
	 */
	private int nextStair(int square) {
		
		for(Integer i: obstacleSquares) {
			try {
				if(i > square && find(i).getObstacle().getType().equals("stair") && find(i).getObstacle().getHead().getNumSquare() == i) {
					square = i;
					break;
				}
			} catch (POOBSTAIRSException e) {
				e.printStackTrace();
			}
		}
		return square;
	}
	/**
	 * Función que busca la abterior serpiente a la posición actual de una ficha
	 * @param square, posición actual de la ficha en cuestion
	 * @return la posición en la cual se encuentra el inicio de la anterior serpiente.
	 */
	private int lastSnake(int square) {
		
		for(Integer i: obstacleSquares) {
			try {
				if(i < square && find(i).getObstacle().getType().equals("snake") && find(i).getObstacle().getTail().getNumSquare() == i) {
					square = i;
					break;
				}
			} catch (POOBSTAIRSException e) {
				e.printStackTrace();
			}
		}
		return square;
	}
}
