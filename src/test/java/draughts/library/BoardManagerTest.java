package draughts.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.boardmodel.BlackPawn;
import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.boardmodel.WhitePawn;

@RunWith(MockitoJUnitRunner.class)
public class BoardManagerTest {
	
	BoardManager testObj;
	
	@Before
	public void setUp() {
		testObj = new BoardManager();
		testObj.prepareBoard();
	}
	
	@Test
	public void prepareTiles_test() {
		testObj.prepareTiles();
		
		assertEquals(Tile.State.BLACK_PAWN, testObj.getBoard()[0][1].getState());
		assertEquals(Tile.State.BLACK_PAWN, testObj.getBoard()[3][2].getState());
		assertEquals(Tile.State.WHITE_PAWN, testObj.getBoard()[7][4].getState());
		assertEquals(Tile.State.WHITE_PAWN, testObj.getBoard()[9][0].getState());
		assertEquals(Tile.State.WHITE_PAWN, testObj.getBoard()[8][3].getState());
		assertEquals(Tile.State.EMPTY, testObj.getBoard()[4][5].getState());
		assertEquals(Tile.State.BLACK_PAWN, testObj.getBoard()[3][8].getState());
		assertEquals(Tile.State.WHITE_TILE, testObj.getBoard()[9][9].getState());
		assertEquals(Tile.State.WHITE_TILE, testObj.getBoard()[0][0].getState());
		assertEquals(Tile.State.WHITE_TILE, testObj.getBoard()[6][8].getState());

	}
	
	@Test
	public void preparePieces_test() {
		testObj.preparePieces();
		
		assertEquals(20, testObj.getWhitePieces().size());
		assertEquals(20, testObj.getBlackPieces().size());
		
		WhitePawn whitePiece1 = (WhitePawn) testObj.getWhitePieces().get(0);
		WhitePawn whitePiece2 = (WhitePawn) testObj.getWhitePieces().get(8);
		WhitePawn whitePiece3 = (WhitePawn) testObj.getWhitePieces().get(19);
		BlackPawn blackPiece1 = (BlackPawn) testObj.getBlackPieces().get(0);
		BlackPawn blackPiece2 = (BlackPawn) testObj.getBlackPieces().get(13);
		BlackPawn blackPiece3 = (BlackPawn) testObj.getBlackPieces().get(19);
		
		assertEquals(31, whitePiece1.getPosition());
		assertEquals(39, whitePiece2.getPosition());
		assertEquals(50, whitePiece3.getPosition());
		assertEquals(1, blackPiece1.getPosition());
		assertEquals(14, blackPiece2.getPosition());
		assertEquals(20, blackPiece3.getPosition());
	}
	
	@Test
	public void findTileByIndex_test() {
		
		assertEquals(Tile.class, testObj.findTileByIndex(1).getClass());
		
		assertEquals(Tile.State.BLACK_PAWN, testObj.findTileByIndex(1).getState());
		assertEquals(Tile.State.BLACK_PAWN, testObj.findTileByIndex(20).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(21).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(30).getState());
		assertEquals(Tile.State.WHITE_PAWN, testObj.findTileByIndex(31).getState());
		assertEquals(Tile.State.WHITE_PAWN, testObj.findTileByIndex(50).getState());
	}
	
	@Test 
	public void findPieceByIndex_test() {
		assertTrue(testObj.findPieceByIndex(2) instanceof Piece);
		
		assertEquals(1, testObj.findPieceByIndex(1).getPosition());
		assertEquals(20, testObj.findPieceByIndex(20).getPosition());
		assertNull(testObj.findPieceByIndex(21));
		assertNull(testObj.findPieceByIndex(30));
		assertEquals(31, testObj.findPieceByIndex(31).getPosition());
		assertEquals(50, testObj.findPieceByIndex(50).getPosition());
	}
	
	@Test
	public void makeHop_test() {
		testObj.makeHop(31, 27);
		
		assertNull(testObj.findPieceByIndex(31));
		assertEquals(27, testObj.findPieceByIndex(27).getPosition());
		assertEquals(Tile.State.WHITE_PAWN, testObj.findTileByIndex(27).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(31).getState());
		
		testObj.makeHop(20, 25);
		
		assertNull(testObj.findPieceByIndex(20));
		assertEquals(25, testObj.findPieceByIndex(25).getPosition());
		assertEquals(Tile.State.BLACK_PAWN, testObj.findTileByIndex(25).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(20).getState());
		
	}
	
	@Test
	public void makeHop_withTakenPawn_test() {
		assertEquals(20, testObj.getWhitePieces().size());
		assertEquals(20, testObj.getBlackPieces().size());
		testObj.makeHop(35, 30);
		testObj.makeHop(19, 24);
		
		testObj.makeHop(30, 19, 24);
		assertNull(testObj.findPieceByIndex(24));
		assertEquals(19, testObj.getBlackPieces().size());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(24).getState());
		
		testObj.makeHop(13, 24, 19);
		assertNull(testObj.findPieceByIndex(19));
		assertEquals(19, testObj.getWhitePieces().size());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(19).getState());
		
	}
	
	public ArrayList<Move> findMovesForPiece(int piecePosition) {
		Piece piece = testObj.findPieceByIndex(piecePosition);
		Tile currentTile = testObj.findTileByIndex(piecePosition);
		return piece.findMoves(testObj.getBoard(), currentTile);
	}
	
	@Test
	public void findMoves_forWhitePawn_test() {
		ArrayList<Move> moves = findMovesForPiece(33);
		assertEquals(Tile.State.WHITE_PAWN, testObj.findTileByIndex(33).getState());
		
		assertEquals(2, moves.size());
		System.out.println(moves.get(0));
		assertTrue(moves.get(0).getDestination() == 28 || 
				   moves.get(0).getDestination() == 29);
		assertTrue(moves.get(1).getDestination() == 28 || 
				   moves.get(1).getDestination() == 29);
	}
	
	@Test
	public void findMoves_forBlackPawn_test() {
		ArrayList<Move> moves = findMovesForPiece(18);
		
		assertEquals(2, moves.size());
		assertTrue(moves.get(0).getDestination() == 22 || 
				   moves.get(0).getDestination() == 22);
		assertTrue(moves.get(1).getDestination() == 23 || 
				   moves.get(1).getDestination() == 23);
	}
	
	@Test
	public void findMoves_forMostLeftPawn_test() {
		ArrayList<Move> moves = findMovesForPiece(16);
		
		assertEquals(1, moves.size());
		assertEquals(21, moves.get(0).getDestination());
	}
	
	@Test
	public void findMoves_forMostRightPawn_test() {
		ArrayList<Move> moves = findMovesForPiece(35);
		
		assertEquals(1, moves.size());
		assertEquals(30, moves.get(0).getDestination());
	}
	
	@Test
	public void findTakes_withSingleHop_forPawn_test() {
		
	}
	
	@Test
	public void findMoves_forQueen_test() {
		
	}
	
	@Test
	public void findMoves_forAllPawns_test() {
		
	}
	

}
