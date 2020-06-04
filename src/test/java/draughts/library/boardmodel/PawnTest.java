package draughts.library.boardmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.Capture;
import draughts.library.Hop;
import draughts.library.Move;

@RunWith(MockitoJUnitRunner.class)
public class PawnTest extends PieceTest{
	
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void findMoves_forWhitePawn_test() {
		boardManager.addWhitePawn(33);
		ArrayList<Move<Hop>> moves = findMovesForPiece(33);
		
		assertEquals(2, moves.size());
		assertTrue(moves.get(0).getHop(0).getDestination().getIndex() == 28 || 
				   moves.get(0).getHop(0).getDestination().getIndex() == 29);
		assertTrue(moves.get(1).getHop(0).getDestination().getIndex() == 28 || 
				   moves.get(1).getHop(0).getDestination().getIndex() == 29);
	}
	
	@Test
	public void findMoves_forBlackPawn_test() {
		boardManager.addBlackPawn(18);
		ArrayList<Move<Hop>> moves = findMovesForPiece(18);
		
		assertEquals(2, moves.size());
		assertTrue(moves.get(0).getHop(0).getDestination().getIndex() == 22 || 
				   moves.get(0).getHop(0).getDestination().getIndex() == 22);
		assertTrue(moves.get(1).getHop(0).getDestination().getIndex() == 23 || 
				   moves.get(1).getHop(0).getDestination().getIndex() == 23);
	}
	
	@Test
	public void findMoves_forMostLeftBlackPawn_test() {
		boardManager.addBlackPawn(16);
		ArrayList<Move<Hop>> moves = findMovesForPiece(16);
		
		assertEquals(1, moves.size());
		assertEquals(21, moves.get(0).getHop(0).getDestination().getIndex());
	}
	
	@Test
	public void findMoves_forMostRightWhitePawn_test() {
		boardManager.addWhitePawn(35);
		ArrayList<Move<Hop>> moves = findMovesForPiece(35);
		
		assertEquals(1, moves.size());
		assertEquals(30, moves.get(0).getHop(0).getDestination().getIndex());
	}
	
	@Test
	public void findSingleCapture_upLeft_test() {
		boardManager.addWhitePawn(22);
		boardManager.addBlackPawn(17);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(22);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(22, whiteMoves.get(0).getSource().getIndex());
		assertEquals(11, whiteMoves.get(0).getDestination().getIndex());
		assertEquals(17, whiteMoves.get(0).getTakenPawn().getIndex());
		
		boardManager.addBlackPawn(39);
		boardManager.addWhitePawn(33);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(39);
		
		assertEquals(1, blackMoves.size());
		assertEquals(39, blackMoves.get(0).getSource().getIndex());
		assertEquals(28, blackMoves.get(0).getDestination().getIndex());
		assertEquals(33, blackMoves.get(0).getTakenPawn().getIndex());
	}
	
	@Test
	public void findSingleCapture_upRight_test() {
		boardManager.addWhitePawn(22);
		boardManager.addBlackPawn(18);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(22);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(22, whiteMoves.get(0).getSource().getIndex());
		assertEquals(13, whiteMoves.get(0).getDestination().getIndex());
		assertEquals(18, whiteMoves.get(0).getTakenPawn().getIndex());
		
		boardManager.addBlackPawn(39);
		boardManager.addWhitePawn(34);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(39);
		
		assertEquals(1, blackMoves.size());
		assertEquals(39, blackMoves.get(0).getSource().getIndex());
		assertEquals(30, blackMoves.get(0).getDestination().getIndex());
		assertEquals(34, blackMoves.get(0).getTakenPawn().getIndex());
	}
	
	@Test
	public void findSingleCapture_downLeft_test() {
		boardManager.addWhitePawn(22);
		boardManager.addBlackPawn(27);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(22);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(22, whiteMoves.get(0).getSource().getIndex());
		assertEquals(31, whiteMoves.get(0).getDestination().getIndex());
		assertEquals(27, whiteMoves.get(0).getTakenPawn().getIndex());
		
		boardManager.addBlackPawn(39);
		boardManager.addWhitePawn(43);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(39);
		
		assertEquals(1, blackMoves.size());
		assertEquals(39, blackMoves.get(0).getSource().getIndex());
		assertEquals(48, blackMoves.get(0).getDestination().getIndex());
		assertEquals(43, blackMoves.get(0).getTakenPawn().getIndex());
	}
	
	@Test
	public void findSingleCapture_downRight_test() {
		boardManager.addWhitePawn(22);
		boardManager.addBlackPawn(28);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(22);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(22, whiteMoves.get(0).getSource().getIndex());
		assertEquals(33, whiteMoves.get(0).getDestination().getIndex());
		assertEquals(28, whiteMoves.get(0).getTakenPawn().getIndex());
		
		boardManager.addBlackPawn(39);
		boardManager.addWhitePawn(44);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(39);
		
		assertEquals(1, blackMoves.size());
		assertEquals(39, blackMoves.get(0).getSource().getIndex());
		assertEquals(50, blackMoves.get(0).getDestination().getIndex());
		assertEquals(44, blackMoves.get(0).getTakenPawn().getIndex());
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
