package test;
import domain.*;
import domain.Die.Face;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class PoobStairsTest {

	/**
	 * Se prueba que el programa deje por lo menos seis casillas sin obstaculos o casillas especiales
	 */
	@Test
	void shouldThrowExceptionIfLessthanSixNormal() {
		Player[] players = {new Player("Camilo", Color.red), new Player("Pollis", Color.blue)};
		try {
			PoobStairs game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 1);
			fail("No lanzo excepción");
		}catch(POOBSTAIRSException e) {
			assertEquals(e.getMessage(),POOBSTAIRSException.NUM_OBSTACLE);
		}
		try {
			PoobStairs game = new PoobStairs(10,10, players);
			game.setGame(20, 4, 0, (float)0.5);
			fail("No lanzo excepción");
		}catch(POOBSTAIRSException e) {
			assertEquals(e.getMessage(),POOBSTAIRSException.NUM_OBSTACLE);
		}
		try {
			PoobStairs game = new PoobStairs(3,3, players);
			game.setGame(2, 1, 0, 0);
			fail("No lanzo excepción");
		}catch(POOBSTAIRSException e) {
			assertEquals(e.getMessage(),POOBSTAIRSException.NUM_OBSTACLE);
		}
		try {
			PoobStairs game = new PoobStairs(3,3, players);
			game.setGame(0, 0, 0, (float) 0.65);
			fail("No lanzo excepción");
		}catch(POOBSTAIRSException e) {
			assertEquals(e.getMessage(),POOBSTAIRSException.NUM_OBSTACLE);
		}
		try {
			PoobStairs game = new PoobStairs(10,5, players);
			game.setGame(15, 15, 0, (float) 0.2);
			fail("No lanzo excepción");
		}catch(POOBSTAIRSException e) {
			assertEquals(e.getMessage(),POOBSTAIRSException.NUM_OBSTACLE);
		}
	}
	
	/**
	 * Se prueba que el programa crea su tablero con casillas especiales, serpientes y escaleras
	 */
	
	@Test
	void shouldNotshouldThrowExceptionIfLessthanSixNormal() {
		Player[] players = {new Player("Camilo", Color.red), new Player("Pollis", Color.blue)};
		try {
			PoobStairs game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, (float)0.5);
		}catch(POOBSTAIRSException e) {
			fail("Lanzo excepción");
		}
		
		try {
			PoobStairs game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
		}catch(POOBSTAIRSException e) {
			fail("Lanzo excepción");
		}
		try {
			PoobStairs game = new PoobStairs(10,5, players);
			game.setGame(5, 10, 0, (float) 0.2);
		}catch(POOBSTAIRSException e) {
			fail("Lanzo excepción");
		}
	}
	/**
	 * Se prueba que las fichas se mueven por el tablero sin el uso de casillas especiales 
	 */
	@Test
	void shouldMovePiece() {
		Player[] players = {new Player("Camilo", Color.red), new Player("Pollis", Color.blue)};
		
		try {
			Player isTurn;
			Square lastPosition;
			PoobStairs game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
			isTurn = game.getTurn();
			game.movePiece(6);
			assertTrue(game.getInLine()[6].contains(isTurn.getPiece()));
			assertFalse(game.getInLine()[0].contains(isTurn.getPiece()));
			assertTrue(isTurn.getPiecePosition() == 6);
			assertTrue(isTurn.getPieceSquare() == game.getInLine()[6]);
			isTurn = game.getTurn();
			game.movePiece(5);
			assertTrue(game.getInLine()[5].contains(isTurn.getPiece()));
			assertFalse(game.getInLine()[0].contains(isTurn.getPiece()));
			assertTrue(isTurn.getPiecePosition() == 5);
			assertTrue(isTurn.getPieceSquare() == game.getInLine()[5]);
			isTurn = game.getTurn();
			game.movePiece(2);
			assertTrue(game.getInLine()[8].contains(isTurn.getPiece()));
			assertFalse(game.getInLine()[6].contains(isTurn.getPiece()));
			assertTrue(isTurn.getPiecePosition() == 8);
			assertTrue(isTurn.getPieceSquare() == game.getInLine()[8]);
			isTurn = game.getTurn();
			game.movePiece(5);
			assertTrue(game.getInLine()[10].contains(isTurn.getPiece()));
			assertFalse(game.getInLine()[5].contains(isTurn.getPiece()));
			assertTrue(isTurn.getPiecePosition() == 10);
			assertTrue(isTurn.getPieceSquare() == game.getInLine()[10]);
		}catch(POOBSTAIRSException e) {
			fail("Lanzo excepción");
		}
		try {
			Player isTurn;
			Square lastPosition;
			PoobStairs game = new PoobStairs(10,4, players);
			game.setGame(0, 0, 0, 0);
			isTurn = game.getTurn();
			game.movePiece(4);
			assertTrue(game.getInLine()[4].contains(isTurn.getPiece()));
			assertFalse(game.getInLine()[0].contains(isTurn.getPiece()));
			assertTrue(isTurn.getPiecePosition() == 4);
			assertTrue(isTurn.getPieceSquare() == game.getInLine()[4]);
			isTurn = game.getTurn();
			game.movePiece(10);
			assertTrue(game.getInLine()[10].contains(isTurn.getPiece()));
			assertFalse(game.getInLine()[0].contains(isTurn.getPiece()));
			assertTrue(isTurn.getPiecePosition() == 10);
			assertTrue(isTurn.getPieceSquare() == game.getInLine()[10]);
			isTurn = game.getTurn();
			game.movePiece(1);
			assertTrue(game.getInLine()[5].contains(isTurn.getPiece()));
			assertFalse(game.getInLine()[4].contains(isTurn.getPiece()));
			assertTrue(isTurn.getPiecePosition() == 5);
			assertTrue(isTurn.getPieceSquare() == game.getInLine()[5]);
			isTurn = game.getTurn();
			game.movePiece(20);
			assertTrue(game.getInLine()[30].contains(isTurn.getPiece()));
			assertFalse(game.getInLine()[10].contains(isTurn.getPiece()));
			assertTrue(isTurn.getPiecePosition() == 30);
			assertTrue(isTurn.getPieceSquare() == game.getInLine()[30]);
		}catch(POOBSTAIRSException e) {
			fail("Lanzo excepción");
		}
	}
	
	
	@Test
	/**
	 * Se prueba que si el numero de posiciones Se sale del tablero, la ficha no cambia de posición
	 */
	void shouldNotMove() {
		Player[] players = {new Player("Camilo", Color.red), new Player("Pollis", Color.blue)};
		try {
			PoobStairs game = new PoobStairs(3,4, players);
			game.setGame(0, 0, 0, 0);
			Player isTurn = game.getTurn();
			int fisrtPosition = isTurn.getPiecePosition();
			game.movePiece(13);
			assertFalse(isTurn.getPiecePosition() == 13);
			assertTrue(isTurn.getPiecePosition() == 0);
			assertFalse(isTurn == game.getTurn());
			game.movePiece(6);
			game.movePiece(4);
			game.movePiece(5);
			isTurn = game.getTurn();
			game.movePiece(0);
			assertTrue(isTurn.getPieceSquare() == game.getInLine()[4]);
			isTurn = game.getTurn();
			game.movePiece(1);
			assertTrue(isTurn.getPieceSquare() == game.getInLine()[11]);
			
		}catch(POOBSTAIRSException e) {
			fail("Lanzo una excepción");
		}
	}
	
	/**
	 * Se prueba que el porgrama analiza que casillas especiales dentro del rango de movimiento de la ficha 
	 */
	@Test
	void shouldTGiveRange() {
		Player[] players = {new Player("Camilo", Color.red), new Player("Pollis", Color.blue)};
		PoobStairs game;
		Square[] testSquare;
		try {
			game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
			testSquare = game.getInLine();
			testSquare[5] = new Mortal(5);
			testSquare[9] = new QA(9);
			testSquare[2] = new Jumper(2);
			assertEquals(game.analize(15).length, 3);
			Integer[]shouldBe = {2,5,9};
			for(int i = 0; i < 3; i++) {
				assertEquals(game.analize(15)[i], shouldBe[i]);
			}
			
		} catch (POOBSTAIRSException e) {
			// TODO Auto-generated catch block
			fail("Lanzo Excepción");
		}
		try {
			game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, (float)0.9);
			assertTrue(game.analize(40).length > 25);
			
		} catch (POOBSTAIRSException e) {
			// TODO Auto-generated catch block
			fail("Lanzo Excepción");
		}
		
		try {
			game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
			testSquare = game.getInLine();
			testSquare[5] = new Mortal(5);
			testSquare[9] = new QA(9);
			testSquare[2] = new Jumper(2);
			testSquare[15] = new Jumper(15);
			assertEquals(game.analize(15).length, 3);
			Integer[]shouldBe = {2,5,9};
			for(int i = 0; i < 3; i++) {
				assertEquals(game.analize(15)[i], shouldBe[i]);
			}
			
		} catch (POOBSTAIRSException e) {
			// TODO Auto-generated catch block
			fail("Lanzo Excepción");
		}
		
		try {
			game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
			testSquare = game.getInLine();
			testSquare[5] = new Mortal(5);
			testSquare[9] = new QA(9);
			testSquare[2] = new Jumper(2);
			testSquare[2] = new Jumper(15);
			testSquare[30] = new Mortal(30);
			testSquare[32] = new QA(32);
			testSquare[40] = new Jumper(40);
			testSquare[50] = new Jumper(50);
			game.movePiece(16);
			game.movePiece(1);
			Integer[]shouldBe = {30,32,40,50};
			for(int i = 0; i < 4; i++) {
				assertEquals(game.analize(40)[i], shouldBe[i]);
			}
			
		} catch (POOBSTAIRSException e) {
			// TODO Auto-generated catch block
			fail("Lanzo Excepción");
		}
		try {
			game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
			testSquare = game.getInLine();
			testSquare[98] = new Mortal(98);
			testSquare[99] = new QA(99);
			game.movePiece(97);
			game.movePiece(1);
			Integer[]shouldBe = {98,99};
			for(int i = 0; i < 2; i++) {
				assertEquals(game.analize(5)[i], shouldBe[i]);
			}
			
		} catch (POOBSTAIRSException e) {
			// TODO Auto-generated catch block
			fail("Lanzo Excepción");
		}
	}
	/**
	 * Se prueba que las piezas bajen por la cabeza de las serpientes.
	 */
	@Test
	void shouldGoDown() {
		Player[] players = {new Player("Camilo", Color.red), new Player("Pollis", Color.blue)};

		PoobStairs game;
		try {
			game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
			Obstacle snake1 = new Obstacle(game.getInLine()[75], game.getInLine()[23],"snake");
			game.getInLine()[23].addObstacle(snake1);
			game.getInLine()[75].addObstacle(snake1);
			Obstacle snake2 = new Obstacle(game.getInLine()[98], game.getInLine()[88],"snake");
			game.getInLine()[98].addObstacle(snake2);
			game.getInLine()[88].addObstacle(snake2);
			Obstacle snake3 = new Obstacle(game.getInLine()[24], game.getInLine()[1],"snake");
			game.getInLine()[24].addObstacle(snake3);
			game.getInLine()[1].addObstacle(snake3);
			Obstacle snake4 = new Obstacle(game.getInLine()[92], game.getInLine()[5],"snake");
			game.getInLine()[92].addObstacle(snake4);
			game.getInLine()[5].addObstacle(snake4);
			Player yourTurn = game.getTurn();
			game.movePiece(75);
			assertTrue(yourTurn.getPieceSquare() == game.getInLine()[23]);
			assertTrue(game.getInLine()[23].contains(yourTurn.getPiece()));
			yourTurn = game.getTurn();
			game.movePiece(24);
			assertTrue(yourTurn.getPieceSquare() == game.getInLine()[1]);
			assertTrue(game.getInLine()[1].contains(yourTurn.getPiece()));
			yourTurn = game.getTurn();
			game.movePiece(75);
			assertTrue(yourTurn.getPieceSquare() == game.getInLine()[88]);
			assertTrue(game.getInLine()[88].contains(yourTurn.getPiece()));
			yourTurn = game.getTurn();
			game.movePiece(91);
			assertTrue(yourTurn.getPieceSquare() == game.getInLine()[5]);
			assertTrue(game.getInLine()[5].contains(yourTurn.getPiece()));
			
			
		} catch (POOBSTAIRSException e) {
			fail("Lanzo excepción");
		}
		
	}
	/**
	 * Se prueba que las serpientes no suban por la cola de la serpeinte
	 */
	@Test
	void shouldNotGoUp() {
		Player[] players = {new Player("Camilo", Color.red), new Player("Pollis", Color.blue)};

		PoobStairs game;
		
		
		try {
			game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
			Obstacle snake1 = new Obstacle(game.getInLine()[75], game.getInLine()[23],"snake");
			game.getInLine()[23].addObstacle(snake1);
			game.getInLine()[75].addObstacle(snake1);
			Obstacle snake2 = new Obstacle(game.getInLine()[98], game.getInLine()[88],"snake");
			game.getInLine()[98].addObstacle(snake2);
			game.getInLine()[88].addObstacle(snake2);
			Obstacle snake3 = new Obstacle(game.getInLine()[24], game.getInLine()[1],"snake");
			game.getInLine()[24].addObstacle(snake3);
			game.getInLine()[1].addObstacle(snake3);
			Obstacle snake4 = new Obstacle(game.getInLine()[92], game.getInLine()[5],"snake");
			game.getInLine()[92].addObstacle(snake4);
			game.getInLine()[5].addObstacle(snake4);
			Player yourTurn = game.getTurn();
			game.movePiece(23);
			assertTrue(yourTurn.getPieceSquare() == game.getInLine()[23]);
			assertTrue(game.getInLine()[23].contains(yourTurn.getPiece()));
			assertFalse(game.getInLine()[75].contains(yourTurn.getPiece()));
			yourTurn = game.getTurn();
			game.movePiece(1);
			assertTrue(yourTurn.getPieceSquare() == game.getInLine()[1]);
			assertTrue(game.getInLine()[1].contains(yourTurn.getPiece()));
			assertFalse(game.getInLine()[24].contains(yourTurn.getPiece()));
		} catch (POOBSTAIRSException e) {
			fail("Lanzo excepción");
		}
		
	}
	@Test
	void shouldGoUp() {
		Player[] players = {new Player("Camilo", Color.red), new Player("Pollis", Color.blue)};

		PoobStairs game;
		try {
			game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
			Obstacle stair1 = new Obstacle(game.getInLine()[24], game.getInLine()[75],"snake");
			game.getInLine()[24].addObstacle(stair1);
			game.getInLine()[75].addObstacle(stair1);
			Obstacle stair2 = new Obstacle(game.getInLine()[88], game.getInLine()[98],"snake");
			game.getInLine()[98].addObstacle(stair2);
			game.getInLine()[88].addObstacle(stair2);
			Obstacle stair3 = new Obstacle(game.getInLine()[1], game.getInLine()[23],"snake");
			game.getInLine()[23].addObstacle(stair3);
			game.getInLine()[1].addObstacle(stair3);
			Obstacle stair4 = new Obstacle(game.getInLine()[5], game.getInLine()[92],"snake");
			game.getInLine()[92].addObstacle(stair4);
			game.getInLine()[5].addObstacle(stair4);
			Player yourTurn = game.getTurn();
			game.movePiece(1);
			assertTrue(yourTurn.getPieceSquare() == game.getInLine()[23]);
			assertTrue(game.getInLine()[23].contains(yourTurn.getPiece()));
			yourTurn = game.getTurn();
			game.movePiece(5);
			assertTrue(yourTurn.getPieceSquare() == game.getInLine()[92]);
			assertTrue(game.getInLine()[92].contains(yourTurn.getPiece()));
			yourTurn = game.getTurn();
			game.movePiece(1);
			assertTrue(yourTurn.getPieceSquare() == game.getInLine()[75]);
			assertTrue(game.getInLine()[75].contains(yourTurn.getPiece()));
			game.movePiece(2);
			yourTurn = game.getTurn();
			game.movePiece(13);
			assertTrue(yourTurn.getPieceSquare() == game.getInLine()[98]);
			assertTrue(game.getInLine()[98].contains(yourTurn.getPiece()));
		} catch (POOBSTAIRSException e) {
			fail("Lanzo excepción");
		}
		
	}
	@Test
	void shouldJump() {
		Player[] players = {new Player("Camilo", Color.red), new Player("Pollis", Color.blue)};

		PoobStairs game;
		
		
		try {
			game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
			game.getInLine()[5] = new Jumper(5);
			
		} catch (POOBSTAIRSException e) {
			fail("Lanzo excepción");
		}
		
	}
	

}

