package draughts.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MoveManagerTest {
	
	MoveManager testObj;
	BoardManager boardManager;
	
	@Before
	public void setUp() {
		testObj = new MoveManager();
		boardManager = new BoardManager();
		boardManager.createEmptyBoard();
	}
	
	//tests for making move all hops at once
	
	@Test
	public void isMadeMoveCorrect_wrongPieceMoved_test() {
		boardManager.addWhitePawn(21);
		boardManager.addWhitePawn(26);
		testObj.findAllCorrectMoves(boardManager, true);
		
		Move<? extends Hop> correctMove = testObj.isMadeMoveCorrect(26, 21, new ArrayList<>());
		assertNull(correctMove);
	}
	
	@Test
	public void isMadeMoveCorrect_wrongDestinationChosen_test() {
		boardManager.addBlackPawn(23);
		boardManager.addBlackPawn(24);
		testObj.findAllCorrectMoves(boardManager, false);

		Move<? extends Hop> correctMove = testObj.isMadeMoveCorrect(23, 24, new ArrayList<>());
		assertNull(correctMove);
	}
	
	@Test
	public void isMadeMoveCorrect_noCapturedPiecesDuringCapture_test() {
		boardManager.addWhitePawn(33);
		boardManager.addBlackPawn(28);
		testObj.findAllCorrectMoves(boardManager, true);
		
		Move<? extends Hop> correctMove = testObj.isMadeMoveCorrect(33, 22, new ArrayList<>());
		assertNull(correctMove);
	}
	
	@Test
	public void isMadeMoveCorrect_wrongPieceCapturedDuringCapture_test() {
		boardManager.addBlackPawn(23);
		boardManager.addWhitePawn(28);
		boardManager.addWhitePawn(29);
		testObj.findAllCorrectMoves(boardManager, false);

		Move<? extends Hop> correctMove = testObj.isMadeMoveCorrect(23, 32, new ArrayList<>(Arrays.asList(29)));
		assertNull(correctMove);
	}
	
	@Test
	public void isMadeMoveCorrect_correctMove_withNoCapture_test() {
		boardManager.addWhitePawn(43);
		testObj.findAllCorrectMoves(boardManager, true);
		
		Move<? extends Hop> correctMove = testObj.isMadeMoveCorrect(43, 38, new ArrayList<>());
		assertEquals(43, correctMove.getMoveSource().getIndex());
		assertEquals(38, correctMove.getMoveDestination().getIndex());
	}
	
	@Test
	public void isMadeMoveCorrect_correctMove_withCapture_test() {
		boardManager.addBlackPawn(9);
		boardManager.addWhitePawn(13);
		boardManager.addWhitePawn(23);
		testObj.findAllCorrectMoves(boardManager, false);

		Move<? extends Hop> correctMove = testObj.isMadeMoveCorrect(9, 29, new ArrayList<>(Arrays.asList(13, 23)));
		assertEquals(13, correctMove.getMoveTakenPawns().get(0).getIndex());
		assertEquals(23, correctMove.getMoveTakenPawns().get(1).getIndex());
	}
	
	
	@Test
	public void findAllCorrectMoves_whenCapturesAvailable_test() {	
		boardManager.addWhitePawn(46);
		boardManager.addWhitePawn(49);
		boardManager.addWhitePawn(43);
		boardManager.addWhiteQueen(35);
		boardManager.addBlackPawn(13);
		boardManager.addBlackPawn(16);
		boardManager.addBlackPawn(22);
		boardManager.addBlackPawn(26);
		
		testObj.findAllCorrectMoves(boardManager, true);
		
		assertEquals(2, testObj.getPossibleMoves().size());	
	}
	
	@Test
	public void findAllCorrectMoves_whenNoCaptureAvailable_test() {
		boardManager.addWhitePawn(46);
		boardManager.addWhitePawn(49);
		boardManager.addWhitePawn(43);
		boardManager.addWhiteQueen(35);
		boardManager.addBlackPawn(18);
		boardManager.addBlackPawn(16);
		boardManager.addBlackPawn(22);
		boardManager.addBlackPawn(26);
		
		testObj.findAllCorrectMoves(boardManager, true);
		
		assertEquals(12, testObj.getPossibleMoves().size());
	}
/*	
	@Test
	public void checkForPawnPromotion_noPromotion_test() {
		boardManager.addWhitePawn(12);
		boardManager.addBlackPawn(39);

		testObj.findAllCorrectMoves(true);
		testObj.makeHop(12, 7);
		gameEngine.moveFinished(7);
		
		assertFalse(boardManager.getWhitePieces().get(0).isQueen());
		
		testObj.makeHop(39, 44);
		gameEngine.moveFinished(44);
		
		assertFalse(boardManager.getBlackPieces().get(0).isQueen());
	}
	
	@Test
	public void checkForPawnPromotion_promotion_test() {
		boardManager.addWhitePawn(7);
		boardManager.addBlackPawn(44);

		testObj.findAllCorrectMoves(true);
		testObj.makeHop(7, 1);
		gameEngine.moveFinished(1);
		
		assertTrue(boardManager.getWhitePieces().get(0).isQueen());
		
		testObj.makeHop(44, 50);
		gameEngine.moveFinished(50);
		
		assertTrue(boardManager.getBlackPieces().get(0).isQueen());
	}
*/

}
