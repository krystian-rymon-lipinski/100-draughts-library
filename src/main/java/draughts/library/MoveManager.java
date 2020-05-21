package draughts.library;

import java.util.ArrayList;
import java.util.Iterator;

public class MoveManager {
	
	private BoardManager boardManager;
	private ArrayList<Move<? extends Hop>> possibleMoves;
	private int hopsMadeInMove;
	
	public MoveManager() {
		boardManager = new BoardManager();
		possibleMoves = new ArrayList<>();
		hopsMadeInMove = 0;
	}
	
	public BoardManager getBoardManager() {
		return boardManager;
	}
	
	public ArrayList<Move<? extends Hop>> getPossibleMoves() {
		return possibleMoves;
	}
	
	public int getHopsMadeInMove() {
		return hopsMadeInMove;
	}
		
	public void makeHop(int source, int destination) {
		findMovesFromAllPossible(destination);
		
		if(!possibleMoves.get(0).isTake())
			boardManager.makeHop(source, destination);
		else {
			Capture capture = (Capture) possibleMoves.get(0).getHop(hopsMadeInMove);
			boardManager.makeCapture(source, destination, capture.getTakenPawn());
		}
		
		hopsMadeInMove++;		
		if(hopsMadeInMove == possibleMoves.get(0).getNumberOfHops())
			moveDone();
	}
	
	
	public void findMovesFromAllPossible(int destination) {
		Iterator<Move<? extends Hop>> movesIterator = possibleMoves.iterator();
		
		while(movesIterator.hasNext()) {
			Move<? extends Hop> move = movesIterator.next();
			if(move.getHop(hopsMadeInMove).getDestination() != destination) {
				movesIterator.remove();
				possibleMoves.remove(move);
			}
		}
	}
	
	public ArrayList<Integer> doesChosenPawnHaveMoves(int position) {
		ArrayList<Integer> possibleHopDestinations = new ArrayList<Integer>();
		for(Move<? extends Hop> move : possibleMoves) {
			if(position == move.getHop(hopsMadeInMove).getSource()) {
				possibleHopDestinations.add(move.getHop(hopsMadeInMove).getDestination());
			}
		}
		return possibleHopDestinations;
	}
	
	public void moveDone() {
		possibleMoves.clear();
		hopsMadeInMove = 0;
	}
	
	public boolean isMoveFinished() {
		return (hopsMadeInMove == 0) ? true : false;
	}
	
	public void findAllCorrectMoves(boolean isWhiteToMove) {
		
		possibleMoves.addAll(boardManager.findCapturesForAllPieces(isWhiteToMove));
		if(possibleMoves.size() == 0)
			possibleMoves.addAll(boardManager.findMovesForAllPieces(isWhiteToMove));
		
	}

}
