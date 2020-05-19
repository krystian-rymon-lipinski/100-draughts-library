package draughts.library.boardmodel;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.Capture;
import draughts.library.Hop;
import draughts.library.Move;

@RunWith(MockitoJUnitRunner.class)
public class QueenTest extends PieceTest {
	
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void findMoves_upLeft_test() {
		boardManager.addWhiteQueen(32);
		boardManager.addWhitePawn(28);
		boardManager.addWhitePawn(38);
		boardManager.addWhitePawn(37);
		ArrayList<Move<Hop>> whiteMoves = findMovesForPiece(32);
		
		assertEquals(3, whiteMoves.size());
		assertEquals(32, whiteMoves.get(0).getHop(0).getSource());
		assertEquals(32, whiteMoves.get(2).getHop(0).getSource());
		assertEquals(27, whiteMoves.get(0).getHop(0).getDestination());
		assertEquals(21, whiteMoves.get(1).getHop(0).getDestination());
		assertEquals(16, whiteMoves.get(2).getHop(0).getDestination());
		
		
		boardManager.addBlackQueen(12);
		boardManager.addBlackPawn(8);
		boardManager.addBlackPawn(18);
		boardManager.addBlackPawn(17);
		ArrayList<Move<Hop>> blackMoves = findMovesForPiece(12);
		
		assertEquals(2, blackMoves.size());
		assertEquals(12, blackMoves.get(0).getHop(0).getSource());
		assertEquals(12, blackMoves.get(1).getHop(0).getSource());
		assertEquals(7, blackMoves.get(0).getHop(0).getDestination());
		assertEquals(1, blackMoves.get(1).getHop(0).getDestination());
	}
	
	@Test
	public void findMoves_upRight_test() {
		boardManager.addWhiteQueen(32);
		boardManager.addWhitePawn(27);
		boardManager.addWhitePawn(38);
		boardManager.addWhitePawn(37);
		ArrayList<Move<Hop>> whiteMoves = findMovesForPiece(32);
		
		assertEquals(6, whiteMoves.size());
		assertEquals(32, whiteMoves.get(0).getHop(0).getSource());
		assertEquals(32, whiteMoves.get(5).getHop(0).getSource());
		assertEquals(28, whiteMoves.get(0).getHop(0).getDestination());
		assertEquals(23, whiteMoves.get(1).getHop(0).getDestination());
		assertEquals(19, whiteMoves.get(2).getHop(0).getDestination());
		assertEquals(14, whiteMoves.get(3).getHop(0).getDestination());
		assertEquals(10, whiteMoves.get(4).getHop(0).getDestination());
		assertEquals(5, whiteMoves.get(5).getHop(0).getDestination());
		
		
		boardManager.addBlackQueen(12);
		boardManager.addBlackPawn(7);
		boardManager.addBlackPawn(18);
		boardManager.addBlackPawn(17);
		ArrayList<Move<Hop>> blackMoves = findMovesForPiece(12);
		
		assertEquals(2, blackMoves.size());
		assertEquals(12, blackMoves.get(0).getHop(0).getSource());
		assertEquals(12, blackMoves.get(1).getHop(0).getSource());
		assertEquals(8, blackMoves.get(0).getHop(0).getDestination());
		assertEquals(3, blackMoves.get(1).getHop(0).getDestination());
	}
	
