package draughts.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
	
	GameEngine testObj;
	
	@Before
	public void setUp() {
		testObj = new GameEngine();
	}
	
	public void makeMove(int source, int destination) throws NoPieceFoundInRequestedTileException, 
														WrongColorFoundInRequestedTileException, 
														NoCorrectMovesForSelectedPieceException, 
														WrongMoveException {
		testObj.tileClicked(source);
		testObj.tileClicked(destination);
	}
	
	@Test
	public void startGame_test() {
		testObj.startGame();
		
		assertTrue(testObj.getIsWhiteToMove());
		assertEquals(0, testObj.getMarkedPiecePosition());
		assertEquals(9, testObj.getMoveManager().getPossibleMoves().size());
		assertEquals(GameState.RUNNING, testObj.getGameState());
		assertEquals(0, testObj.getDrawCounter());
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
	public void tileClicked_twice_properMoveMade_test() throws NoPieceFoundInRequestedTileException, 
	  															WrongColorFoundInRequestedTileException,
	  															NoCorrectMovesForSelectedPieceException, 
	  															WrongMoveException {
		testObj.startGame();
		testObj.tileClicked(33);
		testObj.tileClicked(28);
		
		Tile whiteDestination = testObj.getMoveManager().getBoardManager().findTileByIndex(28);
		Tile whiteSource = testObj.getMoveManager().getBoardManager().findTileByIndex(33);
		Piece whiteMovedPiece = testObj.getMoveManager().getBoardManager().findPieceByIndex(28);
		
		assertEquals(Tile.State.WHITE_PAWN, whiteDestination.getState());
		assertEquals(Tile.State.EMPTY, whiteSource.getState());
		assertEquals(28, whiteMovedPiece.getPosition());
		
		assertFalse(testObj.getIsWhiteToMove());
		assertEquals(0, testObj.getPossibleHopDestinations().size());
		assertEquals(0, testObj.getMarkedPiecePosition());
		
		testObj.tileClicked(19);
		testObj.tileClicked(24);
		
		Tile blackDestination = testObj.getMoveManager().getBoardManager().findTileByIndex(24);
		Tile blackSource = testObj.getMoveManager().getBoardManager().findTileByIndex(19);
		Piece blackMovedPiece = testObj.getMoveManager().getBoardManager().findPieceByIndex(24);
		
		assertEquals(Tile.State.BLACK_PAWN, blackDestination.getState());
		assertEquals(Tile.State.EMPTY, blackSource.getState());
		assertEquals(24, blackMovedPiece.getPosition());
	}
	
	@Test
	public void tileClicked_twice_properCaptureMade_test() throws NoPieceFoundInRequestedTileException, 
																WrongColorFoundInRequestedTileException,
																NoCorrectMovesForSelectedPieceException, 
																WrongMoveException {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addWhitePawn(33);
		boardManager.addBlackPawn(28);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		testObj.tileClicked(33);
		testObj.tileClicked(22);
		
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
	public void checkGameState_whiteWon_byCapturingAllBlackPieces() throws NoPieceFoundInRequestedTileException, 
																			WrongColorFoundInRequestedTileException,
																			NoCorrectMovesForSelectedPieceException, 
																			WrongMoveException {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addWhitePawn(48);
		boardManager.addBlackPawn(43);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		testObj.tileClicked(48);
		testObj.tileClicked(39);
		
		assertEquals(0, boardManager.getBlackPieces().size());
		assertEquals(0, testObj.getMoveManager().getPossibleMoves().size());
		assertEquals(GameState.WON_BY_WHITE, testObj.getGameState());
	}
	
	@Test
	public void checkGameState_whiteWon_byBlockingAllBlackPieces() throws NoPieceFoundInRequestedTileException, 
																			WrongColorFoundInRequestedTileException,
																			NoCorrectMovesForSelectedPieceException, 
																			WrongMoveException {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addBlackPawn(26);
		boardManager.addWhitePawn(31);
		boardManager.addWhitePawn(41);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		testObj.tileClicked(41);
		testObj.tileClicked(37);
		
		assertEquals(1, boardManager.getBlackPieces().size());
		assertEquals(0, testObj.getMoveManager().getPossibleMoves().size());
		assertEquals(GameState.WON_BY_WHITE, testObj.getGameState());
		
	}
	
	@Test
	public void checkGameState_blackWon_byCapturingAllWhitePieces() throws NoPieceFoundInRequestedTileException, 
																			WrongColorFoundInRequestedTileException,
																			NoCorrectMovesForSelectedPieceException, 
																			WrongMoveException {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addBlackPawn(5);
		boardManager.addWhitePawn(10);
		
		testObj.moveFinished();
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		testObj.tileClicked(5);
		testObj.tileClicked(14);
		
		assertEquals(0, boardManager.getWhitePieces().size());
		assertEquals(0, testObj.getMoveManager().getPossibleMoves().size());
		assertEquals(GameState.WON_BY_BLACK, testObj.getGameState());
	}
	
	@Test
	public void checkGameState_blackWon_byBlockingAllWhitePieces() throws NoPieceFoundInRequestedTileException, 
																			WrongColorFoundInRequestedTileException,
																			NoCorrectMovesForSelectedPieceException, 
																			WrongMoveException {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addWhitePawn(25);
		boardManager.addBlackPawn(20);
		boardManager.addBlackPawn(10);
		
		testObj.moveFinished();
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
	
		testObj.tileClicked(10);
		testObj.tileClicked(14);
		
		assertEquals(1, boardManager.getWhitePieces().size());
		assertEquals(0, testObj.getMoveManager().getPossibleMoves().size());
		assertEquals(GameState.WON_BY_BLACK, testObj.getGameState());	
	}
	
	@Test
	public void checkGameState_changingDrawCounterAccordingly_test() throws NoPieceFoundInRequestedTileException, 
																			WrongColorFoundInRequestedTileException,
																			NoCorrectMovesForSelectedPieceException, 
																			WrongMoveException {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(34);
		boardManager.addBlackQueen(20);
		boardManager.addWhitePawn(50);
		boardManager.addBlackPawn(5);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		testObj.tileClicked(34);
		testObj.tileClicked(45);
		
		assertEquals(1, testObj.getDrawCounter());
		
		testObj.tileClicked(20);
		testObj.tileClicked(3);
		
		assertEquals(2, testObj.getDrawCounter());
		
		testObj.tileClicked(50);
		testObj.tileClicked(44);
		
		assertEquals(0, testObj.getDrawCounter());
		
		
	}
	
	@Test 
	public void changeDrawConditions_normalTo3vs1_test() throws NoPieceFoundInRequestedTileException, 
															WrongColorFoundInRequestedTileException,
															NoCorrectMovesForSelectedPieceException, 
															WrongMoveException {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(32);
		boardManager.addBlackQueen(5);
		boardManager.addBlackPawn(3);
		boardManager.addBlackPawn(18);
		boardManager.addBlackPawn(21);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		testObj.tileClicked(32);
		testObj.tileClicked(16);
		
		assertEquals(DrawArbiter.DrawConditions.THREE_VS_ONE, testObj.getDrawArbiter().getDrawConditions());
	}
	
	@Test
	public void changeDrawConditions_3v1To2v1_test() throws NoPieceFoundInRequestedTileException, 
															WrongColorFoundInRequestedTileException,
															NoCorrectMovesForSelectedPieceException, 
															WrongMoveException {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addBlackQueen(18);
		boardManager.addWhiteQueen(49);
		boardManager.addWhiteQueen(33);
		boardManager.addWhitePawn(32);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		testObj.tileClicked(33);
		testObj.tileClicked(29);
		testObj.tileClicked(32);
		testObj.tileClicked(16);
		
		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawArbiter().getDrawConditions());
		
	}
	
	@Test
	public void changeDrawConditions_normalTo2v1_test() throws NoPieceFoundInRequestedTileException, 
															WrongColorFoundInRequestedTileException,
															NoCorrectMovesForSelectedPieceException, 
															WrongMoveException {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(33);
		boardManager.addBlackQueen(5);
		boardManager.addBlackQueen(2);
		boardManager.addBlackPawn(22);
		boardManager.addBlackPawn(12);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		testObj.tileClicked(33);
		testObj.tileClicked(17);
		testObj.tileClicked(3);
		
		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawArbiter().getDrawConditions());	
		
	}
	
	@Test
	public void checkGameState_drawn_normalConditions_test() throws NoPieceFoundInRequestedTileException, 
													WrongColorFoundInRequestedTileException,
													NoCorrectMovesForSelectedPieceException, 
													WrongMoveException {
		
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
				
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(34);
		boardManager.addBlackQueen(20);
		boardManager.addWhitePawn(50);
		boardManager.addBlackPawn(5);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());

		for(int i=0; i<12; i++) {
			testObj.tileClicked(34);
			testObj.tileClicked(45);
			testObj.tileClicked(20);
			testObj.tileClicked(3);
			testObj.tileClicked(45);
			testObj.tileClicked(34);
			testObj.tileClicked(3);
			testObj.tileClicked(20);
		}
		
		testObj.tileClicked(34);
		testObj.tileClicked(45);
		testObj.tileClicked(20);
		testObj.tileClicked(3);
		
		assertEquals(GameEngine.GameState.DRAWN, testObj.getGameState());
		
	}
	
	@Test
	public void checkGameState_drawn_3vs1Conditions_test() throws NoPieceFoundInRequestedTileException, 
																WrongColorFoundInRequestedTileException,
																NoCorrectMovesForSelectedPieceException, 
																WrongMoveException {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(32);
		boardManager.addBlackQueen(5);
		boardManager.addBlackPawn(3);
		boardManager.addBlackPawn(18);
		boardManager.addBlackPawn(21);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		testObj.tileClicked(32);
		testObj.tileClicked(16);
		
		for(int i=0; i<8; i++) {
			testObj.tileClicked(5);
			testObj.tileClicked(46);
			testObj.tileClicked(16);
			testObj.tileClicked(49);
			testObj.tileClicked(46);
			testObj.tileClicked(5);
			testObj.tileClicked(49);
			testObj.tileClicked(16);
		}
		
		assertEquals(GameEngine.GameState.DRAWN, testObj.getGameState());
			
	}
	
	@Test
	public void checkGameState_drawn_2v1Conditions_test() throws NoPieceFoundInRequestedTileException, 
																WrongColorFoundInRequestedTileException,
																NoCorrectMovesForSelectedPieceException, 
																WrongMoveException {
		BoardManager boardManager = testObj.getMoveManager().getBoardManager();
		
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(33);
		boardManager.addBlackQueen(5);
		boardManager.addBlackQueen(2);
		boardManager.addBlackPawn(22);
		boardManager.addBlackPawn(12);
		
		testObj.getMoveManager().findAllCorrectMoves(testObj.getIsWhiteToMove());
		
		testObj.tileClicked(33);
		testObj.tileClicked(17);
		testObj.tileClicked(3);
		
		for(int i=0; i<2; i++) {
			testObj.tileClicked(2);
			testObj.tileClicked(35);
			testObj.tileClicked(3);
			testObj.tileClicked(26);
			testObj.tileClicked(35);
			testObj.tileClicked(2);
			testObj.tileClicked(26);
			testObj.tileClicked(3);
		}
		
		testObj.tileClicked(2);
		testObj.tileClicked(35);
		testObj.tileClicked(3);
		testObj.tileClicked(26);
		
		assertEquals(GameEngine.GameState.DRAWN, testObj.getGameState());
		
	}
	
	

}
