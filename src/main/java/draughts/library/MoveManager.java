package draughts.library;

import java.util.ArrayList;

public class MoveManager {
	
	private BoardManager boardManager;
	private ArrayList<Move<? extends Hop>> moves;
	private int hopsMadeInMove;
	private Move<? extends Hop> currentMove;
	
	public MoveManager() {
		boardManager = new BoardManager();
		//boardManager.createStartingPosition();
		moves = new ArrayList<>();
		currentMove = null;
		hopsMadeInMove = 0;
	}
	
	public BoardManager getBoardManager() {
		return boardManager;
	}
	
	public ArrayList<Move<? extends Hop>> getMoves() {
		return moves;
	}
	
	public int getHopsMadeInMove() {
		return hopsMadeInMove;
	}
		
	public void makeHop(int source, int destination) {
		if(currentMove == null) findCurrentMove(source, destination);
		
		if(!currentMove.isTake())
			boardManager.makeHop(currentMove.getHop(hopsMadeInMove).getSource(), 
								 currentMove.getHop(hopsMadeInMove).getDestination());
		else
			boardManager.makeCapture(currentMove.getHop(hopsMadeInMove).getSource(), 
								 	 currentMove.getHop(hopsMadeInMove).getDestination(),
								 	 currentMove.getHop(hopsMadeInMove).getTakenPawn());
		hopsMadeInMove++;
		
		if(hopsMadeInMove == currentMove.getNumberOfHops())
			moveDone();
	}
	
	public void makeHop(Move<? extends Hop> move) {
		currentMove = move;
	}
	
	public void findCurrentMove(int source, int destination) {
		
	}
	
	public void moveDone() {
		currentMove = null;
		hopsMadeInMove = 0;
	}
	
	public ArrayList<Move> findMoveByHop(int source, int destination) {
		return null;
	}
	
	public ArrayList<Move<Hop>> findMovesForAllPieces(boolean isWhiteToMove) {
		return boardManager.findMoves(isWhiteToMove);
	}
	
	public ArrayList<Capture> findCapturesForAllPieces(boolean isWhiteToMove) {
		return boardManager.findCaptures(isWhiteToMove);
	}

}
