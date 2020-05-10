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
	
	public void move(Tile src, Tile destination) {
		position = destination.getIndex();
		src.setState(Tile.State.EMPTY);
	}

}
