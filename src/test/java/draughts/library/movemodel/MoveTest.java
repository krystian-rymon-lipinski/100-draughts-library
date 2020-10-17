package draughts.library.movemodel;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.boardmodel.BlackPawn;
import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.boardmodel.WhitePawn;
import draughts.library.managers.BoardManager;
import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

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
		Piece whitePawn = new WhitePawn(new Tile(5, 0));
		
		testObj = new Move<Hop>(whitePawn, new Hop(getTile(31), getTile(26)));
		
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
		Piece blackPawn = new BlackPawn(new Tile(6, 9));
		Piece whitePawn1 = new WhitePawn(new Tile(5, 8));
		Piece whitePawn2 = new WhitePawn(new Tile(5, 6));
		Piece whitePawn3 = new WhitePawn(new Tile(5, 4));
		
		testObj2 = new Move<Capture>(blackPawn, new Capture(getTile(35), getTile(24), whitePawn1));
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

}
