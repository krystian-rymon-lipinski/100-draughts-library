package draughts.library.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import draughts.library.exceptions.WrongMoveException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.boardmodel.Piece;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

@RunWith(MockitoJUnitRunner.class)
public class MoveManagerTest extends BaseTest {
	
	MoveManager testObj;

	@Before
	public void setUp() {
		testObj = new MoveManager();
		boardManager = new BoardManager();
		boardManager.createEmptyBoard();
	}
	
	@Test
	public void findAllCorrectMoves_whenCapturesAvailable() {
		boardManager.addWhitePawn(46);
		boardManager.addWhitePawn(49);
		boardManager.addWhitePawn(43);
		boardManager.addWhiteQueen(35);
		boardManager.addBlackPawn(13);
		boardManager.addBlackPawn(16);
		boardManager.addBlackPawn(22);
		boardManager.addBlackPawn(26);
		
		testObj.findAllCorrectMoves(boardManager, true);
		
		assertEquals(2, testObj.getPossibleMoves().size());	
	}
	
	@Test
	public void findAllCorrectMoves_whenNoCaptureAvailable() {
		boardManager.addWhitePawn(46);
		boardManager.addWhitePawn(49);
		boardManager.addWhitePawn(43);
		boardManager.addWhiteQueen(35);
		boardManager.addBlackPawn(18);
		boardManager.addBlackPawn(16);
		boardManager.addBlackPawn(22);
		boardManager.addBlackPawn(26);
		
		testObj.findAllCorrectMoves(boardManager, true);
		
		assertEquals(12, testObj.getPossibleMoves().size());
	}

	@Test (expected = WrongMoveException.class)
	public void convertToMove_wrongPieceMoved() throws WrongMoveException {
		boardManager.addWhitePawn(21);
		boardManager.addWhitePawn(26);
		testObj.findAllCorrectMoves(boardManager, true);
		
		Move<? extends Hop> correctMove = testObj.convertToMove(26, 21, new ArrayList<>());
		assertNull(correctMove);
	}
	
	@Test (expected = WrongMoveException.class)
	public void convertToMove_wrongDestinationChosen() throws WrongMoveException {
		boardManager.addBlackPawn(23);
		boardManager.addBlackPawn(24);
		testObj.findAllCorrectMoves(boardManager, false);

		Move<? extends Hop> correctMove = testObj.convertToMove(23, 24, new ArrayList<>());
		assertNull(correctMove);
	}
	
	@Test (expected = WrongMoveException.class)
	public void convertToMove_noCapturedPiecesDuringCapture()  throws WrongMoveException{
		boardManager.addWhitePawn(33);
		boardManager.addBlackPawn(28);
		testObj.findAllCorrectMoves(boardManager, true);
		
		Move<? extends Hop> correctMove = testObj.convertToMove(33, 22, new ArrayList<>());
		assertNull(correctMove);
	}
	
	@Test (expected = WrongMoveException.class)
	public void convertToMove_wrongPieceCapturedDuringCapture() throws WrongMoveException {
		boardManager.addBlackPawn(23);
		boardManager.addWhitePawn(28);
		boardManager.addWhitePawn(29);
		testObj.findAllCorrectMoves(boardManager, false);

		Move<? extends Hop> correctMove = testObj.convertToMove(23, 32, new ArrayList<>(Collections.singletonList(29)));
		assertNull(correctMove);
	}
	
	@Test
	public void convertToMove_correctMove_withNoCapture() throws WrongMoveException{
		boardManager.addWhitePawn(43);
		testObj.findAllCorrectMoves(boardManager, true);
		
		Move<? extends Hop> correctMove = testObj.convertToMove(43, 38, new ArrayList<>());
		assertNotNull(correctMove);
		assertEquals(43, correctMove.getMoveSource().getIndex());
		assertEquals(38, correctMove.getMoveDestination().getIndex());
	}
	
	@Test
	public void convertToMove_correctMove_withCapture() throws WrongMoveException {
		boardManager.addBlackPawn(9);
		Piece whitePiece1 = boardManager.addWhitePawn(13);
		Piece whitePiece2 = boardManager.addWhitePawn(23);
		testObj.findAllCorrectMoves(boardManager, false);

		Move<? extends Hop> correctMove = testObj.convertToMove(9, 29, new ArrayList<>(Arrays.asList(13, 23)));
		assertNotNull(correctMove);
		assertEquals(whitePiece1, correctMove.getMoveTakenPawns().get(0));
		assertEquals(whitePiece2, correctMove.getMoveTakenPawns().get(1));
	}
}
