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
			Player isTurn;
			int lastPosition;
			PoobStairs game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
			isTurn = game.getTurn();
			lastPosition = game.getTurn().getPiecePosition();
			game.advancePlayer(6);
			assertEquals(lastPosition + 6, isTurn.getPiecePosition());
			isTurn = game.getTurn();
			lastPosition = game.getTurn().getPiecePosition();
			game.advancePlayer(4);
			assertEquals(lastPosition + 4, isTurn.getPiecePosition());
			isTurn = game.getTurn();
			lastPosition = game.getTurn().getPiecePosition();
			game.advancePlayer(1);
			assertEquals(lastPosition + 1, isTurn.getPiecePosition());
			isTurn = game.getTurn();
			lastPosition = game.getTurn().getPiecePosition();
			game.advancePlayer(10);
			assertEquals(lastPosition + 10, isTurn.getPiecePosition());
			isTurn = game.getTurn();
			lastPosition = game.getTurn().getPiecePosition();
			game.advancePlayer(4);
			assertEquals(lastPosition + 4, isTurn.getPiecePosition());
			
		}catch(POOBSTAIRSException e) {
			fail("Lanzo excepción");
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
		
	}
	@Test
	void shouldGoDown() {
		Player[] players = {new Player("Camilo", Color.red), new Player("Pollis", Color.blue)};

		PoobStairs game;
		try {
			game = new PoobStairs(10,10, players);
			game.setGame(0, 0, 0, 0);
			game.board()[0][5].addObstacle(new NormalObstacle(game.board()[3][5],game.board()[0][5],"snake"));
			game.board()[3][5].addObstacle(new NormalObstacle(game.board()[3][5],game.board()[0][5],"snake"));
			Player yourTurn = game.getTurn();
			game.advancePlayer(65);
			assertTrue(game.board()[0][5].contains(yourTurn.getPiece()));
		} catch (POOBSTAIRSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
