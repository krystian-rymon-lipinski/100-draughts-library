package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.Hop;
import draughts.library.Move;

public class BlackPawn extends Pawn {

	public BlackPawn(int position) {
		super(position);
	}
	
	public boolean isWhite() {
		return false;
	}
	
	public void hop(Tile dst) {
		super.hop(dst);
		dst.setState(Tile.State.BLACK_PAWN);
	}
	
	public void reverseHop(Tile src) {
		super.reverseHop(src);
		src.setState(Tile.State.BLACK_PAWN);
	}

	public ArrayList<Move<Hop>> findMoves(Tile[][] board, int currentRow, int currentColumn) {
		
		ArrayList<Move<Hop>> moves = new ArrayList<>();
				
		if(currentRow>1 && currentColumn>1) 
			addMovesIfAny(moves, findMovesInDirection(MoveDirection.DOWN_LEFT, board, currentRow, currentColumn));
		if(currentRow>1 && currentColumn<10) 
			addMovesIfAny(moves, findMovesInDirection(MoveDirection.DOWN_RIGHT, board, currentRow, currentColumn));
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
