package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;



public class PoobStairs {
	private boolean playerOnTurn;
	private int totalSquares;
	private ArrayList<Integer> obstacleSquares;
	private Player[] players;
	private Square[][] board;
	private Square[] boardLine;
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
		if (rows < 3 || columns < 3)
			throw new POOBSTAIRSException(POOBSTAIRSException.RANGE);
		board = new Square[rows][columns];
		totalSquares = rows * columns;
		obstacleSquares = new ArrayList();
		boardLine = new Square[totalSquares];
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
	 * @throws POOBSTAIRSException Se lanza un excepcion si se llega a tener un
	 *                             numero excesivo
	 *                             de casillas especiales y obstaculos.
	 */
	public void setGame(int numSnakes, int numStairs, float pModifier, float pSpecial) throws POOBSTAIRSException {
		int aux = (int) ((totalSquares - 2) * pSpecial);
		if (numSnakes * 2 + numSnakes * 2 + aux > (totalSquares - 2 - 4))
			throw new POOBSTAIRSException(POOBSTAIRSException.NUM_OBSTACLE);
		randomSquareObstacle(numSnakes, false, true);
		randomSquareObstacle(numStairs, true, false);
		randomSquareObstacle(aux, false, false);
		obstacleSquares.sort(null);
		printArrayListValues(obstacleSquares);
		//System.out.println(obstacleSquares.size());
		//printArray(boardLine);
		setBoard();
	}

	public void setBoard(){
		int value=0;
		for(int i= board.length-1 ; i>=0 ;i--){
			if(i%2==1){
				for(int j = board[0].length-1;j>=0;j--){
					
					if(obstacleSquares.contains(value)){
						board[i][j]=boardLine[value];
					}else if(!obstacleSquares.contains(value)){
						board[i][j]=new Normal(value);
					}
					value++;
				}
			}else{
				for(int j = 0;j<board[0].length;j++){
					if(obstacleSquares.contains(value)){
						board[i][j]=boardLine[value];
					}else if(!obstacleSquares.contains(value)){
						board[i][j]=new Normal(value);
					}
					value++;
				}
			}
		}
	}

	public Square[][] board(){
		//printBoard();
		return board;
	}


	public boolean movePlayer(byte positions) {
		return false;
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
	public void randomSquareObstacle(int numObstacle, boolean isStair, boolean isSnake) {
		int random_int;
		int[] obstacle = new int[numObstacle];
		int i = numObstacle;
		while (i > 0) {
			if (isStair || isSnake) {
				random_int = ThreadLocalRandom.current().nextInt(1, totalSquares - board[0].length);
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
		printArray(obstacle);
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
	public void connectObstacle(int[] obstacleSS, String obstacle) {
		int rangeRow = board[0].length;
		int random_int;
		for (int i = 0; i < obstacleSS.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (obstacleSS[i] <= rangeRow) {
					while (true) {
						random_int = ThreadLocalRandom.current().nextInt(rangeRow + 1, totalSquares - 1);
						 //System.out.println(obstacleSS[i]+","+random_int+" Range"+rangeRow);
						if (!obstacleSquares.contains(random_int)) {
							obstacleSquares.add(random_int);
							addTheObstacle(obstacleSS[i], random_int, obstacle);
							break;
						}
						if (rangeRow > board[0].length) {
							random_int = ThreadLocalRandom.current().nextInt(1, rangeRow - board[0].length - 1);
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
				rangeRow += board[0].length;
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
		boardLine[start] = new Normal(start);
		boardLine[finish] = new Normal(finish);
		if (start > finish) {
			head = boardLine[finish];
			tail = boardLine[start];
		} else {
			head = boardLine[start];
			tail = boardLine[finish];
		}
		boardLine[start].addObstacle(new NormalObstacle(head, tail, type));
		boardLine[finish].addObstacle(new NormalObstacle(head, tail, type));
	}

	public void addSpecialSquare(int[] specialSquare){
		int random_int;
		for(int i= 0; i <specialSquare.length;i++){
			random_int = ThreadLocalRandom.current().nextInt(0,6);
			//System.out.println(specialSquare[i]);
			switch (random_int) {
				case 0:
					boardLine[specialSquare[i]]=new Regression(specialSquare[i]);
					break;
				case 1:
					boardLine[specialSquare[i]]=new QA(specialSquare[i]);
					break;
				case 2:
					boardLine[specialSquare[i]]=new Jumper(specialSquare[i]);	
					break;
				case 3:
					boardLine[specialSquare[i]]=new Mortal(specialSquare[i]);
					break;
				case 4:
					boardLine[specialSquare[i]]=new ReverseJumper(specialSquare[i]);
					break;
				default:
					boardLine[specialSquare[i]]=new Advance(specialSquare[i]);
					break;
			} 
		}
	}


	/**No va */
	public void printBoard(){
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if(board[i][j] != null){
					System.out.print(board[i][j].getNumSquareBoardGUI() + " ");
				}else{
					System.out.print("& ");
				}
			}
			System.out.println();
		}
	}

	/** No va */
	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	/**No va */
	public static void printArray(Square[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if(arr[i] != null){
				System.out.print(arr[i].getNumSquareBoardGUI() + " ");
			}else{
				System.out.print("& ");
			}
			
		}
		System.out.println();
	}

	/** No va */
	public static void printArrayListValues(ArrayList<Integer> arrayList) {
		for (int i = 0; i < arrayList.size(); i++) {
			System.out.print(arrayList.get(i) + " ");
		}
		System.out.println();
	}

	/** No va */
	public static void main(String[] args) {
		try {
			PoobStairs gm = new PoobStairs(10, 10);
			gm.setGame(2, 2, 0, (float) 0.1);
			gm.board();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
