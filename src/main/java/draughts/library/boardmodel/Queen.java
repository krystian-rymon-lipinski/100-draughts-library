package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.Move;

public abstract class Queen extends Piece {

	public Queen(int position) {
		super(position);
	}
	
	public ArrayList<Move> findMoves(Tile[][] board, Tile currentPosition) {
		
		int row = currentPosition.getRow();
		int column = currentPosition.getColumn();
		ArrayList<Move> moves = new ArrayList<>();
		
		ArrayList<Move> upLeftMoves = new ArrayList<>();
		ArrayList<Move> upRightMoves = new ArrayList<>();
		ArrayList<Move> downLeftMoves = new ArrayList<>();
		ArrayList<Move> downRightMoves = new ArrayList<>();
		
		if(row>1 && column>1) upLeftMoves = findUpLeftMoves(board, row, column);
		if(row>1 && column<10) upRightMoves = findUpRightMoves(board, row, column);
		if(row<10 && column>1) downLeftMoves = findDownLeftMoves(board, row, column);
		if(row<10 && column<10) downRightMoves = findDownRightMoves(board, row, column);
		
		if(upLeftMoves.size() > 0) moves.addAll(upLeftMoves);
		if(upRightMoves.size() > 0) moves.addAll(upRightMoves);
		if(downLeftMoves.size() > 0) moves.addAll(downLeftMoves);
		if(downRightMoves.size() > 0) moves.addAll(downRightMoves);
		
		
		return moves;
	}
	
	public ArrayList<Move> findUpLeftMoves(Tile[][] board, int row, int column) {
		ArrayList<Move> upLeftMoves = new ArrayList<>();
		int moveToCheck = 1;
		
		while(row-moveToCheck > 0 && column-moveToCheck > 0) {
			Tile target = board[row-1-moveToCheck][column-1-moveToCheck];
			if(target.getState() == Tile.State.EMPTY) {
				upLeftMoves.add(new Move(getPosition(), target.getIndex()));
				moveToCheck++; 
			} else break;
		}
		
		return upLeftMoves;	
	}
	
	public ArrayList<Move> findUpRightMoves(Tile[][] board, int row, int column) {
		ArrayList<Move> upRightMoves = new ArrayList<>();
		int moveToCheck = 1;
		
		while(row-moveToCheck > 0 && column+moveToCheck < 11) {
			Tile target = board[row-1-moveToCheck][column-1+moveToCheck];
			if(target.getState() == Tile.State.EMPTY) {
				upRightMoves.add(new Move(getPosition(), target.getIndex()));
				moveToCheck++;
			} else break;
		}
		
		return upRightMoves;
	}
	
	public ArrayList<Move> findDownLeftMoves(Tile[][] board, int row, int column) {
		ArrayList<Move> downLeftMoves = new ArrayList<>();
		int moveToCheck = 1;
		
		while(row+moveToCheck < 11 && column-moveToCheck > 0) {
			Tile target = board[row-1+moveToCheck][column-1-moveToCheck];
			if(target.getState() == Tile.State.EMPTY) {
				downLeftMoves.add(new Move(getPosition(), target.getIndex()));
				moveToCheck++;
			} else break;
		}
		
		return downLeftMoves;
	}
	
	public ArrayList<Move> findDownRightMoves(Tile[][] board, int row, int column) {
		ArrayList<Move> downRightMoves = new ArrayList<>();
		int moveToCheck = 1;
		
		while(row+moveToCheck < 11 && column+moveToCheck < 11) {
			Tile target = board[row-1+moveToCheck][column-1+moveToCheck];
			if(target.getState() == Tile.State.EMPTY) {
				downRightMoves.add(new Move(getPosition(), target.getIndex()));
				moveToCheck++;
			} else break;
			
		}
		
		return downRightMoves;
	}
	

}
