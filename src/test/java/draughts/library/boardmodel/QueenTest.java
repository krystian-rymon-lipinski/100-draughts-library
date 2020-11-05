package draughts.library.boardmodel;

import static org.junit.Assert.assertEquals;
import static draughts.library.boardmodel.Piece.MoveDirection.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

@RunWith(MockitoJUnitRunner.class)
public class QueenTest extends PieceTest {
	
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void findMove_upLeft() {
		WhiteQueen piece = boardManager.addWhiteQueen(32);

		Move<Hop> upLeftMove1 = piece.findMove(UP_LEFT, 1, boardManager.getBoard());
		Move<Hop> upLeftMove2 = piece.findMove(UP_LEFT, 2, boardManager.getBoard());
		Move<Hop> upLeftMove3 = piece.findMove(UP_LEFT, 3, boardManager.getBoard());

		assertEquals(getTile(32), upLeftMove1.getMoveSource());
		assertEquals(getTile(27), upLeftMove1.getMoveDestination());
		assertEquals(piece, upLeftMove1.getMovingPiece());

		assertEquals(getTile(32), upLeftMove2.getMoveSource());
		assertEquals(getTile(21), upLeftMove2.getMoveDestination());
		assertEquals(piece, upLeftMove2.getMovingPiece());

		assertEquals(getTile(32), upLeftMove3.getMoveSource());
		assertEquals(getTile(16), upLeftMove3.getMoveDestination());
		assertEquals(piece, upLeftMove3.getMovingPiece());
	}

	@Test
	public void findMove_upRight() {
		BlackQueen piece = boardManager.addBlackQueen(18);

		Move<Hop> upRightMove1 = piece.findMove(UP_RIGHT, 1, boardManager.getBoard());
		Move<Hop> upRightMove2 = piece.findMove(UP_RIGHT, 2, boardManager.getBoard());
		Move<Hop> upRightMove3 = piece.findMove(UP_RIGHT, 3, boardManager.getBoard());

		assertEquals(getTile(18), upRightMove1.getMoveSource());
		assertEquals(getTile(13), upRightMove1.getMoveDestination());
		assertEquals(piece, upRightMove1.getMovingPiece());

		assertEquals(getTile(18), upRightMove2.getMoveSource());
		assertEquals(getTile(9), upRightMove2.getMoveDestination());
		assertEquals(piece, upRightMove2.getMovingPiece());

		assertEquals(getTile(18), upRightMove3.getMoveSource());
		assertEquals(getTile(4), upRightMove3.getMoveDestination());
		assertEquals(piece, upRightMove3.getMovingPiece());
	}

	@Test
	public void findMove_downLeft() {
		WhiteQueen piece = boardManager.addWhiteQueen(22);

		Move<Hop> downLeftMove1 = piece.findMove(DOWN_LEFT, 1, boardManager.getBoard());
		Move<Hop> downLeftMove2 = piece.findMove(DOWN_LEFT, 2, boardManager.getBoard());
		Move<Hop> downLeftMove3 = piece.findMove(DOWN_LEFT, 3, boardManager.getBoard());

		assertEquals(getTile(22), downLeftMove1.getMoveSource());
		assertEquals(getTile(27), downLeftMove1.getMoveDestination());
		assertEquals(piece, downLeftMove1.getMovingPiece());

		assertEquals(getTile(22), downLeftMove2.getMoveSource());
		assertEquals(getTile(31), downLeftMove2.getMoveDestination());
		assertEquals(piece, downLeftMove2.getMovingPiece());

		assertEquals(getTile(22), downLeftMove3.getMoveSource());
		assertEquals(getTile(36), downLeftMove3.getMoveDestination());
		assertEquals(piece, downLeftMove3.getMovingPiece());
	}

