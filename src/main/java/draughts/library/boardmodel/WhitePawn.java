package draughts.library.boardmodel;

public class WhitePawn extends Pawn {

	public WhitePawn(int position) {
		super(position);
	}
	
	public void move(Tile src, Tile dst) {
		super.move(src,  dst);
		dst.setState(Tile.State.WHITE_PAWN);
	}

}
