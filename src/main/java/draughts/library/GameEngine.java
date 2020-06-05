package draughts.library;

import java.util.ArrayList;

import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.exceptions.NoCorrectMovesForSelectedPieceException;
import draughts.library.exceptions.NoPieceFoundInRequestedTileException;
import draughts.library.exceptions.WrongColorFoundInRequestedTileException;
import draughts.library.exceptions.WrongMoveException;

public class GameEngine {
	
	private MoveManager moveManager;
	private boolean isWhiteToMove;
	private ArrayList<Integer> possibleHopDestinations;
	private GameState gameState;
	private DrawArbiter drawArbiter;
	private BoardManager boardManager;
	private Piece chosenPiece;
	
	public GameEngine() {
		boardManager = new BoardManager();
		moveManager = new MoveManager();
		drawArbiter = new DrawArbiter();
		chosenPiece = null;
		possibleHopDestinations = new ArrayList<>();
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
	
	public ArrayList<Integer> getPossibleHopDestinations() {
		return possibleHopDestinations;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public DrawArbiter getDrawArbiter() {
		return drawArbiter;
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	
	public void startGame() {
		boardManager.createStartingPosition();
		gameState = GameState.RUNNING;
		isWhiteToMove = true;
		
		moveManager.findAllCorrectMoves(boardManager, isWhiteToMove);
	}
	
	
	
	//methods for making move all hops at once
	
	public void isMadeMoveCorrect(int source, int destination, ArrayList<Integer> taken) throws WrongMoveException {
		if(gameState == GameState.RUNNING) {
			Move<? extends Hop> correctMove = moveManager.isMadeMoveCorrect(source, destination, taken);
			if(correctMove == null) throw new WrongMoveException("Chosen move is not allowed");
			else updateBoard(correctMove);
		}
	}
	
	public void updateBoard(Move<? extends Hop> correctMove) {
		try {
			ArrayList<Piece> takenPieces = new ArrayList<>();
			Piece movedPiece = boardManager.findPieceByIndex(correctMove.getMoveSource().getIndex());
			Tile destinationTile = boardManager.findTileByIndex(correctMove.getMoveDestination().getIndex());
			
			for(int i=0; i<correctMove.getNumberOfHops(); i++) {
				if(correctMove.isCapture()) {
					takenPieces.add(boardManager.findPieceByIndex(correctMove.getMoveTakenPawns().get(i).getIndex()));
					boardManager.makeCapture(movedPiece, correctMove.getHop(i).getDestination(), takenPieces.get(i));
				}
				else {
					boardManager.makeHop(movedPiece, destinationTile);
				}
			}		
		} catch(NoPieceFoundInRequestedTileException ex) {}
		
		finishMove(correctMove);
	}
	
	public void finishMove(Move<? extends Hop> move) {
		checkForPawnPromotion(move);
		changePlayer();	
		checkIfGameShouldEnd(move);
	}
	
	public void checkForPawnPromotion(Move<? extends Hop> move) {
		if(!move.getMovingPiece().isQueen() && 
				(move.getMoveDestination().getIndex() < 6 || move.getMoveDestination().getIndex() > 45))
			boardManager.promotePawn(move.getMovingPiece());
	}
	
	public void changePlayer() {
		moveManager.moveDone();
		isWhiteToMove = !isWhiteToMove;
		moveManager.findAllCorrectMoves(boardManager, isWhiteToMove);
	}
	
	public void checkIfGameShouldEnd(Move<? extends Hop> move) {
		drawArbiter.updateState((boardManager.getIsWhiteQueenOnBoard() && boardManager.getIsBlackQueenOnBoard()), 
								 boardManager.getWhitePieces().size(), boardManager.getBlackPieces().size());
		drawArbiter.updateCounter(move.isCapture(), move.getMovingPiece().isQueen());
		checkGameState();
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
				else if(!isClickedTileOccupiedByProperColor(chosenTile)) 
					throw new WrongColorFoundInRequestedTileException("No piece of your color on chosen tile!");		
				else {
					chosenPiece = boardManager.findPieceByIndex(index);
					if(moveManager.findPossibleHopDestinations(chosenPiece).size() == 0)
						throw new NoCorrectMovesForSelectedPieceException("Other pieces should move");
				}
			}		
			else {
				if(isClickedTileOccupiedByProperColor(chosenTile)) {
					chosenPiece = boardManager.findPieceByIndex(index);
					if(moveManager.findPossibleHopDestinations(chosenPiece).size() == 0)
						throw new NoCorrectMovesForSelectedPieceException("Other pieces should move");
				}
				else if(moveManager.isClickedTilePossibleDestination(chosenTile)) {
					boardManager.makeHop(chosenPiece, chosenTile);
					moveManager.hopFinished(chosenPiece);
					if(moveManager.isMoveFinished()) {
						finishMove(moveManager.getPossibleMoves().get(0));
					}
					else {
						moveManager.updatePossibleMoves(chosenPiece);
						moveManager.findPossibleHopDestinations(chosenPiece);
					}
				}
				else
					throw new WrongMoveException("Wrong move");
			}
		}		
	}
	
	public boolean isClickedTileOccupiedByProperColor(Tile chosenTile) {
		if(isWhiteToMove)
			return (chosenTile.getState() == Tile.State.WHITE_PAWN ||
					chosenTile.getState() == Tile.State.WHITE_QUEEN) ? true : false;
		else
			return (chosenTile.getState() == Tile.State.BLACK_PAWN ||
					chosenTile.getState() == Tile.State.BLACK_QUEEN) ? true : false;
	}
	
	public boolean isChosenTileEmpty(Tile chosenTile) {
		return chosenTile.getState() == Tile.State.EMPTY ? true : false;
	}
	
	////////////////////////// methods useful for both methods
	
	public void checkGameState() {
		if(moveManager.getPossibleMoves().size() == 0) {
			if(isWhiteToMove) setGameState(GameState.WON_BY_BLACK);
			else setGameState(GameState.WON_BY_WHITE);
		}		
		else 
			if(isGameDrawn()) setGameState(GameState.DRAWN);
	}
	
	public boolean isGameDrawn() {
		if(drawArbiter.getDrawCounter() == 0) return true;
		else return false;
	}
	
	public enum GameState {
		RUNNING,
		WON_BY_WHITE,
		WON_BY_BLACK,
		DRAWN
	}

}
