package draughts.library;

import java.util.ArrayList;

public class GameEngine {
	
	private MoveManager moveManager;
	private BoardManager boardManager;
	private boolean isWhiteToMove;
	private boolean pawnAlreadyMarked;
	private ArrayList<Integer> possibleHopDestinations;
	
	public GameEngine() {
		moveManager = new MoveManager();
		boardManager = new BoardManager();
	}
	
	public void startGame() {
		boardManager.createStartingPosition();
		pawnAlreadyMarked = false;
		possibleHopDestinations = new ArrayList<>();
		isWhiteToMove = true;
		moveManager.findAllCorrectMoves(isWhiteToMove);
	}
	
	public void tileClicked(int position) throws NoPieceFoundInRequestedTileException {
		if(!pawnAlreadyMarked) {
			boardManager.findPieceByIndex(position);
			addHopDestinations();
		}
		
	}
	
	public void addHopDestinations() {
		ArrayList<Integer> possibleHopDestinations = moveManager.doesChosenPawnHaveMoves(position);
		if(possibleHopDestinations.size() == 0)
			throw new NoCorrectMovesForSelectedPiece("Another piece should move");
		else possibleHopDestinations.addAll(possibleHopDestinations);
	}
	
	public void changeMove() {
		isWhiteToMove = !isWhiteToMove;
	}

}
