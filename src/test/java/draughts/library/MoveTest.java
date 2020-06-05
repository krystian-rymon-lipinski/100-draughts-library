package draughts.library;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.boardmodel.BlackPawn;
import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.boardmodel.WhitePawn;

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
		testObj.addHop(new Hop(getTile(26), getTile(21)));
		
		assertEquals(2, testObj.getNumberOfHops());	
	}
	
	@Test
	public void addCaptures_test() {
		Piece blackPawn = new BlackPawn(new Tile(6, 9));
		testObj2 = new Move<Capture>(blackPawn, new Capture(getTile(35), getTile(24), getTile(30)));
		testObj2.addHop(new Capture(getTile(24), getTile(15), getTile(20)));
		testObj2.addHop(new Capture(getTile(15), getTile(4), getTile(10)));
		
		assertEquals(3, testObj2.getNumberOfHops());
	}

}
