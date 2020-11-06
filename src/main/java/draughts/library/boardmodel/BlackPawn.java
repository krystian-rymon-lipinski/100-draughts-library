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

	public ArrayList<Move<Hop>> findAllMoves(Tile[][] board) {
		
		ArrayList<Move<Hop>> moves = new ArrayList<>();

		addMoveIfNotNull(moves, findMove(MoveDirection.DOWN_LEFT, board));
		addMoveIfNotNull(moves, findMove(MoveDirection.DOWN_RIGHT, board));

		return moves;
	}

	public Move<Hop> findMove(MoveDirection moveDirection, Tile[][] board) {
		if (isMovePossible(moveDirection)) {
			return findMoveInDirection(moveDirection, board);
		}
		else return null;

	}

	public boolean isMovePossible(MoveDirection direction) {
		switch (direction) {
			case DOWN_LEFT:
				return position.getColumn() > 1;
			case DOWN_RIGHT:
				return position.getColumn() < 10;
			default:
				return false;
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
