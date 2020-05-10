package draughts.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
	public void makeMove_test() {
		testObj.makeMove(31, 27);
		
		assertNull(testObj.findPieceByIndex(31));
		assertEquals(27, testObj.findPieceByIndex(27).getPosition());
		assertEquals(Tile.State.WHITE_PAWN, testObj.findTileByIndex(27).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(31).getState());
		
		testObj.makeMove(20, 25);
		
		assertNull(testObj.findPieceByIndex(20));
		assertEquals(25, testObj.findPieceByIndex(25).getPosition());
		assertEquals(Tile.State.BLACK_PAWN, testObj.findTileByIndex(25).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(20).getState());
		
	}

}
