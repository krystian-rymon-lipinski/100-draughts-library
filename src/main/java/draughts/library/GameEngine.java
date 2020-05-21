package draughts.library;

import java.util.ArrayList;

import draughts.library.boardmodel.Tile;
import draughts.library.exceptions.NoCorrectMovesForSelectedPieceException;
import draughts.library.exceptions.NoPieceFoundInRequestedTileException;
import draughts.library.exceptions.WrongColorFoundInRequestedTileException;
import draughts.library.exceptions.WrongMoveException;

public class GameEngine {
	
	private MoveManager moveManager;
	private boolean isWhiteToMove;
	private int markedPiecePosition;
	private ArrayList<Integer> possibleHopDestinations;
	private GameState gameState;
	
	public GameEngine() {
		moveManager = new MoveManager();
		markedPiecePosition = 0;
		isWhiteToMove = true;
		possibleHopDestinations = new ArrayList<>();
		gameState = GameState.RUNNING;
	}
	
	public boolean getIsWhiteToMove() {
		return isWhiteToMove;
	}
	
	public int getMarkedPiecePosition() {
		return markedPiecePosition;
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
	
	public void startGame() {
		moveManager.getBoardManager().createStartingPosition();
		moveManager.findAllCorrectMoves(isWhiteToMove);
	}
	
	public void tileClicked(int index) throws NoPieceFoundInRequestedTileException, 
												 WrongColorFoundInRequestedTileException,
												 NoCorrectMovesForSelectedPieceException, 
												 WrongMoveException {
		while(gameState == GameState.RUNNING) {
			if(markedPiecePosition == 0) { //no piece marked yet - first part of making a hop
				if(isChosenTileEmpty(index)) 
					throw new NoPieceFoundInRequestedTileException("No piece found on chosen tile!");
					
				else if(!isClickedTileOccupiedByProperColor(index)) 
					throw new WrongColorFoundInRequestedTileException("No piece of your color on chosen tile!");
				
				else {
					markedPiecePosition = index;
					addPossibleHopDestinations(index);
				}
			}
			
			else {
				if(isClickedTileOccupiedByProperColor(index)) {
					markedPiecePosition = index;
					addPossibleHopDestinations(index);
				}
				else if(isClickedTilePossibleDestination(index)) {
					moveManager.makeHop(markedPiecePosition, index);
					if(moveManager.isMoveFinished()) moveFinished();
					else hopFinished(index);
				}
				else
					throw new WrongMoveException("Wrong move");
			}
		}		
	}
	
	public boolean isClickedTilePossibleDestination(int position) {
		for(Integer possibleDestinations : possibleHopDestinations) {
			if(position == possibleDestinations) return true;
		}
		
		return false;
	}
	
	public boolean isClickedTileOccupiedByProperColor(int index) {
		Tile.State chosenTileState = moveManager.getBoardManager().findTileByIndex(index).getState();
		if(isWhiteToMove)
			return (chosenTileState == Tile.State.WHITE_PAWN ||
					chosenTileState == Tile.State.WHITE_QUEEN) ? true : false;
		else
			return (chosenTileState == Tile.State.BLACK_PAWN ||
			chosenTileState == Tile.State.BLACK_QUEEN) ? true : false;
	}
	
	public boolean isChosenTileEmpty(int index) {
		return moveManager.getBoardManager().findTileByIndex(index).getState() == Tile.State.EMPTY ? true : false;
	}
	
	public void addPossibleHopDestinations(int position) throws NoCorrectMovesForSelectedPieceException {
		possibleHopDestinations = moveManager.doesChosenPawnHaveMoves(position);
		if(possibleHopDestinations.size() == 0)
			throw new NoCorrectMovesForSelectedPieceException("Other pieces should move");
	}
	
	public void moveFinished() {

		isWhiteToMove = !isWhiteToMove;
		markedPiecePosition = 0;
		possibleHopDestinations.clear();
		moveManager.findAllCorrectMoves(isWhiteToMove);
		
		checkGameState();
	}
	
	public void hopFinished(int destination) throws NoCorrectMovesForSelectedPieceException {
		markedPiecePosition = destination;
		possibleHopDestinations.clear();
		moveManager.findAllCorrectMoves(isWhiteToMove);
		addPossibleHopDestinations(destination);
		
	}
	
	public void checkGameState() {
		if(moveManager.getPossibleMoves().size() == 0) {
			if(isWhiteToMove) System.out.println("Wygrana czarnych!");
			else System.out.println("Wygrana bia³ych");
		}
		
	}
	
	
	public enum GameState {
		RUNNING,
		FINISHED
	}

}
