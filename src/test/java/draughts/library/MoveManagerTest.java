package draughts.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.boardmodel.Tile;

@RunWith(MockitoJUnitRunner.class)
public class MoveManagerTest {
	
	MoveManager moveManager;
	BoardManager boardManager;
	
	@Before
	public void setUp() {
		moveManager = new MoveManager();
		boardManager = moveManager.getBoardManager();
	}
	
	@Test
	public void addHops_test() {
		Move move = new Move(31, 26);
		move.addHop(21);
		
		assertEquals(2, move.getHops().size());
		assertEquals(0, move.getPawnsTaken().size());
		
		Move move2 = new Move(35, 24, 30);
		move2.addHop(15, 20);
		move2.addHop(4, 10);
		
		assertEquals(3, move2.getHops().size());
		assertEquals(3, move2.getPawnsTaken().size());
	}
	
	@Test
	public void moveWithSingleHop_test() {
		Move whiteMove = new Move(31, 26);
		
		assertEquals(0, moveManager.getHopsMadeInMove());
		moveManager.makeHop(whiteMove);
		
		assertEquals(Tile.State.WHITE_PAWN, boardManager.findTileByIndex(26).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(31).getState());
		assertNull(boardManager.findPieceByIndex(31));
		assertEquals(26, boardManager.findPieceByIndex(26).getPosition());
		assertEquals(0, moveManager.getHopsMadeInMove());
		
		Move blackMove = new Move(20, 24);
		
		assertEquals(0, moveManager.getHopsMadeInMove());
		moveManager.makeHop(blackMove);
		
		assertEquals(Tile.State.BLACK_PAWN, boardManager.findTileByIndex(24).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(20).getState());
		assertNull(boardManager.findPieceByIndex(20));
		assertEquals(24, boardManager.findPieceByIndex(24).getPosition());
		assertEquals(0, moveManager.getHopsMadeInMove());

	}
	
	@Test
	public void moveWithMultipleHops_test() {
		Move whiteMove1 = new Move(35, 30);
		Move blackMove1 = new Move(20, 25);
		Move whiteMove2 = new Move(34, 29);
		Move blackMove2 = new Move(25, 34, 30);
		blackMove2.addHop(23, 29);
		
		moveManager.makeHop(whiteMove1);
		moveManager.makeHop(blackMove1);
		moveManager.makeHop(whiteMove2);
		
		moveManager.makeHop(blackMove2);
		assertEquals(1, moveManager.getHopsMadeInMove());
		moveManager.makeHop(blackMove2);
		assertEquals(0, moveManager.getHopsMadeInMove());
		
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(25).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(30).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(34).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(29).getState());
		assertEquals(Tile.State.BLACK_PAWN, boardManager.findTileByIndex(23).getState());
		
		assertEquals(18, boardManager.getWhitePieces().size());

		
		Move whiteMove3 = new Move(33, 28);
		Move blackMove3 = new Move(19, 24);
		Move whiteMove4 = new Move(28, 19, 23);
		whiteMove4.addHop(30, 24);
		
		moveManager.makeHop(whiteMove3);
		moveManager.makeHop(blackMove3);
		
		moveManager.makeHop(whiteMove4);
		assertEquals(1, moveManager.getHopsMadeInMove());
		moveManager.makeHop(whiteMove4);
		assertEquals(0, moveManager.getHopsMadeInMove());
		
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(28).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(23).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(19).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(24).getState());
		assertEquals(Tile.State.WHITE_PAWN, boardManager.findTileByIndex(30).getState());
		
		assertEquals(18, boardManager.getBlackPieces().size());
		
	}

}
