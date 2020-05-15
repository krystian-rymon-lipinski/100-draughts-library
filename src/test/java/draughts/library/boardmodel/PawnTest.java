package draughts.library.boardmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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
		ArrayList<Move> moves = findMovesForPiece(33);
		assertEquals(Tile.State.WHITE_PAWN, boardManager.findTileByIndex(33).getState());
		
		assertEquals(2, moves.size());
		assertTrue(moves.get(0).getDestination(0) == 28 || 
				   moves.get(0).getDestination(0) == 29);
		assertTrue(moves.get(1).getDestination(0) == 28 || 
				   moves.get(1).getDestination(0) == 29);
	}
	
	@Test
	public void findMoves_forBlackPawn_test() {
		boardManager.addBlackPawn(18);
		ArrayList<Move> moves = findMovesForPiece(18);
		
		assertEquals(2, moves.size());
		assertTrue(moves.get(0).getDestination(0) == 22 || 
				   moves.get(0).getDestination(0) == 22);
		assertTrue(moves.get(1).getDestination(0) == 23 || 
				   moves.get(1).getDestination(0) == 23);
	}
	
	@Test
	public void findMoves_forMostLeftBlackPawn_test() {
		boardManager.addBlackPawn(16);
		ArrayList<Move> moves = findMovesForPiece(16);
		
		assertEquals(1, moves.size());
		assertEquals(21, moves.get(0).getDestination(0));
	}
	
	@Test
	public void findMoves_forMostRightWhitePawn_test() {
		boardManager.addWhitePawn(35);
		ArrayList<Move> moves = findMovesForPiece(35);
		
		assertEquals(1, moves.size());
		assertEquals(30, moves.get(0).getDestination(0));
	}
	
	@Test
	public void findUpLeftTake_test() {
		boardManager.addWhitePawn(22);
		boardManager.addBlackPawn(17);
		
		ArrayList<Move> whiteMoves = findTakesForPiece(22);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(22, whiteMoves.get(0).getSource(0));
		assertEquals(11, whiteMoves.get(0).getDestination(0));
		assertEquals(17, whiteMoves.get(0).getTakenPawn(0));
		
		boardManager.addBlackPawn(39);
		boardManager.addWhitePawn(33);
		
		ArrayList<Move> blackMoves = findTakesForPiece(39);
		
		assertEquals(1, blackMoves.size());
		assertEquals(39, blackMoves.get(0).getSource(0));
		assertEquals(28, blackMoves.get(0).getDestination(0));
		assertEquals(33, blackMoves.get(0).getTakenPawn(0));
	}
	
	@Test
	public void findUpRightTake_test() {
		boardManager.addWhitePawn(22);
		boardManager.addBlackPawn(18);
		
		ArrayList<Move> whiteMoves = findTakesForPiece(22);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(22, whiteMoves.get(0).getSource(0));
		assertEquals(13, whiteMoves.get(0).getDestination(0));
		assertEquals(18, whiteMoves.get(0).getTakenPawn(0));
		
		boardManager.addBlackPawn(39);
		boardManager.addWhitePawn(34);
		
		ArrayList<Move> blackMoves = findTakesForPiece(39);
		
		assertEquals(1, blackMoves.size());
		assertEquals(39, blackMoves.get(0).getSource(0));
		assertEquals(30, blackMoves.get(0).getDestination(0));
		assertEquals(34, blackMoves.get(0).getTakenPawn(0));
	}
	
	@Test
	public void findDownLeftTake_test() {
		boardManager.addWhitePawn(22);
		boardManager.addBlackPawn(27);
		
		ArrayList<Move> whiteMoves = findTakesForPiece(22);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(22, whiteMoves.get(0).getSource(0));
		assertEquals(31, whiteMoves.get(0).getDestination(0));
		assertEquals(27, whiteMoves.get(0).getTakenPawn(0));
		
		boardManager.addBlackPawn(39);
		boardManager.addWhitePawn(43);
		
		ArrayList<Move> blackMoves = findTakesForPiece(39);
		
		assertEquals(1, blackMoves.size());
		assertEquals(39, blackMoves.get(0).getSource(0));
		assertEquals(48, blackMoves.get(0).getDestination(0));
		assertEquals(43, blackMoves.get(0).getTakenPawn(0));
	}
	
	@Test
	public void findDownRightTake_test() {
		boardManager.addWhitePawn(22);
		boardManager.addBlackPawn(28);
		
		ArrayList<Move> whiteMoves = findTakesForPiece(22);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(22, whiteMoves.get(0).getSource(0));
		assertEquals(33, whiteMoves.get(0).getDestination(0));
		assertEquals(28, whiteMoves.get(0).getTakenPawn(0));
		
		boardManager.addBlackPawn(39);
		boardManager.addWhitePawn(44);
		
		ArrayList<Move> blackMoves = findTakesForPiece(39);
		
		assertEquals(1, blackMoves.size());
		assertEquals(39, blackMoves.get(0).getSource(0));
		assertEquals(50, blackMoves.get(0).getDestination(0));
		assertEquals(44, blackMoves.get(0).getTakenPawn(0));
	}
	
	
	@Test
	public void findTakes_withSingleHop_test() {
		boardManager.addWhitePawn(14);
		boardManager.addBlackPawn(9);
		boardManager.addBlackQueen(10);
		boardManager.addBlackQueen(19);
		boardManager.addBlackPawn(20);
		
		ArrayList<Move> whiteMoves = findTakesForPiece(14);
		
		assertEquals(4, whiteMoves.size());
	
		boardManager.addBlackPawn(17);
		boardManager.addWhitePawn(11);
		boardManager.addWhitePawn(12);
		boardManager.addWhiteQueen(21);
		boardManager.addWhiteQueen(22);
		
		ArrayList<Move> blackMoves = findTakesForPiece(17);
		
		assertEquals(4, blackMoves.size());
	}	

}
