package draughts.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.boardmodel.Tile;

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
		
		testObj.findAllCorrectMoves(true);
		
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
		
		testObj.findAllCorrectMoves(true);
		
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
