package draughts.library.boardmodel;

public class WhiteQueen extends Queen {

	public WhiteQueen(int position) {
		super(position);
	}
	
	public void move(Tile src, Tile dst) {
		super.move(src,  dst);
		dst.setState(Tile.State.WHITE_QUEEN);
	}

}
