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
	
	@Test
	void shouldMovePiece() {
		Player[] players = {new Player("Camilo", Color.red), new Player("Pollis", Color.blue)};
		try {
			PoobStairs game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
			for(int i = 0; i < 15; i++) {
				int position = players[0].getPiecePosition();
				int extra = game.rollDice().getValue();
				game.assignPiece(position + extra, players[0].getPiece());
				if(players[0].getPiecePosition() != position + extra) fail("no se mueve correctamente");
			}
			game.assignPiece(5, players[0].getPiece());
			assertEquals(5,players[0].getPiecePosition());
			assertTrue(game.findSquare(5).contains(players[0].getPiece()));
			assertFalse(game.findSquare(0).contains(players[0].getPiece()));
			
		}catch(POOBSTAIRSException e) {
			
		}
	}
	
	@Test
	void shouldNotMoveMove() {
		Player[] players = {new Player("Camilo", Color.red), new Player("Pollis", Color.blue)};
		
		try {
			PoobStairs game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
			Player isYourTurn = game.getTurn();
			game.advancePlayer(101);
			assertEquals(isYourTurn.getPiecePosition(),0);
		} catch (POOBSTAIRSException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	@Test
	void shouldThrowDiceCorrectly() {
		Player[] players = {new Player("Camilo", Color.red), new Player("Pollis", Color.blue)};
		PoobStairs game;
		try {
			game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 1, 0);
			for(int i = 0; i < 200; i++) {
				game.rollDice();
			}
		} catch (POOBSTAIRSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
