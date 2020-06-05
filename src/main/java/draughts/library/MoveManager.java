package draughts.library;

import java.util.ArrayList;
import java.util.Iterator;

import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;

public class MoveManager {
	
	private ArrayList<Move<? extends Hop>> possibleMoves;
	
	//used for making move hop by hop
	private int hopsMadeInMove;
	private ArrayList<Tile> possibleHopDestinations;
	
	public MoveManager() {
		possibleMoves = new ArrayList<>();
		hopsMadeInMove = 0;
		possibleHopDestinations = new ArrayList<>();
	}
	
	public ArrayList<Move<? extends Hop>> getPossibleMoves() {
		return possibleMoves;
	}
	
	public int getHopsMadeInMove() {
		return hopsMadeInMove;
	}
	
	public void findAllCorrectMoves(BoardManager boardManager, boolean isWhiteToMove) {
		
		possibleMoves.addAll(boardManager.findCapturesForAllPieces(isWhiteToMove));
		if(possibleMoves.size() == 0)
			possibleMoves.addAll(boardManager.findMovesForAllPieces(isWhiteToMove));		
	}

	//methods for making move all hops at once
	
	public Move<? extends Hop> isMadeMoveCorrect(int source, int destination, ArrayList<Integer> takenPawns) {
		for(Move<? extends Hop> move : possibleMoves) {
			if(move.doesSourceMatch(source) &&
			   move.doesDestinationMatch(destination) &&
			   move.doesTakenPawnsMatch(takenPawns))
			   		return move;
		}
		
		return null;
	}
	
	//methods for making move hop by hop
	
	public ArrayList<Tile> findPossibleHopDestinations(Piece chosenPiece) {
		for(Move<? extends Hop> move : possibleMoves) {
			if(chosenPiece.getPosition().getIndex() == move.getHop(hopsMadeInMove).getSource().getIndex())
				possibleHopDestinations.add(move.getHop(hopsMadeInMove).getDestination());
		}
		return possibleHopDestinations;
	}
	
	public boolean isClickedTilePossibleDestination(Tile tileDestination) {
		for(Tile possibleDestination : possibleHopDestinations) {
			if(tileDestination.getIndex() == possibleDestination.getIndex()) return true;
		}	
		return false;
	}
	
	public void hopFinished(Piece chosenPiece) {
		possibleHopDestinations.clear();
		hopsMadeInMove++;
		updatePossibleMoves(chosenPiece);
		findPossibleHopDestinations(chosenPiece);
	}
	
	public void updatePossibleMoves(Piece chosenPiece) {
		Iterator<Move<? extends Hop>> movesIterator = possibleMoves.iterator();
		
		while(movesIterator.hasNext()) {
			Move<? extends Hop> move = movesIterator.next();
			if(move.getHop(hopsMadeInMove).getSource().getIndex() != chosenPiece.getPosition().getIndex()) {
				movesIterator.remove();
				possibleMoves.remove(move);
			}
		}
	}
	
	public void moveDone() {
		possibleHopDestinations.clear();
		possibleMoves.clear();
		hopsMadeInMove = 0;
	}
	
	public boolean isMoveFinished() {
		return (hopsMadeInMove == possibleMoves.get(0).getNumberOfHops()) ? true : false;
	}
}
