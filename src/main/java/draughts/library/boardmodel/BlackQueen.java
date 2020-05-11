package draughts.library.boardmodel;

public class BlackQueen extends Queen {

	public BlackQueen(int position) {
		super(position);
	}
	
	public void hop(Tile dst) {
		super.hop(dst);
		dst.setState(Tile.State.BLACK_QUEEN);
	}

}
