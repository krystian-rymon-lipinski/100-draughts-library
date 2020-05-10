package draughts.library.boardmodel;

public class BlackQueen extends Queen {

	public BlackQueen(int position) {
		super(position);
	}
	
	public void move(Tile src, Tile dst) {
		super.move(src,  dst);
		dst.setState(Tile.State.BLACK_QUEEN);
	}

}
