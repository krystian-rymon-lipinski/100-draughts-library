package draughts.library.boardmodel;

public class WhitePawn extends Pawn {

	public WhitePawn(int position) {
		super(position);
	}
	
	public void move(Tile dst) {
		super.move(dst);
		dst.setState(Tile.State.WHITE_PAWN);
	}

}
