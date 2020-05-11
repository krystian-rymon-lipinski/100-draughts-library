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
		int source = 0;
		if(hopsMadeInMove == 0) 
			source = move.getSource();
		else 
			source = move.getHops().get(hopsMadeInMove-1);
		
		int destination = move.getHops().get(hopsMadeInMove);
		
		if(move.getPawnsTaken().size() == 0)
			boardManager.makeHop(source, destination);
		else
			boardManager.makeHop(source,  destination, 
								 move.getPawnsTaken().get(hopsMadeInMove));
		hopsMadeInMove++;
		
		if(hopsMadeInMove == move.getHops().size())
			moveDone();
	}
	
	public void moveDone() {
		hopsMadeInMove = 0;
	}
	
	public void findMoveByHop(int source, int destination) {
		
	}

}
