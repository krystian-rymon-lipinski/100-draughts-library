package draughts.library.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.exceptions.NoCorrectMovesForSelectedPieceException;
import draughts.library.exceptions.NoPieceFoundInRequestedTileException;
import draughts.library.exceptions.WrongColorFoundInRequestedTileException;
import draughts.library.exceptions.WrongMoveException;
import draughts.library.managers.GameEngine.GameState;
import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

@RunWith(MockitoJUnitRunner.class)
public class GameEngineTest {
	
	@Spy
	GameEngine testObj;
	MoveManager moveManager;
	BoardManager boardManager;
	
	@Before
	public void setUp() {
		GameEngine gameEngine = new GameEngine();
		testObj = spy(gameEngine);
		testObj.setGameState(GameState.RUNNING);
		testObj.setIsWhiteToMove(true);
		moveManager = testObj.getMoveManager();
		boardManager = testObj.getBoardManager();
	}
	
	public Tile getTile(int index) {
		return boardManager.findTileByIndex(index);
	}
	
	@Test
	public void startGame_test() {
		testObj.startGame();
		
		assertTrue(testObj.getIsWhiteToMove());
		assertEquals(9, testObj.getMoveManager().getPossibleMoves().size());
		assertEquals(GameState.RUNNING, testObj.getGameState());
		assertEquals(50, testObj.getDrawArbiter().getDrawCounter());
		assertEquals(DrawArbiter.DrawConditions.NONE, testObj.getDrawArbiter().getDrawConditions());
	}
	
	@Test
	public void checkForPawnPromotion_noPromotion_test() throws Exception {
		boardManager.createEmptyBoard();
		boardManager.addWhitePawn(12);
		boardManager.addBlackPawn(39);
		Piece whitePiece = boardManager.findPieceByIndex(12);
		Piece blackPiece = boardManager.findPieceByIndex(39);
		
		Move<? extends Hop> whiteMove = new Move<Hop>(whitePiece, new Hop(getTile(12), getTile(7)));
		Move<? extends Hop> blackMove = new Move<Hop>(blackPiece, new Hop(getTile(39), getTile(44)));
		
		testObj.checkForPawnPromotion(whiteMove);
		assertFalse(boardManager.getWhitePieces().get(0).isQueen());
		assertFalse(whiteMove.getIsPromotion());
		
		testObj.checkForPawnPromotion(blackMove);
		assertFalse(boardManager.getBlackPieces().get(0).isQueen());
		assertFalse(blackMove.getIsPromotion());
	}
	
	@Test
	public void checkForPawnPromotion_promotion_test() throws Exception {
		boardManager.createEmptyBoard();
		boardManager.addWhitePawn(7);
		boardManager.addBlackPawn(44);
		Piece whitePiece = boardManager.findPieceByIndex(7);
		Piece blackPiece = boardManager.findPieceByIndex(44);

		Move<? extends Hop> whiteMove = new Move<Hop>(whitePiece, new Hop(getTile(7), getTile(1)));
		Move<? extends Hop> blackMove = new Move<Hop>(blackPiece, new Hop(getTile(44), getTile(50)));
		
		testObj.checkForPawnPromotion(whiteMove);
		assertTrue(whiteMove.getMovingPiece().isQueen());
		assertTrue(boardManager.getWhitePieces().get(0).isQueen());
		assertTrue(whiteMove.getIsPromotion());
		
		testObj.checkForPawnPromotion(blackMove);
		assertTrue(blackMove.getMovingPiece().isQueen());
		assertTrue(boardManager.getBlackPieces().get(0).isQueen());
		assertTrue(blackMove.getIsPromotion());
	}
	
	@Test
	public void changePlayer_test() {
		boardManager.createEmptyBoard();
		boardManager.addWhitePawn(40);
		boardManager.addBlackPawn(8);
		
		testObj.endPlayerTurn();
		
		assertFalse(testObj.getIsWhiteToMove());
	}
	
	@Test
	public void checkGameState_whiteWon_byCapturingAllBlackPieces() {
		boardManager.createEmptyBoard();
		boardManager.addWhitePawn(48);
		boardManager.addBlackPawn(43);

		makeMove(48, 39);
		
		assertEquals(0, boardManager.getBlackPieces().size());
		assertFalse(testObj.getMoveManager().isAnyMovePossible(boardManager, testObj.getIsWhiteToMove()));
		assertEquals(GameState.WON_BY_WHITE, testObj.getGameState());
	}
	
	@Test
	public void checkGameState_whiteWon_byBlockingAllBlackPieces() {	
		boardManager.createEmptyBoard();
		boardManager.addBlackPawn(26);
		boardManager.addWhitePawn(31);
		boardManager.addWhitePawn(41);

		makeMove(41, 37);
		
		assertEquals(1, boardManager.getBlackPieces().size());
		assertFalse(testObj.getMoveManager().isAnyMovePossible(boardManager, testObj.getIsWhiteToMove()));
		assertEquals(GameState.WON_BY_WHITE, testObj.getGameState());
		
	}
	
