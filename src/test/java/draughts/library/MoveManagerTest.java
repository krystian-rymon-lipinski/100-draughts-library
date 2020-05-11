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
		
		assertEquals(0, testObj.getHopsMadeInMove());
		testObj.makeHop(whiteMove);
		
		assertEquals(Tile.State.WHITE_PAWN, boardManager.findTileByIndex(26).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(31).getState());
		assertNull(boardManager.findPieceByIndex(31));
		assertEquals(26, boardManager.findPieceByIndex(26).getPosition());
		assertEquals(0, testObj.getHopsMadeInMove());
		
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
		Move whiteMove1 = new Move(35, 30);
		Move blackMove1 = new Move(20, 25);
		Move whiteMove2 = new Move(34, 29);
		Move blackMove2 = new Move(25, 34, 30);
		blackMove2.addHop(23, 29);
		
		testObj.makeHop(whiteMove1);
		testObj.makeHop(blackMove1);
		testObj.makeHop(whiteMove2);
		
		testObj.makeHop(blackMove2);
		assertEquals(1, testObj.getHopsMadeInMove());
		testObj.makeHop(blackMove2);
		assertEquals(0, testObj.getHopsMadeInMove());
		
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
		
		testObj.makeHop(whiteMove3);
		testObj.makeHop(blackMove3);
		
		testObj.makeHop(whiteMove4);
		assertEquals(1, testObj.getHopsMadeInMove());
		testObj.makeHop(whiteMove4);
		assertEquals(0, testObj.getHopsMadeInMove());
		
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(28).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(23).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(19).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(24).getState());
		assertEquals(Tile.State.WHITE_PAWN, boardManager.findTileByIndex(30).getState());
		
		assertEquals(18, boardManager.getBlackPieces().size());
		
	}
	
	public ArrayList<Move> findMovesForPiece(int piecePosition) {
		Piece piece = testObj.getBoardManager().findPieceByIndex(piecePosition);
		Tile currentTile = testObj.getBoardManager().findTileByIndex(piecePosition);
		return piece.findMoves(testObj.getBoardManager().getBoard(), currentTile);
	}
	
	@Test
	public void findMoves_forWhitePawn_test() {
		ArrayList<Move> moves = findMovesForPiece(33);
		assertEquals(Tile.State.WHITE_PAWN, testObj.getBoardManager().findTileByIndex(33).getState());
		
		assertEquals(2, moves.size());
		System.out.println(moves.get(0));
		assertTrue(moves.get(0).getDestination(testObj.getHopsMadeInMove()) == 28 || 
				   moves.get(0).getDestination(testObj.getHopsMadeInMove()) == 29);
		assertTrue(moves.get(1).getDestination(testObj.getHopsMadeInMove()) == 28 || 
				   moves.get(1).getDestination(testObj.getHopsMadeInMove()) == 29);
	}
	
	@Test
	public void findMoves_forBlackPawn_test() {
		ArrayList<Move> moves = findMovesForPiece(18);
		
		assertEquals(2, moves.size());
		assertTrue(moves.get(0).getDestination(testObj.getHopsMadeInMove()) == 22 || 
				   moves.get(0).getDestination(testObj.getHopsMadeInMove()) == 22);
		assertTrue(moves.get(1).getDestination(testObj.getHopsMadeInMove()) == 23 || 
				   moves.get(1).getDestination(testObj.getHopsMadeInMove()) == 23);
	}
	
	@Test
	public void findMoves_forMostLeftPawn_test() {
		ArrayList<Move> moves = findMovesForPiece(16);
		
		assertEquals(1, moves.size());
		assertEquals(21, moves.get(0).getDestination(testObj.getHopsMadeInMove()));
	}
	
	@Test
	public void findMoves_forMostRightPawn_test() {
		ArrayList<Move> moves = findMovesForPiece(35);
		
		assertEquals(1, moves.size());
		assertEquals(30, moves.get(0).getDestination(testObj.getHopsMadeInMove()));
	}
	
	@Test
	public void findTakes_withSingleHop_forPawn_test() {
		
	}
	
	@Test
	public void findMoves_forQueen_test() {
		
	}

}
