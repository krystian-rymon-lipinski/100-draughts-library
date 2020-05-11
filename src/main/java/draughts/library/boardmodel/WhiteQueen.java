package draughts.library.boardmodel;

public class WhiteQueen extends Queen {

	public WhiteQueen(int position) {
		super(position);
	}
	
	public void hop(Tile dst) {
		super.hop(dst);
		dst.setState(Tile.State.WHITE_QUEEN);
	}

}
