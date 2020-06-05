package draughts.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.GameEngine.GameState;
import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.exceptions.NoCorrectMovesForSelectedPieceException;
import draughts.library.exceptions.NoPieceFoundInRequestedTileException;
import draughts.library.exceptions.WrongColorFoundInRequestedTileException;
import draughts.library.exceptions.WrongMoveException;

@RunWith(MockitoJUnitRunner.class)
public class GameEngineTest {
	
	@Spy
	GameEngine testObj;
	
	@Before
	public void setUp() {
		GameEngine gameEngine = new GameEngine();
		testObj = spy(gameEngine);
		testObj.setGameState(GameState.RUNNING);
		testObj.setIsWhiteToMove(true);
	}
	
	public void makeMove(int source, int destination) {
		try {
			testObj.tileClicked(source);
			testObj.tileClicked(destination);
		} catch(Exception ex) {}
		
	}
	
	@Test
	public void startGame_test() {
		testObj.startGame();
		
		assertTrue(testObj.getIsWhiteToMove());
		assertNull(testObj.getChosenPiece());
		assertEquals(9, testObj.getMoveManager().getPossibleMoves().size());
		assertEquals(GameState.RUNNING, testObj.getGameState());
		assertEquals(50, testObj.getDrawArbiter().getDrawCounter());
		assertEquals(DrawArbiter.DrawConditions.NONE, testObj.getDrawArbiter().getDrawConditions());
	}
	
	@Test(expected = NoPieceFoundInRequestedTileException.class)
	public void tileClicked_noPieceFound_test() throws NoPieceFoundInRequestedTileException, 
	 												   WrongColorFoundInRequestedTileException,
	 												   NoCorrectMovesForSelectedPieceException, 
	 												   WrongMoveException {
		testObj.startGame();
		testObj.tileClicked(25);
	}
	
	@Test(expected = WrongColorFoundInRequestedTileException.class)
	public void tileClicked_wrongPieceFound_test() throws NoPieceFoundInRequestedTileException, 
														  WrongColorFoundInRequestedTileException,
														  NoCorrectMovesForSelectedPieceException, 
														  WrongMoveException {
		testObj.startGame();
		testObj.tileClicked(12);
	}
	
	@Test(expected = NoCorrectMovesForSelectedPieceException.class)
	public void tileClicked_noMovesForPiece_test() throws NoPieceFoundInRequestedTileException, 
														  WrongColorFoundInRequestedTileException,
														  NoCorrectMovesForSelectedPieceException, 
														  WrongMoveException {
		testObj.startGame();
		testObj.tileClicked(43);
	}
	
	@Test
	public void tileClicked_numberOfPossibleDestinations_test() throws NoPieceFoundInRequestedTileException, 
																		WrongColorFoundInRequestedTileException, 
																		NoCorrectMovesForSelectedPieceException, 
																		WrongMoveException {
		testObj.startGame();
		testObj.tileClicked(33);

		assertEquals(33, testObj.getMarkedPiecePosition());
		assertEquals(2, testObj.getPossibleHopDestinations().size());
		assertTrue(testObj.getPossibleHopDestinations().get(0) == 28 ||
				   testObj.getPossibleHopDestinations().get(0) == 29);
		assertTrue(testObj.getPossibleHopDestinations().get(1) == 28 ||
				   testObj.getPossibleHopDestinations().get(1) == 29);
	}
	
	@Test(expected = WrongMoveException.class)
	public void tileClicked_twice_wrongMoveMade_test() throws NoPieceFoundInRequestedTileException, 
														  WrongColorFoundInRequestedTileException,
														  NoCorrectMovesForSelectedPieceException, 
														  WrongMoveException {
		testObj.startGame();
		testObj.tileClicked(33);
		testObj.tileClicked(26);
	}
	
	@Test
	public void tileClicked_twice_changedMarkedPiece_forOneWithMoves_test() throws NoPieceFoundInRequestedTileException, 
																	WrongColorFoundInRequestedTileException,
																	NoCorrectMovesForSelectedPieceException, 
																	WrongMoveException {
		testObj.startGame();
		testObj.tileClicked(33);
		testObj.tileClicked(31);
		
		assertEquals(31, testObj.getMarkedPiecePosition());
		assertEquals(2, testObj.getPossibleHopDestinations().size());
		assertTrue(testObj.getPossibleHopDestinations().get(0) == 26 ||
				   testObj.getPossibleHopDestinations().get(0) == 27);
		assertTrue(testObj.getPossibleHopDestinations().get(1) == 26 ||
				   testObj.getPossibleHopDestinations().get(1) == 27);
		
	}
	
