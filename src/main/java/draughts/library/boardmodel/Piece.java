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
	
	public abstract boolean isTileOccupiedBySameColor(Tile tile);
	
	public abstract boolean isTileOccupiedByOppositeColor(Tile tile);
	
	public abstract ArrayList<Move> findMoves(Tile[][] board, Tile currentPosition);
	/*
	public ArrayList<Move> findTakes(Tile[][] board, Tile currentPosition) {
		int row = currentPosition.getRow();
		int column = currentPosition.getColumn();
		ArrayList<Move> moves = new ArrayList<>();
		Move move = null;
		
		if(column > 2 && row > 2) move = findUpLeftTakes(board, row, column);
		if(move != null) moves.add(move);
		move = null;
		if(column < 9 && row > 2) move = findUpRightTakes(board, row, column);
		if(move != null) moves.add(move);
		move = null;
		if(column > 2 && row < 9) move = findDownLeftTakes(board, row, column);
		if(move != null) moves.add(move);
		move = null;
		if(column < 9 && row < 9) move = findDownRightTakes(board, row, column);
		if(move != null) moves.add(move);
		move = null;
		
		return moves;
	}
	
	*/
	
	public abstract ArrayList<Move> findTakes(Tile[][] board, Tile currentPosition);
	
	public abstract ArrayList<Move> findUpLeftTakes(Tile board[][], int row, int column);
	
	public abstract ArrayList<Move> findUpRightTakes(Tile board[][], int row, int column);
	
	public abstract ArrayList<Move> findDownLeftTakes(Tile board[][], int row, int column);
	
	public abstract ArrayList<Move> findDownRightTakes(Tile board[][], int row, int column);
	
}
