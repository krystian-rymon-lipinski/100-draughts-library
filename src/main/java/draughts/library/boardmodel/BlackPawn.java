package draughts.library.boardmodel;

import draughts.library.Move;

public class BlackPawn extends Pawn {

	public BlackPawn(int position) {
		super(position);
	}
	
	public void hop(Tile dst) {
		super.hop(dst);
		dst.setState(Tile.State.BLACK_PAWN);
	}
	
	public Move findLeftMove(Tile[][] board, int row, int column) {
		Tile possibleDestination = board[row-1+1][column-1-1];
		
		if(possibleDestination.getState() == Tile.State.EMPTY)
			return new Move(getPosition(), Tile.calculateIndex(row+1, column-1));
		else return null;
			
	}
	
	public Move findRightMove(Tile[][] board, int row, int column) {	
		Tile target = board[row-1+1][column-1+1];
		
		if(target.getState() == Tile.State.EMPTY)
			return new Move(getPosition(), Tile.calculateIndex(row+1, column+1));
		else return null;
	}
	
	public boolean isTileTakenByOppositeColor(Tile.State state) {
		return (state == Tile.State.WHITE_PAWN || state == Tile.State.WHITE_QUEEN) ? true : false;
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
