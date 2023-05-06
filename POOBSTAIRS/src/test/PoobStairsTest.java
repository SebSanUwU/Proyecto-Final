package test;
import domain.*;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class PoobStairsTest {

	/**
	 * Se prueba que el programa deje por lo menos seis casillas sin obstaculos o casillas especiales
	 */
	@Test
	void shouldThrowExceptionIfLessthanSixNormal() {
		try {
			PoobStairs game = new PoobStairs(10,10, null);
			game.setGame(0, 0, 0, 1);
			fail("No lanzo excepción");
		}catch(POOBSTAIRSException e) {
			assertEquals(e.getMessage(),POOBSTAIRSException.NUM_OBSTACLE);
		}
		try {
			PoobStairs game = new PoobStairs(10,10, null);
			game.setGame(20, 4, 0, (float)0.5);
			fail("No lanzo excepción");
		}catch(POOBSTAIRSException e) {
			assertEquals(e.getMessage(),POOBSTAIRSException.NUM_OBSTACLE);
		}
		try {
			PoobStairs game = new PoobStairs(3,3, null);
			game.setGame(2, 1, 0, 0);
			fail("No lanzo excepción");
		}catch(POOBSTAIRSException e) {
			assertEquals(e.getMessage(),POOBSTAIRSException.NUM_OBSTACLE);
		}
		try {
			PoobStairs game = new PoobStairs(3,3, null);
			game.setGame(0, 0, 0, (float) 0.65);
			fail("No lanzo excepción");
		}catch(POOBSTAIRSException e) {
			assertEquals(e.getMessage(),POOBSTAIRSException.NUM_OBSTACLE);
		}
		try {
			PoobStairs game = new PoobStairs(10,5, null);
			game.setGame(15, 15, 0, (float) 0.2);
			fail("No lanzo excepción");
		}catch(POOBSTAIRSException e) {
			assertEquals(e.getMessage(),POOBSTAIRSException.NUM_OBSTACLE);
		}
	}
	
	/**
	 * Se prueba que el programa cree su tablero con casillas especiales, serpientes y escaleras
	 */
	
	@Test
	void shouldNotshouldThrowExceptionIfLessthanSixNormal() {
		Player[] players = {new Player("Camilo", "red"), new Player("Pollis", "blue")};
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
	
	
	
	@Test
	void shouldThrowExceptionifCantMove() {
		Player[] players = {new Player("Camilo", "red"), new Player("Pollis", "blue")};
		try {
			PoobStairs game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
			int firstTurn = game.getTurn();
			game.advancePlayer(200);
			fail("No lanzo excepción");
		} catch (POOBSTAIRSException e) {
			assertEquals(players[0].getPiecePosition(), 0);
			assertEquals(e.getMessage(), POOBSTAIRSException.NO_MORE_SQUARES);
			
		}
		
	}

}
