package draughts.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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
	
	@Test
	public void startGame_test() {
		testObj.startGame();
		
		assertTrue(testObj.getIsWhiteToMove());
		assertEquals(0, testObj.getMarkedPiecePosition());
		assertEquals(9, testObj.getMoveManager().getMoves().size());
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
		
		Tile destination = testObj.getMoveManager().getBoardManager().findTileByIndex(28);
		Tile source = testObj.getMoveManager().getBoardManager().findTileByIndex(33);
		Piece movedPiece = testObj.getMoveManager().getBoardManager().findPieceByIndex(28);
		
		assertEquals(destination.getState(), Tile.State.WHITE_PAWN);
		assertEquals(source.getState(), Tile.State.EMPTY);
		assertEquals(28, movedPiece.getPosition());
	}

}
