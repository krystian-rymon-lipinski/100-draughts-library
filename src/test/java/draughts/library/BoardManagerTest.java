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
	}
	
	@Test
	public void prepareTiles_test() {
		testObj.createStartingPosition();
		
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
		testObj.createStartingPosition();
		
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
		testObj.createStartingPosition();

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
		testObj.createStartingPosition();

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
		testObj.createStartingPosition();

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
		testObj.createStartingPosition();

		assertEquals(20, testObj.getWhitePieces().size());
		assertEquals(20, testObj.getBlackPieces().size());
		testObj.makeHop(35, 30);
		testObj.makeHop(19, 24);
		
		testObj.makeCapture(30, 19, 24);
		assertNull(testObj.findPieceByIndex(24));
		assertEquals(19, testObj.getBlackPieces().size());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(24).getState());
		
		testObj.makeCapture(13, 24, 19);
		assertNull(testObj.findPieceByIndex(19));
		assertEquals(19, testObj.getWhitePieces().size());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(19).getState());
		
	}
	
	//Position creator
	
	@Test
	public void createEmptyBoard_test() {
		testObj.createEmptyBoard();
		
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(1).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(12).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(21).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(15).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(32).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(50).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(48).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(17).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(36).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(24).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(41).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(2).getState());

	} 
	
	@Test
	public void addWhitePieces_test() {
		testObj.createEmptyBoard();
		
		testObj.addWhitePawn(12);
		testObj.addWhitePawn(33);
		testObj.addWhiteQueen(41);
		
		assertEquals(3, testObj.getWhitePieces().size());
		assertEquals(Tile.State.WHITE_PAWN, testObj.findTileByIndex(12).getState());
		assertEquals(Tile.State.WHITE_PAWN, testObj.findTileByIndex(33).getState());
		assertEquals(Tile.State.WHITE_QUEEN, testObj.findTileByIndex(41).getState());
	}
	
	@Test
	public void addBlackPieces_test() {
		testObj.createEmptyBoard();
		
		testObj.addBlackPawn(21);
		testObj.addBlackPawn(1);
		testObj.addBlackQueen(50);
		
		assertEquals(3, testObj.getBlackPieces().size());
		assertEquals(Tile.State.BLACK_PAWN, testObj.findTileByIndex(21).getState());
		assertEquals(Tile.State.BLACK_PAWN, testObj.findTileByIndex(1).getState());
		assertEquals(Tile.State.BLACK_QUEEN, testObj.findTileByIndex(50).getState());
	}
	
	
	
	
	

}
