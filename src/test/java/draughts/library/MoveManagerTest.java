package draughts.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;

@RunWith(MockitoJUnitRunner.class)
public class MoveManagerTest {
	
	MoveManager testObj;
	BoardManager boardManager;
	
	@Before
	public void setUp() {
		testObj = new MoveManager();
		boardManager = testObj.getBoardManager();
		boardManager.createEmptyBoard();
	}
	
	@Test
	public void addHops_test() {
		Move move = new Move(31, 26);
		move.addHop(21);
		
		assertEquals(2, move.getHops().size());
		assertEquals(0, move.getPawnsTaken().size());
		
		Move move2 = new Move(35, 24, 30);
		move2.addHop(15, 20);
		move2.addHop(4, 10);
		
		assertEquals(3, move2.getHops().size());
		assertEquals(3, move2.getPawnsTaken().size());
	}
	
	@Test
	public void moveWithSingleHop_test() {
		boardManager.addWhitePawn(31);
		Move whiteMove = new Move(31, 26);
		
		assertEquals(0, testObj.getHopsMadeInMove());
		testObj.makeHop(whiteMove);
		
		assertEquals(Tile.State.WHITE_PAWN, boardManager.findTileByIndex(26).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(31).getState());
		assertNull(boardManager.findPieceByIndex(31));
		assertEquals(26, boardManager.findPieceByIndex(26).getPosition());
		assertEquals(0, testObj.getHopsMadeInMove());
		
		boardManager.addBlackPawn(20);
		Move blackMove = new Move(20, 24);
		
		assertEquals(0, testObj.getHopsMadeInMove());
		testObj.makeHop(blackMove);
		
		assertEquals(Tile.State.BLACK_PAWN, boardManager.findTileByIndex(24).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(20).getState());
		assertNull(boardManager.findPieceByIndex(20));
		assertEquals(24, boardManager.findPieceByIndex(24).getPosition());
		assertEquals(0, testObj.getHopsMadeInMove());

	}
	
	@Test
	public void moveWithMultipleHops_test() {
		boardManager.addWhitePawn(26);
		boardManager.addWhitePawn(19);
		boardManager.addBlackPawn(21);
		boardManager.addBlackPawn(12);
		boardManager.addBlackPawn(2);
		
		assertEquals(3, boardManager.getBlackPieces().size());
		assertEquals(2, boardManager.getWhitePieces().size());
		
		Move whiteMove = new Move(26, 17, 21);
		whiteMove.addHop(8, 12); //double take by white pawn
		
		testObj.makeHop(whiteMove);
		assertEquals(1, testObj.getHopsMadeInMove());
		testObj.makeHop(whiteMove);
		assertEquals(0, testObj.getHopsMadeInMove());
		
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(26).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(21).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(17).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(12).getState());
		assertEquals(Tile.State.WHITE_PAWN, boardManager.findTileByIndex(8).getState());
		
		assertEquals(1, boardManager.getBlackPieces().size());

		
		Move blackMove = new Move(2, 13, 8);
		blackMove.addHop(24, 19); //double take by black pawn
		
