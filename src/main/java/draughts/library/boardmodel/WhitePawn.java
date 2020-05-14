package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.Move;

public class WhitePawn extends Pawn {

	public WhitePawn(int position) {
		super(position);
	}
	
	public void hop(Tile dst) {
		super.hop(dst);
		dst.setState(Tile.State.WHITE_PAWN);
	}
	
	public ArrayList<Move> findMoves(Tile[][] board, int currentRow, int currentColumn) {
		
		ArrayList<Move> moves = new ArrayList<>();
				
		if(currentRow>1 && currentColumn>1) 
			addMovesIfAny(moves, findMovesInDirection(MoveDirection.UP_LEFT, board, currentRow, currentColumn));
		if(currentRow>1 && currentColumn<10) 
			addMovesIfAny(moves, findMovesInDirection(MoveDirection.UP_RIGHT, board, currentRow, currentColumn));
		return moves;
	}
	
	
	public boolean isTileOccupiedBySameColor(Tile tile) {
		return (tile.getState() == Tile.State.WHITE_PAWN || 
				tile.getState() == Tile.State.WHITE_QUEEN ? true : false);
	}
	
	public boolean isTileOccupiedByOppositeColor(Tile tile) {
		return (tile.getState() == Tile.State.BLACK_PAWN || 
				tile.getState() == Tile.State.BLACK_QUEEN ? true : false);
	}

	
}
