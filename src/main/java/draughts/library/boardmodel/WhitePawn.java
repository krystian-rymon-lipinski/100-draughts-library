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
		if(board[row-1-1][column-1-1].getState() == Tile.State.EMPTY)
			return new Move(getPosition(), Tile.calculateIndex(row-1, column-1));
		else return null;
			
	}
	
	public Move findRightMove(Tile[][] board, int row, int column) {	
		if(board[row-1-1][column-1+1].getState() == Tile.State.EMPTY)
			return new Move(getPosition(), Tile.calculateIndex(row-1, column+1));
		else return null;
	}
	
}
