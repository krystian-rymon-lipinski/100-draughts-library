package draughts.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;

@RunWith(MockitoJUnitRunner.class)
public class MoveManagerTest {
	
	MoveManager testObj;
	BoardManager boardManager;
	
	@Before
	public void setUp() {
		testObj = new MoveManager();
		boardManager = testObj.getBoardManager();
		boardManager.createEmptyBoard();
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
		boardManager.addWhitePawn(31);
		Move whiteMove = new Move(31, 26);
		
		assertEquals(0, testObj.getHopsMadeInMove());
		testObj.makeHop(whiteMove);
		
		assertEquals(Tile.State.WHITE_PAWN, boardManager.findTileByIndex(26).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(31).getState());
		assertNull(boardManager.findPieceByIndex(31));
		assertEquals(26, boardManager.findPieceByIndex(26).getPosition());
		assertEquals(0, testObj.getHopsMadeInMove());
		
		boardManager.addBlackPawn(20);
		Move blackMove = new Move(20, 24);
		
		assertEquals(0, testObj.getHopsMadeInMove());
		testObj.makeHop(blackMove);
		
		assertEquals(Tile.State.BLACK_PAWN, boardManager.findTileByIndex(24).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(20).getState());
		assertNull(boardManager.findPieceByIndex(20));
		assertEquals(24, boardManager.findPieceByIndex(24).getPosition());
		assertEquals(0, testObj.getHopsMadeInMove());

	}
	
	@Test
	public void moveWithMultipleHops_test() {
		boardManager.addWhitePawn(26);
		boardManager.addWhitePawn(19);
		boardManager.addBlackPawn(21);
		boardManager.addBlackPawn(12);
		boardManager.addBlackPawn(2);
		
		assertEquals(3, boardManager.getBlackPieces().size());
		assertEquals(2, boardManager.getWhitePieces().size());
		
		Move whiteMove = new Move(26, 17, 21);
		whiteMove.addHop(8, 12); //double take by white pawn
		
		testObj.makeHop(whiteMove);
		assertEquals(1, testObj.getHopsMadeInMove());
		testObj.makeHop(whiteMove);
		assertEquals(0, testObj.getHopsMadeInMove());
		
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(26).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(21).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(17).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(12).getState());
		assertEquals(Tile.State.WHITE_PAWN, boardManager.findTileByIndex(8).getState());
		
		assertEquals(1, boardManager.getBlackPieces().size());

		
		Move blackMove = new Move(2, 13, 8);
		blackMove.addHop(24, 19); //double take by black pawn
		
		testObj.makeHop(blackMove);
		assertEquals(1, testObj.getHopsMadeInMove());
		testObj.makeHop(blackMove);
		assertEquals(0, testObj.getHopsMadeInMove());

		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(2).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(8).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(13).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(19).getState());
		assertEquals(Tile.State.BLACK_PAWN, boardManager.findTileByIndex(24).getState());
		
		assertEquals(0, boardManager.getWhitePieces().size());
		
	}
	
	
	
	
	

}
