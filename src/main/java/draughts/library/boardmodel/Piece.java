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
	
	public abstract ArrayList<Move> findMoves(Tile[][] board, int currentRow, int currentColumn);
	
	public abstract ArrayList<Move> findTakesInDirection(MoveDirection moveDirection, Tile[][] board, int row, int column);


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
			
	public ArrayList<Move> findTakes(Tile[][] board, int currentRow, int currentColumn) {
		
		ArrayList<Move> moves = new ArrayList<>();
		
		if(currentColumn > 2 && currentRow > 2) 
			addMovesIfAny(moves, findTakesInDirection(MoveDirection.UP_LEFT, board, currentRow, currentColumn));
		if(currentColumn < 9 && currentRow > 2) 
			addMovesIfAny(moves, findTakesInDirection(MoveDirection.UP_RIGHT, board, currentRow, currentColumn));
		if(currentColumn > 2 && currentRow < 9) 
			addMovesIfAny(moves, findTakesInDirection(MoveDirection.DOWN_LEFT, board, currentRow, currentColumn));
		if(currentColumn < 9 && currentRow < 9) 
			addMovesIfAny(moves, findTakesInDirection(MoveDirection.DOWN_RIGHT, board, currentRow, currentColumn));		
		
		return moves;
	}
	
	public Tile findTarget(MoveDirection moveDirection, Tile[][] board, int row, int column, int distanceInTiles) {
		
		switch(moveDirection) {
			case UP_LEFT:
				return board[row-1-distanceInTiles][column-1-distanceInTiles];
			case UP_RIGHT:
				return board[row-1-distanceInTiles][column-1+distanceInTiles];
			case DOWN_LEFT:
				return board[row-1+distanceInTiles][column-1-distanceInTiles];
			case DOWN_RIGHT:
				return board[row-1+distanceInTiles][column-1+distanceInTiles];
			default:
				break;
		}
		return null;
	}
	
	public enum MoveDirection {
		
		UP_LEFT,
		UP_RIGHT,
		DOWN_LEFT,
		DOWN_RIGHT;
	}
		
	
	
}