	@Test
	public void findMoves_downLeft_test() {
		boardManager.addWhiteQueen(32);
		boardManager.addWhitePawn(27);
		boardManager.addWhitePawn(38);
		boardManager.addWhitePawn(28);
		ArrayList<Move<Hop>> whiteMoves = findMovesForPiece(32);
		
		assertEquals(3, whiteMoves.size());
		assertEquals(32, whiteMoves.get(0).getHop(0).getSource());
		assertEquals(32, whiteMoves.get(2).getHop(0).getSource());
		assertEquals(37, whiteMoves.get(0).getHop(0).getDestination());
		assertEquals(41, whiteMoves.get(1).getHop(0).getDestination());
		assertEquals(46, whiteMoves.get(2).getHop(0).getDestination());
		
		
		boardManager.addBlackQueen(12);
		boardManager.addBlackPawn(7);
		boardManager.addBlackPawn(18);
		boardManager.addBlackPawn(8);
		ArrayList<Move<Hop>> blackMoves = findMovesForPiece(12);
		
		assertEquals(3, blackMoves.size());
		assertEquals(12, blackMoves.get(0).getHop(0).getSource());
		assertEquals(12, blackMoves.get(2).getHop(0).getSource());
		assertEquals(17, blackMoves.get(0).getHop(0).getDestination());
		assertEquals(21, blackMoves.get(1).getHop(0).getDestination());
		assertEquals(26, blackMoves.get(2).getHop(0).getDestination());
	}
	
	@Test
	public void findMoves_downRight_test() {
		boardManager.addWhiteQueen(32);
		boardManager.addWhitePawn(27);
		boardManager.addWhitePawn(37);
		boardManager.addWhitePawn(28);
		ArrayList<Move<Hop>> whiteMoves = findMovesForPiece(32);
		
		assertEquals(3, whiteMoves.size());
		assertEquals(32, whiteMoves.get(0).getHop(0).getSource());
		assertEquals(32, whiteMoves.get(2).getHop(0).getSource());
		assertEquals(38, whiteMoves.get(0).getHop(0).getDestination());
		assertEquals(43, whiteMoves.get(1).getHop(0).getDestination());
		assertEquals(49, whiteMoves.get(2).getHop(0).getDestination());
		
		
		boardManager.addBlackQueen(12);
		boardManager.addBlackPawn(7);
		boardManager.addBlackPawn(17);
		boardManager.addBlackPawn(8);
		ArrayList<Move<Hop>> blackMoves = findMovesForPiece(12);
		
		assertEquals(6, blackMoves.size());
		assertEquals(12, blackMoves.get(0).getHop(0).getSource());
		assertEquals(12, blackMoves.get(5).getHop(0).getSource());
		assertEquals(18, blackMoves.get(0).getHop(0).getDestination());
		assertEquals(23, blackMoves.get(1).getHop(0).getDestination());
		assertEquals(29, blackMoves.get(2).getHop(0).getDestination());
		assertEquals(34, blackMoves.get(3).getHop(0).getDestination());
		assertEquals(40, blackMoves.get(4).getHop(0).getDestination());
		assertEquals(45, blackMoves.get(5).getHop(0).getDestination());

	}
	
	@Test
	public void findMoves_inAllDirections_test() {
		boardManager.addWhiteQueen(28);
		boardManager.addWhitePawn(11);
		boardManager.addBlackPawn(5);
		boardManager.addBlackQueen(32);
		boardManager.addWhiteQueen(50);
		ArrayList<Move<Hop>> whiteMoves = findMovesForPiece(28);
		
		assertEquals(9, whiteMoves.size());
		
		boardManager.addBlackQueen(30);
		boardManager.addWhitePawn(13);
		boardManager.addBlackPawn(25);
		boardManager.addBlackQueen(35);
		boardManager.addWhiteQueen(48);
		
		ArrayList<Move<Hop>> blackMoves = findMovesForPiece(30);
		
		assertEquals(5, blackMoves.size());
			
	}
	