	@Test(expected = NoCorrectMovesForSelectedPieceException.class)
	public void tileClicked_twice_changedMarkedPiece_forOneWithoutMoves_test() throws NoPieceFoundInRequestedTileException, 
																	WrongColorFoundInRequestedTileException,
																	NoCorrectMovesForSelectedPieceException, 
																	WrongMoveException {
		testObj.startGame();
		testObj.tileClicked(33);
		testObj.tileClicked(44);
		
	}
	
	@Test
	public void tileClicked_twice_properMoveMade_test() throws NoPieceFoundInRequestedTileException {
		testObj.startGame();
		makeMove(33, 28);
		
		Tile whiteDestination = testObj.getMoveManager().getBoardManager().findTileByIndex(28);
		Tile whiteSource = testObj.getMoveManager().getBoardManager().findTileByIndex(33);
		Piece whiteMovedPiece = testObj.getMoveManager().getBoardManager().findPieceByIndex(28);
		
		assertEquals(Tile.State.WHITE_PAWN, whiteDestination.getState());
		assertEquals(Tile.State.EMPTY, whiteSource.getState());
		assertEquals(28, whiteMovedPiece.getPosition());
		
		assertFalse(testObj.getIsWhiteToMove());
		assertEquals(0, testObj.getPossibleHopDestinations().size());
		assertEquals(0, testObj.getMarkedPiecePosition());
		
		makeMove(19, 24);
		
		Tile blackDestination = testObj.getMoveManager().getBoardManager().findTileByIndex(24);
		Tile blackSource = testObj.getMoveManager().getBoardManager().findTileByIndex(19);
		Piece blackMovedPiece = testObj.getMoveManager().getBoardManager().findPieceByIndex(24);
		
		assertEquals(Tile.State.BLACK_PAWN, blackDestination.getState());
		assertEquals(Tile.State.EMPTY, blackSource.getState());
		assertEquals(24, blackMovedPiece.getPosition());
	}
	
	@Test
	public void tileClicked_twice_properCaptureMade_test() {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addWhitePawn(33);
		boardManager.addBlackPawn(28);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		makeMove(33, 22);
		
		assertEquals(Tile.State.EMPTY, boardManager.findTileByIndex(28).getState());
		assertEquals(0, boardManager.getBlackPieces().size());
	}
	
	@Test 
	public void tileClicked_consecutiveCaptures_ultimate_test() throws NoPieceFoundInRequestedTileException, 
																	WrongColorFoundInRequestedTileException,
																	NoCorrectMovesForSelectedPieceException, 
																	WrongMoveException {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addWhitePawn(48);
		boardManager.addWhitePawn(44);
		boardManager.addBlackPawn(20);
		boardManager.addBlackPawn(30);
		boardManager.addBlackPawn(40);
		boardManager.addBlackPawn(21);
		boardManager.addBlackPawn(31);
		boardManager.addBlackPawn(32);
		boardManager.addBlackPawn(33);
		boardManager.addBlackPawn(42);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		testObj.tileClicked(48);
		try {
			testObj.tileClicked(47);
		} catch (WrongMoveException ex) {}
		assertEquals(48, testObj.getMarkedPiecePosition());
		
		testObj.tileClicked(37);
		
		assertEquals(37, testObj.getMarkedPiecePosition());
		assertEquals(1, testObj.getMoveManager().getHopsMadeInMove());
		assertEquals(2, testObj.getPossibleHopDestinations().size());
		assertTrue(testObj.getIsWhiteToMove());
		
		try {
			testObj.tileClicked(44);
		} catch(NoCorrectMovesForSelectedPieceException ex) {}
		
		assertEquals(44, testObj.getMarkedPiecePosition());
		
		try {
			testObj.tileClicked(28);
		} catch(WrongMoveException ex) {}
		
		assertEquals(44, testObj.getMarkedPiecePosition());
		
		testObj.tileClicked(37);
		
		assertEquals(37, testObj.getMarkedPiecePosition());
		assertEquals(2, testObj.getPossibleHopDestinations().size());
		
		testObj.tileClicked(28);
		
		assertEquals(28, testObj.getMarkedPiecePosition());
		assertEquals(1, testObj.getPossibleHopDestinations().size());
		assertEquals(2, testObj.getMoveManager().getHopsMadeInMove());
		assertTrue(testObj.getIsWhiteToMove());
		
		testObj.tileClicked(39);
		
		assertEquals(0, testObj.getMarkedPiecePosition());
		assertEquals(0, testObj.getMoveManager().getHopsMadeInMove());
		assertFalse(testObj.getIsWhiteToMove());
		assertEquals(5, testObj.getMoveManager().getBoardManager().getBlackPieces().size());
		
	}
	