	@Test
	public void findMove_downRight() {
		BlackQueen piece = boardManager.addBlackQueen(29);

		Move<Hop> downRightMove1 = piece.findMove(DOWN_RIGHT, 1, boardManager.getBoard());
		Move<Hop> downRightMove2 = piece.findMove(DOWN_RIGHT, 2, boardManager.getBoard());
		Move<Hop> downRightMove3 = piece.findMove(DOWN_RIGHT, 3, boardManager.getBoard());

		assertEquals(getTile(29), downRightMove1.getMoveSource());
		assertEquals(getTile(34), downRightMove1.getMoveDestination());
		assertEquals(piece, downRightMove1.getMovingPiece());

		assertEquals(getTile(29), downRightMove2.getMoveSource());
		assertEquals(getTile(40), downRightMove2.getMoveDestination());
		assertEquals(piece, downRightMove2.getMovingPiece());

		assertEquals(getTile(29), downRightMove3.getMoveSource());
		assertEquals(getTile(45), downRightMove3.getMoveDestination());
		assertEquals(piece, downRightMove3.getMovingPiece());
	}
	
	@Test
	public void findAllMoves_upLeft() {
		WhiteQueen movingPiece = boardManager.addWhiteQueen(32);
		boardManager.addWhitePawn(16); //limit queen possibilities

		ArrayList<Move<Hop>> upLeftMoves = movingPiece.findMovesInDirection(UP_LEFT, boardManager.getBoard());
		
		assertEquals(2, upLeftMoves.size());
	}
	
	@Test
	public void findMoves_upRight() {
		BlackQueen movingPiece = boardManager.addBlackQueen(41);
		boardManager.addBlackPawn(19);

		ArrayList<Move<Hop>> upRightMoves = movingPiece.findMovesInDirection(UP_RIGHT, boardManager.getBoard());
		
		assertEquals(4, upRightMoves.size());
	}
	
	@Test
	public void findMoves_downLeft() {
		WhiteQueen movingPiece = boardManager.addWhiteQueen(25);
		boardManager.addWhiteQueen(43);

		ArrayList<Move<Hop>> downLeftMoves = movingPiece.findMovesInDirection(DOWN_LEFT, boardManager.getBoard());
		
		assertEquals(3, downLeftMoves.size());
	}
	
	@Test
	public void findMoves_downRight() {
		Piece movingPiece = boardManager.addWhiteQueen(6);
		boardManager.addBlackQueen(50);

		ArrayList<Move<Hop>> downRightMoves = movingPiece.findMovesInDirection(DOWN_RIGHT, boardManager.getBoard());
		
		assertEquals(7, downRightMoves.size());

	}
	
	@Test
	public void findMoves_inAllDirections() {
		WhiteQueen whiteQueen = boardManager.addWhiteQueen(28);
		boardManager.addWhitePawn(11);
		boardManager.addBlackPawn(5);
		boardManager.addBlackQueen(32);
		boardManager.addWhiteQueen(50);
		ArrayList<Move<Hop>> whiteMoves = whiteQueen.findAllMoves(boardManager.getBoard());
		
		assertEquals(9, whiteMoves.size());
		
		BlackQueen blackQueen = boardManager.addBlackQueen(30);
		boardManager.addWhitePawn(13);
		boardManager.addBlackPawn(25);
		boardManager.addBlackQueen(35);
		boardManager.addWhiteQueen(48);
		
		ArrayList<Move<Hop>> blackMoves = blackQueen.findAllMoves(boardManager.getBoard());
		
		assertEquals(5, blackMoves.size());
			
	}
	
	@Test
	public void findSingleCaptures_upLeft_test() {
		boardManager.addWhiteQueen(38);
		Piece takenPiece = boardManager.addBlackPawn(32);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(38);
		
		assertEquals(3, whiteMoves.size());
		for(Capture capture : whiteMoves) {
			assertEquals(38, capture.getSource().getIndex());
			assertEquals(takenPiece, capture.getTakenPiece());
		}
		assertEquals(27, whiteMoves.get(0).getDestination().getIndex());
		assertEquals(21, whiteMoves.get(1).getDestination().getIndex());
		assertEquals(16, whiteMoves.get(2).getDestination().getIndex());
				
		
		boardManager.addBlackQueen(18);
		Piece takenPiece2 = boardManager.addWhitePawn(12);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(18);
		
		assertEquals(2, blackMoves.size());
		for(Capture capture : blackMoves) {
			assertEquals(18, capture.getSource().getIndex());
			assertEquals(takenPiece2, capture.getTakenPiece());
		}
		assertEquals(7, blackMoves.get(0).getDestination().getIndex());
		assertEquals(1, blackMoves.get(1).getDestination().getIndex());
		
		
	}

