package draughts.library.movemodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.boardmodel.BlackPawn;
import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.boardmodel.WhitePawn;
import draughts.library.managers.BoardManager;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MoveTest {
	
	Move<Hop> testObj;
	Move<Capture> testObj2;
	BoardManager boardManager;
	
	@Before
	public void setUp() {
		boardManager = new BoardManager();
		boardManager.createEmptyBoard();
	}
	
	public Tile getTile(int index) {
		return boardManager.findTileByIndex(index);
	}
	
	@Test
	public void addHops_test() {
		Piece whitePawn = new WhitePawn(getTile(31));
		
		testObj = new Move<>(whitePawn, new Hop(getTile(31), getTile(26)));
		
		assertEquals(1, testObj.getNumberOfHops());
		assertEquals(whitePawn, testObj.getMovingPiece());
		assertEquals(31, testObj.getMoveSource().getIndex());
		assertEquals(26, testObj.getMoveDestination().getIndex());
		
		testObj.addHop(new Hop(getTile(26), getTile(21)));
			
		assertEquals(2, testObj.getNumberOfHops());	
		assertEquals(31, testObj.getMoveSource().getIndex());
		assertEquals(21, testObj.getMoveDestination().getIndex());
	}
	
	@Test
	public void addCaptures_test() {
		Piece blackPawn = new BlackPawn(getTile(35));
		Piece whitePawn1 = new WhitePawn(getTile(30));
		Piece whitePawn2 = new WhitePawn(getTile(29));
		Piece whitePawn3 = new WhitePawn(getTile(28));
		
		testObj2 = new Move<>(blackPawn, new Capture(getTile(35), getTile(24), whitePawn1));
		testObj2.classify();
		
		assertEquals(1, testObj2.getNumberOfHops());
		assertEquals(blackPawn, testObj2.getMovingPiece());
		assertEquals(35, testObj2.getMoveSource().getIndex());
		assertEquals(24, testObj2.getMoveDestination().getIndex());
		assertEquals(1, testObj2.getMoveTakenPawns().size());
		assertEquals(whitePawn1, testObj2.getMoveTakenPawns().get(0));
		
		testObj2.addHop(new Capture(getTile(24), getTile(15), whitePawn2));
		
		assertEquals(2, testObj2.getNumberOfHops());
		assertEquals(blackPawn, testObj2.getMovingPiece());
		assertEquals(35, testObj2.getMoveSource().getIndex());
		assertEquals(15, testObj2.getMoveDestination().getIndex());
		assertEquals(2, testObj2.getMoveTakenPawns().size());
		assertEquals(whitePawn2, testObj2.getMoveTakenPawns().get(1));
		
		testObj2.addHop(new Capture(getTile(15), getTile(4), whitePawn3));
		
		assertEquals(3, testObj2.getNumberOfHops());
		assertEquals(blackPawn, testObj2.getMovingPiece());
		assertEquals(35, testObj2.getMoveSource().getIndex());
		assertEquals(4, testObj2.getMoveDestination().getIndex());
		assertEquals(3, testObj2.getMoveTakenPawns().size());
		assertEquals(whitePawn3, testObj2.getMoveTakenPawns().get(2));
	}

	@Test
	public void classify_noPromotion_noCapture() {
		Piece whitePawn = new WhitePawn(getTile(20));

		testObj = new Move<>(whitePawn, new Hop(getTile(20), getTile(15)));
		testObj.classify();

		assertFalse(testObj.isCapture());
		assertFalse(testObj.isPromotion());
	}

	@Test
	public void classify_noPromotion_capture() {
		Piece blackPawn = new BlackPawn(getTile(33));
		Piece whitePawn = new WhitePawn(getTile(28));

		testObj2 = new Move<>(blackPawn, new Capture(getTile(33), getTile(22), whitePawn));
		testObj2.classify();

		assertFalse(testObj2.isPromotion());
		assertTrue(testObj2.isCapture());
	}

	@Test
	public void classify_promotion_noCapture() {
		Piece whitePawn = new WhitePawn(getTile(9));

		testObj = new Move<>(whitePawn, new Hop(getTile(9), getTile(3)));
		testObj.classify();

		assertTrue(testObj.isPromotion());
		assertFalse(testObj.isCapture());
	}

	@Test
	public void classify_promotion_capture() {
		Piece blackPawn = new BlackPawn(getTile(37));
		Piece whitePawn = new WhitePawn(getTile(42));

		testObj2 = new Move<>(blackPawn, new Capture(getTile(37), getTile(48), whitePawn));
		testObj2.classify();

		assertTrue(testObj2.isPromotion());
		assertTrue(testObj2.isCapture());
	}

}