	@Test
	public void checkGameState_whiteWon_byCapturingAllBlackPieces() {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addWhitePawn(48);
		boardManager.addBlackPawn(43);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		makeMove(48, 39);
		
		assertEquals(0, boardManager.getBlackPieces().size());
		assertEquals(0, testObj.getMoveManager().getPossibleMoves().size());
		assertEquals(GameState.WON_BY_WHITE, testObj.getGameState());
	}
	
	@Test
	public void checkGameState_whiteWon_byBlockingAllBlackPieces() {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addBlackPawn(26);
		boardManager.addWhitePawn(31);
		boardManager.addWhitePawn(41);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		makeMove(41, 37);
		
		assertEquals(1, boardManager.getBlackPieces().size());
		assertEquals(0, testObj.getMoveManager().getPossibleMoves().size());
		assertEquals(GameState.WON_BY_WHITE, testObj.getGameState());
		
	}
	
	@Test
	public void checkGameState_blackWon_byCapturingAllWhitePieces() {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addBlackPawn(5);
		boardManager.addWhitePawn(10);
		
		testObj.setIsWhiteToMove(false);
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		makeMove(5, 14);
		
		assertEquals(0, boardManager.getWhitePieces().size());
		assertEquals(0, testObj.getMoveManager().getPossibleMoves().size());
		assertEquals(GameState.WON_BY_BLACK, testObj.getGameState());
	}
	
	@Test
	public void checkGameState_blackWon_byBlockingAllWhitePieces() {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addWhitePawn(25);
		boardManager.addBlackPawn(20);
		boardManager.addBlackPawn(10);
		
		testObj.setIsWhiteToMove(false);	
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		makeMove(10, 14);
		
		assertEquals(1, boardManager.getWhitePieces().size());
		assertEquals(0, testObj.getMoveManager().getPossibleMoves().size());
		assertEquals(GameState.WON_BY_BLACK, testObj.getGameState());	
	}
	
	
	@Test
	public void checkGameState_drawn_normalConditions_test() {
		
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
				
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(34);
		boardManager.addBlackQueen(20);
		boardManager.addWhitePawn(50);
		boardManager.addBlackPawn(5);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		testObj.getDrawArbiter().updateState(true, 2, 2);
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
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(16);
		boardManager.addBlackQueen(5);
		boardManager.addBlackPawn(3);
		boardManager.addBlackPawn(21);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		testObj.getDrawArbiter().updateState(true, 1, 3);
		
		for(int i=0; i<8; i++) {
			makeMove(16, 49);
			makeMove(5, 46);
			makeMove(49, 16);
			makeMove(46, 5);
		}
		
		assertEquals(GameEngine.GameState.DRAWN, testObj.getGameState());
			
	}
	
	@Test
	public void checkGameState_drawn_2v1Conditions_test() {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(33);
		boardManager.addBlackQueen(5);
		boardManager.addBlackQueen(2);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		testObj.getDrawArbiter().updateState(true, 1, 2);
		
		for(int i=0; i<2; i++) {
			makeMove(33, 50);
			makeMove(2, 35);
			makeMove(50, 33);
			makeMove(35, 2);
		}
		
		makeMove(33, 50);
		makeMove(2, 35);
		
		assertEquals(GameEngine.GameState.DRAWN, testObj.getGameState());
		
	}
	
}