	@Test
	public void checkGameState_blackWon_byCapturingAllWhitePieces() {		
		boardManager.createEmptyBoard();
		boardManager.addBlackPawn(5);
		boardManager.addWhitePawn(10);
		
		testObj.setIsWhiteToMove(false);
		makeMove(5, 14);
		
		assertEquals(0, boardManager.getWhitePieces().size());
		assertFalse(testObj.getMoveManager().isAnyMovePossible(boardManager, testObj.getIsWhiteToMove()));
		assertEquals(GameState.WON_BY_BLACK, testObj.getGameState());
	}
	
	@Test
	public void checkGameState_blackWon_byBlockingAllWhitePieces() {		
		boardManager.createEmptyBoard();
		boardManager.addWhitePawn(25);
		boardManager.addBlackPawn(20);
		boardManager.addBlackPawn(10);
		
		testObj.setIsWhiteToMove(false);	
		makeMove(10, 14);
		
		assertEquals(1, boardManager.getWhitePieces().size());
		assertFalse(testObj.getMoveManager().isAnyMovePossible(boardManager, testObj.getIsWhiteToMove()));
		assertEquals(GameState.WON_BY_BLACK, testObj.getGameState());	
	}
	
	
	@Test
	public void checkGameState_drawn_normalConditions_test() {				
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(34);
		boardManager.addBlackQueen(20);
		boardManager.addWhitePawn(50);
		boardManager.addBlackPawn(5);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);

		testObj.getDrawArbiter().updateConditions(true, 2, 2);
		assertEquals(DrawArbiter.DrawConditions.NORMAL, testObj.getDrawArbiter().getDrawConditions());

		for(int i=0; i<12; i++) {
			makeMove(34, 45);
			makeMove(20, 3);
			makeMove(45, 34);
			makeMove(3, 20);
		}
		
		makeMove(34, 45);
		makeMove(20, 3);
		
		assertEquals(0, testObj.getDrawArbiter().getDrawCounter());
		assertEquals(GameEngine.GameState.DRAWN, testObj.getGameState());
	}
	
	@Test
	public void checkGameState_drawn_3vs1Conditions_test() {		
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(16);
		boardManager.addBlackQueen(5);
		boardManager.addBlackPawn(3);
		boardManager.addBlackPawn(26);
		assertEquals(3, boardManager.getBlackPieces().size());
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		testObj.getDrawArbiter().updateConditions(true, 1, 3);
		
		for(int i=0; i<8; i++) {
			makeMove(16, 49);
			makeMove(5, 46);
			makeMove(49, 16);
			makeMove(46, 5);
		}
		
		assertEquals(0, testObj.getDrawArbiter().getDrawCounter());
		assertEquals(GameEngine.GameState.DRAWN, testObj.getGameState());	
	}
	
	@Test
	public void checkGameState_drawn_2v1Conditions_test() {		
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(33);
		boardManager.addBlackQueen(5);
		boardManager.addBlackQueen(2);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		testObj.getDrawArbiter().updateConditions(true, 1, 2);
		
		for(int i=0; i<2; i++) {
			makeMove(33, 50);
			makeMove(2, 35);
			makeMove(50, 33);
			makeMove(35, 2);
		}
		
		makeMove(33, 50);
		makeMove(2, 35);
		
		assertEquals(0, testObj.getDrawArbiter().getDrawCounter());
		assertEquals(GameEngine.GameState.DRAWN, testObj.getGameState());
	}
	
	//tests for making move all hops at once
	
	@Test
	public void updateBoard_noCapture_test() {
		boardManager.createEmptyBoard();
		Piece chosenPiece = boardManager.addWhiteQueen(30);
		Move<Hop> move = new Move<>(chosenPiece, 
								new Hop(chosenPiece.getPosition(), getTile(13)));
		testObj.updateBoard(move);
		assertEquals(Tile.State.EMPTY, getTile(30).getState());
		assertEquals(13, chosenPiece.getPosition().getIndex());
		assertEquals(Tile.State.WHITE_QUEEN, chosenPiece.getPosition().getState());
	}
	
	@Test
	public void updateBoard_capture_test() {
		boardManager.createEmptyBoard();
		Piece chosenPiece = boardManager.addBlackQueen(25);
		Piece whitePiece1 = boardManager.addWhitePawn(12);
		Piece whitePiece2 = boardManager.addWhitePawn(14);
		Piece whitePiece3 = boardManager.addWhitePawn(22);
		Move<Capture> move = new Move<>(chosenPiece, 
								new Capture(chosenPiece.getPosition(), getTile(3), whitePiece1));
		move.addHop(new Capture(getTile(3), getTile(17), whitePiece2));
		move.addHop(new Capture(getTile(17), getTile(50), whitePiece3));
		
		testObj.updateBoard(move);
		assertEquals(Tile.State.EMPTY, getTile(25).getState());
		assertEquals(Tile.State.EMPTY, getTile(14).getState());
		assertEquals(Tile.State.EMPTY, getTile(12).getState());
		assertEquals(Tile.State.EMPTY, getTile(22).getState());
		assertEquals(50, chosenPiece.getPosition().getIndex());
		assertEquals(Tile.State.BLACK_QUEEN, chosenPiece.getPosition().getState());
		assertEquals(0, boardManager.getWhitePieces().size());
	}
}
