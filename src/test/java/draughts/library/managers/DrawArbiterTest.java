package draughts.library.managers;

import static org.junit.Assert.assertEquals;

import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.exceptions.NoPieceFoundInRequestedTileException;
import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;
import org.junit.Before;
import org.junit.Test;

import draughts.library.managers.BoardManager;
import draughts.library.managers.DrawArbiter;
import draughts.library.managers.GameEngine;
import draughts.library.managers.MoveManager;
import draughts.library.managers.GameEngine.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DrawArbiterTest {
	
	DrawArbiter testObj;
	GameEngine gameEngine;
	BoardManager boardManager;
	MoveManager moveManager;
	
	@Before
	public void setUp() {
		gameEngine = new GameEngine();
		gameEngine.setIsWhiteToMove(true);
		gameEngine.setGameState(GameState.RUNNING);
		testObj = gameEngine.getDrawArbiter();
		boardManager = gameEngine.getBoardManager();
		moveManager = gameEngine.getMoveManager();
		boardManager.createEmptyBoard();
	}

	public Tile getTile(int index) {
		return boardManager.findTileByIndex(index);
	}

	public Piece getPiece(int index) {
		try {
			return boardManager.findPieceByIndex(index);
		} catch (NoPieceFoundInRequestedTileException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Move<Hop> makeMove(int source, int destination) {
		Piece movingPiece = getPiece(source);
		Tile sourceTile = getTile(source);
		Tile destinationTile = getTile(destination);

		Move<Hop> move = new Move<>(movingPiece, new Hop(sourceTile, destinationTile));
		boardManager.makeWholeMove(move);
		gameEngine.finishMove(move);
		return move;
	}

	public Move<Capture> makeCapture(int source, ArrayList<Integer> jumpDestinations,
									 ArrayList<Integer> takenPawns)  {

		Piece movingPiece = getPiece(source);
		Move<Capture> move = new Move<>(movingPiece);

		Capture capture;
		for (int i=0; i<jumpDestinations.size(); i++) {
			Tile sourceTile;
			if (i==0) sourceTile = getTile(source);
			else 	  sourceTile = getTile(jumpDestinations.get(i-1));

			Tile destinationTile = getTile(jumpDestinations.get(i));
			Piece takenPiece = getPiece(takenPawns.get(i));
			capture = new Capture(sourceTile, destinationTile, takenPiece);
			move.addHop(capture);
		}

		boardManager.makeWholeMove(move);
		gameEngine.finishMove(move);
		return move;
	}


	@Test
	public void changeDrawConditions_noneToNormal_test() {
		boardManager.addWhitePawn(43);
		boardManager.addWhitePawn(10);
		boardManager.addWhitePawn(50);
		boardManager.addBlackPawn(36);
		boardManager.addBlackPawn(26);
		boardManager.addBlackQueen(16);
		boardManager.setIsBlackQueenOnBoard(true);

		makeMove(10, 5);
		
		assertEquals(DrawArbiter.DrawConditions.NORMAL, testObj.getDrawConditions());
		assertEquals(50, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawConditions_noneTo3v1_byCapture_test() {
		boardManager.addWhiteQueen(33);
		boardManager.addBlackPawn(11);
		boardManager.addBlackPawn(12);
		boardManager.addBlackPawn(3);
		boardManager.addBlackQueen(10);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);

		makeCapture(33, new ArrayList<>(Collections.singletonList(6)),
								new ArrayList<>(Collections.singletonList(11)));
		
		assertEquals(DrawArbiter.DrawConditions.THREE_VS_ONE, testObj.getDrawConditions());
		assertEquals(32, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawConditions_noneTo3v1_byPromotion_test() {
		boardManager.addBlackPawn(42);
		boardManager.addWhiteQueen(36);
		boardManager.addWhiteQueen(40);
		boardManager.addWhiteQueen(49);
		boardManager.setIsWhiteQueenOnBoard(true);
		
		gameEngine.setIsWhiteToMove(false);
		makeMove(42, 47);
		
		assertEquals(DrawArbiter.DrawConditions.THREE_VS_ONE, testObj.getDrawConditions());
		assertEquals(32, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawConditions_noneTo2v1_byCapture_test() {
		boardManager.addBlackPawn(34);
		boardManager.addBlackPawn(9);
		boardManager.addBlackQueen(37);
		boardManager.addBlackQueen(22);
		boardManager.addWhiteQueen(43);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);

		makeCapture(43, new ArrayList<>(Arrays.asList(25, 3)), new ArrayList<>(Arrays.asList(34, 9)));
		
		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawConditions());
		assertEquals(10, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawConditions_noneTo2v1_byPromotion() {
		boardManager.addBlackPawn(45);
		boardManager.addWhiteQueen(2);
		boardManager.setIsWhiteQueenOnBoard(true);
		
		gameEngine.setIsWhiteToMove(false);

		makeMove(45, 50);
	
		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawConditions());
		assertEquals(10, testObj.getDrawCounter());
	}
	
	
	@Test
	public void changeDrawConditions_normalTo3v1_byCapture() {
		boardManager.addWhitePawn(16);
		boardManager.addWhiteQueen(26);
		boardManager.addWhiteQueen(37);
		boardManager.addBlackPawn(8);
		boardManager.addBlackPawn(23);
		boardManager.addBlackQueen(15);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);

		makeCapture(37, new ArrayList<>(Arrays.asList(19, 2)), new ArrayList<>(Arrays.asList(23, 8)));
		
		assertEquals(DrawArbiter.DrawConditions.THREE_VS_ONE, testObj.getDrawConditions());
		assertEquals(32, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawConditions_normalTo2v1_byCapture() {
		boardManager.addWhiteQueen(46);
		boardManager.addWhiteQueen(22);
		boardManager.addWhiteQueen(38);
		boardManager.addBlackQueen(4);
		boardManager.addBlackPawn(25);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		gameEngine.setIsWhiteToMove(false);

		makeCapture(4, new ArrayList<>(Arrays.asList(27, 49)), new ArrayList<>(Arrays.asList(22, 38)));
		
		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawConditions());
		assertEquals(10, testObj.getDrawCounter());
	}
	
	@Test 
	public void changeDrawConditions_normalToNone_byCapturingQueen() {
		boardManager.addWhitePawn(38);
		boardManager.addWhitePawn(39);
		boardManager.addWhitePawn(43);
		boardManager.addWhiteQueen(46);
		boardManager.addBlackPawn(7);
		boardManager.addBlackPawn(11);
		boardManager.addBlackPawn(21);
		boardManager.addBlackQueen(19);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);

		makeCapture(46, new ArrayList<>(Collections.singletonList(14)), new ArrayList<>(Collections.singletonList(19)));
		
		assertEquals(DrawArbiter.DrawConditions.NONE, testObj.getDrawConditions());
		assertEquals(50, testObj.getDrawCounter());
	}
	
	@Test
	public void changeDrawConditions_3v1To2v1() {
		boardManager.addWhitePawn(29);
		boardManager.addWhiteQueen(26);
		boardManager.addWhiteQueen(46);
		boardManager.addBlackQueen(15);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		gameEngine.setIsWhiteToMove(false);
		makeCapture(15, new ArrayList<>(Collections.singletonList(47)), new ArrayList<>(Collections.singletonList(29)));
		
		assertEquals(DrawArbiter.DrawConditions.TWO_VS_ONE, testObj.getDrawConditions());
		assertEquals(10, testObj.getDrawCounter());
	}
	
	@Test
	public void updateDrawCounter_normalConditions() {
		boardManager.addWhitePawn(42);
		boardManager.addWhitePawn(48);
		boardManager.addWhiteQueen(41);
		boardManager.addBlackPawn(2);
		boardManager.addBlackPawn(3);
		boardManager.addBlackQueen(4);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		testObj.updateConditions(true, 3, 3);

		assertEquals(50, testObj.getDrawCounter());
		
		makeMove(41, 36);
		assertEquals(49, testObj.getDrawCounter());
		makeMove(4, 15);
		assertEquals(48, testObj.getDrawCounter());
		makeMove(36, 4);
		assertEquals(47, testObj.getDrawCounter());
		makeCapture(15, new ArrayList<>(Collections.singletonList(47)), new ArrayList<>(Collections.singletonList(42)));
		assertEquals(50, testObj.getDrawCounter()); //reset counter after capture
		makeMove(4, 36);
		assertEquals(49, testObj.getDrawCounter());
		makeMove(3, 8); //reset counter after pawn move
		assertEquals(50, testObj.getDrawCounter());	
	}
	
	@Test
	public void updateDrawCounter_3v1Conditions_test() {
		boardManager.addWhitePawn(42);
		boardManager.addWhitePawn(48);
		boardManager.addWhiteQueen(41);
		boardManager.addBlackQueen(3);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		testObj.updateConditions(true, 3, 1);

		assertEquals(32, testObj.getDrawCounter());
		
		makeMove(41, 36);
		assertEquals(31, testObj.getDrawCounter());
		makeMove(3, 25);
		assertEquals(30, testObj.getDrawCounter());
		makeMove(42, 38); //pawn move - but counter doesn't reset in these conditions
		assertEquals(29, testObj.getDrawCounter());
	}
	
	@Test
	public void updateDrawCounter_2v1Conditions_test() {
		boardManager.addWhiteQueen(23);
		boardManager.addBlackQueen(30);
		boardManager.addBlackPawn(9);
		boardManager.setIsWhiteQueenOnBoard(true);
		boardManager.setIsBlackQueenOnBoard(true);
		
		testObj.updateConditions(true, 2, 1);

		assertEquals(10, testObj.getDrawCounter());
		
		makeMove(23, 1);
		assertEquals(9, testObj.getDrawCounter());
		makeMove(9, 14); //pawn move - but counter doesn't reset in these conditions
		assertEquals(8, testObj.getDrawCounter());
		
	}
}
