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
	
	
	
	public Move findUpLeftTake(Tile[][] board, int row, int column) {
		Tile target = board[row-1-2][column-1-2];
		Tile possibleTake = board[row-1-1][column-1-1];
		
		return checkForTake(target, possibleTake, row, column);
			
	}
	
	public Move findUpRightTake(Tile board[][], int row, int column) {
		Tile target = board[row-1-2][column-1+2];
		Tile possibleTake = board[row-1-1][column-1+1];
		
		return checkForTake(target, possibleTake, row, column);
	}
	
	public Move findDownLeftTake(Tile board[][], int row, int column) {
		Tile target = board[row-1+2][column-1-2];
		Tile possibleTake = board[row-1+1][column-1-1];
		
		return checkForTake(target, possibleTake, row, column);
	}
	
	public Move findDownRightTake(Tile board[][], int row, int column) {
		Tile target = board[row-1+2][column-1+2];
		Tile possibleTake = board[row-1+1][column-1+1];
		
		return checkForTake(target, possibleTake, row, column);
	}
	
	public abstract boolean isTileTakenByOppositeColor(Tile.State state);
	
	
	public boolean isTakePossible(Tile target, Tile possibleTake, int row, int column) {
		return isTileTakenByOppositeColor(possibleTake.getState()) && 
				   target.getState() == Tile.State.EMPTY ? true : false;
	}
	
	public Move checkForTake(Tile target, Tile possibleTake, int row, int column) {
		if(isTakePossible(target, possibleTake, row, column))
			return new Move(Tile.calculateIndex(row, column), 
							    target.getIndex(), 
							    possibleTake.getIndex());
		else return null;
	}

}
