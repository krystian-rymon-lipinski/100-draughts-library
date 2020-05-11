package draughts.library.boardmodel;

public class WhitePawn extends Pawn {

	public WhitePawn(int position) {
		super(position);
	}
	
	public void hop(Tile dst) {
		super.hop(dst);
		dst.setState(Tile.State.WHITE_PAWN);
	}

}
