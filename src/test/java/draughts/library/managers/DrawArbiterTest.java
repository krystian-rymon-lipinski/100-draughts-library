package draughts.library.managers;

import static org.junit.Assert.assertEquals;

import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;

public class DrawArbiterTest extends BaseTest {
	
	DrawArbiter testObj;
	GameEngine gameEngine;

	@Before
	public void setUp() {
		gameEngine = new GameEngine();
		testObj = gameEngine.getDrawArbiter();
		boardManager = gameEngine.getBoardManager();
		boardManager.createEmptyBoard();
	}

	public Move<Hop> makeMove(int source, int destination) {
		Move<Hop> move = generateMove(source, destination);
		boardManager.makeWholeMove(move);
		testObj.updateCounter(move.isCapture(), move.getMovingPiece().isQueen());
		return move;
	}

	public Move<Capture> makeCapture(int source, ArrayList<Integer> jumpDestinations,
									 ArrayList<Integer> takenPawns) {
		Move<Capture> move = generateMoveWithCaptures(source, jumpDestinations, takenPawns);
		boardManager.makeWholeMove(move);
		testObj.updateCounter(move.isCapture(), move.getMovingPiece().isQueen());
		return move;
	}

	@Test
	public void changeDrawConditions_noneToNormal() {
		testObj.updateConditions(true, 3, 3);
		
		assertEquals(DrawArbiter.DrawConditions.NORMAL, testObj.getDrawConditions());
		assertEquals(50, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawConditions_noneTo3v1_threeWhitePieces() {
		testObj.updateConditions(true, 3, 1);
		
		assertEquals(DrawArbiter.DrawConditions.THREE_VS_ONE, testObj.getDrawConditions());
		assertEquals(32, testObj.getDrawCounter());
	}

	@Test
	public void changeDrawConditions_noneTo3v1_threeBlackPieces() {
		testObj.updateConditions(true, 1, 3);

		assertEquals(DrawArbiter.DrawConditions.THREE_VS_ONE, testObj.getDrawConditions());
		assertEquals(32, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawConditions_noneTo2v1_twoWhitePieces() {
		testObj.updateConditions(true, 2, 1);
		
		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawConditions());
		assertEquals(10, testObj.getDrawCounter());
	}

	@Test
	public void changeDrawConditions_noneTo2v1_twoBlackPieces() {
		testObj.updateConditions(true, 1, 2);

		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawConditions());
		assertEquals(10, testObj.getDrawCounter());
	}

	@Test
	public void changeDrawConditions_normalTo3v1_threeWhitePieces() {
		testObj.setDrawConditions(DrawArbiter.DrawConditions.NORMAL);

		testObj.updateConditions(true, 3, 1);
		
		assertEquals(DrawArbiter.DrawConditions.THREE_VS_ONE, testObj.getDrawConditions());
		assertEquals(32, testObj.getDrawCounter());
	}

	@Test
	public void changeDrawConditions_normalTo3v1_threeBlackPieces() {
		testObj.setDrawConditions(DrawArbiter.DrawConditions.NORMAL);

		testObj.updateConditions(true, 1, 3);

		assertEquals(DrawArbiter.DrawConditions.THREE_VS_ONE, testObj.getDrawConditions());
		assertEquals(32, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawConditions_normalTo2v1_twoWhitePieces() {
		testObj.setDrawConditions(DrawArbiter.DrawConditions.NORMAL);

		testObj.updateConditions(true, 2, 1);
		
		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawConditions());
		assertEquals(10, testObj.getDrawCounter());
	}

	@Test
	public void changeDrawConditions_normalTo2v1_twoBlackPieces() {
		testObj.setDrawConditions(DrawArbiter.DrawConditions.NORMAL);

		testObj.updateConditions(true, 1, 2);

		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawConditions());
		assertEquals(10, testObj.getDrawCounter());
	}
	
	@Test 
	public void changeDrawConditions_normalToNone_byCapturingQueen() {
		testObj.setDrawConditions(DrawArbiter.DrawConditions.NORMAL);

		testObj.updateConditions(false, 2, 2);
		
		assertEquals(DrawArbiter.DrawConditions.NONE, testObj.getDrawConditions());
		assertEquals(50, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawConditions_3v1To2v1_twoWhitePieces() {
		testObj.setDrawConditions(DrawArbiter.DrawConditions.THREE_VS_ONE);

		testObj.updateConditions(true, 2, 1);
		
		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawConditions());
		assertEquals(10, testObj.getDrawCounter());
	}

	@Test
	public void changeDrawConditions_3v1To2v1_twoBlackPieces() {
		testObj.setDrawConditions(DrawArbiter.DrawConditions.THREE_VS_ONE);

		testObj.updateConditions(true, 1, 2);

		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawConditions());
		assertEquals(10, testObj.getDrawCounter());
	}
	
	@Test
	public void updateDrawCounter_normalConditions() {
		boardManager.addWhitePawn(42);
		boardManager.addWhitePawn(48);
		boardManager.addWhiteQueen(41);
		boardManager.addBlackPawn(2);
		boardManager.addBlackPawn(3);
		boardManager.addBlackQueen(4);
		
		testObj.setDrawConditions(DrawArbiter.DrawConditions.NORMAL);

		assertEquals(50, testObj.getDrawCounter());
		
		makeMove(41, 36);
		assertEquals(49, testObj.getDrawCounter());
		makeMove(4, 15);
		assertEquals(48, testObj.getDrawCounter());
		makeMove(36, 4);
		assertEquals(47, testObj.getDrawCounter());
		makeCapture(15, new ArrayList<>(Collections.singletonList(47)), new ArrayList<>(Collections.singletonList(42)));
		assertEquals(50, testObj.getDrawCounter()); //reset counter after capture
		makeMove(4, 36);
		assertEquals(49, testObj.getDrawCounter());
		makeMove(3, 8); //reset counter after pawn move
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
		
		testObj.setDrawConditions(DrawArbiter.DrawConditions.THREE_VS_ONE);
		testObj.setDrawCounter(32);

		makeMove(41, 36);
		assertEquals(31, testObj.getDrawCounter());
		makeMove(3, 25);
		assertEquals(30, testObj.getDrawCounter());
		makeMove(42, 38); //pawn move - but counter doesn't reset in these conditions
		assertEquals(29, testObj.getDrawCounter());
		//no point in checking if capture does reset the counter - it changes conditions (encapsulated in other tests)
	}
	
	@Test
	public void updateDrawCounter_2v1Conditions_test() {
		boardManager.addWhiteQueen(23);
		boardManager.addBlackQueen(30);
		boardManager.addBlackPawn(9);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		testObj.setDrawConditions(DrawArbiter.DrawConditions.TWO_VS_ONE);
		testObj.setDrawCounter(10);
		
		makeMove(23, 5);
		assertEquals(9, testObj.getDrawCounter());
		makeMove(9, 14);
		assertEquals(8, testObj.getDrawCounter()); //pawn move - but counter doesn't reset in these conditions
		makeCapture(5, new ArrayList<>(Collections.singletonList(46)),
				new ArrayList<>(Collections.singletonList(14)));
		assertEquals(7, testObj.getDrawCounter()); //capture - but counter doesn't reset in these conditions
		
	}
}
