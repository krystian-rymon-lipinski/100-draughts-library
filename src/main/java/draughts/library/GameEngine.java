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
	
	public GameEngine() {
		moveManager = new MoveManager();
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
	
	public void startGame() {
		moveManager.getBoardManager().createStartingPosition();

		markedPiecePosition = 0;
		isWhiteToMove = true;
		possibleHopDestinations = new ArrayList<>();
		moveManager.findAllCorrectMoves(isWhiteToMove);
	}
	
	public void tileClicked(int index) throws NoPieceFoundInRequestedTileException, 
												 WrongColorFoundInRequestedTileException,
												 NoCorrectMovesForSelectedPieceException, 
												 WrongMoveException {
		if(markedPiecePosition == 0) { //no piece marked yet - first part of making a hop
			if(isChosenTileEmpty(index)) 
				throw new NoPieceFoundInRequestedTileException("No piece found on chosen tile!");
				
			else if(!isChosenTileOccupiedByProperColor(index)) 
				throw new WrongColorFoundInRequestedTileException("No piece of your color on chosen tile!");
			
			else {
				markedPiecePosition = index;
				addPossibleHopDestinations(index);
			}
		}
		
		else {
			if(isChosenTileOccupiedByProperColor(index)) {
				markedPiecePosition = index;
				addPossibleHopDestinations(index);
			}
			else if(isClickedTilePossibleDestination(index)) {
				possibleHopDestinations.clear();
				moveManager.makeHop(markedPiecePosition, index);
			}
			else
				throw new WrongMoveException("Wrong move");
		}
		
	}
	
	public boolean isClickedTilePossibleDestination(int position) {
		for(Integer possibleDestinations : possibleHopDestinations) {
			if(position == possibleDestinations) return true;
		}
		
		return false;
	}
	
	public boolean isChosenTileOccupiedByProperColor(int index) {
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
	
	public void changeMove() {
		isWhiteToMove = !isWhiteToMove;
		markedPiecePosition = 0;
		possibleHopDestinations.clear();
	}

}
