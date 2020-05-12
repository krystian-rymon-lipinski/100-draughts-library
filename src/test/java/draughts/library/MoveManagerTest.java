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
		whiteMove.addHop(8, 12);
		
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
		blackMove.addHop(24, 19);
		
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
	
	public ArrayList<Move> findMovesForPiece(int piecePosition) {
		Piece piece = testObj.getBoardManager().findPieceByIndex(piecePosition);
		Tile currentTile = testObj.getBoardManager().findTileByIndex(piecePosition);
		return piece.findMoves(testObj.getBoardManager().getBoard(), currentTile);
	}
	
	@Test
	public void findMoves_forWhitePawn_test() {
		boardManager.addWhitePawn(33);
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
		boardManager.addBlackPawn(18);
		ArrayList<Move> moves = findMovesForPiece(18);
		
		assertEquals(2, moves.size());
		assertTrue(moves.get(0).getDestination(testObj.getHopsMadeInMove()) == 22 || 
				   moves.get(0).getDestination(testObj.getHopsMadeInMove()) == 22);
		assertTrue(moves.get(1).getDestination(testObj.getHopsMadeInMove()) == 23 || 
				   moves.get(1).getDestination(testObj.getHopsMadeInMove()) == 23);
	}
	
	@Test
	public void findMoves_forMostLeftPawn_test() {
		boardManager.addBlackPawn(16);
		ArrayList<Move> moves = findMovesForPiece(16);
		
		assertEquals(1, moves.size());
		assertEquals(21, moves.get(0).getDestination(testObj.getHopsMadeInMove()));
	}
	
	@Test
	public void findMoves_forMostRightPawn_test() {
		boardManager.addWhitePawn(35);
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
