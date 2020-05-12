package draughts.library.boardmodel;

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
	
	public boolean isTileTakenByOppositeColor(Tile.State state) {
		return (state == Tile.State.BLACK_PAWN || state == Tile.State.BLACK_QUEEN) ? true : false;
	}
	
}
