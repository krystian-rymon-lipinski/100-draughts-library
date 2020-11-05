package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

public class WhitePawn extends Pawn {

	public WhitePawn(Tile position) {
		super(position);
	}
	
	public boolean isWhite() {
		return true;
	}
	
	public void hop(Tile dst) {
		super.hop(dst);
		dst.setState(Tile.State.WHITE_PAWN);
	}
	
	public ArrayList<Move<Hop>> findAllMoves(Tile[][] board) {
		ArrayList<Move<Hop>> moves = new ArrayList<>();
		addMoveIfNotNull(moves, findMove(MoveDirection.UP_LEFT, board));
		addMoveIfNotNull(moves, findMove(MoveDirection.UP_RIGHT, board));
		return moves;
	}

	public Move<Hop> findMove(MoveDirection moveDirection, Tile[][] board) {
		switch (moveDirection) {
			case UP_LEFT:
				if (position.getColumn() > 1) return findMoveInDirection(MoveDirection.UP_LEFT, board);
				else 					      return null;
			case UP_RIGHT:
				if (position.getColumn() < 10) return findMoveInDirection(MoveDirection.UP_RIGHT, board);
				else 						   return null;
			default:
				return null;
		}
	}

	public boolean isTileOccupiedBySameColor(Tile tile) {
		return (tile.getState() == Tile.State.WHITE_PAWN || 
				tile.getState() == Tile.State.WHITE_QUEEN);
	}
	
	public boolean isTileOccupiedByOppositeColor(Tile tile) {
		return (tile.getState() == Tile.State.BLACK_PAWN || 
				tile.getState() == Tile.State.BLACK_QUEEN);
	}

	
}
