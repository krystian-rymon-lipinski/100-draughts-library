package draughts.library;

import static org.junit.Assert.assertEquals;
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
import draughts.library.exceptions.NoPieceFoundInRequestedTileException;

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
	public void findPieceByIndex_test() throws NoPieceFoundInRequestedTileException {
		testObj.createStartingPosition();

		assertTrue(testObj.findPieceByIndex(2) instanceof Piece);
		
		assertEquals(1, testObj.findPieceByIndex(1).getPosition());
		assertEquals(20, testObj.findPieceByIndex(20).getPosition());
		assertEquals(31, testObj.findPieceByIndex(31).getPosition());
		assertEquals(50, testObj.findPieceByIndex(50).getPosition());
	}
	
	@Test(expected = NoPieceFoundInRequestedTileException.class)
	public void findPieceByIndex_NoPieceFound_test() throws NoPieceFoundInRequestedTileException {
		testObj.createStartingPosition();
		
		testObj.findPieceByIndex(23);
	}
	
	@Test
	public void makeHop_test() throws NoPieceFoundInRequestedTileException {
		testObj.createStartingPosition();

		testObj.makeHop(31, 27);
		
		assertEquals(27, testObj.findPieceByIndex(27).getPosition());
		assertEquals(Tile.State.WHITE_PAWN, testObj.findTileByIndex(27).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(31).getState());
		
		testObj.makeHop(20, 25);
		
		assertEquals(25, testObj.findPieceByIndex(25).getPosition());
		assertEquals(Tile.State.BLACK_PAWN, testObj.findTileByIndex(25).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(20).getState());
		
	}
	
	@Test
	public void makeCapture_test() throws NoPieceFoundInRequestedTileException{
		testObj.createStartingPosition();

		assertEquals(20, testObj.getWhitePieces().size());
		assertEquals(20, testObj.getBlackPieces().size());
		testObj.makeHop(35, 30);
		testObj.makeHop(19, 24);
		
		testObj.makeCapture(30, 19, 24);
		assertEquals(19, testObj.getBlackPieces().size());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(24).getState());
		
		testObj.makeCapture(13, 24, 19);
		assertEquals(19, testObj.getWhitePieces().size());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(19).getState());	
	}
	
	@Test 
	public void reverseHop_test() throws NoPieceFoundInRequestedTileException {
		testObj.createEmptyBoard();
		
		testObj.addWhitePawn(33);
		testObj.makeHop(33, 28);
		testObj.reverseHop(33, 28);
		
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(28).getState());
		assertEquals(Tile.State.WHITE_PAWN, testObj.findTileByIndex(33).getState());
		assertEquals(33, testObj.findPieceByIndex(33).getPosition());
		
		testObj.addBlackPawn(19);
		testObj.makeHop(19, 23);
		testObj.reverseHop(19, 23);
		
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(23).getState());
		assertEquals(Tile.State.BLACK_PAWN, testObj.findTileByIndex(19).getState());
		assertEquals(19, testObj.findPieceByIndex(19).getPosition());
	}

		
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
	

	@Test
	public void findLongestConsecutiveCapturesForPiece_twoLevels_test() throws NoPieceFoundInRequestedTileException {
		testObj.createEmptyBoard();
		
		testObj.addWhitePawn(24);
		testObj.addBlackPawn(19);
		testObj.addBlackPawn(18);
		
		Piece testPiece = testObj.findPieceByIndex(24);
		ArrayList<Move<Capture>> whiteMoves = testObj.findLongestConsecutiveCaptures(testPiece);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(2, whiteMoves.get(0).getNumberOfHops());
		assertEquals(24, whiteMoves.get(0).getHop(0).getSource());
		assertEquals(13, whiteMoves.get(0).getHop(1).getSource());
		assertEquals(13, whiteMoves.get(0).getHop(0).getDestination());
		assertEquals(22, whiteMoves.get(0).getHop(1).getDestination());
		assertEquals(19, whiteMoves.get(0).getHop(0).getTakenPawn());
		assertEquals(18, whiteMoves.get(0).getHop(1).getTakenPawn());
		
		assertEquals(24, testObj.findPieceByIndex(24).getPosition());
		
	}
	
	@Test
	public void findLongestConsecutiveCapturesForPiece_twoLevels_inTwoDirections_test() throws NoPieceFoundInRequestedTileException {
		testObj.createEmptyBoard();
		
		testObj.addWhitePawn(28);
		testObj.addBlackPawn(22);
		testObj.addBlackPawn(11);
		testObj.addBlackPawn(33);
		testObj.addBlackPawn(44);
		
		Piece testPiece = testObj.findPieceByIndex(28);
		ArrayList<Move<Capture>> whiteMoves = testObj.findLongestConsecutiveCaptures(testPiece);
		
		assertEquals(2, whiteMoves.size());
		assertEquals(2, whiteMoves.get(0).getNumberOfHops());
		assertEquals(2, whiteMoves.get(1).getNumberOfHops());

	}

	@Test
	public void findLongestConsecutiveCapturesForPiece_twoLevels_withSameRoot_test() throws NoPieceFoundInRequestedTileException {
		testObj.createEmptyBoard();
		
		testObj.addWhiteQueen(50);
		testObj.addBlackPawn(39);
		testObj.addBlackQueen(14);
		testObj.addBlackPawn(32);
		
		Piece testPiece = testObj.findPieceByIndex(50);
		ArrayList<Move<Capture>> whiteMoves = testObj.findLongestConsecutiveCaptures(testPiece);
		
		assertEquals(5, whiteMoves.size());
		assertEquals(2, whiteMoves.get(0).getNumberOfHops());
		assertEquals(2, whiteMoves.get(4).getNumberOfHops());

	}
	
	@Test
	public void findLongestConsecutiveCapturesForPiece_threeLevels_test() throws NoPieceFoundInRequestedTileException {
		testObj.createEmptyBoard();
		
		testObj.addWhiteQueen(48);
		testObj.addBlackPawn(37);
		testObj.addBlackQueen(22);
		testObj.addBlackPawn(7);
		
		Piece testPiece = testObj.findPieceByIndex(48);
		ArrayList<Move<Capture>> whiteMoves = testObj.findLongestConsecutiveCaptures(testPiece);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(3, whiteMoves.get(0).getNumberOfHops());
	}
	
	@Test
	public void findLongestConsecutiveCapturesForPiece_threeLevels_withMultipleBranches_test() throws NoPieceFoundInRequestedTileException {
		testObj.createEmptyBoard();
		
		testObj.addBlackQueen(3);
		testObj.addWhitePawn(11);
		testObj.addWhitePawn(12);
		testObj.addWhiteQueen(19);
		testObj.addWhitePawn(22);
		testObj.addWhitePawn(33);
		testObj.addWhiteQueen(41);
		
		Piece testPiece = testObj.findPieceByIndex(3);
		ArrayList<Move<Capture>> blackMoves = testObj.findLongestConsecutiveCaptures(testPiece);
		
		assertEquals(7, blackMoves.size());
		assertEquals(3, blackMoves.get(0).getNumberOfHops());
		assertEquals(3, blackMoves.get(6).getNumberOfHops());
	}
	
	@Test
	public void findLongestConsecutiveCapturesForPiece_fourLevels_inCircle_test() throws NoPieceFoundInRequestedTileException {
		testObj.createEmptyBoard();
		
		testObj.addBlackQueen(4);
		testObj.addWhitePawn(22);
		testObj.addWhitePawn(23);
		testObj.addWhiteQueen(32);
		testObj.addWhitePawn(33);
		
		Piece testPiece = testObj.findPieceByIndex(4);
		ArrayList<Move<Capture>> blackMoves = testObj.findLongestConsecutiveCaptures(testPiece);
		
		assertEquals(4, blackMoves.size());
		assertEquals(4, blackMoves.get(0).getNumberOfHops());
		assertEquals(4, blackMoves.get(3).getNumberOfHops());
	}
	
	@Test
	public void findLongestConsecutiveCapturesForPiece_ultimate_test() throws NoPieceFoundInRequestedTileException {
		testObj.createEmptyBoard();
		
		testObj.addWhiteQueen(50);
		testObj.addBlackPawn(8);
		testObj.addBlackPawn(9);
		testObj.addBlackPawn(10);
		testObj.addBlackPawn(11);
		testObj.addBlackPawn(19);
		testObj.addBlackPawn(20);
		testObj.addBlackPawn(28);
		testObj.addBlackPawn(30);
		testObj.addBlackPawn(41);

		Piece testPiece = testObj.findPieceByIndex(50);
		ArrayList<Move<Capture>> whiteMoves = testObj.findLongestConsecutiveCaptures(testPiece);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(7, whiteMoves.get(0).getNumberOfHops());
	}
	
	@Test
	public void findLongestConsecutiveCapturesForAllPieces_ultimate_test() {
		testObj.createEmptyBoard();
		
		testObj.addWhitePawn(46);
		testObj.addWhitePawn(49);
		testObj.addWhitePawn(43);
		testObj.addBlackPawn(44);
		testObj.addBlackPawn(41);
		testObj.addBlackPawn(38);
		testObj.addBlackPawn(31);
		testObj.addBlackPawn(34);
		testObj.addBlackPawn(28);
		testObj.addBlackPawn(24);
		testObj.addBlackPawn(18);
		testObj.addBlackPawn(19);
		
		ArrayList<Move<Capture>> whiteMoves = testObj.findCapturesForAllPieces(true);
		
		assertEquals(3, whiteMoves.size());
		assertEquals(3, whiteMoves.get(0).getNumberOfHops());
		
	}
	
	@Test
	public void findMovesForAllPieces_noCaptures_test() {
		testObj.createEmptyBoard();
		
		testObj.addWhitePawn(46);
		testObj.addWhitePawn(49);
		testObj.addWhitePawn(43);
		testObj.addWhiteQueen(35);
		testObj.addBlackPawn(21);
		testObj.addBlackPawn(16);
		testObj.addBlackPawn(22);
		testObj.addBlackPawn(19);

		ArrayList<Move<Hop>> whiteMoves = testObj.findMovesForAllPieces(true);
		
		assertEquals(8, whiteMoves.size());
		
	}
}
