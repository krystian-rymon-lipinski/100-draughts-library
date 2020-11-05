package draughts.library.boardmodel;

import static draughts.library.boardmodel.Piece.MoveDirection.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

@RunWith(MockitoJUnitRunner.class)
public class PawnTest extends PieceTest {
	
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void findMove_whitePawn_inDirection_upLeft() {
		WhitePawn whitePawn = boardManager.addWhitePawn(18);

		Move<Hop> upLeftMove = whitePawn.findMove(UP_LEFT, boardManager.getBoard());

		assertEquals(getTile(18), upLeftMove.getMoveSource());
		assertEquals(getTile(12), upLeftMove.getMoveDestination());
		assertEquals(whitePawn, upLeftMove.getMovingPiece());

		WhitePawn whitePawn2 = boardManager.addWhitePawn(26);

		Move<Hop> upLeftMove2 = whitePawn2.findMove(UP_LEFT, boardManager.getBoard());

		assertNull(upLeftMove2);
	}

	@Test
	public void findMove_whitePawn_inDirection_upRight() {
		WhitePawn whitePawn = boardManager.addWhitePawn(33);

		Move<Hop> upRightMove = whitePawn.findMove(UP_RIGHT, boardManager.getBoard());

		assertEquals(getTile(33), upRightMove.getMoveSource());
		assertEquals(getTile(29), upRightMove.getMoveDestination());
		assertEquals(whitePawn, upRightMove.getMovingPiece());

		WhitePawn whitePawn2 = boardManager.addWhitePawn(35);

		Move<Hop> upRightMove2 = whitePawn2.findMove(UP_RIGHT, boardManager.getBoard());

		assertNull(upRightMove2);
	}

	@Test
	public void findMove_whitePawn_wrongDirections() {
		WhitePawn whitePawn = boardManager.addWhitePawn(38);

		Move<Hop> downLeftMove = whitePawn.findMove(DOWN_LEFT, boardManager.getBoard());
		Move<Hop> downRightMove = whitePawn.findMove(DOWN_RIGHT, boardManager.getBoard());

		assertNull(downLeftMove);
		assertNull(downRightMove);
	}
	
	@Test
	public void findMoves_forWhitePawn_test() {
		Piece movingPiece = boardManager.addWhitePawn(33);
		ArrayList<Move<Hop>> moves = findMovesForPiece(33);
		
		assertEquals(2, moves.size());
		for (Move<Hop> move : moves) {
			assertEquals(33, move.getMoveSource().getIndex());
			assertEquals(movingPiece, move.getMovingPiece());
			assertEquals(1, move.getNumberOfHops());
		}
		assertTrue(moves.get(0).getMoveDestination().getIndex() == 28 || 
				   moves.get(0).getMoveDestination().getIndex() == 29);
		assertTrue(moves.get(1).getMoveDestination().getIndex() == 28 || 
				   moves.get(1).getMoveDestination().getIndex() == 29);
		
	}
	
	@Test
	public void findMoves_forBlackPawn_test() {
		Piece movingPiece = boardManager.addBlackPawn(18);
		ArrayList<Move<Hop>> moves = findMovesForPiece(18);
		
		assertEquals(2, moves.size());
		for (Move<Hop> move : moves) {
			assertEquals(18, move.getMoveSource().getIndex());
			assertEquals(movingPiece, move.getMovingPiece());
			assertEquals(1, move.getNumberOfHops());
		}
		assertTrue(moves.get(0).getHop(0).getDestination().getIndex() == 22 || 
				   moves.get(0).getHop(0).getDestination().getIndex() == 22);
		assertTrue(moves.get(1).getHop(0).getDestination().getIndex() == 23 || 
				   moves.get(1).getHop(0).getDestination().getIndex() == 23);
	}
	
	@Test
	public void findMoves_forMostLeftBlackPawn_test() {
		Piece movingPiece = boardManager.addBlackPawn(16);
		ArrayList<Move<Hop>> moves = findMovesForPiece(16);
		
		assertEquals(1, moves.size());
		assertEquals(16, moves.get(0).getMoveSource().getIndex());
		assertEquals(21, moves.get(0).getMoveDestination().getIndex());
		assertEquals(movingPiece, moves.get(0).getMovingPiece());
		assertEquals(1, moves.get(0).getNumberOfHops());
	}
	
