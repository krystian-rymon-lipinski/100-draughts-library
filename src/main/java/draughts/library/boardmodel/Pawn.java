package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.Hop;
import draughts.library.Capture;
import draughts.library.Move;

public abstract class Pawn extends Piece {

	public Pawn(Tile position) {
		super(position);
	}
	
	public boolean isQueen() {
		return false;
	}
	
	
	public ArrayList<Move<Hop>> findMovesInDirection(MoveDirection moveDirection, Tile[][] board, int currentRow, int currentColumn) {
		
		ArrayList<Move<Hop>> moves = new ArrayList<>();
		Tile target = findTarget(moveDirection, board, currentRow, currentColumn, 1);
		
		if(target.getState() == Tile.State.EMPTY)
			moves.add(new Move<Hop>(new Hop(getPosition(), target.getIndex())));
		return moves;
	}
	
	public ArrayList<Capture> findCapturesInDirection(MoveDirection moveDirection, 
														Tile[][] board, int row, int column) {
		
		ArrayList<Capture> hops = new ArrayList<>();
		
		Tile target = findTarget(moveDirection, board, row, column, 2);
		Tile possibleTake = findTarget(moveDirection, board, row, column, 1);
		
		if(isTakePossible(target, possibleTake))
				hops.add(new Capture(getPosition(), target.getIndex(), possibleTake.getIndex()));
		return hops;
	}
	
	
	public boolean isTakePossible(Tile target, Tile possibleTake) {
		return isTileOccupiedByOppositeColor(possibleTake) && 
				   target.getState() == Tile.State.EMPTY ? true : false;
	}

}
