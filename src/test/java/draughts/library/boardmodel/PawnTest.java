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
	public void findMove_blackPawn_inDirection_downLeft() {
		BlackPawn blackPawn = boardManager.addBlackPawn(19);

		Move<Hop> downLeftMove = blackPawn.findMove(DOWN_LEFT, boardManager.getBoard());

		assertEquals(getTile(19), downLeftMove.getMoveSource());
		assertEquals(getTile(23), downLeftMove.getMoveDestination());
		assertEquals(blackPawn, downLeftMove.getMovingPiece());

		BlackPawn blackPawn2 = boardManager.addBlackPawn(36);

		Move<Hop> downLeftMove2 = blackPawn2.findMove(DOWN_LEFT, boardManager.getBoard());

		assertNull(downLeftMove2);
	}

	@Test
	public void findMove_blackPawn_inDirection_downRight() {
		BlackPawn blackPawn = boardManager.addBlackPawn(12);

		Move<Hop> downLeftMove = blackPawn.findMove(DOWN_RIGHT, boardManager.getBoard());

		assertEquals(getTile(12), downLeftMove.getMoveSource());
		assertEquals(getTile(18), downLeftMove.getMoveDestination());
		assertEquals(blackPawn, downLeftMove.getMovingPiece());

		BlackPawn blackPawn2 = boardManager.addBlackPawn(45);

		Move<Hop> downLeftMove2 = blackPawn2.findMove(DOWN_RIGHT, boardManager.getBoard());

		assertNull(downLeftMove2);
	}

	@Test
	public void findMove_blackPawn_wrongDirections() {
		BlackPawn blackPawn = boardManager.addBlackPawn(12);

		Move<Hop> upLeftMove = blackPawn.findMove(UP_LEFT, boardManager.getBoard());
		Move<Hop> upRightMove = blackPawn.findMove(UP_RIGHT, boardManager.getBoard());

		assertNull(upLeftMove);
		assertNull(upRightMove);
	}
	
	@Test
	public void findAllMoves_whitePawn() {
		Piece piece = boardManager.addWhitePawn(33);

		ArrayList<Move<Hop>> moves = piece.findAllMoves(boardManager.getBoard());

		assertEquals(2, moves.size());
	}
	
	@Test
	public void findAllMoves_blackPawn() {
		Piece piece = boardManager.addBlackPawn(18);

		ArrayList<Move<Hop>> moves = piece.findAllMoves(boardManager.getBoard());
		
		assertEquals(2, moves.size());
	}
	
	@Test
	public void findCapture_bothColors_upLeft() {
		WhitePawn whitePawn = boardManager.addWhitePawn(22);
		Piece takenPiece = boardManager.addBlackPawn(17);
		
		Capture whiteCapture = whitePawn.findCapture(UP_LEFT, boardManager.getBoard(), boardManager.getBlackPieces());

		assertEquals(getTile(22), whiteCapture.getSource());
		assertEquals(getTile(11), whiteCapture.getDestination());
		assertEquals(takenPiece, whiteCapture.getTakenPiece());
		
		BlackPawn blackPawn = boardManager.addBlackPawn(39);
		Piece takenPiece2 = boardManager.addWhitePawn(33);
		
		Capture blackCapture = blackPawn.findCapture(UP_LEFT, boardManager.getBoard(), boardManager.getWhitePieces());

		assertEquals(getTile(39), blackCapture.getSource());
		assertEquals(getTile(28), blackCapture.getDestination());
		assertEquals(takenPiece2, blackCapture.getTakenPiece());
	}
	
	@Test
	public void findCapture_bothColors_upRight() {
		WhitePawn whitePawn = boardManager.addWhitePawn(22);
		Piece takenPiece = boardManager.addBlackPawn(18);
		
		Capture whiteCapture = whitePawn.findCapture(UP_RIGHT, boardManager.getBoard(), boardManager.getBlackPieces());
		
		assertEquals(getTile(22), whiteCapture.getSource());
		assertEquals(getTile(13), whiteCapture.getDestination());
		assertEquals(takenPiece, whiteCapture.getTakenPiece());
		
		BlackPawn blackPawn = boardManager.addBlackPawn(39);
		Piece takenPiece2 = boardManager.addWhitePawn(34);
		
		Capture blackCapture = blackPawn.findCapture(UP_RIGHT, boardManager.getBoard(), boardManager.getWhitePieces());

		assertEquals(getTile(39), blackCapture.getSource());
		assertEquals(getTile(30), blackCapture.getDestination());
		assertEquals(takenPiece2, blackCapture.getTakenPiece());
	}
	
	@Test
	public void findCapture_bothColors_downLeft() {
		WhitePawn whitePawn = boardManager.addWhitePawn(22);
		Piece takenPiece = boardManager.addBlackPawn(27);
		
		Capture whiteCapture = whitePawn.findCapture(DOWN_LEFT, boardManager.getBoard(), boardManager.getBlackPieces());
		
		assertEquals(getTile(22), whiteCapture.getSource());
		assertEquals(getTile(31), whiteCapture.getDestination());
		assertEquals(takenPiece, whiteCapture.getTakenPiece());
		
		BlackPawn blackPawn = boardManager.addBlackPawn(39);
		Piece takenPiece2 = boardManager.addWhitePawn(43);
		
		Capture blackCapture = blackPawn.findCapture(DOWN_LEFT, boardManager.getBoard(), boardManager.getWhitePieces());

		assertEquals(getTile(39), blackCapture.getSource());
		assertEquals(getTile(48), blackCapture.getDestination());
		assertEquals(takenPiece2, blackCapture.getTakenPiece());
	}
	
	@Test
	public void findCapture_bothColors_downRight() {
		WhitePawn whitePawn = boardManager.addWhitePawn(22);
		Piece takenPiece = boardManager.addBlackPawn(28);
		
		Capture whiteCapture = whitePawn.findCapture(DOWN_RIGHT, boardManager.getBoard(), boardManager.getBlackPieces());
		
		assertEquals(getTile(22), whiteCapture.getSource());
		assertEquals(getTile(33), whiteCapture.getDestination());
		assertEquals(takenPiece, whiteCapture.getTakenPiece());
		
		BlackPawn blackPawn = boardManager.addBlackPawn(39);
		Piece takenPiece2 = boardManager.addWhitePawn(44);
		
		Capture blackCapture = blackPawn.findCapture(DOWN_RIGHT, boardManager.getBoard(), boardManager.getWhitePieces());
		
		assertEquals(getTile(39), blackCapture.getSource());
		assertEquals(getTile(50), blackCapture.getDestination());
		assertEquals(takenPiece2, blackCapture.getTakenPiece());
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
