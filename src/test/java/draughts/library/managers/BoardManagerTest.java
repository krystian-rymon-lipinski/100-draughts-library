package draughts.library.managers;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.boardmodel.BlackPawn;
import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.boardmodel.WhitePawn;
import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BoardManagerTest extends BaseTest{
	
	BoardManager testObj;
	
	@Before
	public void setUp() {
		boardManager = new BoardManager();
		testObj = boardManager;
	}

	public void makeHop(int source, int destination) {
		Piece movedPiece = getPiece(source);
		Tile dst = getTile(destination);
		
		testObj.makeHop(movedPiece, dst);
	}
	
	public void makeCapture(int source, int destination, int taken) {
		Piece movedPiece = getPiece(source);
		Tile dst = getTile(destination);
		Piece takenPiece = getPiece(taken);
		
		testObj.makeCapture(movedPiece, dst, takenPiece);
	}
	
	@Test
	public void createEmptyBoard() {
		testObj.createEmptyBoard();
		
		assertEquals(Tile.State.EMPTY, getTile(1).getState());
		assertEquals(Tile.State.EMPTY, getTile(12).getState());
		assertEquals(Tile.State.EMPTY, getTile(21).getState());
		assertEquals(Tile.State.EMPTY, getTile(15).getState());
		assertEquals(Tile.State.EMPTY, getTile(32).getState());
		assertEquals(Tile.State.EMPTY, getTile(50).getState());
	}

	@Test
	public void prepareTiles_forStartingPosition() {
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
	public void preparePieces_forStartingPosition() {
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
	public void addWhitePieces() {
		testObj.createEmptyBoard();
		
		testObj.addWhitePawn(12);
		testObj.addWhiteQueen(41);
		
		assertEquals(2, testObj.getWhitePieces().size());
		assertEquals(getTile(12), testObj.getWhitePieces().get(0).getPosition());
		assertEquals(getTile(41), testObj.getWhitePieces().get(1).getPosition());
	}
	
	@Test
	public void addBlackPieces() {
		testObj.createEmptyBoard();
		
		testObj.addBlackPawn(1);
		testObj.addBlackQueen(50);
		
		assertEquals(2, testObj.getBlackPieces().size());
		assertEquals(getTile(1), testObj.getBlackPieces().get(0).getPosition());
		assertEquals(getTile(50), testObj.getBlackPieces().get(1).getPosition());
	}
	
	@Test
	public void removeWhitePieces() {
		testObj.createEmptyBoard();
		
		Piece piece1 = testObj.addWhitePawn(30);
		Piece piece2 = testObj.addWhiteQueen(45);
		Piece piece3 = testObj.addWhiteQueen(50);
	
		assertTrue(testObj.getIsWhiteQueenOnBoard());
		
		testObj.removeWhitePiece(piece1);
		assertEquals(2, testObj.getWhitePieces().size());
		assertTrue(testObj.getIsWhiteQueenOnBoard());
		assertEquals(Tile.State.EMPTY, getTile(30).getState());
		
		testObj.removeWhitePiece(piece2);
		assertEquals(1, testObj.getWhitePieces().size());
		assertTrue(testObj.getIsWhiteQueenOnBoard());
		assertEquals(Tile.State.EMPTY, getTile(45).getState());
		
		testObj.removeWhitePiece(piece3);
		assertEquals(0, testObj.getWhitePieces().size());
		assertFalse(testObj.getIsWhiteQueenOnBoard());
		assertEquals(Tile.State.EMPTY, getTile(50).getState());
	}
	
	@Test
	public void removeBlackPieces() {
		testObj.createEmptyBoard();
		
		Piece piece1 = testObj.addBlackPawn(13);
		Piece piece2 = testObj.addBlackQueen(24);
		Piece piece3 = testObj.addBlackQueen(43);
	
		assertTrue(testObj.getIsBlackQueenOnBoard());
		
		testObj.removeBlackPiece(piece1);
		assertEquals(2, testObj.getBlackPieces().size());
		assertTrue(testObj.getIsBlackQueenOnBoard());
		assertEquals(Tile.State.EMPTY, getTile(13).getState());
		
		testObj.removeBlackPiece(piece2);
		assertEquals(1, testObj.getBlackPieces().size());
		assertTrue(testObj.getIsBlackQueenOnBoard());
		assertEquals(Tile.State.EMPTY, getTile(24).getState());
		
		testObj.removeBlackPiece(piece3);
		assertEquals(0, testObj.getBlackPieces().size());
		assertFalse(testObj.getIsBlackQueenOnBoard());
		assertEquals(Tile.State.EMPTY, getTile(43).getState());
	}
	
	@Test
	public void findTileByIndex() {
		testObj.createStartingPosition();

		assertEquals(Tile.class, getTile(1).getClass());
		
		assertEquals(Tile.State.BLACK_PAWN, getTile(1).getState());
		assertEquals(Tile.State.BLACK_PAWN, getTile(20).getState());
		assertEquals(Tile.State.EMPTY, getTile(21).getState());
		assertEquals(Tile.State.EMPTY, getTile(30).getState());
		assertEquals(Tile.State.WHITE_PAWN, getTile(31).getState());
		assertEquals(Tile.State.WHITE_PAWN, getTile(50).getState());
	}
	
	@Test 
	public void findPieceByIndex() {
		testObj.createStartingPosition();

		assertNotNull(getPiece(2));
		
		assertEquals(1, getPiece(1).getPosition().getIndex());
		assertEquals(20, getPiece(20).getPosition().getIndex());
		assertEquals(31, getPiece(31).getPosition().getIndex());
		assertEquals(50, getPiece(50).getPosition().getIndex());
	}

	@Test
	public void findPieceByIndex_NoPieceFound() {
		testObj.createStartingPosition();
		
		Piece piece = getPiece(23);
		assertNull(piece);
	}
	
	@Test
	public void makeHop() {
		testObj.createEmptyBoard();
		testObj.addWhitePawn(31);
		testObj.addBlackPawn(20);

		makeHop(31, 27);
		
		assertEquals(getTile(27), testObj.getWhitePieces().get(0).getPosition());
		assertEquals(Tile.State.EMPTY, getTile(31).getState());
		
		makeHop(20, 25);
		
		assertEquals(getTile(25), testObj.getBlackPieces().get(0).getPosition());
		assertEquals(Tile.State.EMPTY, getTile(20).getState());
		
	}
	
	@Test
	public void makeCapture() {
		testObj.createEmptyBoard();
		testObj.addWhitePawn(27);
		testObj.addBlackPawn(22);
		testObj.addWhitePawn(39);
		testObj.addBlackPawn(34);
		
		makeCapture(27, 18, 22);
		assertEquals(1, testObj.getBlackPieces().size());
		assertEquals(Tile.State.EMPTY, getTile(22).getState());
		
		makeCapture(34, 43, 39);
		assertEquals(1, testObj.getWhitePieces().size());
		assertEquals(Tile.State.EMPTY, getTile(39).getState());	
	}

	@Test
	public void restoreCapturedPiece() {
		testObj.createEmptyBoard();
		Piece movingPiece = testObj.addWhitePawn(20);
		Piece takenPiece = testObj.addBlackPawn(14);

		Capture capture = new Capture(getTile(20), getTile(9), takenPiece);

		testObj.makeCapture(movingPiece, getTile(9), takenPiece);

		assertEquals(0, testObj.getBlackPieces().size());

		testObj.restoreCapturedPiece(movingPiece, capture);

		assertEquals(1, testObj.getBlackPieces().size());
		assertEquals(takenPiece, testObj.getBlackPieces().get(0));
		assertEquals(getTile(14), testObj.getBlackPieces().get(0).getPosition());
	}
	
	@Test
	public void makeWholeMove_capture() {
		testObj.createEmptyBoard();
		testObj.addWhitePawn(33);
		testObj.addBlackPawn(28);
		testObj.addBlackPawn(18);
		testObj.addBlackPawn(8);

		Move<Capture> move = generateMoveWithCaptures(33, new ArrayList<>(Arrays.asList(22, 13, 2)),
				new ArrayList<>(Arrays.asList(28, 18, 8)));
		testObj.makeWholeMove(move);
		
		assertEquals(0, testObj.getBlackPieces().size());
		assertEquals(getTile(2), testObj.getWhitePieces().get(0).getPosition());
		assertEquals(Tile.State.EMPTY, getTile(33).getState());
		assertEquals(Tile.State.EMPTY, getTile(28).getState());
		assertEquals(Tile.State.EMPTY, getTile(18).getState());
		assertEquals(Tile.State.EMPTY, getTile(8).getState());
	}

	@Test
	public void reverseWholeMove_capture() {
		testObj.createEmptyBoard();
		testObj.addBlackPawn(5);
		Piece takenPiece1 = testObj.addWhiteQueen(10);
		Piece takenPiece2 = testObj.addWhitePawn(19);
		Piece takenPiece3 = testObj.addWhitePawn(28);
		Piece takenPiece4 = testObj.addWhiteQueen(38);

		Move<Capture> move = generateMoveWithCaptures(5, new ArrayList<>(Arrays.asList(14, 23, 32, 43)),
				new ArrayList<>(Arrays.asList(10, 19, 28, 38)));
		
		testObj.makeWholeMove(move);
		assertEquals(0, testObj.getWhitePieces().size());

		testObj.reverseWholeMove(move);
		
		assertEquals(4, testObj.getWhitePieces().size());
		assertEquals(getTile(5), testObj.getBlackPieces().get(0).getPosition());
		assertEquals(Tile.State.EMPTY, getTile(43).getState());
		assertEquals(takenPiece4, testObj.getWhitePieces().get(0));
		assertEquals(Tile.State.WHITE_QUEEN, move.getHop(3).getTakenPiece().getPosition().getState());
		assertEquals(takenPiece3, testObj.getWhitePieces().get(1));
		assertEquals(Tile.State.WHITE_PAWN, move.getHop(2).getTakenPiece().getPosition().getState());
		assertEquals(takenPiece2, testObj.getWhitePieces().get(2));
		assertEquals(Tile.State.WHITE_PAWN, move.getHop(1).getTakenPiece().getPosition().getState());
		assertEquals(takenPiece1, testObj.getWhitePieces().get(3));
		assertEquals(Tile.State.WHITE_QUEEN, move.getHop(0).getTakenPiece().getPosition().getState());
	}

	@Test
	public void reverseWholeMove_demoteQueen() {
		testObj.createEmptyBoard();
		testObj.addWhitePawn(6);
	}

	@Test
	public void promotePawn() {
		testObj.createEmptyBoard();
		Piece whitePawn = testObj.addWhitePawn(5);
		Piece blackPawn = testObj.addBlackPawn(50);

		Piece whiteQueen = testObj.promotePawn(whitePawn);

		assertEquals(Tile.State.WHITE_QUEEN, getTile(5).getState());
		assertEquals(1, testObj.getWhitePieces().size());
		assertTrue(testObj.getWhitePieces().get(0).isQueen());
		assertTrue(testObj.getIsWhiteQueenOnBoard());
		assertNotEquals(whitePawn, whiteQueen);

		Piece blackQueen = testObj.promotePawn(blackPawn);

		assertEquals(Tile.State.BLACK_QUEEN, getTile(50).getState());
		assertEquals(1, testObj.getWhitePieces().size());
		assertTrue(testObj.getBlackPieces().get(0).isQueen());
		assertTrue(testObj.getIsBlackQueenOnBoard());
		assertNotEquals(blackPawn, blackQueen);
	}

	@Test
	public void demoteQueen() {
		testObj.createEmptyBoard();
		Piece whiteQueen = testObj.addWhiteQueen(3);
		Piece blackQueen = testObj.addBlackQueen(46);
		testObj.setIsWhiteQueenOnBoard(true);
		testObj.setIsBlackQueenOnBoard(true);

		Piece whitePawn = testObj.demoteQueen(whiteQueen);

		assertEquals(Tile.State.WHITE_PAWN, getTile(3).getState());
		assertEquals(1, testObj.getWhitePieces().size());
		assertFalse(testObj.getWhitePieces().get(0).isQueen());
		assertFalse(testObj.getIsWhiteQueenOnBoard());
		assertNotEquals(whiteQueen, whitePawn);

		Piece blackPawn = testObj.demoteQueen(blackQueen);

		assertEquals(Tile.State.BLACK_PAWN, getTile(46).getState());
		assertEquals(1, testObj.getWhitePieces().size());
		assertFalse(testObj.getBlackPieces().get(0).isQueen());
		assertFalse(testObj.getIsBlackQueenOnBoard());
		assertNotEquals(blackQueen, blackPawn);
	}
	
	@Test
	public void findLongestConsecutiveCapturesForPiece_twoLevels() {
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
	public void findLongestConsecutiveCapturesForPiece_twoLevels_inTwoDirections() {
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
	public void findLongestConsecutiveCapturesForPiece_twoLevels_withSameRoot() {
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
	public void findLongestConsecutiveCapturesForPiece_threeLevels() {
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
	public void findLongestConsecutiveCapturesForPiece_threeLevels_withMultipleBranches() {
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
	public void findLongestConsecutiveCapturesForPiece_fourLevels_inCircle() {
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
	public void findLongestConsecutiveCapturesForPiece_ultimateTest() {
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
	public void findLongestConsecutiveCapturesForAllPieces_ultimateTest() {
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
	public void findMovesForAllPieces_noCaptures() {
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
	public void findCapturesForAllPieces() {
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
	public void findMovesForAllPieces_forStartingPosition() {
		testObj.createStartingPosition();
		
		ArrayList<Move<Hop>> whiteMoves = testObj.findMovesForAllPieces(true);
		
		assertEquals(9, whiteMoves.size());
		for (Move<Hop> move : whiteMoves) {
			assertEquals(1, move.getNumberOfHops());
		}
	}
	
}
