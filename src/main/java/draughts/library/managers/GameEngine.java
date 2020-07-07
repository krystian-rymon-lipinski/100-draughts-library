package draughts.library.managers;

import java.util.ArrayList;

import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.exceptions.*;
import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

public class GameEngine {
	
	private MoveManager moveManager;
	private boolean isWhiteToMove;
	private GameState gameState;
	private DrawArbiter drawArbiter;
	private BoardManager boardManager;
	private Piece chosenPiece;
	
	public GameEngine() {
		boardManager = new BoardManager();
		moveManager = new MoveManager();
		drawArbiter = new DrawArbiter();
		chosenPiece = null;
	}
	
	public boolean getIsWhiteToMove() {
		return isWhiteToMove;
	}
	
	public void setIsWhiteToMove(boolean isWhiteToMove) {
		this.isWhiteToMove = isWhiteToMove;
	}
	
	public Piece getChosenPiece() {
		return chosenPiece;
	}
	
	public MoveManager getMoveManager() {
		return moveManager;
	}
	
	public BoardManager getBoardManager() {
		return boardManager;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public DrawArbiter getDrawArbiter() {
		return drawArbiter;
	}

	public void setDrawArbiter(DrawArbiter drawArbiter) { this.drawArbiter = drawArbiter; }
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	
	public void startGame() {
		boardManager.createStartingPosition();
		gameState = GameState.RUNNING;
		isWhiteToMove = true;

		prepareMove(isWhiteToMove);
	}

	public ArrayList<Move<? extends Hop>> prepareMove(boolean isWhiteToMove) {
		ArrayList<Move<? extends Hop>> moves = moveManager.findAllCorrectMoves(boardManager, isWhiteToMove);
		//checkGameState();
		return moves;
	}
	
	
	
	//methods for making move all hops at once
	
	public Move<? extends Hop> checkIfMoveIsCorrect(int source, int destination, ArrayList<Integer> taken) throws WrongMoveException, GameAlreadyEndedException {
		if(gameState == GameState.RUNNING) {
			Move<? extends Hop> correctMove = moveManager.isMadeMoveCorrect(source, destination, taken);
			if (correctMove == null) {
				throw new WrongMoveException("Chosen move is not allowed");
			}
			return correctMove;
		}
		else throw new GameAlreadyEndedException("Game already ended, you cannot search for moves!");
	}
	
	public void updateBoard(Move<? extends Hop> move) {
		boardManager.makeWholeMove(move);
	}
	
	
	//methods for making moves hop by hop
	
	public void tileClicked(int index) throws NoPieceFoundInRequestedTileException, 
												 WrongColorFoundInRequestedTileException,
												 NoCorrectMovesForSelectedPieceException, 
												 WrongMoveException {
		if(gameState == GameState.RUNNING) {
			Tile chosenTile = boardManager.findTileByIndex(index);

			if(chosenPiece == null) { //no piece marked yet - first part of making a hop
				if(isChosenTileEmpty(chosenTile)) 
					throw new NoPieceFoundInRequestedTileException("No piece found on chosen tile!");				
				else if(!isChosenTileOccupiedByProperColor(chosenTile)) 
					throw new WrongColorFoundInRequestedTileException("No piece of your color on chosen tile!");		
				else {
					chosenPiece = boardManager.findPieceByIndex(index);
					if(moveManager.findPossibleHops(chosenPiece).size() == 0)
						throw new NoCorrectMovesForSelectedPieceException("Other pieces should move");
				}
			}		
			else {
				if(isChosenTileOccupiedByProperColor(chosenTile)) {
					moveManager.getPossibleHops().clear();
					chosenPiece = boardManager.findPieceByIndex(index);
					if(moveManager.findPossibleHops(chosenPiece).size() == 0)
						throw new NoCorrectMovesForSelectedPieceException("Other pieces should move");
				}
				else {
					Hop hop = moveManager.findHopByDestination(chosenTile);
					if(hop == null)
						throw new WrongMoveException("Wrong move");
					else {
						if(hop instanceof Capture) {
							Capture capture = (Capture) hop;
							Piece capturedPiece = capture.getTakenPiece();
							boardManager.makeCapture(chosenPiece, chosenTile, capturedPiece);
						}
						else boardManager.makeHop(chosenPiece, chosenTile);
						moveManager.hopFinished();
						if(moveManager.isMoveFinished()) {
							finishMove(moveManager.findMoveMade(chosenTile));
						}
						else {
							moveManager.updatePossibleMoves(chosenPiece);
							moveManager.findPossibleHops(chosenPiece);
						}
					}					
				}
			}		
		}
	}
	
	public boolean isChosenTileOccupiedByProperColor(Tile chosenTile) {
		if(isWhiteToMove)
			return (chosenTile.getState() == Tile.State.WHITE_PAWN ||
					chosenTile.getState() == Tile.State.WHITE_QUEEN);
		else
			return (chosenTile.getState() == Tile.State.BLACK_PAWN ||
					chosenTile.getState() == Tile.State.BLACK_QUEEN);
	}
	
	public boolean isChosenTileEmpty(Tile chosenTile) {
		return chosenTile.getState() == Tile.State.EMPTY;
	}
	
	////////////////////////// methods useful for both methods
	
	public void finishMove(Move<? extends Hop> move) {
		checkForPawnPromotion(move);
		updateDrawArbiter(move);
		endPlayerTurn();
		checkGameState();
	}
	
	public void checkForPawnPromotion(Move<? extends Hop> move) {
		if(!move.getMovingPiece().isQueen() && 
				(move.getMoveDestination().getIndex() < 6 || move.getMoveDestination().getIndex() > 45)) {
			move.setIsPromotion(true);
			Piece newQueen = boardManager.promotePawn(move.getMovingPiece());
			move.setMovingPiece(newQueen);
		}
	}
	
	public void endPlayerTurn() {
		moveManager.moveDone();
		chosenPiece = null;
		changeColor();
	}

	public void changeColor() {
		isWhiteToMove = !isWhiteToMove;
	}
	
	public void updateDrawArbiter(Move<? extends Hop> move) {
		drawArbiter.updateCounter(move.isCapture(), move.getMovingPiece().isQueen());
		drawArbiter.updateConditions((boardManager.getIsWhiteQueenOnBoard() && boardManager.getIsBlackQueenOnBoard()), 
								 boardManager.getWhitePieces().size(), boardManager.getBlackPieces().size());
	}
	
	public void checkGameState() {
		if (!moveManager.isAnyMovePossible(boardManager, isWhiteToMove)) {
			if(isWhiteToMove) setGameState(GameState.WON_BY_BLACK);
			else setGameState(GameState.WON_BY_WHITE);
		}
		if(drawArbiter.isGameDrawn()) setGameState(GameState.DRAWN);
	}
	
	
	public enum GameState {
		RUNNING,
		WON_BY_WHITE,
		WON_BY_BLACK,
		DRAWN
	}

}
