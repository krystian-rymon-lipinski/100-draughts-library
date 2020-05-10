package draughts.library.boardmodel;

public class BlackQueen extends Queen {

	public BlackQueen(int position) {
		super(position);
	}
	
	public void move(Tile dst) {
		super.move(dst);
		dst.setState(Tile.State.BLACK_QUEEN);
	}

}