	@Test
	public void findMoves_forMostRightWhitePawn_test() {
		Piece movingPiece = boardManager.addWhitePawn(35);
		ArrayList<Move<Hop>> moves = findMovesForPiece(35);
		
		assertEquals(1, moves.size());
		assertEquals(35, moves.get(0).getMoveSource().getIndex());
		assertEquals(30, moves.get(0).getMoveDestination().getIndex());
		assertEquals(movingPiece, moves.get(0).getMovingPiece());
		assertEquals(1, moves.get(0).getNumberOfHops());
	}
	
	@Test
	public void findSingleCapture_upLeft_test() {
		boardManager.addWhitePawn(22);
		Piece takenPiece = boardManager.addBlackPawn(17);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(22);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(22, whiteMoves.get(0).getSource().getIndex());
		assertEquals(11, whiteMoves.get(0).getDestination().getIndex());
		assertEquals(takenPiece, whiteMoves.get(0).getTakenPiece());
		
		boardManager.addBlackPawn(39);
		Piece takenPiece2 = boardManager.addWhitePawn(33);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(39);
		
		assertEquals(1, blackMoves.size());
		assertEquals(39, blackMoves.get(0).getSource().getIndex());
		assertEquals(28, blackMoves.get(0).getDestination().getIndex());
		assertEquals(takenPiece2, blackMoves.get(0).getTakenPiece());
	}
	
	@Test
	public void findSingleCapture_upRight_test() {
		boardManager.addWhitePawn(22);
		Piece takenPiece = boardManager.addBlackPawn(18);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(22);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(22, whiteMoves.get(0).getSource().getIndex());
		assertEquals(13, whiteMoves.get(0).getDestination().getIndex());
		assertEquals(takenPiece, whiteMoves.get(0).getTakenPiece());
		
		boardManager.addBlackPawn(39);
		Piece takenPiece2 = boardManager.addWhitePawn(34);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(39);
		
		assertEquals(1, blackMoves.size());
		assertEquals(39, blackMoves.get(0).getSource().getIndex());
		assertEquals(30, blackMoves.get(0).getDestination().getIndex());
		assertEquals(takenPiece2, blackMoves.get(0).getTakenPiece());
	}
	
	@Test
	public void findSingleCapture_downLeft_test() {
		boardManager.addWhitePawn(22);
		Piece takenPiece = boardManager.addBlackPawn(27);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(22);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(22, whiteMoves.get(0).getSource().getIndex());
		assertEquals(31, whiteMoves.get(0).getDestination().getIndex());
		assertEquals(takenPiece, whiteMoves.get(0).getTakenPiece());
		
		boardManager.addBlackPawn(39);
		Piece takenPiece2 = boardManager.addWhitePawn(43);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(39);
		
		assertEquals(1, blackMoves.size());
		assertEquals(39, blackMoves.get(0).getSource().getIndex());
		assertEquals(48, blackMoves.get(0).getDestination().getIndex());
		assertEquals(takenPiece2, blackMoves.get(0).getTakenPiece());
	}
	
	@Test
	public void findSingleCapture_downRight_test() {
		boardManager.addWhitePawn(22);
		Piece takenPiece = boardManager.addBlackPawn(28);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(22);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(22, whiteMoves.get(0).getSource().getIndex());
		assertEquals(33, whiteMoves.get(0).getDestination().getIndex());
		assertEquals(takenPiece, whiteMoves.get(0).getTakenPiece());
		
		boardManager.addBlackPawn(39);
		Piece takenPiece2 = boardManager.addWhitePawn(44);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(39);
		
		assertEquals(1, blackMoves.size());
		assertEquals(39, blackMoves.get(0).getSource().getIndex());
		assertEquals(50, blackMoves.get(0).getDestination().getIndex());
		assertEquals(takenPiece2, blackMoves.get(0).getTakenPiece());
	}
	
	
	@Test
	public void findSingleCaptures_inAllDirections_test() {
		boardManager.addWhitePawn(14);
		boardManager.addBlackPawn(9);
		boardManager.addBlackQueen(10);
		boardManager.addBlackQueen(19);
		boardManager.addBlackPawn(20);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(14);
		
		assertEquals(4, whiteMoves.size());
	
		boardManager.addBlackPawn(17);
		boardManager.addWhitePawn(11);
		boardManager.addWhitePawn(12);
		boardManager.addWhiteQueen(21);
		boardManager.addWhiteQueen(22);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(17);
		
		assertEquals(4, blackMoves.size());
	}	

}
