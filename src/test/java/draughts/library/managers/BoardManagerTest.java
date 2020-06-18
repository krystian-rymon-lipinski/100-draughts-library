package draughts.library.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import draughts.library.managers.BoardManager;
import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

@RunWith(MockitoJUnitRunner.class)
public class BoardManagerTest {
	
	BoardManager testObj;
	
	@Before
	public void setUp() {
		testObj = new BoardManager();
	}
	
	public void makeHop(int source, int destination) {
		try {
			Piece movedPiece = testObj.findPieceByIndex(source);
			Tile dst = testObj.findTileByIndex(destination);
			
			testObj.makeHop(movedPiece, dst);
		} catch(NoPieceFoundInRequestedTileException e) {}
	}
	
	public void makeCapture(int source, int destination, int taken) {
		try {
			Piece movedPiece = testObj.findPieceByIndex(source);
			Tile dst = testObj.findTileByIndex(destination);
			Piece takenPiece = testObj.findPieceByIndex(taken);
			
			testObj.makeCapture(movedPiece, dst, takenPiece);
		} catch(NoPieceFoundInRequestedTileException e) {}
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
	} 
	
	@Test
	public void addWhitePieces_test() {
		testObj.createEmptyBoard();
		
		testObj.addWhitePawn(12);
		testObj.addWhiteQueen(41);
		
		assertEquals(2, testObj.getWhitePieces().size());
		assertEquals(Tile.State.WHITE_PAWN, testObj.findTileByIndex(12).getState());
		assertEquals(Tile.State.WHITE_QUEEN, testObj.findTileByIndex(41).getState());
		assertEquals(12, testObj.getWhitePieces().get(0).getPosition().getIndex());
		assertEquals(41, testObj.getWhitePieces().get(1).getPosition().getIndex());
	}
	
	@Test
	public void addBlackPieces_test() {
		testObj.createEmptyBoard();
		
		testObj.addBlackPawn(1);
		testObj.addBlackQueen(50);
		
		assertEquals(2, testObj.getBlackPieces().size());
		assertEquals(Tile.State.BLACK_PAWN, testObj.findTileByIndex(1).getState());
		assertEquals(Tile.State.BLACK_QUEEN, testObj.findTileByIndex(50).getState());
		assertEquals(1, testObj.getBlackPieces().get(0).getPosition().getIndex());
		assertEquals(50, testObj.getBlackPieces().get(1).getPosition().getIndex());
	}
	
	@Test
	public void removeWhitePieces_test() throws Exception {
		testObj.createEmptyBoard();
		
		testObj.addWhitePawn(30);
		testObj.addWhiteQueen(45);
		testObj.addWhiteQueen(50);
		
		Piece piece1 = testObj.findPieceByIndex(30);
		Piece piece2 = testObj.findPieceByIndex(45);
		Piece piece3 = testObj.findPieceByIndex(50);
		assertTrue(testObj.getIsWhiteQueenOnBoard());
		
		testObj.removeWhitePiece(piece1);
		assertEquals(2, testObj.getWhitePieces().size());
		
		testObj.removeWhitePiece(piece2);
		assertEquals(1, testObj.getWhitePieces().size());
		assertTrue(testObj.getIsWhiteQueenOnBoard());
		
		testObj.removeWhitePiece(piece3);
		assertEquals(0, testObj.getWhitePieces().size());
		assertFalse(testObj.getIsWhiteQueenOnBoard());
	}
	
	@Test
	public void removeBlackPieces_Test() throws Exception {
		testObj.createEmptyBoard();
		
		testObj.addBlackPawn(13);
		testObj.addBlackQueen(24);
		testObj.addBlackQueen(43);
		
		Piece piece1 = testObj.findPieceByIndex(13);
		Piece piece2 = testObj.findPieceByIndex(24);
		Piece piece3 = testObj.findPieceByIndex(43);
		assertTrue(testObj.getIsBlackQueenOnBoard());
		
		testObj.removeBlackPiece(piece1);
		assertEquals(2, testObj.getBlackPieces().size());
		
		testObj.removeBlackPiece(piece2);
		assertEquals(1, testObj.getBlackPieces().size());
		assertTrue(testObj.getIsBlackQueenOnBoard());
		
		testObj.removeBlackPiece(piece3);
		assertEquals(0, testObj.getBlackPieces().size());
		assertFalse(testObj.getIsBlackQueenOnBoard());
	}
	
