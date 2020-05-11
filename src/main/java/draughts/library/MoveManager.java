package draughts.library;

import java.util.ArrayList;

public class MoveManager {
	
	private BoardManager boardManager;
	private ArrayList<Move> moves;
	private int hopsMadeInMove;
	
	public MoveManager() {
		boardManager = new BoardManager();
		boardManager.prepareBoard();
		moves = new ArrayList<>();
		hopsMadeInMove = 0;
	}
	
	public BoardManager getBoardManager() {
		return boardManager;
	}
	
	public ArrayList<Move> getMoves() {
		return moves;
	}
	
	public int getHopsMadeInMove() {
		return hopsMadeInMove;
	}
		
	public void makeHop(Move move) {
		
		int destination = move.getHops().get(hopsMadeInMove);
		
		if(!move.isTake())
			boardManager.makeHop(move.getSource(hopsMadeInMove), move.getDestination(hopsMadeInMove));
		else
			boardManager.makeHop(move.getSource(hopsMadeInMove), move.getDestination(hopsMadeInMove),
								 move.getPawnsTaken().get(hopsMadeInMove));
		hopsMadeInMove++;
		
		if(hopsMadeInMove == move.getHops().size())
			moveDone();
	}
	
	public void moveDone() {
		hopsMadeInMove = 0;
	}
	
	public ArrayList<Move> findMoveByHop(int source, int destination) {
		return null;
	}
	
	public ArrayList<Move> findAllPossibleMoves(boolean isWhiteToMove) {
		return boardManager.findMoves(isWhiteToMove);
	}

}