	@Test
	public void findSingleCaptures_upRight_test() {
		boardManager.addWhiteQueen(38);
		Piece takenPiece = boardManager.addBlackPawn(33);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(38);
		
		assertEquals(4, whiteMoves.size());
		for(Capture capture : whiteMoves) {
			assertEquals(38, capture.getSource().getIndex());
			assertEquals(takenPiece, capture.getTakenPiece());
		}
		assertEquals(29, whiteMoves.get(0).getDestination().getIndex());
		assertEquals(24, whiteMoves.get(1).getDestination().getIndex());
		assertEquals(20, whiteMoves.get(2).getDestination().getIndex());
		assertEquals(15, whiteMoves.get(3).getDestination().getIndex());
				
		
		boardManager.addBlackQueen(18);
		Piece takenPiece2 = boardManager.addWhitePawn(13);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(18);
		
		assertEquals(2, blackMoves.size());
		for(Capture capture : blackMoves) {
			assertEquals(18, capture.getSource().getIndex());
			assertEquals(takenPiece2, capture.getTakenPiece());
		}	
		assertEquals(9, blackMoves.get(0).getDestination().getIndex());
		assertEquals(4, blackMoves.get(1).getDestination().getIndex());	
	}
	
	@Test
	public void findSingleCaptures_downLeftTake_test() {
		boardManager.addWhiteQueen(38);
		Piece takenPiece = boardManager.addBlackPawn(42);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(38);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(38, whiteMoves.get(0).getSource().getIndex());
		assertEquals(47, whiteMoves.get(0).getDestination().getIndex());
		assertEquals(takenPiece, whiteMoves.get(0).getTakenPiece());
				
		
		boardManager.addBlackQueen(18);
		Piece takenPiece2 = boardManager.addWhitePawn(22);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(18);
		
		assertEquals(3, blackMoves.size());
		for(Capture capture : blackMoves) {
			assertEquals(18, capture.getSource().getIndex());
			assertEquals(takenPiece2, capture.getTakenPiece());
		}
		assertEquals(27, blackMoves.get(0).getDestination().getIndex());
		assertEquals(31, blackMoves.get(1).getDestination().getIndex());
		assertEquals(36, blackMoves.get(2).getDestination().getIndex());
	}
	
	@Test
	public void findSingleCaptures_downRight_test() {
		boardManager.addWhiteQueen(38);
		Piece takenPiece = boardManager.addBlackPawn(43);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(38);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(38, whiteMoves.get(0).getSource().getIndex());
		assertEquals(49, whiteMoves.get(0).getDestination().getIndex());
		assertEquals(takenPiece, whiteMoves.get(0).getTakenPiece());
				
		
		boardManager.addBlackQueen(18);
		Piece takenPiece2 = boardManager.addWhitePawn(23);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(18);
		
		assertEquals(4, blackMoves.size());
		for(Capture capture : blackMoves) {
			assertEquals(18, capture.getSource().getIndex());
			assertEquals(takenPiece2, capture.getTakenPiece());
		}
		assertEquals(29, blackMoves.get(0).getDestination().getIndex());
		assertEquals(34, blackMoves.get(1).getDestination().getIndex());
		assertEquals(40, blackMoves.get(2).getDestination().getIndex());
		assertEquals(45, blackMoves.get(3).getDestination().getIndex());	
	}
	
	@Test
	public void findSingleCaptures_inAllDirections_test() {
		boardManager.addWhiteQueen(19);
		boardManager.addBlackPawn(5);
		boardManager.addBlackQueen(8);
		boardManager.addBlackQueen(24);
		boardManager.addBlackPawn(37);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(19);
		
		assertEquals(5, whiteMoves.size());
	
		boardManager.addBlackQueen(27);
		boardManager.addWhitePawn(18);
		boardManager.addWhitePawn(21);
		boardManager.addWhiteQueen(36);
		boardManager.addWhiteQueen(43);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(27);
		
		assertEquals(5, blackMoves.size());
	}

}
