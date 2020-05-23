package draughts.library;

import java.util.ArrayList;
import java.util.Iterator;

import draughts.library.boardmodel.Piece;
import draughts.library.exceptions.NoPieceFoundInRequestedTileException;

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
		
		if(!possibleMoves.get(0).isCapture())
			boardManager.makeHop(source, destination);
		else {
			Capture capture = (Capture) possibleMoves.get(0).getHop(hopsMadeInMove);
			boardManager.makeCapture(source, destination, capture.getTakenPawn());
		}
		
		if(++hopsMadeInMove == possibleMoves.get(0).getNumberOfHops()) {
			checkForPawnPromotion(destination);
			moveDone();
		}
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
	
	public void checkForPawnPromotion(int destination) {
		if(destination < 6 || destination > 45) {
			Piece piece = null;
			try {
				piece = boardManager.findPieceByIndex(destination);
			} catch (NoPieceFoundInRequestedTileException ex) {
				ex.printStackTrace();
			}
			
			if(!piece.isQueen()) boardManager.promotePawn(piece);
		}
	}
	
	
	
}