		testObj.makeHop(blackMove);
		assertEquals(1, testObj.getHopsMadeInMove());
		testObj.makeHop(blackMove);
		assertEquals(0, testObj.getHopsMadeInMove());

		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(2).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(8).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(13).getState());
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(19).getState());
		assertEquals(Tile.State.BLACK_PAWN, boardManager.findTileByIndex(24).getState());
		
		assertEquals(0, boardManager.getWhitePieces().size());
		
	}
	
	public ArrayList<Move> findMovesForPiece(int piecePosition) {
		Piece piece = testObj.getBoardManager().findPieceByIndex(piecePosition);
		Tile currentTile = testObj.getBoardManager().findTileByIndex(piecePosition);
		return piece.findMoves(testObj.getBoardManager().getBoard(), currentTile);
	}
	
	public ArrayList<Move> findTakesForPiece(int piecePosition) {
		Piece piece = testObj.getBoardManager().findPieceByIndex(piecePosition);
		Tile currentTile = testObj.getBoardManager().findTileByIndex(piecePosition);
		return piece.findTakes(testObj.getBoardManager().getBoard(), currentTile);
	}
	
	@Test(expected = NullPointerException.class)
	public void findMoves_noPawnInChosenPosition_test() {
		findMovesForPiece(11);
	}
	
	@Test(expected = NullPointerException.class)
	public void findTakes_noPawnInChosenPosition_test() {
		findMovesForPiece(11);
	}
	
	@Test
	public void findMoves_forWhitePawn_test() {
		boardManager.addWhitePawn(33);
		ArrayList<Move> moves = findMovesForPiece(33);
		assertEquals(Tile.State.WHITE_PAWN, testObj.getBoardManager().findTileByIndex(33).getState());
		
		assertEquals(2, moves.size());
		assertTrue(moves.get(0).getDestination(testObj.getHopsMadeInMove()) == 28 || 
				   moves.get(0).getDestination(testObj.getHopsMadeInMove()) == 29);
		assertTrue(moves.get(1).getDestination(testObj.getHopsMadeInMove()) == 28 || 
				   moves.get(1).getDestination(testObj.getHopsMadeInMove()) == 29);
	}
	
	@Test
	public void findMoves_forBlackPawn_test() {
		boardManager.addBlackPawn(18);
		ArrayList<Move> moves = findMovesForPiece(18);
		
		assertEquals(2, moves.size());
		assertTrue(moves.get(0).getDestination(testObj.getHopsMadeInMove()) == 22 || 
				   moves.get(0).getDestination(testObj.getHopsMadeInMove()) == 22);
		assertTrue(moves.get(1).getDestination(testObj.getHopsMadeInMove()) == 23 || 
				   moves.get(1).getDestination(testObj.getHopsMadeInMove()) == 23);
	}
	
	@Test
	public void findMoves_forMostLeftBlackPawn_test() {
		boardManager.addBlackPawn(16);
		ArrayList<Move> moves = findMovesForPiece(16);
		
		assertEquals(1, moves.size());
		assertEquals(21, moves.get(0).getDestination(testObj.getHopsMadeInMove()));
	}
	
	@Test
	public void findMoves_forMostRightWhitePawn_test() {
		boardManager.addWhitePawn(35);
		ArrayList<Move> moves = findMovesForPiece(35);
		
		assertEquals(1, moves.size());
		assertEquals(30, moves.get(0).getDestination(testObj.getHopsMadeInMove()));
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
	public void findTakes_withSingleHop_forWhitePawn_test() {
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
	
	@Test
	public void findUpLeftMoves_forQueen_test() {
		boardManager.addWhiteQueen(32);
		boardManager.addWhitePawn(28);
		boardManager.addWhitePawn(38);
		boardManager.addWhitePawn(37);
		ArrayList<Move> whiteMoves = findMovesForPiece(32);
		
		assertEquals(3, whiteMoves.size());
		assertEquals(32, whiteMoves.get(0).getSource(0));
		assertEquals(32, whiteMoves.get(2).getSource(0));
		assertEquals(27, whiteMoves.get(0).getDestination(0));
		assertEquals(21, whiteMoves.get(1).getDestination(0));
		assertEquals(16, whiteMoves.get(2).getDestination(0));
		
		
		boardManager.addBlackQueen(12);
		boardManager.addBlackPawn(8);
		boardManager.addBlackPawn(18);
		boardManager.addBlackPawn(17);
		ArrayList<Move> blackMoves = findMovesForPiece(12);
		
		assertEquals(2, blackMoves.size());
		assertEquals(12, blackMoves.get(0).getSource(0));
		assertEquals(12, blackMoves.get(1).getSource(0));
		assertEquals(7, blackMoves.get(0).getDestination(0));
		assertEquals(1, blackMoves.get(1).getDestination(0));
	}
	
	@Test
	public void findUpRightMoves_forQueen_test() {
		boardManager.addWhiteQueen(32);
		boardManager.addWhitePawn(27);
		boardManager.addWhitePawn(38);
		boardManager.addWhitePawn(37);
		ArrayList<Move> whiteMoves = findMovesForPiece(32);
		
		assertEquals(6, whiteMoves.size());
		assertEquals(32, whiteMoves.get(0).getSource(0));
		assertEquals(32, whiteMoves.get(5).getSource(0));
		assertEquals(28, whiteMoves.get(0).getDestination(0));
		assertEquals(23, whiteMoves.get(1).getDestination(0));
		assertEquals(19, whiteMoves.get(2).getDestination(0));
		assertEquals(14, whiteMoves.get(3).getDestination(0));
		assertEquals(10, whiteMoves.get(4).getDestination(0));
		assertEquals(5, whiteMoves.get(5).getDestination(0));
		
		
		boardManager.addBlackQueen(12);
		boardManager.addBlackPawn(7);
		boardManager.addBlackPawn(18);
		boardManager.addBlackPawn(17);
		ArrayList<Move> blackMoves = findMovesForPiece(12);
		
		assertEquals(2, blackMoves.size());
		assertEquals(12, blackMoves.get(0).getSource(0));
		assertEquals(12, blackMoves.get(1).getSource(0));
		assertEquals(8, blackMoves.get(0).getDestination(0));
		assertEquals(3, blackMoves.get(1).getDestination(0));
	}
	
	@Test
	public void findDownLeftMoves_forQueen_test() {
		boardManager.addWhiteQueen(32);
		boardManager.addWhitePawn(27);
		boardManager.addWhitePawn(38);
		boardManager.addWhitePawn(28);
		ArrayList<Move> whiteMoves = findMovesForPiece(32);
		
		assertEquals(3, whiteMoves.size());
		assertEquals(32, whiteMoves.get(0).getSource(0));
		assertEquals(32, whiteMoves.get(2).getSource(0));
		assertEquals(37, whiteMoves.get(0).getDestination(0));
		assertEquals(41, whiteMoves.get(1).getDestination(0));
		assertEquals(46, whiteMoves.get(2).getDestination(0));
		
		
		boardManager.addBlackQueen(12);
		boardManager.addBlackPawn(7);
		boardManager.addBlackPawn(18);
		boardManager.addBlackPawn(8);
		ArrayList<Move> blackMoves = findMovesForPiece(12);
		
		assertEquals(3, blackMoves.size());
		assertEquals(12, blackMoves.get(0).getSource(0));
		assertEquals(12, blackMoves.get(2).getSource(0));
		assertEquals(17, blackMoves.get(0).getDestination(0));
		assertEquals(21, blackMoves.get(1).getDestination(0));
		assertEquals(26, blackMoves.get(2).getDestination(0));
	}
	
	@Test
	public void findDownRightMoves_forQueen_test() {
		boardManager.addWhiteQueen(32);
		boardManager.addWhitePawn(27);
		boardManager.addWhitePawn(37);
		boardManager.addWhitePawn(28);
		ArrayList<Move> whiteMoves = findMovesForPiece(32);
		
		assertEquals(3, whiteMoves.size());
		assertEquals(32, whiteMoves.get(0).getSource(0));
		assertEquals(32, whiteMoves.get(2).getSource(0));
		assertEquals(38, whiteMoves.get(0).getDestination(0));
		assertEquals(43, whiteMoves.get(1).getDestination(0));
		assertEquals(49, whiteMoves.get(2).getDestination(0));
		
		
		boardManager.addBlackQueen(12);
		boardManager.addBlackPawn(7);
		boardManager.addBlackPawn(17);
		boardManager.addBlackPawn(8);
		ArrayList<Move> blackMoves = findMovesForPiece(12);
		
		assertEquals(6, blackMoves.size());
		assertEquals(12, blackMoves.get(0).getSource(0));
		assertEquals(12, blackMoves.get(5).getSource(0));
		assertEquals(18, blackMoves.get(0).getDestination(0));
		assertEquals(23, blackMoves.get(1).getDestination(0));
		assertEquals(29, blackMoves.get(2).getDestination(0));
		assertEquals(34, blackMoves.get(3).getDestination(0));
		assertEquals(40, blackMoves.get(4).getDestination(0));
		assertEquals(45, blackMoves.get(5).getDestination(0));

	}
	
	@Test
	public void findMoves_forQueen_test() {
		boardManager.addWhiteQueen(28);
		boardManager.addWhitePawn(11);
		boardManager.addBlackPawn(5);
		boardManager.addBlackQueen(32);
		boardManager.addWhiteQueen(50);
		ArrayList<Move> whiteMoves = findMovesForPiece(28);
		
		assertEquals(9, whiteMoves.size());
		
		boardManager.addBlackQueen(30);
		boardManager.addWhitePawn(13);
		boardManager.addBlackPawn(25);
		boardManager.addBlackQueen(35);
		boardManager.addWhiteQueen(48);
		
		ArrayList<Move> blackMoves = findMovesForPiece(30);
		
		assertEquals(5, blackMoves.size());
			
	}
	
	@Test
	public void findUpLeftTake_forQueen_test() {
		boardManager.addWhiteQueen(38);
		boardManager.addBlackPawn(32);
		
		ArrayList<Move> whiteMoves = findTakesForPiece(38);
		
		assertEquals(3, whiteMoves.size());
		assertEquals(38, whiteMoves.get(0).getSource(0));
		assertEquals(38, whiteMoves.get(2).getSource(0));
		assertEquals(27, whiteMoves.get(0).getDestination(0));
		assertEquals(21, whiteMoves.get(1).getDestination(0));
		assertEquals(16, whiteMoves.get(2).getDestination(0));
		assertEquals(32, whiteMoves.get(0).getTakenPawn(0));
		assertEquals(32, whiteMoves.get(2).getTakenPawn(0));
				
		
		boardManager.addBlackQueen(18);
		boardManager.addWhitePawn(12);
		
		ArrayList<Move> blackMoves = findTakesForPiece(18);
		
		assertEquals(2, blackMoves.size());
		assertEquals(18, blackMoves.get(0).getSource(0));
		assertEquals(18, blackMoves.get(1).getSource(0));
		assertEquals(7, blackMoves.get(0).getDestination(0));
		assertEquals(1, blackMoves.get(1).getDestination(0));
		assertEquals(12, blackMoves.get(0).getTakenPawn(0));
		assertEquals(12, blackMoves.get(1).getTakenPawn(0));
		
	}

	@Test
	public void findUpRightTake_forQueen_test() {
		boardManager.addWhiteQueen(38);
		boardManager.addBlackPawn(33);
		
		ArrayList<Move> whiteMoves = findTakesForPiece(38);
		
		assertEquals(4, whiteMoves.size());
		assertEquals(38, whiteMoves.get(0).getSource(0));
		assertEquals(38, whiteMoves.get(3).getSource(0));
		assertEquals(29, whiteMoves.get(0).getDestination(0));
		assertEquals(24, whiteMoves.get(1).getDestination(0));
		assertEquals(20, whiteMoves.get(2).getDestination(0));
		assertEquals(15, whiteMoves.get(3).getDestination(0));
		assertEquals(33, whiteMoves.get(0).getTakenPawn(0));
		assertEquals(33, whiteMoves.get(3).getTakenPawn(0));
				
		
		boardManager.addBlackQueen(18);
		boardManager.addWhitePawn(13);
		
		ArrayList<Move> blackMoves = findTakesForPiece(18);
		
		assertEquals(2, blackMoves.size());
		assertEquals(18, blackMoves.get(0).getSource(0));
		assertEquals(18, blackMoves.get(1).getSource(0));
		assertEquals(9, blackMoves.get(0).getDestination(0));
		assertEquals(4, blackMoves.get(1).getDestination(0));
		assertEquals(13, blackMoves.get(0).getTakenPawn(0));
		assertEquals(13, blackMoves.get(1).getTakenPawn(0));
		
	}
	
	@Test
	public void findDownLeftTake_forQueen_test() {
		boardManager.addWhiteQueen(38);
		boardManager.addBlackPawn(42);
		
		ArrayList<Move> whiteMoves = findTakesForPiece(38);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(38, whiteMoves.get(0).getSource(0));
		assertEquals(47, whiteMoves.get(0).getDestination(0));
		assertEquals(42, whiteMoves.get(0).getTakenPawn(0));
				
		
		boardManager.addBlackQueen(18);
		boardManager.addWhitePawn(22);
		
		ArrayList<Move> blackMoves = findTakesForPiece(18);
		
		assertEquals(3, blackMoves.size());
		assertEquals(18, blackMoves.get(0).getSource(0));
		assertEquals(18, blackMoves.get(2).getSource(0));
		assertEquals(27, blackMoves.get(0).getDestination(0));
		assertEquals(31, blackMoves.get(1).getDestination(0));
		assertEquals(36, blackMoves.get(2).getDestination(0));
		assertEquals(22, blackMoves.get(0).getTakenPawn(0));
		assertEquals(22, blackMoves.get(1).getTakenPawn(0));
		
	}
	
	@Test
	public void findDownRightTake_forQueen_test() {
		boardManager.addWhiteQueen(38);
		boardManager.addBlackPawn(43);
		
		ArrayList<Move> whiteMoves = findTakesForPiece(38);
		
		assertEquals(1, whiteMoves.size());
		assertEquals(38, whiteMoves.get(0).getSource(0));
		assertEquals(49, whiteMoves.get(0).getDestination(0));
		assertEquals(43, whiteMoves.get(0).getTakenPawn(0));
				
		
		boardManager.addBlackQueen(18);
		boardManager.addWhitePawn(23);
		
		ArrayList<Move> blackMoves = findTakesForPiece(18);
		
		assertEquals(4, blackMoves.size());
		assertEquals(18, blackMoves.get(0).getSource(0));
		assertEquals(18, blackMoves.get(3).getSource(0));
		assertEquals(29, blackMoves.get(0).getDestination(0));
		assertEquals(34, blackMoves.get(1).getDestination(0));
		assertEquals(40, blackMoves.get(2).getDestination(0));
		assertEquals(45, blackMoves.get(3).getDestination(0));
		assertEquals(23, blackMoves.get(0).getTakenPawn(0));
		assertEquals(23, blackMoves.get(3).getTakenPawn(0));
		
	}
	
	
	@Test
	public void findTakes_withSingleHop_forQueen_test() {
		boardManager.addWhiteQueen(19);
		boardManager.addBlackPawn(5);
		boardManager.addBlackQueen(8);
		boardManager.addBlackQueen(24);
		boardManager.addBlackPawn(37);
		
		ArrayList<Move> whiteMoves = findTakesForPiece(19);
		
		assertEquals(5, whiteMoves.size());
	
		boardManager.addBlackQueen(27);
		boardManager.addWhitePawn(18);
		boardManager.addWhitePawn(21);
		boardManager.addWhiteQueen(36);
		boardManager.addWhiteQueen(43);
		
		ArrayList<Move> blackMoves = findTakesForPiece(27);
		
		assertEquals(5, blackMoves.size());
	}

}
