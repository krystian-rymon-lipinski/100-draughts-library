package draughts.library;

import java.util.ArrayList;

public class MoveManager {
	
	private BoardManager boardManager;
	private ArrayList<Move<? extends Hop>> allMoves;
	private int hopsMadeInMove;
	private Move<? extends Hop> currentMove;
	
	public MoveManager() {
		boardManager = new BoardManager();
		allMoves = new ArrayList<>();
		currentMove = null;
		hopsMadeInMove = 0;
	}
	
	public BoardManager getBoardManager() {
		return boardManager;
	}
	
	public ArrayList<Move<? extends Hop>> getMoves() {
		return allMoves;
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
	
	public ArrayList<Integer> doesChosenPawnHaveMoves(int position) {
		ArrayList<Integer> possibleHopDestinations = new ArrayList<Integer>();
		for(Move<? extends Hop> move : allMoves) {
			if(position == move.getHop(hopsMadeInMove).getSource()) {
				possibleHopDestinations.add(move.getHop(hopsMadeInMove).getDestination());
			}
		}
		return possibleHopDestinations;
	}
	
	public void moveDone() {
		currentMove = null;
		hopsMadeInMove = 0;
	}
	
	public void findMovesByHopDestination(int destination) {
		
	}
	
	public void findAllCorrectMoves(boolean isWhiteToMove) {
		
		allMoves.addAll(boardManager.findCapturesForAllPieces(isWhiteToMove));
		if(allMoves.size() == 0)
			allMoves.addAll(boardManager.findMovesForAllPieces(isWhiteToMove));
		
	}

}