	@Test
	public void findSingleCaptures_upLeft_test() {
		boardManager.addWhiteQueen(38);
		boardManager.addBlackPawn(32);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(38);
		
		assertEquals(3, whiteMoves.size());
		assertEquals(38, whiteMoves.get(0).getSource());
		assertEquals(38, whiteMoves.get(2).getSource());
		assertEquals(27, whiteMoves.get(0).getDestination());
		assertEquals(21, whiteMoves.get(1).getDestination());
		assertEquals(16, whiteMoves.get(2).getDestination());
		assertEquals(32, whiteMoves.get(0).getTakenPawn());
		assertEquals(32, whiteMoves.get(2).getTakenPawn());
				
		
		boardManager.addBlackQueen(18);
		boardManager.addWhitePawn(12);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(18);
		
		assertEquals(2, blackMoves.size());
		assertEquals(18, blackMoves.get(0).getSource());
		assertEquals(18, blackMoves.get(1).getSource());
		assertEquals(7, blackMoves.get(0).getDestination());
		assertEquals(1, blackMoves.get(1).getDestination());
		assertEquals(12, blackMoves.get(0).getTakenPawn());
		assertEquals(12, blackMoves.get(1).getTakenPawn());
		
	}

	@Test
	public void findSingleCaptures_upRight_test() {
		boardManager.addWhiteQueen(38);
		boardManager.addBlackPawn(33);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(38);
		
		assertEquals(4, whiteMoves.size());
		assertEquals(38, whiteMoves.get(0).getSource());
		assertEquals(38, whiteMoves.get(3).getSource());
		assertEquals(29, whiteMoves.get(0).getDestination());
		assertEquals(24, whiteMoves.get(1).getDestination());
		assertEquals(20, whiteMoves.get(2).getDestination());
		assertEquals(15, whiteMoves.get(3).getDestination());
		assertEquals(33, whiteMoves.get(0).getTakenPawn());
		assertEquals(33, whiteMoves.get(3).getTakenPawn());
				
		
		boardManager.addBlackQueen(18);
		boardManager.addWhitePawn(13);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(18);
		
		assertEquals(2, blackMoves.size());
		assertEquals(18, blackMoves.get(0).getSource());
		assertEquals(18, blackMoves.get(1).getSource());
		assertEquals(9, blackMoves.get(0).getDestination());
		assertEquals(4, blackMoves.get(1).getDestination());
		assertEquals(13, blackMoves.get(0).getTakenPawn());
		assertEquals(13, blackMoves.get(1).getTakenPawn());
		
	}
	
	@Test
	public void findSingleCaptures_downLeftTake_test() {
		boardManager.addWhiteQueen(38);
		boardManager.addBlackPawn(42);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(38);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(38, whiteMoves.get(0).getSource());
		assertEquals(47, whiteMoves.get(0).getDestination());
		assertEquals(42, whiteMoves.get(0).getTakenPawn());
				
		
		boardManager.addBlackQueen(18);
		boardManager.addWhitePawn(22);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(18);
		
		assertEquals(3, blackMoves.size());
		assertEquals(18, blackMoves.get(0).getSource());
		assertEquals(18, blackMoves.get(2).getSource());
		assertEquals(27, blackMoves.get(0).getDestination());
		assertEquals(31, blackMoves.get(1).getDestination());
		assertEquals(36, blackMoves.get(2).getDestination());
		assertEquals(22, blackMoves.get(0).getTakenPawn());
		assertEquals(22, blackMoves.get(1).getTakenPawn());
		
	}
	
	@Test
	public void findSingleCaptures_downRight_test() {
		boardManager.addWhiteQueen(38);
		boardManager.addBlackPawn(43);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(38);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(38, whiteMoves.get(0).getSource());
		assertEquals(49, whiteMoves.get(0).getDestination());
		assertEquals(43, whiteMoves.get(0).getTakenPawn());
				
		
		boardManager.addBlackQueen(18);
		boardManager.addWhitePawn(23);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(18);
		
		assertEquals(4, blackMoves.size());
		assertEquals(18, blackMoves.get(0).getSource());
		assertEquals(18, blackMoves.get(3).getSource());
		assertEquals(29, blackMoves.get(0).getDestination());
		assertEquals(34, blackMoves.get(1).getDestination());
		assertEquals(40, blackMoves.get(2).getDestination());
		assertEquals(45, blackMoves.get(3).getDestination());
		assertEquals(23, blackMoves.get(0).getTakenPawn());
		assertEquals(23, blackMoves.get(3).getTakenPawn());
		
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
