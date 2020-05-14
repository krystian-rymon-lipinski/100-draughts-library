package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.Move;

public abstract class Pawn extends Piece {

	public Pawn(int position) {
		super(position);
	}
	
	
	public ArrayList<Move> findMovesInDirection(MoveDirection moveDirection, Tile[][] board, int currentRow, int currentColumn) {
		
		ArrayList<Move> moves = new ArrayList<>();
		Tile target = findTarget(moveDirection, board, currentRow, currentColumn, 1);
		
		if(target.getState() == Tile.State.EMPTY)
			moves.add(new Move(getPosition(), target.getIndex()));
		return moves;
	}
	
	public ArrayList<Move> findTakesInDirection(MoveDirection moveDirection, Tile[][] board, int row, int column) {
		
		ArrayList<Move> moves = new ArrayList<>();
		
		Tile target = findTarget(moveDirection, board, row, column, 2);
		Tile possibleTake = findTarget(moveDirection, board, row, column, 1);
		
		if(isTakePossible(target, possibleTake, row, column))
				moves.add(new Move(getPosition(), target.getIndex(), possibleTake.getIndex()));
		return moves;
	}
	
	
	public boolean isTakePossible(Tile target, Tile possibleTake, int row, int column) {
		return isTileOccupiedByOppositeColor(possibleTake) && 
				   target.getState() == Tile.State.EMPTY ? true : false;
	}

}