	@Test
	public void prepareTiles_forStartingPosition_test() {
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
	public void preparePieces_forStartingPosition_test() {
		testObj.createStartingPosition();
		
		assertEquals(20, testObj.getWhitePieces().size());
		assertEquals(20, testObj.getBlackPieces().size());
		
		WhitePawn whitePiece1 = (WhitePawn) testObj.getWhitePieces().get(0);
		WhitePawn whitePiece2 = (WhitePawn) testObj.getWhitePieces().get(8);
		WhitePawn whitePiece3 = (WhitePawn) testObj.getWhitePieces().get(19);
		BlackPawn blackPiece1 = (BlackPawn) testObj.getBlackPieces().get(0);
		BlackPawn blackPiece2 = (BlackPawn) testObj.getBlackPieces().get(13);
		BlackPawn blackPiece3 = (BlackPawn) testObj.getBlackPieces().get(19);
		
		assertEquals(31, whitePiece1.getPosition().getIndex());
		assertEquals(39, whitePiece2.getPosition().getIndex());
		assertEquals(50, whitePiece3.getPosition().getIndex());
		assertEquals(1, blackPiece1.getPosition().getIndex());
		assertEquals(14, blackPiece2.getPosition().getIndex());
		assertEquals(20, blackPiece3.getPosition().getIndex());
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
		
		assertEquals(1, testObj.findPieceByIndex(1).getPosition().getIndex());
		assertEquals(20, testObj.findPieceByIndex(20).getPosition().getIndex());
		assertEquals(31, testObj.findPieceByIndex(31).getPosition().getIndex());
		assertEquals(50, testObj.findPieceByIndex(50).getPosition().getIndex());
	}
	
	@Test(expected = NoPieceFoundInRequestedTileException.class)
	public void findPieceByIndex_NoPieceFound_test() throws NoPieceFoundInRequestedTileException {
		testObj.createStartingPosition();
		
		testObj.findPieceByIndex(23);
	}
	
	@Test
	public void makeHop_test() {
		testObj.createEmptyBoard();
		testObj.addWhitePawn(31);
		testObj.addBlackPawn(20);

		makeHop(31, 27);
		
		assertEquals(27, testObj.getWhitePieces().get(0).getPosition().getIndex());
		assertEquals(Tile.State.WHITE_PAWN, testObj.findTileByIndex(27).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(31).getState());
		
		makeHop(20, 25);
		
		assertEquals(25, testObj.getBlackPieces().get(0).getPosition().getIndex());
		assertEquals(Tile.State.BLACK_PAWN, testObj.findTileByIndex(25).getState());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(20).getState());
		
	}
	
	@Test
	public void makeCapture_test() {
		testObj.createEmptyBoard();
		testObj.addWhitePawn(27);
		testObj.addBlackPawn(22);
		testObj.addWhitePawn(39);
		testObj.addBlackPawn(34);
		
		makeCapture(27, 18, 22);
		assertEquals(1, testObj.getBlackPieces().size());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(22).getState());
		
