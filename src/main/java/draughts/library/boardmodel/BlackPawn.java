package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

public class BlackPawn extends Pawn {

	public BlackPawn(Tile position) {
		super(position);
	}
	
	public boolean isWhite() {
		return false;
	}
	
	public void hop(Tile dst) {
		super.hop(dst);
		dst.setState(Tile.State.BLACK_PAWN);
	}

	public ArrayList<Move<Hop>> findMoves(Tile[][] board) {
		
		ArrayList<Move<Hop>> moves = new ArrayList<>();

		addMoveIfNotNull(moves, findMove(MoveDirection.DOWN_LEFT, board));
		addMoveIfNotNull(moves, findMove(MoveDirection.DOWN_RIGHT, board));

		return moves;
	}

	public Move<Hop> findMove(MoveDirection moveDirection, Tile[][] board) {
		switch (moveDirection) {
			case DOWN_LEFT:
				if (position.getColumn() > 1) return findMoveInDirection(MoveDirection.DOWN_LEFT, board);
				else 						  return null;
			case DOWN_RIGHT:
				if (position.getColumn() < 10) return findMoveInDirection(MoveDirection.DOWN_RIGHT, board);
				else 						   return null;
			default:
				return null;
		}
	}
	
	public boolean isTileOccupiedBySameColor(Tile tile) {
		return (tile.getState() == Tile.State.BLACK_PAWN || 
				tile.getState() == Tile.State.BLACK_QUEEN);
	}
	
	public boolean isTileOccupiedByOppositeColor(Tile tile) {
		return (tile.getState() == Tile.State.WHITE_PAWN || 
				tile.getState() == Tile.State.WHITE_QUEEN);
	}

}
