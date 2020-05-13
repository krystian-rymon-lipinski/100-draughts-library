package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.Move;

public abstract class Piece {
	
	private int position;
	
	public Piece(int position) {
		this.setPosition(position);
	}
	
	public abstract boolean isTileOccupiedBySameColor(Tile tile);
	
	public abstract boolean isTileOccupiedByOppositeColor(Tile tile);

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public void hop(Tile dst) {
		position = dst.getIndex();
	}
	
	public void addMovesIfAny(ArrayList<Move> mainList, ArrayList<Move> candidateList) {
		if(candidateList != null && candidateList.size() > 0)
			mainList.addAll(candidateList);
	}
	
	public void addMoveIfNotNull(ArrayList<Move> mainList, Move candidateMove) {
		if(candidateMove != null)
			mainList.add(candidateMove);
	}
	
	public abstract ArrayList<Move> findMoves(Tile[][] board, int currentRow, int currentColumn);
	
	public ArrayList<Move> findTakes(Tile[][] board, int currentRow, int currentColumn) {
		
		ArrayList<Move> moves = new ArrayList<>();
		
		if(currentColumn > 2 && currentRow > 2) 
			addMovesIfAny(moves, findUpLeftTakes(board, currentRow, currentColumn));
		if(currentColumn < 9 && currentRow > 2) 
			addMovesIfAny(moves, findUpRightTakes(board, currentRow, currentColumn));
		if(currentColumn > 2 && currentRow < 9) 
			addMovesIfAny(moves, findDownLeftTakes(board, currentRow, currentColumn));
		if(currentColumn < 9 && currentRow < 9) 
			addMovesIfAny(moves, findDownRightTakes(board, currentRow, currentColumn));
		
		return moves;
	}
		
	public abstract ArrayList<Move> findUpLeftTakes(Tile board[][], int row, int column);
	
	public abstract ArrayList<Move> findUpRightTakes(Tile board[][], int row, int column);
	
	public abstract ArrayList<Move> findDownLeftTakes(Tile board[][], int row, int column);
	
	public abstract ArrayList<Move> findDownRightTakes(Tile board[][], int row, int column);
	
}
