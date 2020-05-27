package draughts.library;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import draughts.library.GameEngine.GameState;

public class DrawArbiterTest {
	
	DrawArbiter testObj;
	GameEngine gameEngine;
	BoardManager boardManager;
	
	@Before
	public void setUp() {
		gameEngine = new GameEngine();
		gameEngine.setIsWhiteToMove(true);
		gameEngine.setGameState(GameState.RUNNING);
		testObj = gameEngine.getDrawArbiter();
		boardManager = gameEngine.getMoveManager().getBoardManager();
		boardManager.createEmptyBoard();
	}
	
	public void makeMove(int source, int destination) {
		try {
			gameEngine.tileClicked(source);
			gameEngine.tileClicked(destination);
		} catch (Exception ex) {}
	}
	
	@Test
	public void changeDrawConditions_noneToNormal_test() {
		boardManager.addWhitePawn(43);
		boardManager.addWhitePawn(10);
		boardManager.addWhitePawn(50);
		boardManager.addBlackPawn(36);
		boardManager.addBlackPawn(26);
		boardManager.addBlackQueen(16);
		boardManager.setIsBlackQueenOnBoard(true);
		
		gameEngine.getMoveManager().findAllCorrectMoves(gameEngine.getIsWhiteToMove());
		
		makeMove(10, 5);
		
		assertEquals(DrawArbiter.DrawConditions.NORMAL, testObj.getDrawConditions());
		assertEquals(50, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawConditions_noneTo3v1_byCapture_test() {
		boardManager.addWhiteQueen(33);
		boardManager.addBlackPawn(11);
		boardManager.addBlackPawn(12);
		boardManager.addBlackPawn(3);
		boardManager.addBlackQueen(10);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		gameEngine.getMoveManager().findAllCorrectMoves(gameEngine.getIsWhiteToMove());
		
		makeMove(33, 6);
		
		assertEquals(DrawArbiter.DrawConditions.THREE_VS_ONE, testObj.getDrawConditions());
		assertEquals(32, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawConditions_noneTo3v1_byPromotion_test() {
		boardManager.addBlackPawn(42);
		boardManager.addWhiteQueen(36);
		boardManager.addWhiteQueen(40);
		boardManager.addWhiteQueen(49);
		boardManager.setIsWhiteQueenOnBoard(true);
		
		gameEngine.setIsWhiteToMove(false);
		gameEngine.getMoveManager().findAllCorrectMoves(gameEngine.getIsWhiteToMove());
		
		makeMove(42, 47);
		
		assertEquals(DrawArbiter.DrawConditions.THREE_VS_ONE, testObj.getDrawConditions());
		assertEquals(32, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawConditions_noneTo2v1_byCapture_test() {
		boardManager.addBlackPawn(34);
		boardManager.addBlackPawn(9);
		boardManager.addBlackQueen(37);
		boardManager.addBlackQueen(22);
		boardManager.addWhiteQueen(43);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		gameEngine.getMoveManager().findAllCorrectMoves(gameEngine.getIsWhiteToMove());
		
		makeMove(43, 25);
		makeMove(25, 3);
		
		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawConditions());
		assertEquals(10, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawCondtions_noneTo2v1_byPromotion() {
		boardManager.addBlackPawn(45);
		boardManager.addWhiteQueen(2);
		boardManager.setIsWhiteQueenOnBoard(true);
		
		gameEngine.setIsWhiteToMove(false);
		gameEngine.getMoveManager().findAllCorrectMoves(gameEngine.getIsWhiteToMove());
		
		makeMove(45, 50);
	
		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawConditions());
		assertEquals(10, testObj.getDrawCounter());
	}
	
	
	@Test
	public void changeDrawConditions_normalTo3v1_test() {
		boardManager.addWhitePawn(16);
		boardManager.addWhiteQueen(26);
		boardManager.addWhiteQueen(37);
		boardManager.addBlackPawn(8);
		boardManager.addBlackPawn(23);
		boardManager.addBlackQueen(15);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		gameEngine.getMoveManager().findAllCorrectMoves(gameEngine.getIsWhiteToMove());
		
		makeMove(37, 19);
		makeMove(19, 2);
		
		assertEquals(DrawArbiter.DrawConditions.THREE_VS_ONE, testObj.getDrawConditions());
		assertEquals(32, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawConditions_normalTo2v1_test() {
		boardManager.addWhiteQueen(46);
		boardManager.addWhiteQueen(22);
		boardManager.addWhiteQueen(38);
		boardManager.addBlackQueen(4);
		boardManager.addBlackPawn(25);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		gameEngine.setIsWhiteToMove(false);
		gameEngine.getMoveManager().findAllCorrectMoves(gameEngine.getIsWhiteToMove());
		
		makeMove(4, 27);
		makeMove(27, 49);
		
		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawConditions());
		assertEquals(10, testObj.getDrawCounter());
	}
	
	@Test 
	public void changeDrawConditions_normalToNone_test() {
		boardManager.addWhitePawn(38);
		boardManager.addWhitePawn(39);
		boardManager.addWhitePawn(43);
		boardManager.addWhiteQueen(46);
		boardManager.addBlackPawn(7);
		boardManager.addBlackPawn(11);
		boardManager.addBlackPawn(21);
		boardManager.addBlackQueen(19);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		gameEngine.getMoveManager().findAllCorrectMoves(gameEngine.getIsWhiteToMove());
		
		makeMove(46, 14);
		
		assertEquals(DrawArbiter.DrawConditions.NONE, testObj.getDrawConditions());
		assertEquals(50, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawCondtions_3v1To2v1_test() {
		boardManager.addWhitePawn(29);
		boardManager.addWhiteQueen(26);
		boardManager.addWhiteQueen(46);
		boardManager.addBlackQueen(15);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		gameEngine.setIsWhiteToMove(false);
		gameEngine.getMoveManager().findAllCorrectMoves(gameEngine.getIsWhiteToMove());
		
		makeMove(15, 38);
		
		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawConditions());
		assertEquals(10, testObj.getDrawCounter());
	}
	
	@Test
	public void updateDrawCounter_normalConditions_test() {
		boardManager.addWhitePawn(42);
		boardManager.addWhitePawn(48);
		boardManager.addWhiteQueen(41);
		boardManager.addBlackPawn(2);
		boardManager.addBlackPawn(3);
		boardManager.addBlackQueen(4);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		testObj.updateState(true, 3, 3);
		gameEngine.getMoveManager().findAllCorrectMoves(gameEngine.getIsWhiteToMove());

		assertEquals(50, testObj.getDrawCounter());
		
		makeMove(41, 36);
		assertEquals(49, testObj.getDrawCounter());
		makeMove(4, 15);
		assertEquals(48, testObj.getDrawCounter());
		makeMove(36, 4);
		assertEquals(47, testObj.getDrawCounter());
		makeMove(15, 47); //capture
		assertEquals(50, testObj.getDrawCounter());
		makeMove(4, 36);
		assertEquals(49, testObj.getDrawCounter());
		makeMove(3, 8); //pawn move
		assertEquals(50, testObj.getDrawCounter());	
	}
	
	@Test
	public void updateDrawCounter_3v1Conditions_test() {
		boardManager.addWhitePawn(42);
		boardManager.addWhitePawn(48);
		boardManager.addWhiteQueen(41);
		boardManager.addBlackQueen(3);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		testObj.updateState(true, 3, 1);
		gameEngine.getMoveManager().findAllCorrectMoves(gameEngine.getIsWhiteToMove());
		
		assertEquals(32, testObj.getDrawCounter());
		
		makeMove(41, 36);
		assertEquals(31, testObj.getDrawCounter());
		makeMove(3, 25);
		assertEquals(30, testObj.getDrawCounter());
		makeMove(42, 38); //pawn move - but it doesn't change anything under this circumstances
		assertEquals(29, testObj.getDrawCounter());
	}
	
	@Test
	public void updateDrawCounter_2v1Conditions_test() {
		boardManager.addWhiteQueen(23);
		boardManager.addBlackQueen(30);
		boardManager.addBlackPawn(9);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		testObj.updateState(true, 2, 1);
		gameEngine.getMoveManager().findAllCorrectMoves(gameEngine.getIsWhiteToMove());
		
		assertEquals(10, testObj.getDrawCounter());
		
		makeMove(23, 1);
		assertEquals(9, testObj.getDrawCounter());
		makeMove(9, 14); //pawn move - but it doesn't change anything under this circumstances
		assertEquals(8, testObj.getDrawCounter());
		
	}
}
