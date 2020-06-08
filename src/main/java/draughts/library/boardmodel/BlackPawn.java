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
				
		if(position.getRow()<10 && position.getColumn()>1) 
			addMovesIfAny(moves, findMovesInDirection(MoveDirection.DOWN_LEFT, board));
		if(position.getRow()<10 && position.getColumn()<10) 
			addMovesIfAny(moves, findMovesInDirection(MoveDirection.DOWN_RIGHT, board));
		return moves;
	}
	
	public boolean isTileOccupiedBySameColor(Tile tile) {
		return (tile.getState() == Tile.State.BLACK_PAWN || 
				tile.getState() == Tile.State.BLACK_QUEEN ? true : false);
	}
	
	public boolean isTileOccupiedByOppositeColor(Tile tile) {
		return (tile.getState() == Tile.State.WHITE_PAWN || 
				tile.getState() == Tile.State.WHITE_QUEEN ? true : false);
	}

}
