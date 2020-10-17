package draughts.library.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import draughts.library.managers.GameEngine.GameState;
import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

import java.util.ArrayList;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class GameEngineTest extends BaseTest {
	
	@Spy
	GameEngine testObj;

	MoveManager moveManager;

	@Before
	public void setUp() {
		GameEngine gameEngine = new GameEngine();
		testObj = spy(gameEngine);
		testObj.setGameState(GameState.RUNNING);
		testObj.setIsWhiteToMove(true);
		moveManager = testObj.getMoveManager();
		boardManager = testObj.getBoardManager();
	}

	public Move<Hop> makeMove(int source, int destination) {
		Move<Hop> move = generateMove(source, destination);
		boardManager.makeWholeMove(move);
		return move;
	}

	public Move<Capture> makeCapture(int source, ArrayList<Integer> jumpDestinations,
							ArrayList<Integer> takenPawns)  {

		Move<Capture> move = generateMoveWithCaptures(source, jumpDestinations, takenPawns);
		boardManager.makeWholeMove(move);
		return move;
	}


	@Test
	public void startGame() {
		testObj.startGame();
		
		assertTrue(testObj.getIsWhiteToMove());
		assertEquals(GameState.RUNNING, testObj.getGameState());
		assertEquals(50, testObj.getDrawArbiter().getDrawCounter());
		assertEquals(DrawArbiter.DrawConditions.NONE, testObj.getDrawArbiter().getDrawConditions());
	}
	
	@Test
	public void checkForPawnPromotion_noPromotion() {
		boardManager.createEmptyBoard();
		boardManager.addWhitePawn(12);
		boardManager.addBlackPawn(39);

		Move<Hop> whiteMove = makeMove(12, 7);
		testObj.checkForPawnPromotion(whiteMove);

		assertFalse(boardManager.getWhitePieces().get(0).isQueen()); //TODO: po co ta linjka? wystarczy chyba sprawdzić czy ruch jest promocją, wszystkie pierodły związane z ruchem powinny być sprawdzane w testach BoardManagera!
		assertFalse(whiteMove.isPromotion());

		Move<Hop> blackMove = makeMove(39, 44);
		testObj.checkForPawnPromotion(blackMove);

		assertFalse(boardManager.getBlackPieces().get(0).isQueen());
		assertFalse(blackMove.isPromotion());
	}
	
	@Test
	public void checkForPawnPromotion_promotion() {
		boardManager.createEmptyBoard();
		boardManager.addWhitePawn(7);
		boardManager.addBlackPawn(44);

		Move<Hop> whiteMove = makeMove(7, 3);
		testObj.checkForPawnPromotion(whiteMove);

		assertTrue(whiteMove.getMovingPiece().isQueen());
		assertTrue(boardManager.getWhitePieces().get(0).isQueen());
		assertTrue(whiteMove.isPromotion());

		Move<Hop> blackMove = makeMove(44, 49);
		testObj.checkForPawnPromotion(blackMove);

		assertTrue(blackMove.getMovingPiece().isQueen());
		assertTrue(boardManager.getBlackPieces().get(0).isQueen());
		assertTrue(blackMove.isPromotion());
	}
	
	@Test
	public void changePlayer() {
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

		makeCapture(48, new ArrayList<>(Collections.singletonList(39)),
							  new ArrayList<>(Collections.singletonList(43)));
		testObj.checkGameState();

		assertEquals(0, boardManager.getBlackPieces().size());
		assertFalse(testObj.getBoardManager().isAnyMovePossible(!testObj.getIsWhiteToMove()));
		assertEquals(GameState.WON_BY_WHITE, testObj.getGameState());
	}

	@Test
	public void checkGameState_whiteWon_byBlockingAllBlackPieces() {
		boardManager.createEmptyBoard();
		boardManager.addBlackPawn(26);
		boardManager.addWhitePawn(31);
		boardManager.addWhitePawn(41);

		makeMove(41, 37);
		testObj.checkGameState();

		assertEquals(1, boardManager.getBlackPieces().size());
		assertFalse(testObj.getBoardManager().isAnyMovePossible(!testObj.getIsWhiteToMove()));
		assertEquals(GameState.WON_BY_WHITE, testObj.getGameState());
		
	}

	@Test
	public void checkGameState_blackWon_byCapturingAllWhitePieces() {		
		boardManager.createEmptyBoard();
		boardManager.addBlackPawn(5);
		boardManager.addWhitePawn(10);
		
		testObj.setIsWhiteToMove(false);
		makeCapture(5, new ArrayList<>(Collections.singletonList(14)),
					         new ArrayList<>(Collections.singletonList(10)));
		testObj.checkGameState();

		assertEquals(0, boardManager.getWhitePieces().size());
		assertFalse(testObj.getBoardManager().isAnyMovePossible(!testObj.getIsWhiteToMove()));
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
		testObj.checkGameState();

		assertEquals(1, boardManager.getWhitePieces().size());
		assertFalse(testObj.getBoardManager().isAnyMovePossible(!testObj.getIsWhiteToMove()));
		assertEquals(GameState.WON_BY_BLACK, testObj.getGameState());	
	}
	
	
	@Test
	public void checkGameState_drawn_normalConditions() {
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(34);
		boardManager.addBlackQueen(20);
		boardManager.addWhitePawn(50);
		boardManager.addBlackPawn(5);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);

		testObj.getDrawArbiter().setDrawConditions(DrawArbiter.DrawConditions.NORMAL);
		testObj.getDrawArbiter().setDrawCounter(1);

		Move<Hop> move = makeMove(34, 45);
		testObj.updateDrawArbiter(move);
		testObj.checkGameState();

		assertEquals(0, testObj.getDrawArbiter().getDrawCounter());
		assertEquals(GameEngine.GameState.DRAWN, testObj.getGameState());
	}
	
	@Test
	public void checkGameState_drawn_3vs1Conditions() {
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(16);
		boardManager.addBlackQueen(5);
		boardManager.addBlackPawn(3);
		boardManager.addBlackPawn(26);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);

		testObj.getDrawArbiter().setDrawConditions(DrawArbiter.DrawConditions.THREE_VS_ONE);
		testObj.getDrawArbiter().setDrawCounter(1);

		Move<Hop> move = makeMove(16, 49);
		testObj.updateDrawArbiter(move);
		testObj.checkGameState();
		
		assertEquals(0, testObj.getDrawArbiter().getDrawCounter());
		assertEquals(GameEngine.GameState.DRAWN, testObj.getGameState());	
	}
	
	@Test
	public void checkGameState_drawn_2v1Conditions() {
		boardManager.createEmptyBoard();
		boardManager.addWhiteQueen(33);
		boardManager.addBlackQueen(5);
		boardManager.addBlackQueen(2);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);

		testObj.getDrawArbiter().setDrawConditions(DrawArbiter.DrawConditions.TWO_VS_ONE);
		testObj.getDrawArbiter().setDrawCounter(1);

		Move<Hop> move = makeMove(33, 50);
		testObj.updateDrawArbiter(move);
		testObj.checkGameState();
		
		assertEquals(0, testObj.getDrawArbiter().getDrawCounter());
		assertEquals(GameEngine.GameState.DRAWN, testObj.getGameState());
	}
}
