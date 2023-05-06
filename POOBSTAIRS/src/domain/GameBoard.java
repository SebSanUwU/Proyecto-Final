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
				if (obstacleSS[i] <= rangeRow) {
					while (true) {
						random_int = ThreadLocalRandom.current().nextInt(rangeRow + 1, totalSquares - 1);
						 //System.out.println(obstacleSS[i]+","+random_int+" Range"+rangeRow);
						if (!obstacleSquares.contains(random_int)) {
							obstacleSquares.add(random_int);
							addTheObstacle(obstacleSS[i], random_int, obstacle);
							break;
						}
						if (rangeRow > squares[0].length) {
							random_int = ThreadLocalRandom.current().nextInt(1, rangeRow - squares[0].length - 1);
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
		squaresInLine[start].addObstacle(new NormalObstacle(head, tail, type));
		squaresInLine[finish].addObstacle(new NormalObstacle(head, tail, type));
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
	
	public int getTotalSquares() {
		return totalSquares;
	}
	
	protected void assignPiece(int square, Piece piece) {
		Square found = findSquare(square);
		int square1 = square;
		try {
			square1 = found.useObstacle();
		}catch(POOBSTAIRSException e) {
			square1 = useSquare(found);
		}
		if(square1 != square) assignPiece(square1, piece);
		found.receivePiece(piece);
		piece.changePositionTo(found);
			
		
	}
	private int useSquare(Square square) {
		int newSquare;
		if(square instanceof Normal) newSquare = square.getNumSquare();
		else if(square instanceof Jumper) newSquare = square.getNumSquare() + 5;
		else if(square instanceof ReverseJumper) newSquare = square.getNumSquare() - 5;
		else if(square instanceof Advance) newSquare = nextStair(square.getNumSquare());
		else if(square instanceof Regression) newSquare = lastSnake(square.getNumSquare());
		else newSquare = 0;
		return newSquare;
	}
	
	private int nextStair(int square) {
		
		for(Integer i: obstacleSquares) {
			try {
				if(i > square && findSquare(i).getObstacle().equals("stair") && findSquare(i).getObstacle().getNumHead() == i) {
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
		
		for(Integer i: obstacleSquares) {
			try {
				if(i < square && findSquare(i).getObstacle().equals("snake") && findSquare(i).getObstacle().getNumHead() == i) {
					square = i;
					break;
				}
			} catch (POOBSTAIRSException e) {
				e.printStackTrace();
			}
		}
		return square;
	}
	
	public Square findSquare(int num) {
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
	public static void main(String[] args) {
		try {
			GameBoard juego = new GameBoard(10,10);
			juego.setArea(5, 3, 0, (float)0.05);
			juego.getSquares();
		} catch (POOBSTAIRSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	*/
}
