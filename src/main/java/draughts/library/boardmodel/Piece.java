package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.Hop;
import draughts.library.Capture;
import draughts.library.Move;

public abstract class Piece {
	
	private int position;
	
	public Piece(int position) {
		this.setPosition(position);
	}
	
	public abstract boolean isTileOccupiedBySameColor(Tile tile);
	
	public abstract boolean isTileOccupiedByOppositeColor(Tile tile);
	
	public abstract ArrayList<Move<Hop>> findMoves(Tile[][] board, int currentRow, int currentColumn);
	
	public abstract ArrayList<Capture> findCapturesInDirection(MoveDirection moveDirection, Tile[][] board, int row, int column);
	
	public abstract boolean isQueen();
	
	public abstract boolean isWhite();
	

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public void hop(Tile dst) {
		position = dst.getIndex();
	}
	
	public void reverseHop(Tile src) {
		position = src.getIndex();
	}
	
	public void addMovesIfAny(ArrayList<Move<Hop>> mainList, ArrayList<Move<Hop>> candidateList) {
		if(candidateList != null && candidateList.size() > 0)
			mainList.addAll(candidateList);
	}
	
	public void addMoveIfNotNull(ArrayList<Move<Hop>> mainList, Move<Hop> candidateMove) {
		if(candidateMove != null)
			mainList.add(candidateMove);
	}
	
	public void addHopsIfAny(ArrayList<Capture> mainList, ArrayList<Capture> candidateList) {
		if(candidateList != null && candidateList.size() > 0)
			mainList.addAll(candidateList);
	}
			
	public ArrayList<Capture> findCaptures(Tile[][] board, int currentRow, int currentColumn) {
		
		ArrayList<Capture> moves = new ArrayList<>();
		
		if(currentColumn > 2 && currentRow > 2) 
			addHopsIfAny(moves, findCapturesInDirection(MoveDirection.UP_LEFT, board, currentRow, currentColumn));
		if(currentColumn < 9 && currentRow > 2) 
			addHopsIfAny(moves, findCapturesInDirection(MoveDirection.UP_RIGHT, board, currentRow, currentColumn));
		if(currentColumn > 2 && currentRow < 9) 
			addHopsIfAny(moves, findCapturesInDirection(MoveDirection.DOWN_LEFT, board, currentRow, currentColumn));
		if(currentColumn < 9 && currentRow < 9) 
			addHopsIfAny(moves, findCapturesInDirection(MoveDirection.DOWN_RIGHT, board, currentRow, currentColumn));		
		
		return moves;
	}
	
	public Tile findTarget(MoveDirection moveDirection, Tile[][] board, int row, int column, int hopLength) {
		
		switch(moveDirection) {
			case UP_LEFT:
				return board[row-1-hopLength][column-1-hopLength];
			case UP_RIGHT:
				return board[row-1-hopLength][column-1+hopLength];
			case DOWN_LEFT:
				return board[row-1+hopLength][column-1-hopLength];
			case DOWN_RIGHT:
				return board[row-1+hopLength][column-1+hopLength];
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
