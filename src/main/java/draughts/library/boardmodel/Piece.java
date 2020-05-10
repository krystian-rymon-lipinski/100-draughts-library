package draughts.library.boardmodel;

public abstract class Piece {
	
	private int position;
	
	public Piece(int position) {
		this.setPosition(position);
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public void move(Tile dst) {
		position = dst.getIndex();
	}
	
	public void take(Tile src, Tile dst, Tile taken) {
		move(dst);
		taken.setState(Tile.State.EMPTY);
	}

}
