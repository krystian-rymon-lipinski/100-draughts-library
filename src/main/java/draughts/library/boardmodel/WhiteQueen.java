package draughts.library.boardmodel;

public class WhiteQueen extends Queen {

	public WhiteQueen(int position) {
		super(position);
	}
	
	public void hop(Tile dst) {
		super.hop(dst);
		dst.setState(Tile.State.WHITE_QUEEN);
	}
	
	public void reverseHop(Tile src) {
		super.reverseHop(src);
		src.setState(Tile.State.WHITE_QUEEN);
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
