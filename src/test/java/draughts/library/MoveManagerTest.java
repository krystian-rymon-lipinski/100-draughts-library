package draughts.library;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

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
		boardManager = testObj.getBoardManager();
		boardManager.createEmptyBoard();
	}
	
	@Test
	public void addHops_test() {
		Move<Hop> move = new Move<Hop>(new Hop(31, 26));
		move.addHop(new Hop(26, 21));
		
		assertEquals(2, move.getNumberOfHops());
		
		Move<Capture> move2 = new Move<Capture>(new Capture(35, 24, 30));
		move2.addHop(new Capture(24, 15, 20));
		move2.addHop(new Capture(15, 4, 10));
		
		assertEquals(3, move2.getNumberOfHops());
	}
	
	@Test
	public void findHopMovesForAllPieces_test() {
		boardManager.addWhitePawn(23);
		boardManager.addWhitePawn(34);
		boardManager.addWhiteQueen(40);
		
		ArrayList<Move<Hop>> whiteMoves = testObj.findMovesForAllPieces(true);
		
		assertEquals(8, whiteMoves.size());
		
		boardManager.addBlackPawn(31);
		boardManager.addBlackPawn(17);
		boardManager.addBlackQueen(3);
		
		ArrayList<Move<Hop>> blackMoves = testObj.findMovesForAllPieces(false);
		
		assertEquals(10, blackMoves.size());
	}
	
	@Test
	public void findSingleCapturesForAllPieces() {
		boardManager.addWhitePawn(14);
		boardManager.addBlackPawn(10);
		boardManager.addWhiteQueen(25);
		boardManager.addBlackPawn(20);
		boardManager.addBlackPawn(34);
		
		ArrayList<Capture> whiteMoves = testObj.findCapturesForAllPieces(true);
		
		assertEquals(4, whiteMoves.size());
		
		boardManager.createEmptyBoard();
		
		boardManager.addBlackPawn(18);
		boardManager.addWhitePawn(12);
		boardManager.addWhitePawn(13);
		boardManager.addBlackQueen(32);
		boardManager.addWhitePawn(41);
		boardManager.addWhitePawn(27);
		boardManager.addWhitePawn(49);
		
		ArrayList<Capture> blackMoves = testObj.findCapturesForAllPieces(false);
		
		assertEquals(5, blackMoves.size());
	}
	
	
	
	
	
	/*
	
	//Coœ na póŸniej
	
	@Test
	public void moveWithSingleHop_test() {
		boardManager.addWhitePawn(31);
		testObj.makeHop(31, 26);
		
		assertEquals(0, testObj.getHopsMadeInMove());
		
		assertEquals(Tile.State.WHITE_PAWN, boardManager.findTileByIndex(26).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(31).getState());
		assertNull(boardManager.findPieceByIndex(31));
		assertEquals(26, boardManager.findPieceByIndex(26).getPosition());
		assertEquals(0, testObj.getHopsMadeInMove());
		
		boardManager.addBlackPawn(20);
		testObj.makeHop(20, 24);
		
		assertEquals(0, testObj.getHopsMadeInMove());
		
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
		
		Move<Capture> whiteMove = new Move<Capture>(new Capture(26, 17, 21));
		whiteMove.addHop(new Capture(17, 8, 12)); //double take by white pawn
		
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

		
		Move<Capture> blackMove = new Move<Capture>(new Capture(2, 13, 8));
		blackMove.addHop(new Capture(13, 24, 19)); //double take by black pawn
		
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
	
	*/
	
	
	

}