		makeCapture(34, 43, 39);
		assertEquals(1, testObj.getWhitePieces().size());
		assertEquals(Tile.State.EMPTY, testObj.findTileByIndex(39).getState());	
	}
	
	@Test
	public void findLongestConsecutiveCapturesForPiece_twoLevels_test() {
		testObj.createEmptyBoard();
		
		Piece movingPiece = testObj.addWhitePawn(24);
		Piece takenPiece1 = testObj.addBlackPawn(19);
		Piece takenPiece2 = testObj.addBlackPawn(18);
		
		ArrayList<Move<Capture>> whiteMoves = testObj.findLongestConsecutiveCaptures(movingPiece);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(2, whiteMoves.get(0).getNumberOfHops());
		assertEquals(24, whiteMoves.get(0).getHop(0).getSource().getIndex());
		assertEquals(13, whiteMoves.get(0).getHop(1).getSource().getIndex());
		assertEquals(13, whiteMoves.get(0).getHop(0).getDestination().getIndex());
		assertEquals(22, whiteMoves.get(0).getHop(1).getDestination().getIndex());
		assertEquals(takenPiece1, whiteMoves.get(0).getHop(0).getTakenPiece());
		assertEquals(takenPiece2, whiteMoves.get(0).getHop(1).getTakenPiece());
		assertEquals(movingPiece, whiteMoves.get(0).getMovingPiece());
		assertEquals(24, whiteMoves.get(0).getMovingPiece().getPosition().getIndex());
		
	}
	
	@Test
	public void findLongestConsecutiveCapturesForPiece_twoLevels_inTwoDirections_test() {
		testObj.createEmptyBoard();
		
		Piece movingPiece = testObj.addWhitePawn(28);
		testObj.addBlackPawn(22);
		testObj.addBlackPawn(11);
		testObj.addBlackPawn(33);
		testObj.addBlackPawn(44);
		
		ArrayList<Move<Capture>> whiteMoves = testObj.findLongestConsecutiveCaptures(movingPiece);
		
		assertEquals(2, whiteMoves.size());
		assertEquals(2, whiteMoves.get(0).getNumberOfHops());
		assertEquals(2, whiteMoves.get(1).getNumberOfHops());

	}

	@Test
	public void findLongestConsecutiveCapturesForPiece_twoLevels_withSameRoot_test() {
		testObj.createEmptyBoard();
		
		Piece movingPiece = testObj.addWhiteQueen(50);
		testObj.addBlackPawn(39);
		testObj.addBlackQueen(14);
		testObj.addBlackPawn(32);
		
		ArrayList<Move<Capture>> whiteMoves = testObj.findLongestConsecutiveCaptures(movingPiece);
		
		assertEquals(5, whiteMoves.size());
		for (Move<Capture> move : whiteMoves) {
			assertEquals(2, move.getNumberOfHops());
		}
	}
	
	@Test
	public void findLongestConsecutiveCapturesForPiece_threeLevels_test() {
		testObj.createEmptyBoard();
		
		Piece movingPiece = testObj.addWhiteQueen(48);
		testObj.addBlackPawn(37);
		testObj.addBlackQueen(22);
		testObj.addBlackPawn(7);
		
		ArrayList<Move<Capture>> whiteMoves = testObj.findLongestConsecutiveCaptures(movingPiece);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(3, whiteMoves.get(0).getNumberOfHops());
	}
	
	@Test
	public void findLongestConsecutiveCapturesForPiece_threeLevels_withMultipleBranches_test() {
		testObj.createEmptyBoard();
		
		Piece movingPiece = testObj.addBlackQueen(3);
		testObj.addWhitePawn(11);
		testObj.addWhitePawn(12);
		testObj.addWhiteQueen(19);
		testObj.addWhitePawn(22);
		testObj.addWhitePawn(33);
		testObj.addWhiteQueen(41);
		
		ArrayList<Move<Capture>> blackMoves = testObj.findLongestConsecutiveCaptures(movingPiece);
		
		assertEquals(7, blackMoves.size());
		for (Move<Capture> move : blackMoves) {
			assertEquals(3, move.getNumberOfHops());
		}
	}
	
	@Test
	public void findLongestConsecutiveCapturesForPiece_fourLevels_inCircle_test() {
		testObj.createEmptyBoard();
		
		Piece movingPiece = testObj.addBlackQueen(4);
		testObj.addWhitePawn(22);
		testObj.addWhitePawn(23);
		testObj.addWhiteQueen(32);
		testObj.addWhitePawn(33);
		
		ArrayList<Move<Capture>> blackMoves = testObj.findLongestConsecutiveCaptures(movingPiece);
		
		assertEquals(4, blackMoves.size());
		for (Move<Capture> move : blackMoves) {
			assertEquals(4, move.getNumberOfHops());
		}
	}
	
	@Test
	public void findLongestConsecutiveCapturesForPiece_ultimate_test() {
		testObj.createEmptyBoard();
		
		Piece movingPiece = testObj.addWhiteQueen(50);
		testObj.addBlackPawn(8);
		testObj.addBlackPawn(9);
		testObj.addBlackPawn(10);
		testObj.addBlackPawn(11);
		testObj.addBlackPawn(19);
		testObj.addBlackPawn(20);
		testObj.addBlackPawn(28);
		testObj.addBlackPawn(30);
		testObj.addBlackPawn(41);

		ArrayList<Move<Capture>> whiteMoves = testObj.findLongestConsecutiveCaptures(movingPiece);
		
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
		for (Move<Capture> move : whiteMoves) {
			assertEquals(3, move.getNumberOfHops());
		}		
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
		for (Move<Hop> move : whiteMoves) {
			assertEquals(1, move.getNumberOfHops());
		}
	}
	
	@Test
	public void findCapturesForAllPieces_test() {
		testObj.createEmptyBoard();
		
		testObj.addWhitePawn(46);
		testObj.addWhitePawn(49);
		testObj.addWhitePawn(43);
		testObj.addWhiteQueen(35);
		testObj.addBlackPawn(13);
		testObj.addBlackPawn(16);
		testObj.addBlackPawn(22);
		testObj.addBlackPawn(26);
		
		ArrayList<Move<Capture>> whiteMoves = testObj.findCapturesForAllPieces(true);
		
		assertEquals(2, whiteMoves.size());	
	}
	
	@Test
	public void findMovesForAllPieces_forStartingPosition_test() {
		testObj.createStartingPosition();
		
		ArrayList<Move<Hop>> whiteMoves = testObj.findMovesForAllPieces(true);
		
		assertEquals(9, whiteMoves.size());
		for (Move<Hop> move : whiteMoves) {
			assertEquals(1, move.getNumberOfHops());
		}
	}
	
	@Test
	public void promotePawn_test() {
		testObj.createEmptyBoard();
		
		testObj.addWhitePawn(10);
		testObj.addBlackPawn(45);
		
		makeHop(10, 5);
		testObj.promotePawn(testObj.getWhitePieces().get(0));
		
		assertEquals(Tile.State.WHITE_QUEEN, testObj.findTileByIndex(5).getState());
		assertEquals(1, testObj.getWhitePieces().size());
		assertTrue(testObj.getWhitePieces().get(0).isQueen());
		assertTrue(testObj.getIsWhiteQueenOnBoard());
		
		makeHop(45, 50);
		testObj.promotePawn(testObj.getBlackPieces().get(0));
		
		assertEquals(Tile.State.BLACK_QUEEN, testObj.findTileByIndex(50).getState());
		assertEquals(1, testObj.getWhitePieces().size());
		assertTrue(testObj.getBlackPieces().get(0).isQueen());
		assertTrue(testObj.getIsBlackQueenOnBoard());
	}
	
}
