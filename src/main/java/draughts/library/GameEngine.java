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
	
	public void finishMove() {
		checkForPawnPromotion();
		checkIfGameShouldEnd();
		changePlayer();
		
	}
	
	//methods for making move all hops at once
	
	public void isMadeMoveCorrect(int source, int destination, ArrayList<Integer> taken) {
		Move<? extends Hop> correctMove = moveManager.isMadeMoveCorrect(source, destination, taken);
		if(correctMove != null) {
			updateBoard(correctMove);
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
		
		finishMove(correctMove());
	}
	
	//methods for making moves hop by hop
	
	/*
	public void tileClicked(int index) throws NoPieceFoundInRequestedTileException, 
												 WrongColorFoundInRequestedTileException,
												 NoCorrectMovesForSelectedPieceException, 
												 WrongMoveException {
		if(gameState == GameState.RUNNING) {
			if(chosenPiece == null) { //no piece marked yet - first part of making a hop
				if(isChosenTileEmpty(index)) 
					throw new NoPieceFoundInRequestedTileException("No piece found on chosen tile!");
					
				else if(!isClickedTileOccupiedByProperColor(index)) 
					throw new WrongColorFoundInRequestedTileException("No piece of your color on chosen tile!");
				
				else {
					chosenPiece = boardManager.findPieceByIndex(index);
					addPossibleHopDestinations(index);
				}
			}
			
			else {
				if(isClickedTileOccupiedByProperColor(index)) {
					chosenPiece = boardManager.findPieceByIndex(index);
					addPossibleHopDestinations(index);
				}
				else if(isClickedTilePossibleDestination(index)) {
					boardManager.makeHop(chosenPiece, index);
					if(moveManager.isMoveFinished()) {
						moveFinished(index);
					}
					else hopFinished(index);
				}
				else
					throw new WrongMoveException("Wrong move");
			}
		}		
	}
	*/
	
	
	public boolean isClickedTilePossibleDestination(int position) {
		for(Integer possibleDestinations : possibleHopDestinations) {
			if(position == possibleDestinations) return true;
		}	
		return false;
	}
	
	public boolean isClickedTileOccupiedByProperColor(int index) {
		Tile.State chosenTileState = boardManager.findTileByIndex(index).getState();
		if(isWhiteToMove)
			return (chosenTileState == Tile.State.WHITE_PAWN ||
					chosenTileState == Tile.State.WHITE_QUEEN) ? true : false;
		else
			return (chosenTileState == Tile.State.BLACK_PAWN ||
			chosenTileState == Tile.State.BLACK_QUEEN) ? true : false;
	}
	
	public boolean isChosenTileEmpty(int index) {
		return boardManager.findTileByIndex(index).getState() == Tile.State.EMPTY ? true : false;
	}
	
	public void addPossibleHopDestinations(int position) throws NoCorrectMovesForSelectedPieceException {
		possibleHopDestinations = moveManager.doesChosenPawnHaveMoves(position);
		if(possibleHopDestinations.size() == 0)
			throw new NoCorrectMovesForSelectedPieceException("Other pieces should move");
	}
	
	public void moveFinished(int destination) {
		finishPreviousMove(destination);
		prepareNewMove();
	
		moveManager.findAllCorrectMoves(isWhiteToMove);		
		checkGameState();
	}
	
	public void finishPreviousMove(int destination) {
		try {
			moveManager.checkForPawnPromotion(destination);
			drawArbiter.updateCounter(moveManager.getPossibleMoves().get(0).isCapture(), 
									boardManager.findPieceByIndex(destination).isQueen());
			} catch(Exception ex) {}
			
			drawArbiter.updateState((boardManager.getIsWhiteQueenOnBoard() && 
									 boardManager.getIsBlackQueenOnBoard()),
									 boardManager.getWhitePieces().size(),
									 boardManager.getBlackPieces().size());	
			moveManager.moveDone();
	}
	
	public void prepareNewMove() {
		isWhiteToMove = !isWhiteToMove;
		markedPiecePosition = 0;
		possibleHopDestinations.clear();
	}
	
	public void hopFinished(int destination) throws NoCorrectMovesForSelectedPieceException {
		markedPiecePosition = destination;
		possibleHopDestinations.clear();
		addPossibleHopDestinations(destination);
		
	}
	
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
