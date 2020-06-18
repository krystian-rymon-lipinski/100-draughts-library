package draughts.library.boardmodel;

import static org.junit.Assert.assertEquals;

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
	public void findMoves_upLeft_test() {
		boardManager.addWhiteQueen(32);
		boardManager.addWhitePawn(28);
		boardManager.addWhitePawn(38);
		boardManager.addWhitePawn(37);
		ArrayList<Move<Hop>> whiteMoves = findMovesForPiece(32);
		
		assertEquals(3, whiteMoves.size());
		assertEquals(32, whiteMoves.get(0).getHop(0).getSource().getIndex());
		assertEquals(32, whiteMoves.get(2).getHop(0).getSource().getIndex());
		assertEquals(27, whiteMoves.get(0).getHop(0).getDestination().getIndex());
		assertEquals(21, whiteMoves.get(1).getHop(0).getDestination().getIndex());
		assertEquals(16, whiteMoves.get(2).getHop(0).getDestination().getIndex());
		assertEquals(32, whiteMoves.get(0).getMovingPiece().getPosition().getIndex());
		assertEquals(32, whiteMoves.get(2).getMovingPiece().getPosition().getIndex());
		
		
		boardManager.addBlackQueen(12);
		boardManager.addBlackPawn(8);
		boardManager.addBlackPawn(18);
		boardManager.addBlackPawn(17);
		ArrayList<Move<Hop>> blackMoves = findMovesForPiece(12);
		
		assertEquals(2, blackMoves.size());
		assertEquals(12, blackMoves.get(0).getHop(0).getSource().getIndex());
		assertEquals(12, blackMoves.get(1).getHop(0).getSource().getIndex());
		assertEquals(7, blackMoves.get(0).getHop(0).getDestination().getIndex());
		assertEquals(1, blackMoves.get(1).getHop(0).getDestination().getIndex());
		assertEquals(12, blackMoves.get(0).getMovingPiece().getPosition().getIndex());
		assertEquals(12, blackMoves.get(1).getMovingPiece().getPosition().getIndex());
	}
	
	@Test
	public void findMoves_upRight_test() {
		boardManager.addWhiteQueen(32);
		boardManager.addWhitePawn(27);
		boardManager.addWhitePawn(38);
		boardManager.addWhitePawn(37);
		ArrayList<Move<Hop>> whiteMoves = findMovesForPiece(32);
		
		assertEquals(6, whiteMoves.size());
		assertEquals(32, whiteMoves.get(0).getHop(0).getSource().getIndex());
		assertEquals(32, whiteMoves.get(5).getHop(0).getSource().getIndex());
		assertEquals(28, whiteMoves.get(0).getHop(0).getDestination().getIndex());
		assertEquals(23, whiteMoves.get(1).getHop(0).getDestination().getIndex());
		assertEquals(19, whiteMoves.get(2).getHop(0).getDestination().getIndex());
		assertEquals(14, whiteMoves.get(3).getHop(0).getDestination().getIndex());
		assertEquals(10, whiteMoves.get(4).getHop(0).getDestination().getIndex());
		assertEquals(5, whiteMoves.get(5).getHop(0).getDestination().getIndex());
		assertEquals(32, whiteMoves.get(0).getMovingPiece().getPosition().getIndex());
		assertEquals(32, whiteMoves.get(5).getMovingPiece().getPosition().getIndex());
		
		
		boardManager.addBlackQueen(12);
		boardManager.addBlackPawn(7);
		boardManager.addBlackPawn(18);
		boardManager.addBlackPawn(17);
		ArrayList<Move<Hop>> blackMoves = findMovesForPiece(12);
		
		assertEquals(2, blackMoves.size());
		assertEquals(12, blackMoves.get(0).getHop(0).getSource().getIndex());
		assertEquals(12, blackMoves.get(1).getHop(0).getSource().getIndex());
		assertEquals(8, blackMoves.get(0).getHop(0).getDestination().getIndex());
		assertEquals(3, blackMoves.get(1).getHop(0).getDestination().getIndex());
		assertEquals(12, blackMoves.get(0).getMovingPiece().getPosition().getIndex());
		assertEquals(12, blackMoves.get(1).getMovingPiece().getPosition().getIndex());
	}
	
	@Test
	public void findMoves_downLeft_test() {
		boardManager.addWhiteQueen(32);
		boardManager.addWhitePawn(27);
		boardManager.addWhitePawn(38);
		boardManager.addWhitePawn(28);
		ArrayList<Move<Hop>> whiteMoves = findMovesForPiece(32);
		
		assertEquals(3, whiteMoves.size());
		assertEquals(32, whiteMoves.get(0).getHop(0).getSource().getIndex());
		assertEquals(32, whiteMoves.get(2).getHop(0).getSource().getIndex());
		assertEquals(37, whiteMoves.get(0).getHop(0).getDestination().getIndex());
		assertEquals(41, whiteMoves.get(1).getHop(0).getDestination().getIndex());
		assertEquals(46, whiteMoves.get(2).getHop(0).getDestination().getIndex());
		assertEquals(32, whiteMoves.get(0).getMovingPiece().getPosition().getIndex());
		assertEquals(32, whiteMoves.get(2).getMovingPiece().getPosition().getIndex());
		
		
		boardManager.addBlackQueen(12);
		boardManager.addBlackPawn(7);
		boardManager.addBlackPawn(18);
		boardManager.addBlackPawn(8);
		ArrayList<Move<Hop>> blackMoves = findMovesForPiece(12);
		
		assertEquals(3, blackMoves.size());
		assertEquals(12, blackMoves.get(0).getHop(0).getSource().getIndex());
		assertEquals(12, blackMoves.get(2).getHop(0).getSource().getIndex());
		assertEquals(17, blackMoves.get(0).getHop(0).getDestination().getIndex());
		assertEquals(21, blackMoves.get(1).getHop(0).getDestination().getIndex());
		assertEquals(26, blackMoves.get(2).getHop(0).getDestination().getIndex());
		assertEquals(12, blackMoves.get(0).getMovingPiece().getPosition().getIndex());
		assertEquals(12, blackMoves.get(2).getMovingPiece().getPosition().getIndex());
	}
	
	@Test
	public void findMoves_downRight_test() {
		boardManager.addWhiteQueen(32);
		boardManager.addWhitePawn(27);
		boardManager.addWhitePawn(37);
		boardManager.addWhitePawn(28);
		ArrayList<Move<Hop>> whiteMoves = findMovesForPiece(32);
		
		assertEquals(3, whiteMoves.size());
		assertEquals(32, whiteMoves.get(0).getHop(0).getSource().getIndex());
		assertEquals(32, whiteMoves.get(2).getHop(0).getSource().getIndex());
		assertEquals(38, whiteMoves.get(0).getHop(0).getDestination().getIndex());
		assertEquals(43, whiteMoves.get(1).getHop(0).getDestination().getIndex());
		assertEquals(49, whiteMoves.get(2).getHop(0).getDestination().getIndex());
		assertEquals(32, whiteMoves.get(0).getMovingPiece().getPosition().getIndex());
		assertEquals(32, whiteMoves.get(2).getMovingPiece().getPosition().getIndex());
		
		
		boardManager.addBlackQueen(12);
		boardManager.addBlackPawn(7);
		boardManager.addBlackPawn(17);
		boardManager.addBlackPawn(8);
		ArrayList<Move<Hop>> blackMoves = findMovesForPiece(12);
		
		assertEquals(6, blackMoves.size());
		assertEquals(12, blackMoves.get(0).getHop(0).getSource().getIndex());
		assertEquals(12, blackMoves.get(5).getHop(0).getSource().getIndex());
		assertEquals(18, blackMoves.get(0).getHop(0).getDestination().getIndex());
		assertEquals(23, blackMoves.get(1).getHop(0).getDestination().getIndex());
		assertEquals(29, blackMoves.get(2).getHop(0).getDestination().getIndex());
		assertEquals(34, blackMoves.get(3).getHop(0).getDestination().getIndex());
		assertEquals(40, blackMoves.get(4).getHop(0).getDestination().getIndex());
		assertEquals(45, blackMoves.get(5).getHop(0).getDestination().getIndex());
		assertEquals(12, blackMoves.get(0).getMovingPiece().getPosition().getIndex());
		assertEquals(12, blackMoves.get(5).getMovingPiece().getPosition().getIndex());

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
		Piece takenPiece = boardManager.addBlackPawn(32);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(38);
		
		assertEquals(3, whiteMoves.size());
		assertEquals(38, whiteMoves.get(0).getSource().getIndex());
		assertEquals(38, whiteMoves.get(2).getSource().getIndex());
		assertEquals(27, whiteMoves.get(0).getDestination().getIndex());
		assertEquals(21, whiteMoves.get(1).getDestination().getIndex());
		assertEquals(16, whiteMoves.get(2).getDestination().getIndex());
		assertEquals(takenPiece, whiteMoves.get(0).getTakenPiece());
		assertEquals(takenPiece, whiteMoves.get(2).getTakenPiece());
				
		
		boardManager.addBlackQueen(18);
		Piece takenPiece2 = boardManager.addWhitePawn(12);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(18);
		
		assertEquals(2, blackMoves.size());
		assertEquals(18, blackMoves.get(0).getSource().getIndex());
		assertEquals(18, blackMoves.get(1).getSource().getIndex());
		assertEquals(7, blackMoves.get(0).getDestination().getIndex());
		assertEquals(1, blackMoves.get(1).getDestination().getIndex());
		assertEquals(takenPiece2, blackMoves.get(0).getTakenPiece());
		assertEquals(takenPiece2, blackMoves.get(1).getTakenPiece());
		
	}

	@Test
	public void findSingleCaptures_upRight_test() {
		boardManager.addWhiteQueen(38);
		Piece takenPiece = boardManager.addBlackPawn(33);
		
		ArrayList<Capture> whiteMoves = findTakesForPiece(38);
		
		assertEquals(4, whiteMoves.size());
		assertEquals(38, whiteMoves.get(0).getSource().getIndex());
		assertEquals(38, whiteMoves.get(3).getSource().getIndex());
		assertEquals(29, whiteMoves.get(0).getDestination().getIndex());
		assertEquals(24, whiteMoves.get(1).getDestination().getIndex());
		assertEquals(20, whiteMoves.get(2).getDestination().getIndex());
		assertEquals(15, whiteMoves.get(3).getDestination().getIndex());
		assertEquals(takenPiece, whiteMoves.get(0).getTakenPiece());
		assertEquals(takenPiece, whiteMoves.get(3).getTakenPiece());
				
		
		boardManager.addBlackQueen(18);
		Piece takenPiece2 = boardManager.addWhitePawn(13);
		
		ArrayList<Capture> blackMoves = findTakesForPiece(18);
		
		assertEquals(2, blackMoves.size());
		assertEquals(18, blackMoves.get(0).getSource().getIndex());
		assertEquals(18, blackMoves.get(1).getSource().getIndex());
		assertEquals(9, blackMoves.get(0).getDestination().getIndex());
		assertEquals(4, blackMoves.get(1).getDestination().getIndex());
		assertEquals(takenPiece2, blackMoves.get(0).getTakenPiece());
		assertEquals(takenPiece2, blackMoves.get(1).getTakenPiece());
		
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
		assertEquals(18, blackMoves.get(0).getSource().getIndex());
		assertEquals(18, blackMoves.get(2).getSource().getIndex());
		assertEquals(27, blackMoves.get(0).getDestination().getIndex());
		assertEquals(31, blackMoves.get(1).getDestination().getIndex());
		assertEquals(36, blackMoves.get(2).getDestination().getIndex());
		assertEquals(takenPiece2, blackMoves.get(0).getTakenPiece());
		assertEquals(takenPiece2, blackMoves.get(1).getTakenPiece());
		
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
		assertEquals(18, blackMoves.get(0).getSource().getIndex());
		assertEquals(18, blackMoves.get(3).getSource().getIndex());
		assertEquals(29, blackMoves.get(0).getDestination().getIndex());
		assertEquals(34, blackMoves.get(1).getDestination().getIndex());
		assertEquals(40, blackMoves.get(2).getDestination().getIndex());
		assertEquals(45, blackMoves.get(3).getDestination().getIndex());
		assertEquals(takenPiece2, blackMoves.get(0).getTakenPiece());
		assertEquals(takenPiece2, blackMoves.get(3).getTakenPiece());
		
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
