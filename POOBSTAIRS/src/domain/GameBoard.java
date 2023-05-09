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
	public void setArea(int numSnakes, int numStairs, float pSpecial,Player[] player) throws POOBSTAIRSException {
		int aux = (int) Math.round((totalSquares - 2) * pSpecial);
		if (numSnakes * 2 + numStairs * 2 + aux > (totalSquares - 2 - 4))
			throw new POOBSTAIRSException(POOBSTAIRSException.NUM_OBSTACLE);
		
		randomSquareObstacle(numSnakes, false, true);
		randomSquareObstacle(numStairs, true, false);
		randomSquareObstacle(aux, false, false);
		obstacleSquares.sort(null);
		setSquares();
		squaresInLine[0].receivePiece(player[0].getPiece());
		squaresInLine[0].receivePiece(player[1].getPiece());
		
		//setActualSquare();
		
	}
	/**
	 * Ensambla todas las casillas que hacen parte del tablero
	 */
	public void setSquares(){
		int value=0;
		for(int i= squares.length-1 ; i>=0 ;i--){
			if(i%2==1){
				
				for(int j = 0;j<squares[0].length;j++){
					if(obstacleSquares.contains(value)){
						squares[i][j]=squaresInLine[value];
					}else if(!obstacleSquares.contains(value)){
						squares[i][j]=new Square(value);
						squaresInLine[value]  = new Square(value);
					}
					value++;
				}
			}else{
				for(int j = squares[0].length-1;j>=0;j--){
					if(obstacleSquares.contains(value)){
						squares[i][j]=squaresInLine[value];
					}else if(!obstacleSquares.contains(value)){
						squares[i][j]=new Square(value);
						squaresInLine[value]  = new Square(value);
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
						random_int = ThreadLocalRandom.current().nextInt(rangeRow + 1, totalSquares - 1);
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
		squaresInLine[start] = new Square(start);
		squaresInLine[finish] = new Square(finish);
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
					squaresInLine[specialSquare[i]]=new Regression(specialSquare[i],this);
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
					squaresInLine[specialSquare[i]]=new Advance(specialSquare[i],this);
					break;
			} 
		}
	}
	
	
	

	/**
	 * Se encarga de analizar que casillas especiales se encuentran en el rango de movimiento
	 * la pieza en turno
	 * @param movements, numero de casillas que va a recorrer la pieza
	 * @param player, jugador en turno
	 * @return un arreglo de enteros indicando las casillas especiales dentro del rango entre
	 * la posicion actual del usuario la casilla final de este mismo
	 */
	public Integer[] analizeSpecials(int movements, Player player) {
		ArrayList<Integer> inRange = new ArrayList<Integer>();
		int limInf = player.getPiecePosition();
		int limSup = limInf + movements;
		
		for (int i=limInf + 1; i < limSup; i ++) {
			if(i < totalSquares && squaresInLine[i] instanceof SpecialSquare) inRange.add(i);
			if (i >= totalSquares) break;
		}
		Integer[] inRangeArray = new Integer[inRange.size()];
		return inRange.toArray(inRangeArray);
	}
	/**
	 * Reposiciona una pieza dentro del tablero
	 * @param positions, numero de casillas que va a recorrer la pieza
	 * @param piece, Pieza que va a reubicada dentro del tablero
	 * @return La nueva casilla de la pieza
	 * @throws POOBSTAIRSException NO_MORE_SQUARES si, al hacer el primermovimiento, la ficha se 
	 * sale de los limites del tablero
	 */
	public Square changePiece(int positions, Piece piece) throws POOBSTAIRSException {
		int firstPos = piece.getIntPosition();
		int secondPos = firstPos + positions;
		int lastPos = secondPos;
		if(secondPos >= totalSquares || secondPos < 0) throw new POOBSTAIRSException(POOBSTAIRSException.NO_MORE_SQUARES);
		//Se trata de utilizar la trampa de la nueva casilla y si la casilla no tiene obstaculo se trata de usar el especial
		try {
			lastPos = squaresInLine[secondPos].useObstacle();
		}catch(POOBSTAIRSException e) {
			//En caso de ser una casilla especial se usa 
			if(squaresInLine[secondPos] instanceof SpecialSquare) {
				lastPos = ((SpecialSquare)squaresInLine[secondPos]).useTrap();
				if(lastPos >= totalSquares || lastPos < 0) lastPos = secondPos;
			}
		}
		squaresInLine[firstPos].removePiece(piece);
		squaresInLine[lastPos].receivePiece(piece);
		return squaresInLine[lastPos];
	}

	public int findCloseStair(int actualPos){
		for(int i = actualPos; i<squaresInLine.length;i++){
			try {
				Obstacle obstacle = squaresInLine[i].getObstacle();
				if(obstacle.getType().equals("stair")){
					return squaresInLine[i].getNumSquare();
				}
			} catch (Exception e) {
				//System.out.println(e.getMessage()+" Board actualizar plis");
			}
		}
		return actualPos;
		//return -1;
	}

	public int findCloseSnake(int actualPos){
		for(int i = actualPos; i>0;i--){
			try {
				Obstacle obstacle = squaresInLine[i].getObstacle();
				if(obstacle.getType().equals("snake")){
					return squaresInLine[i].getNumSquare();
				}
			} catch (Exception e) {
				
			}
		}
		return actualPos;
		//return -1;
	}
	

	
	public ArrayList<Integer> getObstacleSquares(){
		return obstacleSquares;
	}
	protected Square[] getInLine(){
		return squaresInLine;
	}
	
	/**
	 * Metodos no utilizados
	 */
	public void  setActualSquare(){
		int value=0;
		for(int i= squares.length-1 ; i>=0 ;i--){
			if(i%2==1){
				for(int j = 0;j<squares[0].length;j++){
					squares[i][j]=squaresInLine[value];
					value++;
				}
			}else{
				for(int j = squares[0].length-1;j>=0;j--){
					squares[i][j]=squaresInLine[value];
					value++;
				}
			}
		}
	}
	public void movePiece(Player player,int posicion) throws POOBSTAIRSException  {
		int advancePos = player.getPiecePosition()+posicion;
		int posicionInicial=player.getPiecePosition();
		if(advancePos >= totalSquares || advancePos < 0) throw new POOBSTAIRSException(POOBSTAIRSException.NO_MORE_SQUARES);
		//verificar si posicion para avanzar es una casilla especial 
		if(squaresInLine[advancePos] instanceof SpecialSquare){
			SpecialSquare special = (SpecialSquare) squaresInLine[advancePos];
			if(squaresInLine[advancePos] instanceof Mortal){
				if(posicionInicial==0){
					advancePos=-1;
				}else{
					advancePos=special.useTrap();
				}
			}else if(squaresInLine[advancePos] instanceof QA){
				/*falta implementar */
			}else if(squaresInLine[advancePos] instanceof Advance || squaresInLine[advancePos] instanceof Regression){
				int nextObstacle = special.useTrap();
				if(nextObstacle>0){
					advancePos=nextObstacle;
				}
			}else{
				int n =advancePos+special.useTrap();
				//System.out.println(n);
				if(n<squaresInLine.length-1 && n>-1){
					advancePos=n;
				}
			}
		}
		if(advancePos>=0 && posicionInicial!=advancePos){
			try{
				Obstacle trap =squaresInLine[advancePos].getObstacle();
				if(trap.use()!=posicionInicial){
					changePieceBoard(posicionInicial, trap.use(), player);
					player.changePositionPiece(squaresInLine[trap.use()]);
				}
			}catch(POOBSTAIRSException e){
				changePieceBoard(posicionInicial, advancePos, player);
				player.changePositionPiece(squaresInLine[advancePos]);
			}
		}
		setActualSquare();
	}

	public void changePieceBoard(int actualPos,int finalPos,Player player){
		try {
			squaresInLine[finalPos].receivePiece(player.getPiece());
			squaresInLine[actualPos].removePiece(player.getPiece());
		} catch (Exception ed) {
			System.out.println(ed.getMessage()+" Move Piece");
		}
	}

	
}
