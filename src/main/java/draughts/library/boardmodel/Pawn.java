package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.Move;

public abstract class Pawn extends Piece {

	public Pawn(int position) {
		super(position);
	}
	
	public abstract Move findLeftMove(Tile[][] board, int row, int column);
	
	public abstract Move findRightMove(Tile[][] board, int row, int column);
	
	public ArrayList<Move> findMoves(Tile[][] board, int row, int column) {
	
		ArrayList<Move> moves = new ArrayList<Move>();
	
		if(column > 1)
			addMoveIfNotNull(moves, findLeftMove(board, row, column));
		if(column < 10) 
			addMoveIfNotNull(moves, findRightMove(board, row, column));
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
