package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.Move;

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
	
	public void hop(Tile dst) {
		position = dst.getIndex();
	}
	
	public abstract ArrayList<Move> findMoves(Tile[][] board, Tile currentPosition);
	
	public abstract ArrayList<Move> findTakes(Tile[][] board, Tile currentPosition);
	

}
