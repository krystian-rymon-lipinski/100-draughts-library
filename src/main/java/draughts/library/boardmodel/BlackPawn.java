package draughts.library.boardmodel;

public class BlackPawn extends Pawn {

	public BlackPawn(int position) {
		super(position);
	}
	
	public void move(Tile src, Tile dst) {
		super.move(src,  dst);
		dst.setState(Tile.State.BLACK_PAWN);
	}

}
