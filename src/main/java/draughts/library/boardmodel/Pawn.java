package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.Move;

public abstract class Pawn extends Piece {

	public Pawn(int position) {
		super(position);
	}
	
	public ArrayList<Move> findMoves(Tile[][] board, Tile currentPosition) {
		int row = currentPosition.getRow();
		int column = currentPosition.getColumn();
		ArrayList<Move> moves = new ArrayList<Move>();
		Move move = null;
		
		if(!currentPosition.isTileMostLeftInRow()) 
			move = findLeftMove(board, row, column);
			if(move != null) moves.add(move);
			move = null;
		if(!currentPosition.isTileMostRightInRow()) 
			move = findRightMove(board, row, column);
			if(move != null) moves.add(move);
			move = null;
		return moves;
	}
	
	public abstract Move findLeftMove(Tile[][] board, int row, int column);
	
	public abstract Move findRightMove(Tile[][] board, int row, int column); 
	
	public ArrayList<Move> findTakes(Tile[][] board, Tile currentPosition) {
		return null;
	}

}
