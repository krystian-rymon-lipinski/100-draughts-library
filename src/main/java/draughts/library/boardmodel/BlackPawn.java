package draughts.library.boardmodel;

public class BlackPawn extends Pawn {

	public BlackPawn(int position) {
		super(position);
	}
	
	public void hop(Tile dst) {
		super.hop(dst);
		dst.setState(Tile.State.BLACK_PAWN);
	}

}
