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
	

	
	
	public Move findLeftMove(Tile[][] board, int row, int column) {
		Tile target = board[row-1-1][column-1-1];
		
		if(target.getState() == Tile.State.EMPTY)
			return new Move(getPosition(), target.getIndex());
		else return null;
			
	}
	
	public Move findRightMove(Tile[][] board, int row, int column) {	
		Tile target = board[row-1-1][column-1+1];
		
		if(target.getState() == Tile.State.EMPTY)
			return new Move(getPosition(), target.getIndex());
		else return null;
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
